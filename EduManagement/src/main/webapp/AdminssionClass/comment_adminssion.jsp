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
<link rel="stylesheet" href="<c:url value='/css/comment.css'/>">
<script src="<c:url value='/js/adminssionClass.js'/>"></script>

<%-- <link rel="stylesheet" href="<c:url value='/css/bootstrap/css/bootstrap.css'/>"> --%>

<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>

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
.filter-container {
	position: relative;
	top: 138px;
	text-align: right;
}

.filter-dropdown {
	display: none;
	position: absolute;
	top: 40px;
	right: 0;
	background-color: white;
	border: 1px solid #ddd;
	border-radius: 4px;
	box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
	z-index: 1000;
}

.filter-dropdown .filter-option {
	padding: 10px;
	display: block;
	color: black;
	text-decoration: none;
}

.filter-dropdown .filter-option:hover {
	background-color: #f1f1f1;
}

.filter-icon {
	cursor: pointer;
	font-size: 24px;
}
</style>

</head>

<body id="top">
	<c:import url="/WEB-INF/fragments/menuclient.jsp" />


	<section class="content-item" id="comments">
		<div style="margin-bottom: 10px; font-size: 14px; color: #555;">
			<c:if test="${classDetails != null}">
				<h2 class="class_name">Class name: ${classDetails.class_name}</h2>
				<h3 class="class_edu">Class: ${classDetails.eduClass}</h3>
			</c:if>
			<c:if test="${classDetails == null}">
				<p>No class details available.</p>
			</c:if>
		</div>
		<h3>New Comment</h3>
		<form
			action="<%=request.getContextPath()%>/CommentAdminssionController?action=/postComment"
			method="post">
			<input type="hidden" name="class_id"
				value="<%=request.getParameter("class_id")%>" />
			<textarea placeholder="Write your comment here..." name="content"
				required></textarea>
			<button type="submit" class="btn">Submit</button>
		</form>

		<h3>Comments</h3>
		<div class="list__comment">
			<c:forEach var="comment" items="${listComment}">
				<div class="media">
					<!-- Avatar của người dùng -->
					<img
						src="${comment.user_id.image != null ? comment.user_id.image : 'https://bootdey.com/img/Content/avatar/avatar1.png'}"
						alt="User Avatar">

					<div class="media-body">
						<!-- Tên người dùng -->
						<h4>${comment.user_id.firstName}${comment.user_id.lastName}</h4>

						<!-- Nội dung bình luận -->
						<p>${comment.content}</p>

						<!-- Chi tiết bình luận -->
						<div class="media-detail">
							<!-- Ngày tạo bình luận -->
							<span><i class="fa fa-calendar"></i> ${comment.created_at}
							</span>

							<!-- Các hành động -->
							<a href="#"><i class="fa fa-thumbs-up"></i> Like</a> <a href="#"><i
								class="fa fa-reply"></i> Reply</a> <a href="#"><i
								class="fa fa-edit"></i> Edit</a> <a href="#"><i
								class="fa fa-trash"></i> Delete</a>
						</div>
					</div>
				</div>
			</c:forEach>
		</div>
	</section>
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
