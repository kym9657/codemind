CODEMIND GIT 연동 테스트

20251103 젠킨스 연동 테스트

1. git hub Token 생성
setting - personal access tokens - Tokens (classic) - Generate new token - Generate new token (classic)

scopes : repo, admin:org, admin:repo_hook

Token 생성

2. Jenkins Credentials 설정
Setting - Credentials - Domains > global - Add Credential

Username with Password - Username : GIT ID, Password : Token 입력 후 생성

3. Git hub Webhooks 생성
Repo Setting - Webhooks - Add Webhook

Payload URL 
http://121.65.25.90:8030/github-webhook/

Web Hook 생성

4. Jenkins Pipelins 생성
"Github project" 체크
"Github hook trigger for GITScm polling" 체크

Pipeline script from SCM

SCM : GIT
Repository URL : git 주소
Credential 설정 : 생성한 Credntial

Brandches to build 설정 : */main

Script Path : Repo 에 있는 스크립트 경로
