<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
	<head>
		<%@include file="include/Head.jsp" %>
		<link href="css/Common.css" rel="stylesheet" type="text/css">
		<title>Add Human</title>
	</head>
	<body>
		<div class="container-fluid">
			<div>
				<c:if test="${not empty successMsg}">
					<h3 class="successClass">${successMsg}</h3>
				</c:if>
				<c:if test="${not empty errMsg}">
					<h3 class="errClass">${errMsg}</h3>
				</c:if>
			</div>
			<sForm:form action="${pageContext.request.contextPath}/addhuman" method="post" modelAttribute="human">
				<div>
					<div class="form-group">
						<sForm:hidden path="humanId"/>
						<label>Enter Name :: </label>
						<sForm:input class="form-control" path="name"/>
						<sForm:errors class="errClass" path="name"></sForm:errors>
					</div>
					<div class="form-group">
						<label>Enter Cast :: </label>
						<sForm:input class="form-control" path="cast"/>
						<sForm:errors class="errClass" path="cast"></sForm:errors>
					</div>
					<div class="form-group">
						<button type="submit" class="btn btn-primary btn-block">Submit</button>
					</div>
				</div>
			</sForm:form>
		</div>
	</body>
</html>