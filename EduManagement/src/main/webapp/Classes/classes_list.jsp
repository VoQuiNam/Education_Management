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
									<h1>Class List</h1>
									<a
										href="<%=request.getContextPath()%>/ClassesController?action=/newCl"
										class="btn btn-primary">Add</a>
								</div>
								<div class="card-body">
									<table id="example1" class="table table-bordered table-striped">
										<thead>
											<tr>
												<th>Class Name</th>
												<th>Class</th>
												<th>Study Time</th>
												<th>Subject</th>
												<th>Number Students</th>
											</tr>
										</thead>
										<tbody>
											<c:forEach var="classes" items="${listClass}">
												<tr>
													<td><c:out value="${classes.class_name}" /></td>
													<td><c:out value="${classes.eduClass}" /></td>
													<td><c:out value="${classes.study_time}" /></td>
													<td><c:out value="${classes.subject}" /></td>
													<td><c:out value="${classes.numberOfStudents}" /></td>
													<%--
													<td>
														<c:out value="${classes.users.firstName} ${classes.users.lastName}" />
													</td> --%>

													<td class="action-buttons"><a
														href="<%=request.getContextPath()%>/ClassesController?action=/editCl&id=${classes.id}"
														class="btn btn-warning">Edit</a> <a
														href="<%=request.getContextPath()%>/ClassesController?action=/deleteCl&id=${classes.id}"
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
