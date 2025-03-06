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
									<h1>Contact List</h1>
								</div>
								<div class="card-body">
									<c:choose>
										
										<c:when test="${empty listContacts}">
											<div class="alert alert-warning" role="alert">No
												contact available.</div>
										</c:when>
										
										<c:otherwise>
											<table id="example1"
												class="table table-bordered table-striped">
												<thead>
													<tr>
														<th>Title</th>
														<th>Content</th>
														<th>User</th>
														<th>Date</th>
													</tr>
												</thead>
												<tbody>
													<c:forEach var="contacts" items="${listContacts}">
														<tr>
															<td><c:out value="${contacts.title}" /></td>
															<td><c:out value="${contacts.content}" /></td>
															<td><c:out value="${contacts.user_id.firstName}" /> <c:out value="${contacts.user_id.lastName}" /></td>
															<td><c:out value="${contacts.created_at}" /></td>
														</tr>
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
