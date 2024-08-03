<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<style>
/* Optional CSS for custom styling */
.form-container {
	max-width: 500px;
	margin: 0 auto;
	padding: 20px;
}

.error-message {
	color: red;
	font-size: 14px;
	margin-top: 5px;
}
</style>
<!DOCTYPE html>
<html lang="en">
<c:import url="/WEB-INF/fragments/header.jsp" />
<body class="hold-transition sidebar-mini layout-fixed" />
<div class="wrapper">
	<c:import url="/WEB-INF/fragments/menus.jsp" />
	<div class="container">
		<div class="row justify-content-center">
			<div class="col-md-8" style="margin-top: 20px; margin-left: 80px;">
				<div class="card">
					<div class="card-body">
						<c:if test="${ClassesUser  == null}">
							<form action="<%=request.getContextPath()%>/ClassesUserController?action=/insertUCL" method="post">
						</c:if>

						<caption>
							<h2>
								<c:if test="${ClassesUser == null}">
               					Add New Class User
              				</c:if>
							</h2>
						</caption>

						'
						<!-- <c:if test="${Classes != null}">
							<input type="hidden" name="id"
								value="<c:out value='${Classes.id}' />" />
						</c:if> -->

						<fieldset class="form-group">
							<label>User ID</label> <select class="form-control"
								name="id_user">
								<option value="">Select User</option>
								<c:forEach var="user" items="${listUser}">
									<option value="${user.id}">${user.id}</option>
								</c:forEach>
							</select> <span class="text-danger">${requestScope.userId_error}</span>
						</fieldset>

						<fieldset class="form-group">
							<label>Class ID</label> <select class="form-control"
								name="class_id">
								<option value="">Select Class</option>
								<c:forEach var="classes" items="${listClass}">
									<option value="${classes.id}">${classes.id}</option>
								</c:forEach>
							</select> <span class="text-danger">${requestScope.classId_error}</span>
						</fieldset>


						<button type="submit" class="btn btn-success">Save</button>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>

	<c:import url="/WEB-INF/fragments/footer.jsp" />
</div>
<c:import url="/WEB-INF/fragments/addition.jsp" />
</body>
</html>

<script>
	
</script>
