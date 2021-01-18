<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="sForm" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sTag" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html>
	<head>
		<%@include file="include/Head.jsp" %>
		<link href="css/Common.css" rel="stylesheet" type="text/css">
		<title>
			<c:choose>
				<c:when test="${opearion eq 'add'}">
					<sTag:message code="label_title_add_human_page"/>
				</c:when>
				<c:otherwise>
					<sTag:message code="label_title_update_human_page"/>
				</c:otherwise>
			</c:choose>
		</title>
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
						<input type="hidden" id="operation" name="operation" value="${operation}">
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
						<button type="submit" class="btn btn-primary btn-block">
							<c:choose>
								<c:when test="${operation eq 'add'}">Submit</c:when>
								<c:otherwise>Update</c:otherwise>
							</c:choose>
						</button>
					</div>
				</div>
			</sForm:form>
		</div>
	</body>
</html>