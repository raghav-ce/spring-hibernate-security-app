<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>
<head>
<title>Frozen Soul Gaming</title>
</head>
<body>
	<h2>Frozen Soul Gaming - Yeah!!!</h2>
	<hr>
	<p>
	Welcome To Home Page!!!
	</p>
	<p>
	User:<security:authentication property="principal.username"/>
	Roles:<security:authentication property="principal.authorities"/>
	</p>
	<hr>
	<!-- Add link to Leaders -->
	<security:authorize access="hasRole('MANAGER')">
	<p>
	<a href="${pageContext.request.contextPath}/leaders">Leadership Meeting</a>
	<hr>
	</p>
	</security:authorize>
	
	<!-- Add link to systems -->
	<security:authorize access="hasRole('ADMIN')">
	<p>
	<a href="${pageContext.request.contextPath}/systems">Admin Meeting</a>
	<hr>
	</p>
	</security:authorize>
	
	<!-- Add logout button -->
	<form:form action="${pageContext.request.contextPath}/logout" method="POST">
		<input type="submit" value="Logout"/>
	</form:form>
</body>
</html>