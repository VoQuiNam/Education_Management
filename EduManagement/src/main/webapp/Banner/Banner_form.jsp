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

#closePreview {
	position: absolute; /* Định vị nút */
	top: 10px; /* Khoảng cách từ trên xuống */
	right: 10px; /* Khoảng cách từ bên phải */
	background: rgba(255, 255, 255, 0.7); /* Nền trắng trong suốt */
	border: none; /* Không có viền */
	border-radius: 50%; /* Hình tròn */
	cursor: pointer; /* Con trỏ chuột khi di chuột qua */
	padding: 5px; /* Khoảng cách bên trong */
	display: none; /* Ẩn nút ban đầu */
}

#imagePreview {
	max-width: 100%; /* Chiều rộng tối đa */
	max-height: 200px; /* Chiều cao tối đa */
	display: none; /* Ẩn ảnh ban đầu */
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
						<c:if test="${Banner != null}">
							<form
								action="<%=request.getContextPath()%>/BannerController?action=/update"
								method="post" enctype="multipart/form-data">
						</c:if>
						<c:if test="${Banner == null}">
							<form
								action="<%=request.getContextPath()%>/BannerController?action=/insert"
								method="post" enctype="multipart/form-data">
						</c:if>
						<caption>
							<h2>
								<c:if test="${Banner != null}">
               Edit Banner
              </c:if>
								<c:if test="${Banner == null}">
               Add New Banner
              </c:if>
							</h2>
						</caption>

						<c:if test="${Banner != null}">
							<input type="hidden" name="BannerID"
								value="<c:out value='${Banner.bannerID}' />" />
						</c:if>

						<fieldset class="form-group">
							<label>Title</label> <input type="text"
								value="<c:out value='${Banner.title}' />" class="form-control"
								name="Title">
							<%-- <span class="text-danger">${requestScope.first_name_error}</span> --%>
						</fieldset>

						<fieldset class="form-group">
							<label>Description</label> <input type="text"
								value="<c:out value='${Banner.description}' />"
								class="form-control" name="Description">
							<%-- <span class="text-danger">${requestScope.last_name_error}</span> --%>
						</fieldset>

						<fieldset class="form-group">
							<label>Image</label> <input type="file" class="form-control"
								name="ImageUrl" id="imageInput" style="border: 0;"
								onchange="previewImage(event)">

							<!-- Display the current image -->
							<br> <img id="currentImage"
								src="${Banner.imageUrl}" alt=""
								style="max-width: 100%; max-height: 200px;">

							<!-- Hidden field to store the current image URL -->
							<input type="hidden" name="currentImage"
								value="${Banner.imageUrl}">
						</fieldset>

					<%-- 	<fieldset class="form-group">
							<label>Start Date</label> <input type="date"
								value="<c:out value='${Banner.startDate}' />"
								class="form-control" name="StartDate">
							<span class="text-danger">${requestScope.dob_error}</span>
						</fieldset>

						<fieldset class="form-group">
							<label>End Date</label> <input type="date"
								value="<c:out value='${Banner.endDate}' />" class="form-control"
								name="EndDate">
							<span class="text-danger">${requestScope.dob_error}</span>
						</fieldset> --%>


						<fieldset class="form-group">
							<label>Is Active</label> <select class="form-control"
								name="IsActive" onchange="toggleOrderIndex(this)">
								<option value="true" ${Banner.isActive ? 'selected' : ''}>Yes</option>
								<option value="false" ${!Banner.isActive ? 'selected' : ''}>No</option>
							</select>
						</fieldset>

						<fieldset class="form-group">
							<label>Position</label> <select class="form-control"
								name="Position">
								<option value="banner_slide"
									<c:if test="${Banner.position == 'banner_slide'}">selected</c:if>>Slide</option>
								<option value="banner_footer"
									<c:if test="${Banner.position == 'banner_footer'}">selected</c:if>>Footer</option>
							</select>
						</fieldset>


						<%-- <fieldset class="form-group" id="orderIndexField" style="display: ${Banner.isActive ? 'block' : 'none'};">
							<label>Order Index</label> <input type="number"
								value="<c:out value='${Banner.orderIndex}' />"
								class="form-control" name="OrderIndex" min="0">
						</fieldset> --%>

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
