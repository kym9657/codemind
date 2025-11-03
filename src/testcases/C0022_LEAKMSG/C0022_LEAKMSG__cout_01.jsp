<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>error</title>
</head>
<body>
<div class="box_con">
이용에 불편을 드려 죄송합니다.<br/>현재 서비스되는 페이지인지 다시 한번 확인해<br/>
주시기 바랍니다.
<!-- // flaw: -->
error 메시지: <c:out value="${exception.message}"/>
<pre>${exception.printStackTrace(pageContext.response.writer)}</pre>
</div>
</body>
</html>