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
							<form
								action="<%=request.getContextPath()%>/UserController?action=/update"
								method="post" enctype="multipart/form-data">
						</c:if>
						<c:if test="${User == null}">
							<form
								action="<%=request.getContextPath()%>/UserController?action=/insert"
								method="post" enctype="multipart/form-data">
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
						<!-- requestScope là một đối tượng hoặc phạm vi (scope) được cung cấp bởi JSP (Java Server Pages), 
						dùng để lưu trữ và truy xuất các thuộc tính hoặc giá trị chỉ tồn tại trong vòng đời của một request HTTP. -->
						<fieldset class="form-group">
							<label>Avatar</label> <input type="file" class="form-control"
								name="image" id="imageInput" style="border: 0;"
								onchange="previewImage(event)">

							<!-- Display the current image -->
							<br> <img id="currentImage"
								src="${User.image}" alt=""
								style="max-width: 100%; max-height: 200px;">

							<!-- Hidden field to store the current image URL -->
							<input type="hidden" name="currentImage"
								value="${Banner.imageUrl}">
						</fieldset>
						<fieldset class="form-group">
							<label>First name</label> <input type="text"
								value="<c:out value='${requestScope.first_name != null ? requestScope.first_name : User.firstName}' />"
								class="form-control" name="first_name"> <span
								class="text-danger">${requestScope.first_name_error}</span>
						</fieldset>

						<fieldset class="form-group">
							<label>Last name</label> <input type="text"
								value="<c:out value='${requestScope.last_name != null ? requestScope.last_name : User.lastName}' />"
								class="form-control" name="last_name"> <span
								class="text-danger">${requestScope.last_name_error}</span>
						</fieldset>

						<fieldset class="form-group">
							<label>Date</label> <input type="date"
								value="<c:out value='${requestScope.dob != null ? requestScope.dob : User.dob}' />"
								class="form-control" name="DOB"> <span
								class="text-danger">${requestScope.dob_error}</span>
						</fieldset>


						<fieldset class="form-group">
							<label>Gender</label> <select class="form-control" name="gender">
								<option value=""
									${empty requestScope.gender && empty User.gender ? 'selected' : ''}>Select</option>
								<option value="Female"
									${requestScope.gender eq 'Female' || User.gender eq 'Female' ? 'selected' : ''}>Female</option>
								<option value="Male"
									${requestScope.gender eq 'Male' || User.gender eq 'Male' ? 'selected' : ''}>Male</option>
							</select> <span class="text-danger">${requestScope.gender_error}</span>
						</fieldset>


						<fieldset class="form-group">
							<label>Address</label> <input type="text"
								value="<c:out value='${requestScope.address != null ? requestScope.address : User.address}' />"
								class="form-control" name="address"> <span
								class="text-danger">${requestScope.address_error}</span>
						</fieldset>

						<fieldset class="form-group">
							<label>Phone number</label> <input type="text"
								value="<c:out value='${requestScope.phone_number != null ? requestScope.phone_number : User.phoneNumber}' />"
								class="form-control" name="phone_number"> <span
								class="text-danger">${requestScope.phone_number_error}</span>
						</fieldset>

						<fieldset class="form-group">
							<label>Account</label> <input type="text"
								value="<c:out value='${requestScope.account != null ? requestScope.account : User.account}' />"
								class="form-control" name="account"> <span
								class="text-danger">${requestScope.account_error}</span>
						</fieldset>

						<fieldset class="form-group">
							<label>Pass</label> <input type="password"
								value="<c:out value='${requestScope.password != null ? requestScope.password : User.password}' />"
								class="form-control" name="password"> <span
								class="text-danger">${requestScope.password_error}</span>
						</fieldset>

						<fieldset class="form-group">
							<label for="type">Type</label> <select class="form-control"
								id="type" name="type">
								<option value=""
									${empty requestScope.type && empty User.type ? 'selected' : ''}>Select</option>
								<option value="Admin"
									${requestScope.type eq 'Admin' || User.type eq 'Admin' ? 'selected' : ''}>Admin</option>
								<option value="Parents"
									${requestScope.type eq 'Parents' || User.type eq 'Parents' ? 'selected' : ''}>Parents</option>
								<option value="Tutors"
									${requestScope.type eq 'Tutors' || User.type eq 'Tutors' ? 'selected' : ''}>Tutors</option>
							</select> <span class="text-danger">${requestScope.type_error}</span>
						</fieldset>


						<button type="submit" class="btn btn-success">Save</button>
						</form>
						
						<script>
							// JavaScript function to preview the selected image
							function previewImage(event) {
								const reader = new FileReader();
								reader.onload = function() {
									const output = document
											.getElementById('currentImage');
									output.src = reader.result;
								};
								reader.readAsDataURL(event.target.files[0]);
							}
							
							 function toggleOrderIndex(select) {
							        var orderIndexField = document.getElementById('orderIndexField');
							        if (select.value == "false") {
							            orderIndexField.style.display = 'none';
							        } else {
							            orderIndexField.style.display = 'block';
							        }
							    }
						</script>
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
