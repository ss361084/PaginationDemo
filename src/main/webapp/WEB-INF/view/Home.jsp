<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
	<head>
		<%@include file="include/Head.jsp" %>
		<title>Home</title>
		<link href="css/Common.css" rel="stylesheet" type="text/css">
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
			<div class="row">
				<div class="col-4"></div>
				<div class="col-4">
					<a href="${pageContext.request.contextPath}/showaddhuman" class="btn btn-primary btn-block">Add Student</a>					
				</div>
				<div class="col-4"></div>
			</div>
			<div>
				<c:choose>
					<c:when test="${not empty humanList}">
						<table class="table">
							<tr>
								<th>Name</th>
								<th>Cast</th>
								<th>Action</th>
							</tr>
							<c:forEach items="${humanList}" var="human">
								<tr>
									<td>${human.name}</td>
									<td>${human.cast}</td>
									<td>
										<a href="${pageContext.request.contextPath}/deletehuman/${human.humanId}" class="btn btn-primary">Delete</a>
										<a href="${pageContext.request.contextPath}/updatehuman/${human.humanId}" class="btn btn-primary">Update</a>
									</td>
								</tr>
							</c:forEach>
						</table>
					</c:when>
					<c:otherwise>
						<h3 class="col-md-4 offset-4 mt-4">No Record Found.</h3>
					</c:otherwise>
				</c:choose>
			</div>
		</div>
	</body>
</html>