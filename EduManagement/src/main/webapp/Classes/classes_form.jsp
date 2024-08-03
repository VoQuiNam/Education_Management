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
						<c:if test="${Classes != null}">
							<form
								action="<%=request.getContextPath()%>/ClassesController?action=/updateCl"
								method="post">
						</c:if>
						<c:if test="${Classes == null}">
							<form
								action="<%=request.getContextPath()%>/ClassesController?action=/insertCl"
								method="post">
						</c:if>

						<caption>
							<h2>
								<c:if test="${Classes != null}">
               Edit Class
              </c:if>
								<c:if test="${Classes == null}">
               Add New Class
              </c:if>
							</h2>
						</caption>

						<c:if test="${Classes != null}">
							<input type="hidden" name="id"
								value="<c:out value='${Classes.id}' />" />
						</c:if>

						<fieldset class="form-group">
							<label>Class name</label> <input type="text"
								value="<c:out value='${Classes.class_name}' />"
								class="form-control" name="class_name"> <span
								class="text-danger">${requestScope.class_name_error}</span>
						</fieldset>

						<fieldset class="form-group">
							<label>Class</label> <input type="text"
								value="<c:out value='${Classes.eduClass}' />"
								class="form-control" name="class"> <span
								class="text-danger">${requestScope.class_error}</span>
						</fieldset>

						<fieldset class="form-group">
							<label>Time</label> <input type="date"
								value="<c:out value='${Classes.study_time}' />"
								class="form-control" name="study_time"> <span
								class="text-danger">${requestScope.study_time_error}</span>
						</fieldset>

						<fieldset class="form-group">
							<label>Subject</label> <input type="text"
								value="<c:out value='${Classes.subject}' />"
								class="form-control" name="subject"> <span
								class="text-danger">${requestScope.subject_error}</span>
						</fieldset>

						<fieldset class="form-group">
							<label>Address</label> <input type="text"
								value="<c:out value='${Classes.address}' />"
								class="form-control" name="address"> <span
								class="text-danger">${requestScope.address_error}</span>
						</fieldset>


						<fieldset class="form-group">
							<label>Session</label> <select class="form-control"
								name="session">
								<option value="" ${empty Classes.session ? 'selected' : ''}>Select</option>
								<option value="Morning"
									${Classes.session eq 'Morning' ? 'selected' : ''}>Morning</option>
								<option value="Afternoon"
									${Classes.session eq 'Afternoon' ? 'selected' : ''}>Afternoon</option>
								<option value="Evening"
									${Classes.session eq 'Evening' ? 'selected' : ''}>Evening</option>
							</select> <span class="text-danger">${requestScope.session_error}</span>
						</fieldset>

						<fieldset class="form-group">
							<label>Status</label> <select class="form-control" name="status">
								<option value="" ${empty Classes.status ? 'selected' : ''}>Select</option>
								<option value="New" ${Classes.status eq 'New' ? 'selected' : ''}>New
								</option>
								<option value="Teaching"
									${Classes.status eq 'Teaching' ? 'selected' : ''}>Teaching</option>
								<option value="Success"
									${Classes.status eq 'Success' ? 'selected' : ''}>Success</option>
								<option value="Cancel"w
									${Classes.status eq 'Cancel' ? 'selected' : ''}>Cancel</option>
							</select> <span class="text-danger">${requestScope.status_error}</span>
						</fieldset>

						<fieldset class="form-group">
							<label>Request Status</label> <select class="form-control"
								name="requeststatus">
								<option value="Unapproved"
									${Classes.requeststatus eq 'Unapproved' ? 'selected' : ''}>Unapproved</option>
								<option value="Approved"
									${Classes.requeststatus eq 'Approved' ? 'selected' : ''}>Approved</option>
							</select> <span class="text-danger">${requestScope.requeststatus_error}</span>
						</fieldset>

						<c:if test="${Classes != null}">
							<fieldset class="form-group">
								<label>Number Studets</label> <input type="text"
									value="<c:out value='${Classes.numberOfStudents}' />"
									class="form-control" name="numberOfStudents"> <span
									class="text-danger">${requestScope.numberOfStudents_error}</span>
							</fieldset>
						</c:if>


						<fieldset class="form-group">
							<label>User ID</label> <select class="form-control" name="users">
								<option value="">Select User</option>
								<c:forEach var="user" items="${listUser}">
									<c:if test="${user.type eq 'Tutors'}">
										<c:choose>
											<c:when test="${user.id eq Classes.users.id}">
												<option value="${user.id}" selected>${user.account}</option>
											</c:when>
											<c:otherwise>
												<option value="${user.id}">${user.account}</option>
											</c:otherwise>
										</c:choose>
									</c:if>
									<c:if test="${user.type eq 'Parents'}">
										<c:choose>
											<c:when test="${user.id eq Classes.users.id}">
												<option value="${user.id}" selected>${user.account}</option>
											</c:when>
											<c:otherwise>
												<option value="${user.id}">${user.account}</option>
											</c:otherwise>
										</c:choose>
									</c:if>
								</c:forEach>
							</select> <span class="text-danger">${requestScope.userId_error}</span>
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
