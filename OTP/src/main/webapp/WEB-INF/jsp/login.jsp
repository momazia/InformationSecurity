<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link href='https://fonts.googleapis.com/css?family=Roboto+Slab:400,300|Roboto:400,300' rel='stylesheet'>
<link href="https://fonts.googleapis.com/css?family=Arima+Madurai" rel="stylesheet">
<link rel="stylesheet" href="/static/css/main.css">
<script type="text/javascript" src="/static/js/jquery-2.2.4.js"></script>
</head>
<body>
	<div class="middle_of_page">
		<div class="flex_container_column">
				<div class="form_header_row">
					<h3>
						<spring:message code="title.login" />
					</h3>
				</div>
				<form:form method="POST" action="${pageContext.request.contextPath}/validateUser" commandName="loginForm">
					<div class="flex_container_column">
						<div class="form_row bottom_margin">
			                <p>
			                	<form:label path="email">
									<spring:message code="label.email" />
								</form:label>
							</p>
							<form:input path="email" cssClass="input_textbox" />
			            </div>
			            <div class="form_row bottom_margin">
			                <p>
			                	<form:label path="password">
									<spring:message code="label.password" />
								</form:label>
							</p>
							<form:password path="password" cssClass="input_textbox" />
			            </div>
		            	<!-- /login?error=true -->
						<c:if test="${param.error == 'true'}">
							<div class="form_row bottom_margin">
								<span class="error">${sessionScope["SPRING_SECURITY_LAST_EXCEPTION"].message}</span>
							</div>
						</c:if>
			            <div class="form_row">
							<span class="submit_button_full_width" onclick="$(this).closest('form').submit()">
								<spring:message code="button.login" />
							</span>
						</div>
					</div>
				</form:form>
		</div>
	</div>
</body>
</html>