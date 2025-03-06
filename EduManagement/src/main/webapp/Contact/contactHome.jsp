<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<title>EduWeb - The Best Program to Enroll for Exchange</title>
<meta name="title"
	content="EduWeb - The Best Program to Enroll for Exchange">
<meta name="description"
	content="This is an education html template made by codewithsadee">

<!-- Custom CSS -->
<link rel="stylesheet" href="<c:url value='/css/contact.css'/>">
<link rel="stylesheet" href="<c:url value='/css/style.css'/>">
<link rel="stylesheet" href="<c:url value='/css/iconuser.css'/>">
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@10"></script>
<!-- Google Fonts -->
<link
	href="https://fonts.googleapis.com/css2?family=League+Spartan:wght@400;500;600;700;800&family=Poppins:wght@400;500&display=swap"
	rel="stylesheet">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">

<!-- Preload Images -->
<link rel="preload" as="image"
	href="<c:url value='/images/hero-bg.svg'/>">
<link rel="preload" as="image"
	href="<c:url value='/images/hero-banner-1.jpg'/>">
<link rel="preload" as="image"
	href="<c:url value='/images/hero-banner-2.jpg'/>">
</head>

<body id="top">
	<c:import url="/WEB-INF/fragments/menuclient.jsp" />
	<section class="contact-section contact-page">
		<div class="container">
			<h2 class="section-title">Liên hệ với chúng tôi</h2>
			<p class="section-description">Nếu bạn có bất kỳ câu hỏi nào, vui
				lòng điền vào biểu mẫu bên dưới và chúng tôi sẽ liên hệ lại sớm nhất
				có thể.</p>

			<form
				action="<%=request.getContextPath()%>/ContactController?action=/post"
				method="post" class="contact-form">

				<div class="input-group">
					<label for="subject">Tiêu đề</label> <input type="text" id="title"
						name="title" required placeholder="Nhập tiêu đề liên hệ">
				</div>

				<div class="input-group">
					<label for="message">Nội dung</label>
					<textarea id="content" name="content" rows="5" required
						placeholder="Nhập nội dung tin nhắn"></textarea>
				</div>

				<fieldset class="form-group border-0">
					<input type="hidden" name="user_id"
						value="${sessionScope.loggedInUser.id}">
				</fieldset>
				<button type="submit" class="btn-submit">Gửi tin nhắn</button>
			</form>
		</div>
	</section>

	<script>
	    document.addEventListener('DOMContentLoaded', function () {
	        // Kiểm tra thông báo thành công
	    	<c:if test="${not empty sessionScope.successMessage}">
			Swal.fire({
				title : 'Success',
				text : '${sessionScope.successMessage}',
				icon : 'success'
			});
			<c:remove var="successMessage" scope="session"/>
			</c:if>

			<c:if test="${not empty sessionScope.errorMessage}">
			Swal.fire({
				title : 'Error',
				text : '${sessionScope.errorMessage}',
				icon : 'error'
			});
			<c:remove var="errorMessage" scope="session"/>
			</c:if>
	    });
	</script>
	<!-- Xóa thông báo khỏi session để tránh hiển thị lại -->
	
	

	<c:import url="/WEB-INF/fragments/footerclient.jsp" />



	<!-- Back to Top Button -->
	<a href="#top" class="back-top-btn" aria-label="back top top"
		data-back-top-btn> <ion-icon name="chevron-up" aria-hidden="true"></ion-icon>
	</a>

	<!-- Custom Scripts -->
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
