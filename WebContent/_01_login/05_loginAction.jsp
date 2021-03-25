<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>05_loginAction</title>
</head>
<body>

	<c:choose>
		<c:when test="${ isLogin eq true }">
			<script type="text/javascript">
				alert("${ sessionScope.menuId }님 환영합니다!");
				location.href="main.do";
			</script>
		</c:when>
		
		<c:otherwise>
			<script type="text/javascript">
				alert("아이디와 비밀번호를 확인하세요.");
				history.go(-1);
			</script>
		</c:otherwise>
	</c:choose>

</body>
</html>