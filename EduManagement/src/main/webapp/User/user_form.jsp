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
						<c:if test="${User != null}">
							<form action="<%=request.getContextPath()%>/UserController?action=/update" method="post">
						</c:if>
						<c:if test="${User == null}">
							<form action="<%=request.getContextPath()%>/UserController?action=/insert" method="post">
						</c:if>

						<caption>
							<h2>
								<c:if test="${User != null}">
               Edit User
              </c:if>
								<c:if test="${User == null}">
               Add New User
              </c:if>
							</h2>
						</caption>

						<c:if test="${User != null}">
							<input type="hidden" name="id"
								value="<c:out value='${User.id}' />" />
						</c:if>

						<fieldset class="form-group">
							<label>First name</label> <input type="text"
								value="<c:out value='${User.firstName}' />" class="form-control"
								name="first_name">
							<span class="text-danger">${requestScope.first_name_error}</span>
						</fieldset>

						<fieldset class="form-group">
							<label>Last name</label> <input type="text"
								value="<c:out value='${User.lastName}' />" class="form-control"
								name="last_name">
								<span class="text-danger">${requestScope.last_name_error}</span>
						</fieldset>

						<fieldset class="form-group">
							<label>Date</label> 
							<input type="date"
								value="<c:out value='${User.dob}' />" class="form-control"
								name="DOB">
								<span class="text-danger">${requestScope.dob_error}</span>
						</fieldset>

						<fieldset class="form-group">
							<label>Gender</label> 
							<select class="form-control" name="gender">
								<option value="" ${empty User.gender ? 'selected' : ''}>Select</option>
								<option value="Female"
									${User.gender eq 'Female' ? 'selected' : ''}>Female</option>
								<option value="Male" ${User.gender eq 'Male' ? 'selected' : ''}>Male</option>
							</select>
							<span class="text-danger">${requestScope.gender_error}</span>
						</fieldset>

						<fieldset class="form-group">
							<label>Address</label> <input type="text"
								value="<c:out value='${User.address}' />" class="form-control"
								name="address">
								<span class="text-danger">${requestScope.address_error}</span>
						</fieldset>

						<fieldset class="form-group">
							<label>Phone number</label> <input type="text"
								value="<c:out value='${User.phoneNumber}' />"
								class="form-control" name="phone_number">
								<span class="text-danger">${requestScope.phone_number_error}</span>
						</fieldset>

						<fieldset class="form-group">
							<label>Account</label> <input type="text"
								value="<c:out value='${User.account}' />" class="form-control"
								name="account">
								<span class="text-danger">${requestScope.account_error}</span>
						</fieldset>

						<fieldset class="form-group">
							<label>Pass</label> <input type="password"
								value="<c:out value='${User.password}' />" class="form-control"
								name="password">
							
							<span class="text-danger">${requestScope.password_error}</span>
						</fieldset>

						<fieldset class="form-group">
							<label for="type">Type</label> <select class="form-control"
								id="type" name="type">
								<option value="" ${empty User.type ? 'selected' : ''}>Select</option>
								<option value="Admin" ${User.type eq 'Admin' ? 'selected' : ''}>Admin</option>
								<option value="Parents"
									${User.type eq 'Parents' ? 'selected' : ''}>Parents</option>
								<option value="Tutors"
									${User.type eq 'Tutors' ? 'selected' : ''}>Tutors</option>
							</select>
							<span class="text-danger">${requestScope.type_error}</span>
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
