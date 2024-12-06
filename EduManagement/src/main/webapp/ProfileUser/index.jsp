<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<!-- 
    - primary meta tag
  -->
<title>EduWeb - The Best Program to Enroll for Exchange</title>
<meta name="title"
	content="EduWeb - The Best Program to Enroll for Exchange">
<meta name="description"
	content="This is an education html template made by codewithsadee">

<!-- 
    - custom css link
  -->
<link rel="stylesheet" href="<c:url value='/css/style.css'/>">
<link rel="stylesheet" href="<c:url value='/css/iconuser.css'/>">


<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/sweetalert2@11/dist/sweetalert2.min.css">
<script
	src="https://cdn.jsdelivr.net/npm/sweetalert2@11/dist/sweetalert2.min.js"></script>

<!-- 
    - google font link
  -->
<!--  dòng để thiết lập kết nối ban đầu (preconnect) với các domain fonts.googleapis.com và 
 fonts.gstatic.com. Điều này giúp tăng tốc quá trình tải font từ các domain đó. -->
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<!-- dòng thực sự kết nối và tải hai font chữ là League Spartan và Poppins từ Google Fonts -->
<link
	href="https://fonts.googleapis.com/css2?family=League+Spartan:wght@400;500;600;700;800&family=Poppins:wght@400;500&display=swap"
	rel="stylesheet">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">

<!-- 
    - preload images
  -->
<link rel="preload" as="image"
	href="<c:url value='/images/hero-bg.svg'/>">
<link rel="preload" as="image"
	href="<c:url value='/images/hero-banner-1.jpg'/>">
<link rel="preload" as="image"
	href="<c:url value='/images/hero-banner-2.jpg'/>">
<link rel="preload" as="image"
	href="<c:url value='/images/hero-shape-1.svg'/>">
<link rel="preload" as="image"
	href="<c:url value='/images/hero-shape-2.svg'/>">

<style>
body {
	background-color: #e9ecef;
	margin: 0;
	padding: 0;
}

span.text-danger {
	color: red;
}

.container-form {
	width: 50%;
	margin: 140px auto;
	background-color: #ffffff;
	padding: 30px;
	border-radius: 10px;
	box-shadow: 0 8px 16px rgba(0, 0, 0, 0.2);
}

h2 {
	text-align: center;
	margin-bottom: 30px;
	font-size: 28px;
	color: #333;
}

.form-group {
	margin-bottom: 20px;
}

.form-group label {
	font-weight: bold;
	color: #555;
	display: block;
	margin-bottom: 5px;
}

.form-group input, .form-group select {
	width: calc(100% - 20px);
	padding: 10px;
	margin-top: 5px;
	border: 1px solid #ccc;
	border-radius: 5px;
	box-sizing: border-box;
	transition: border-color 0.3s;
}

.form-group input:focus, .form-group select:focus {
	border-color: #ff5722;
	outline: none;
}

.form-group button {
	background-color: #ff5722;
	color: white;
	border: none;
	padding: 12px;
	border-radius: 5px;
	cursor: pointer;
	font-size: 16px;
	transition: background-color 0.3s;
}

.form-group button:hover {
	background-color: #e64a19;
}

.form-group .gender-radio {
	display: inline-block;
	margin-right: 20px;
}

.form-group .change-password {
	color: #2196F3;
	cursor: pointer;
	text-decoration: underline;
}
</style>

</head>

