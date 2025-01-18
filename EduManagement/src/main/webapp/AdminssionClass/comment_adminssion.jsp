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
<script src="<c:url value='/js/comment.js'/>"></script>
<script src="<c:url value='/js/adminssionClass.js'/>"></script>
<script src="<c:url value='/js/delete_waring.js'/>"></script>

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
		<form id="commentForm" method="post">
			<input type="hidden" name="class_id"
				value="<%=request.getParameter("class_id")%>" />
			<textarea placeholder="Write your comment here..." name="content"
				required></textarea>
			<button type="submit" class="btn">Submit</button>
		</form>


		<h3>Comments</h3>
		<div class="list__comment">
			<c:forEach var="comment" items="${listComment}">
				<c:if test="${comment.parentComment == null}">
					<!-- Top-level comment -->
					<div class="media" id="comment-${comment.id}">
						<img
							src="${comment.user_id.image != null ? comment.user_id.image : 'https://bootdey.com/img/Content/avatar/avatar1.png'}"
							alt="User Avatar">
						<div class="media-body">
							<h4>${comment.user_id.firstName}${comment.user_id.lastName}</h4>
							<p id="comment-content-${comment.id}">${comment.content}</p>
							<div class="media-detail">
								<span><i class="fa fa-calendar"></i>
									${comment.created_at}</span> <a href="#"
									onclick="showReplyForm('${comment.id}')"><i
									class="fa fa-reply"></i> Reply</a> <a href="javascript:void(0);"
									onclick="editComment(this, '${comment.id}')"><i
									class="fa fa-edit"></i> Edit</a> <a href="javascript:void(0);"
									onclick="confirmDelete('${comment.id}', '${comment.class_id.id}')"><i
									class="fa fa-trash delete-button"></i> Delete</a>
							</div>

							<!-- Reply form -->
							<div id="reply-form-${comment.id}" class="reply-form"
								style="display: none;" data-class-id="${comment.class_id.id}">
								<textarea id="reply-text-${comment.id}"
									placeholder="Write your reply..."></textarea>
								<button onclick="submitReply('${comment.id}')">Submit
									Reply</button>
							</div>


							<!-- Recursively display replies -->
							<div id="replies-${comment.id}" class="replies-container">
								<c:forEach var="reply" items="${listComment}">
									<c:if
										test="${reply.parentComment != null && reply.parentComment.id == comment.id}">
										<div class="media" style="margin-left: 30px;"
											id="reply-${reply.id}">
											<img
												src="${reply.user_id.image != null ? reply.user_id.image : 'https://bootdey.com/img/Content/avatar/avatar1.png'}"
												alt="User Avatar">
											<div class="media-body">
												<h4>${reply.user_id.firstName}
													${reply.user_id.lastName}</h4>
												<p id="comment-content-${reply.id}">${reply.content}</p>
												<div class="media-detail">
													<span><i class="fa fa-calendar"></i>
														${reply.created_at}</span> <a href="#"
														onclick="showReplyForm('${reply.id}')"><i
														class="fa fa-reply"></i> Reply</a> <a
														href="javascript:void(0);"
														onclick="editComment(this, '${reply.id}')"><i
														class="fa fa-edit"></i> Edit</a> <a href="javascript:void(0);"
														onclick="confirmDelete('${reply.id}', '${reply.class_id.id}')"><i
														class="fa fa-trash delete-button"></i> Delete</a>
												</div>

												<!-- Reply form for replies -->
												<div id="reply-form-${reply.id}" class="reply-form"
													style="display: none;" data-class-id="${reply.class_id.id}">
													<textarea id="reply-text-${reply.id}"
														placeholder="Write your reply..."></textarea>
													<button onclick="submitReply('${reply.id}')">Submit
														Reply</button>
												</div>

												<!-- Recursively display further replies -->
												<div id="replies-${reply.id}" class="replies-container">
													<c:forEach var="nestedReply" items="${listComment}">
														<c:if
															test="${nestedReply.parentComment != null && nestedReply.parentComment.id == reply.id}">
															<div class="media" style="margin-left: 60px;"
																id="nested-reply-${nestedReply.id}">
																<img
																	src="${nestedReply.user_id.image != null ? nestedReply.user_id.image : 'https://bootdey.com/img/Content/avatar/avatar1.png'}"
																	alt="User Avatar">
																<div class="media-body">
																	<h4>${nestedReply.user_id.firstName}
																		${nestedReply.user_id.lastName}</h4>
																	<p id="comment-content-${nestedReply.id}">${nestedReply.content}</p>
																	<div class="media-detail">
																		<span><i class="fa fa-calendar"></i>
																			${nestedReply.created_at}</span> <a href="#"
																			onclick="showReplyForm('${nestedReply.id}')"><i
																			class="fa fa-reply"></i> Reply</a> <a
																			href="javascript:void(0);"
																			onclick="editComment(this, '${nestedReply.id}')"><i
																			class="fa fa-edit"></i> Edit</a> <a
																			href="javascript:void(0);"
																			onclick="confirmDelete('${nestedReply.id}', '${nestedReply.class_id.id}')"><i
																			class="fa fa-trash delete-button"></i> Delete</a>
																	</div>
																</div>
															</div>
														</c:if>
													</c:forEach>
												</div>
											</div>
										</div>
									</c:if>
								</c:forEach>
							</div>
						</div>
					</div>
				</c:if>
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

	<script>
		document
				.getElementById("commentForm")
				.addEventListener(
						"submit",
						function(e) {

							var classId = document
									.querySelector('input[name="class_id"]').value;
							var content = document
									.querySelector('textarea[name="content"]').value
									.trim();

							if (!content) {
								Swal.fire({
									icon : 'warning',
									title : 'Cảnh báo',
									text : 'Bạn cần nhập nội dung bình luận!',
								});
								return;
							}

							var xhr = new XMLHttpRequest();
							xhr
									.open(
											"POST",
											"CommentAdminssionController?action=/postComment",
											true);
							xhr.setRequestHeader("Content-Type",
									"application/x-www-form-urlencoded");

							xhr.onreadystatechange = function() {
								if (xhr.readyState === XMLHttpRequest.DONE) {
									if (xhr.status === 200) {
										try {
											var response = JSON
													.parse(xhr.responseText);
											console.log("Server response:",
													response);

											if (response.success) {
												if (response.redirectUrl) {
													window.location.href = response.redirectUrl; // Chuyển hướng
												} else {
													Swal
															.fire({
																icon : 'error',
																title : 'Lỗi',
																text : 'Không tìm thấy URL chuyển hướng.',
															});
												}
											} else {
												Swal
														.fire({
															icon : 'error',
															title : 'Thất bại',
															text : response.error
																	|| 'Không thể gửi bình luận.',
														});
											}
										} catch (e) {
											console.error(
													"Error parsing JSON:",
													xhr.responseText);
											Swal
													.fire({
														icon : 'error',
														title : 'Lỗi',
														text : 'Phản hồi từ server không hợp lệ!',
													});
										}
									} else {
										Swal
												.fire({
													icon : 'error',
													title : 'Lỗi Server',
													text : `Mã lỗi: ${xhr.status}. Vui lòng thử lại!`,
												});
									}
								}
							};

							// Chuẩn bị dữ liệu gửi đi
							var requestData = "content="
									+ encodeURIComponent(content)
									+ "&class_id="
									+ encodeURIComponent(classId);
							console.log("Sending data:", requestData);
							xhr.send(requestData);
						});
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
	</script>
	<script type="module"
		src="https://unpkg.com/ionicons@5.5.2/dist/ionicons/ionicons.esm.js"></script>
	<script nomodule
		src="https://unpkg.com/ionicons@5.5.2/dist/ionicons/ionicons.js"></script>
</body>

</html>
