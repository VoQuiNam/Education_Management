<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="vi">
<head>
    <c:import url="/WEB-INF/fragments/header.jsp" />
    <meta http-equiv="Content-Language" content="vi" />
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/sweetalert2@11/dist/sweetalert2.min.css">
    <style>
    .action-buttons {
        white-space: nowrap;
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
                                    <h1>Account List</h1>
                                    <a href="<%=request.getContextPath()%>/UserController?action=/new"
                                        class="btn btn-primary">Add</a>
                                </div>
                                <div class="card-body">
                                    <table id="example1" class="table table-bordered table-striped">
                                        <thead>
                                            <tr>
                                                <th>First Name</th>
                                                <th>Last Name</th>
                                                <th>Year of Birth</th>
                                                <th>Gender</th>
                                                <th>Address</th>
                                                <th>Type</th>
                                                <th>Action</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach var="user" items="${listUser}">
                                                <tr>
                                                    <td><c:out value="${user.firstName}" /></td>
                                                    <td><c:out value="${user.lastName}" /></td>
                                                    <td><c:out value="${user.dob}" /></td>
                                                    <td><c:out value="${user.gender}" /></td>
                                                    <td><c:out value="${user.address}" /></td>
                                                    <td><c:out value="${user.type}" /></td>
                                                    <td class="action-buttons">
                                                        <a href="<%=request.getContextPath()%>/UserController?action=/edit&id=${user.id}" class="btn btn-warning">Edit</a>
                                                        <a href="<%=request.getContextPath()%>/UserController?action=/delete&id=${user.id}" class="btn btn-danger delete-button">Delete</a>
                                                    </td>
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
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11/dist/sweetalert2.all.min.js"></script>
    <script>
    </script>
</body>
</html>
