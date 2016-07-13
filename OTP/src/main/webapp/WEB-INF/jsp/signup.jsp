<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link href='https://fonts.googleapis.com/css?family=Roboto+Slab:400,300|Roboto:400,300' rel='stylesheet'>
<link href="https://fonts.googleapis.com/css?family=Arima+Madurai" rel="stylesheet">
<link rel="stylesheet" href="/static/css/main.css">
</head>
<body>
	<div class="middle_of_page">
		<spring:message code="text.welcome" />
		<div class="flex_container_row index_action_btns">
			<a href="/login/render"><spring:message code="text.login" /></a> 
			<a href="/signup/render"><spring:message code="text.signup" /></a>
		</div>
	</div>
</body>
</html>