<body id="top">
	<c:if test="${not empty successMessage}">
		<script>
			Swal.fire({
				icon : 'success',
				title : 'Success!',
				text : '${successMessage}'
			});
		</script>
		<c:set var="successMessage"
			value="${ProfileUserController.session.removeAttribute('successMessage')}" />
	</c:if>

	<c:if test="${not empty errorMessage}">
		<script>
			Swal.fire({
				icon : 'error',
				title : 'Error!',
				text : '${errorMessage}'
			});
		</script>
		<c:set var="errorMessage" value="${ProfileUserController.session.removeAttribute('errorMessage')}" />
	</c:if>
	<c:import url="/WEB-INF/fragments/menuclient.jsp" />
	<div class="container-form">
		<h2>My Profile</h2>
		<c:if test="${User != null}">
			<form
				action="<%=request.getContextPath()%>/ProfileUserController?action=/update"
				method="post">
		</c:if>

		<c:if test="${User != null}">
			<input type="hidden" name="id" value="<c:out value='${User.id}' />" />
		</c:if>

		<div class="form-group">
			<label for="lastname">Last name</label> <input type="text"
				id="last_name" name="last_name"
				value="${requestScope.input_last_name != null ? requestScope.input_last_name : User.lastName}">
			<span class="text-danger">${requestScope.last_name_error}</span>
		</div>

		<div class="form-group">
			<label for="firstname">First name</label> <input type="text"
				id="first_name" name="first_name"
				value="${requestScope.input_first_name != null ? requestScope.input_first_name : User.firstName}">
			<span class="text-danger">${requestScope.first_name_error}</span>
		</div>

		<div class="form-group">
			<label for="phone">Telephone</label> <input type="text"
				id="phone_number" name="phone_number"
				value="${requestScope.input_phone_number != null ? requestScope.input_phone_number : User.phoneNumber}">
			<span class="text-danger">${requestScope.phone_number_error}</span>
		</div>

		<div class="form-group">
			<label>Gender</label> <select class="form-control" name="gender">
				<option value=""
					${empty requestScope.input_gender && empty User.gender ? 'selected' : ''}>Select</option>
				<option value="Female"
					${requestScope.input_gender eq 'Female' || User.gender eq 'Female' ? 'selected' : ''}>Female</option>
				<option value="Male"
					${requestScope.input_gender eq 'Male' || User.gender eq 'Male' ? 'selected' : ''}>Male</option>
			</select> <span class="text-danger">${requestScope.gender_error}</span>
		</div>

		<div class="form-group">
			<label for="dob">Date</label> <input type="date"
				value="${requestScope.input_dob != null ? requestScope.input_dob : User.dob}"
				class="form-control" name="DOB"> <span class="text-danger">${requestScope.dob_error}</span>
		</div>

		<div class="form-group">
			<label for="dob">Address</label> <input type="text"
				value="${requestScope.input_address != null ? requestScope.input_address : User.address}"
				class="form-control" name="address"> <span
				class="text-danger">${requestScope.address_error}</span>
		</div>

		<div class="form-group" style="display: none;">
			<label for="dob">Account</label> <input type="hidden"
				value="${requestScope.input_account != null ? requestScope.input_account : User.account}"
				class="form-control" name="account">
		</div>

		<div class="form-group" style="display: none;">
			<label for="dob">Password</label> <input type="hidden"
				value="${requestScope.input_password != null ? requestScope.input_password : User.password}"
				class="form-control" name="password">
		</div>

		<div class="form-group" style="display: none;">
			<label for="type">Type</label> <input type="hidden"
				value="<c:out value='${User.type}' />" name="type">
		</div>

		<div class="form-group">
			<button type="submit">Save</button>
		</div>
		</form>
	</div>




	<c:import url="/WEB-INF/fragments/footerclient.jsp" />

	<!-- 
    - #BACK TO TOP
  -->
	<a href="#top" class="back-top-btn" aria-label="back top top"
		data-back-top-btn> <ion-icon name="chevron-up" aria-hidden="true"></ion-icon>
	</a>

	<!-- 
    - custom js link
  -->
	<script src="<c:url value='/js/script.js'/>" defer></script>

	<!-- 
    - ionicon link
  -->
	<script type="module"
		src="https://unpkg.com/ionicons@5.5.2/dist/ionicons/ionicons.esm.js"></script>
	<script nomodule
		src="https://unpkg.com/ionicons@5.5.2/dist/ionicons/ionicons.js"></script>
</body>

</html>
