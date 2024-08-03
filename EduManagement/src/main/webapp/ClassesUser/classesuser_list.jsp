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
	<div class="wrapper">
		<c:import url="/WEB-INF/fragments/menus.jsp" />
		<div class="content-wrapper" id="main-content">
			<section class="content">
				<div class="container-fluid">
					<div class="row">
						<div class="col-12">
							<div class="card">
								<div class="card-header">
									<h1>User Detail List</h1>
									<a
										href="<%=request.getContextPath()%>/ClassesUserController?action=/newUCL"
										class="btn btn-primary">Add</a>
								</div>
								<div class="card-body">
									<table id="example1" class="table table-bordered table-striped">
										<thead>
											<tr>
												<th>User Id</th>
												<th>Class Id</th>
											</tr>
										</thead>
										<tbody>
											<c:forEach var="classesuser" items="${listUcl}">
												<tr>
													<td><c:out value="${classesuser.id_user.account}" /></td>
													<td><c:out value="${classesuser.class_id.class_name}" /></td>
													<td class="action-buttons"><a
														href="<%=request.getContextPath()%>/ClassesUserController?action=/deleteUCL&id_user=${classesuser.id_user.id}&class_id=${classesuser.class_id.id}"
														class="btn btn-danger delete-button">Delete</a></td>
												</tr>
											</c:forEach>
										</tbody>
									</table>
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
