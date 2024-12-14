<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="vi">
<head>
<c:import url="/WEB-INF/fragments/header.jsp" />
<meta http-equiv="Content-Language" content="vi" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/sweetalert2@11/dist/sweetalert2.min.css">
<script
	src="https://cdn.jsdelivr.net/npm/sweetalert2@11/dist/sweetalert2.min.js"></script>
<style>
/* Tùy chỉnh CSS */
.action-buttons {
	white-space: nowrap;
}

.action-buttons a {
	margin-right: 5px;
}
</style>
<script src="<c:url value='/js/delete_waring.js'/>"></script>
</head>
<body class="hold-transition sidebar-mini layout-fixed">
	<c:if test="${not empty errorMessage}">
		<script>
			Swal.fire({
				icon : 'error',
				title : 'Error!',
				text : '${errorMessage}'
			});
		</script>
	</c:if>
	<div class="wrapper">
		<c:import url="/WEB-INF/fragments/menus.jsp" />
		<div class="content-wrapper" id="main-content">
			<section class="content">
				<div class="container-fluid">
					<div class="row">
						<div class="col-12">
							<div class="card">
								<div class="card-header">
									<h1>Banner List</h1>
									<a
										href="<%=request.getContextPath()%>/BannerController?action=/newBanner"
										class="btn btn-primary">Add</a>
								</div>
								<div class="card-body">
									<c:choose>

										<c:when test="${empty listBanner}">
											<div class="alert alert-warning" role="alert">No Banner
												available.</div>
										</c:when>

										<c:otherwise>
											<!-- Table for 'banner_slide' -->
											<table id="table1" class="table table-bordered table-striped">
												<thead>
													<tr>
														<th>#</th>
														<th>Title</th>
														<th>Description</th>
														<th>Image Url</th>
														<th>Is Active</th>
														<th>Position</th>
													</tr>
												</thead>
												<tbody>
													<!-- Filter banners with position 'banner_slide' -->
													<c:set var="counter1" value="1" />
													<!-- Initialize counter for table1 -->
													<c:forEach var="banners" items="${listBanner}">
														<c:if test="${banners.position == 'banner_slide'}">
															<tr>
																<td><c:out value="${counter1}" /></td>
																<!-- Display counter -->
																<td><c:out value="${banners.title}" /></td>
																<td><c:out value="${banners.description}" /></td>
																<td><img src="${banners.imageUrl}"
																	alt="Banner Image" style="width: 100px; height: auto;" /></td>
																<td><c:out value="${banners.isActive}" /></td>
																<td><c:out value="${banners.position}" /></td>
																<td class="action-buttons"><a
																	href="<%=request.getContextPath()%>/BannerController?action=/editBanner&BannerID=${banners.bannerID}"
																	class="btn btn-warning">Edit</a> <a
																	href="<%=request.getContextPath()%>/BannerController?action=/deleteBanner&BannerID=${banners.bannerID}"
																	class="btn btn-danger delete-button">Delete</a></td>
															</tr>
															<c:set var="counter1" value="${counter1 + 1}" />
															<!-- Increment counter -->
														</c:if>
													</c:forEach>
												</tbody>
											</table>

											<!-- Table for 'banner_footer' -->
											<table id="table2" class="table table-bordered table-striped"
												style="margin-top: 30px;">
												<thead>
													<tr>
														<th>#</th>
														<th>Title</th>
														<th>Description</th>
														<th>Image Url</th>
														<th>Is Active</th>
														<th>Position</th>
													</tr>
												</thead>
												<tbody>
													<!-- Filter banners with position 'banner_footer' -->
													<c:set var="counter2" value="1" />
													<!-- Initialize counter for table2 -->
													<c:forEach var="banners" items="${listBanner}">
														<c:if test="${banners.position == 'banner_footer'}">
															<tr>
																<td><c:out value="${counter2}" /></td>
																<!-- Display counter -->
																<td><c:out value="${banners.title}" /></td>
																<td><c:out value="${banners.description}" /></td>
																<td><img src="${banners.imageUrl}"
																	alt="Banner Image" style="width: 100px; height: auto;" /></td>
																<td><c:out value="${banners.isActive}" /></td>
																<td><c:out value="${banners.position}" /></td>
																<td class="action-buttons"><a
																	href="<%=request.getContextPath()%>/BannerController?action=/editBanner&BannerID=${banners.bannerID}"
																	class="btn btn-warning">Edit</a> <a
																	href="<%=request.getContextPath()%>/BannerController?action=/deleteBanner&BannerID=${banners.bannerID}"
																	class="btn btn-danger delete-button">Delete</a></td>
															</tr>
															<c:set var="counter2" value="${counter2 + 1}" />
															<!-- Increment counter -->
														</c:if>
													</c:forEach>
												</tbody>
											</table>

										</c:otherwise>
									</c:choose>
								</div>
							</div>
						</div>
					</div>
				</div>
			</section>
		</div>
		<c:import url="/WEB-INF/fragments/footer.jsp" />
	</div>
	<c:import url="/WEB-INF/fragments/addition.jsp" />
</body>
</html>
