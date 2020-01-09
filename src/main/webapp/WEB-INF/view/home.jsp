<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<%--
  Created by IntelliJ IDEA.
  User: heavy
  Date: 04.01.2020
  Time: 19:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Home page</title>
</head>
<body>
    <h2>Home Page</h2>
    <hr>
    <p>Welcome Home Page!</p>

    <hr>
    <p>
    User: <security:authentication property="principal.username"/>
    <br><br>
    Role(s): <security:authentication property="principal.authorities"/>
    </p>

    <hr>
    <p><a href="${pageContext.request.contextPath}/leaders">LeaderShip Meeting</a> (only for managers)</p>
    <p><a href="${pageContext.request.contextPath}/systems">IT Systems Meeting</a> (only for admins)</p>

<hr>

    <form:form action="${pageContext.request.contextPath}/logout" method="POST">
        <input type="submit" value="Logout"/>
    </form:form>

</body>
</html>
