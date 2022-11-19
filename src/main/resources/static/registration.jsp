<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html>
    <head>
        <title>Spring Security Example </title>
    </head>
    <body>
        
        <div>
            <form:form method="POST" modelAttribute="userForm">
                <h2>Create your account</h2>
                <spring:bind path="usrname">
                    <div>
                        <form:input type="text" path="username" placeholder="username" autofocus="true"></form:input>
                        <form:errors path="username"></form:errors>
                    </div>
                </spring:bind>

                <spring:bind path="password">
                    <div>
                        <form:input type="password" path="password" placeholder="Password"></form:input>
                        <form:errors path="password"></form:errors>
                    </div>
                </spring:bind>

                <spring:bind path="passwordConfirm">
                    <div>
                        <form:input type="password" path="passwordConfirm" placeholder="Confirm your password"></form:input>
                        <form:errors path="passwordConfirm"></form:errors>
                    </div>
                </spring:bind>

                <button type="submit">Submit</button>
            </form:form>
        </div>

    </body>
</html>
