import groovy.json.JsonSlurper

//---------------------------------------------------
// Base Jenkines Pipeline Script for Codemind v4.5.8
//---------------------------------------------------
// 해당 script는 기본적인 세팅이 되어있는 프로젝트가 만들어졌다는 전제로 진행됩니다.
// 프로젝트 설정은 소스 경로 지정 : git / 품질 조건 설정. 이외에는 자율적으로 설정하시면 됩니다.
// 코드마인드 로그인 시 사용하는 id, password는 Jenkinds Credential을 이용하여 설정해주시고
// codemind 접근 url, 프로젝트 식별자, 브랜치 이름, 커밋 id를 파라미터로 지정하여 실행합니다.

pipeline {
    agent any

    environment {
        //CODEMIND_BASE_URL = "https://${env.CODEMIND_HOST}"
		CODEMIND_BASE_URL = "https://121.65.25.90:10443"
		
    }

    parameters {
        string(name: 'PROJECT_NAME', description: '프로젝트 식별자를 입력하세요.')
        string(name: 'BRANCH_NAME', description: 'BRANCH NAME을 입력하세요.')
        string(name: 'COMMIT_ID', description: '분석을 시작할 commit id를 입력하세요.')
    }

    stages {
        stage('Login') {
            steps {
                script {
                    try {
                        withCredentials([usernamePassword(credentialsId: 'codemind-base-auth', usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD')]) {
                            def loginRes = sh(
                                script: """
                                    curl -c cookie -o /dev/null -s -w "%{http_code}" -d 'username=${USERNAME}&password=${PASSWORD}&REQUEST_KIND=API' ${env.CODEMIND_BASE_URL}/user/login/process -k
                                """,
                                returnStdout: true
                            ).trim()

                            echo "Authentication response from Codemind server: ${loginRes}"

                            if (loginRes != "200") {
                                echo "LOGIN_FAIL: Unable to authenticate with Codemind server."
                                error "LOGIN_FAIL: Unable to authenticate with Codemind server."
                            }
                        }
                    } catch (Exception e) {
                        error "An error occurred during Codemind login: ${e.getMessage()}"
                    }
                }
            }
        }

        stage('Check Valid Project') {
            steps {
                script {
                    try {
                        def validProjectRes = sh(
                            script: """
                                curl -b cookie -H 'Accept:application/json' -o /dev/null -s -w "%{http_code}" '${env.CODEMIND_BASE_URL}/api/project/${PROJECT_NAME}/info' -k
                            """,
                            returnStdout: true
                        ).trim()

                        if (validProjectRes == '200') {
                            echo "Project(${PROJECT_NAME}) is valid"
                        } else {
                            echo "Project(${PROJECT_NAME}) not valid"
                        }
                    } catch (Exception e) {
                        error "An error occurred while creating the project: ${e.getMessage()}"
                    }
                }
            }
        }

        stage('Code Analysis Request') {
            steps {
                script {
                    def queryParams = []
                    queryParams << "checkDuplicatedAnalysis=false"
                    queryParams << "checkoutMode=HEAD"

                    if (COMMIT_ID) {
                        queryParams << "revision=${COMMIT_ID}"
                    }
                    if (BRANCH_NAME) {
                        queryParams << "branch=${BRANCH_NAME}"
                    }
                    def queryString = queryParams ? "?" + queryParams.join("&") : ""

                    def startScript = """
                        curl -o /dev/null -s -w '%{http_code}' -b cookie -X POST '${env.CODEMIND_BASE_URL}/api/analysis/${PROJECT_NAME}${queryString}' -k
                    """
                    
                    try {
                        def statusCode = sh(
                            script: startScript, 
                            returnStdout: true
                        ).trim()

                        if (statusCode == "200") {
                            echo "Response from starting analysis project (${PROJECT_NAME}) was successful."
                        } else {
                            echo "Failed to start analysis project (${PROJECT_NAME}). Status code: ${statusCode}"
                            error "Failed to start analysis project (${PROJECT_NAME}). Status code: ${statusCode}"
                        }
                    } catch (Exception e) {
                        error "An error occurred while starting analysis project (${PROJECT_NAME}): ${e.getMessage()}"
                    }    
                        
                    sleep(5)
                }
            }
        }

        stage('Monitor Analysis Progress') {
            steps {
                timeout(time: 30, unit: 'MINUTES') {
                    script {
                        def statusUrl = """
                            curl -b cookie -H 'Accept:application/json' ${env.CODEMIND_BASE_URL}/api/project/${PROJECT_NAME}/status -k
                        """
					
                        while (true) {
                            try {
                                def data = sh(
                                    script: statusUrl, 
                                    returnStdout: true
                                ).trim()

                                def jsonResponse = new JsonSlurper().parseText(data)
                                def status = jsonResponse.status
                                env.SEQUENCE = jsonResponse.sequence

                                def now = new Date()
                                echo "[${now.format('yyyy-MM-dd HH:mm:ss', TimeZone.getTimeZone('Asia/Seoul'))}] Received from Codemind, received status(${status}) ==="

                                if (status == 'success') {
                                    echo "======================= Codemind project(${PROJECT_NAME}) analysis succeeded =================="
                                    break
                                } else if (status == 'stop') {
                                    echo "================ Codemind project(${PROJECT_NAME}) analysis stopped ==============="
                                    echo "Analysis Stopped for project ${PROJECT_NAME}"
                                    error "Analysis Stopped for project ${PROJECT_NAME}"
                                    break
                                } else if (status == 'fail') {
                                    echo "================ Codemind project(${PROJECT_NAME}) analysis failed ==============="
                                    echo "Analysis failed for project ${PROJECT_NAME}"
                                    error "Analysis failed for project ${PROJECT_NAME}"
                                    break
                                } else if (status == 'reserved') {
                                    echo "======================= Codemind project(${PROJECT_NAME}) reserved... =================="
                                } else {
                                    echo "======================= Codemind project(${PROJECT_NAME}) analyzing... =================="
                                }
                            } catch (Exception e) {
                                error "An error occurred while checking analysis status for project ${PROJECT_NAME}: ${e.getMessage()}"
                            }

                            sleep(5)
                        }
                    }
                }
            }
        }

        stage('Analysis Quality Results') {
            steps {
                script {
                    try {
                        def response = sh(
                            script: """
                                curl -b cookie -H 'Accept:application/json' '${env.CODEMIND_BASE_URL}/api/project/${PROJECT_NAME}/check-quality-result?sequence=${env.SEQUENCE}' -k
                            """,
                            returnStdout: true
                        ).trim()
                        echo "Quality analysis results for project (${PROJECT_NAME}): ${response}"
                        def json = new JsonSlurper().parseText(response)

                        if (json.result != true) {
                            echo "Code quality check failed (weakness detected: ${json.weaknessCount})"
                            currentBuild.result = 'FAILURE'
                        } else {
                            echo "Code quality check passed"
                            currentBuild.result = 'SUCCESS'
                        }
                    } catch (Exception e) {
                        error "An error occurred while retrieving quality analysis results for project (${PROJECT_NAME}): ${e.getMessage()}"
                    }
                }
            }
        }
    }
}
