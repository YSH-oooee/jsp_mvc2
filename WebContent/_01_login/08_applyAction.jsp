<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>08_applyAction</title>
</head>
<body>
	<!-- applyAction.do에서 입사지원정보를 DB에 저장 완료하면
		 메세지창 출력 후, 메인으로 돌아감 -->
	<script>
		alert('입사지원을 완료하였습니다.');
		location.href='main.do';
	</script>
</body>
</html>