<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.util.List"%>
<%@ page import="com.joctopus.model.Notification"%>
<%@ page import="com.joctopus.dao.NotificationDao"%>
<%@ page import="com.joctopus.dao.NotificationDaoImpl"%>

<%
NotificationDao notificationDao = new NotificationDaoImpl();
List<Notification> notifications = notificationDao.selectAllNotifications();
request.setAttribute("notifications", notifications);
%>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

<style>
    .dropdown-item {
        display: flex;
        justify-content: space-between;
        align-items: center;
    }

    .dropdown-item a {
        flex-grow: 1;
    }

    .close {
        margin-left: 10px;
    }
</style>
<link rel="stylesheet" href="<c:url value='/css/main.css'/>">

<!-- Preloader -->
<div class="preloader flex-column justify-content-center align-items-center">
    <img class="animation__shake" src="<c:url value='/images/AdminLTELogo.png'/>" alt="AdminLTELogo" height="60" width="60">
</div>

<!-- Navbar -->
<nav class="main-header navbar navbar-expand navbar-white navbar-light">
    <!-- Left navbar links -->
    <!-- <ul class="navbar-nav">
        <li class="nav-item">
            <a class="nav-link" data-widget="pushmenu" href="#" role="button"><i class="fas fa-bars"></i></a>
        </li>
    </ul> -->

    <!-- Right navbar links -->
    <ul class="navbar-nav ml-auto">
        <!-- Navbar Search -->
        <li class="nav-item">
            <a class="nav-link" data-widget="navbar-search" href="#" role="button"><i class="fas fa-search"></i></a>
            <div class="navbar-search-block">
                <form class="form-inline">
                    <div class="input-group input-group-sm">
                        <input class="form-control form-control-navbar" type="search" placeholder="Search" aria-label="Search">
                        <div class="input-group-append">
                            <button class="btn btn-navbar" type="submit">
                                <i class="fas fa-search"></i>
                            </button>
                            <button class="btn btn-navbar" type="button" data-widget="navbar-search">
                                <i class="fas fa-times"></i>
                            </button>
                        </div>
                    </div>
                </form>
            </div>
        </li>

        <!-- Notifications Dropdown Menu -->
        <li class="nav-item dropdown">
            <a class="nav-link" data-toggle="dropdown" href="#">
                <i class="far fa-bell"></i>
                <span class="badge badge-warning navbar-badge"><%= notifications.size() %></span>
            </a>
            <div class="dropdown-menu dropdown-menu-lg dropdown-menu-right">
                <span class="dropdown-item dropdown-header">
                    <span class="notification-count"><%= notifications.size() %></span> Notifications
                </span>
                <div class="dropdown-divider"></div>
                <c:choose>
                    <c:when test="${notifications.size() > 0}">
                        <c:forEach var="notification" items="${notifications}">
                            <div class="dropdown-item d-flex justify-content-between align-items-center notification-item" data-notification-id="${notification.id}">
                                <a href="<%=request.getContextPath()%>/ClassesController?action=/listCl" class="flex-grow-1 notification-text">
                                    <i class="fas fa-envelope mr-2"></i> ${notification.message}
                                </a>
                                <button type="button" class="close remove-notification" aria-label="Close" data-notification-id="${notification.id}">
                                    <span aria-hidden="true">&times;</span>
                                </button>
                            </div>
                            <div class="dropdown-divider"></div>
                        </c:forEach>
                        <a href="#" class="dropdown-item dropdown-footer">See All Notifications</a>
                    </c:when>
                    <c:otherwise>
                        <!-- Do not remove the header and divider -->
                        <div class="dropdown-item">No Notifications</div>
                    </c:otherwise>
                </c:choose>
            </div>
        </li>

        <li class="nav-item">
            <a class="nav-link" data-widget="fullscreen" href="#" role="button"><i class="fas fa-expand-arrows-alt"></i></a>
        </li>
        <li class="nav-item">
            <a class="nav-link" data-widget="control-sidebar" data-controlsidebar-slide="true" href="#" role="button"><i class="fas fa-th-large"></i></a>
        </li>
        <li class="nav-item">
            <a class="nav-link" href="<%=request.getContextPath()%>/logout"><i class="fas fa-sign-out-alt"></i></a>
        </li>
    </ul>
</nav>
<!-- /.navbar -->

<!-- Main Sidebar Container -->
<aside class="main-sidebar sidebar-dark-primary elevation-4">
    <!-- Brand Logo -->
    <a href="<%=request.getContextPath()%>/Home/index.jsp" class="brand-link">
        <img src="<c:url value='/images/AdminLTELogo.png'/>" alt="AdminLTE Logo" class="brand-image img-circle elevation-3" style="opacity: .8">
        <span class="brand-text font-weight-light">AdminLTE 3</span>
    </a>

    <!-- Sidebar -->
    <div class="sidebar">
        <!-- Sidebar user panel (optional) -->
        <div class="user-panel mt-3 pb-3 mb-3 d-flex">
            <div class="image">
                <img src="<c:url value='/images/icon_admin.png'/>" class="img-circle elevation-2" alt="User Image">
            </div>
            <div class="info">
                <a href="#" class="d-block">Admin</a>
            </div>
        </div>

        <!-- SidebarSearch Form -->
        <div class="form-inline">
            <div class="input-group" data-widget="sidebar-search">
                <input class="form-control form-control-sidebar" type="search" placeholder="Search" aria-label="Search">
                <div class="input-group-append">
                    <button class="btn btn-sidebar">
                        <i class="fas fa-search fa-fw"></i>
                    </button>
                </div>
            </div>
        </div>

        <!-- Sidebar Menu -->
        <nav class="mt-2">
            <ul class="nav nav-pills nav-sidebar flex-column" data-widget="treeview" role="menu" data-accordion="false">
                <li class="nav-item">
                    <a href="<%=request.getContextPath()%>/Home/index.jsp" class="nav-link">
                        <i class="nav-icon fas fa-tachometer-alt"></i>
                        <p>Dashboard</p>
                    </a>
                </li>
                <li class="nav-item">
                    <a href="<%=request.getContextPath()%>/UserController?action=/list" class="nav-link">
                        <i class="nav-icon fas fa-user"></i>
                        <p>User</p>
                    </a>
                </li>
                <li class="nav-item">
                    <a href="<%=request.getContextPath()%>/ClassesController?action=/listCl" class="nav-link">
                        <i class="nav-icon fas fa-school"></i>
                        <p>Class</p>
                    </a>
                </li>
                <li class="nav-item">
                    <a href="<%=request.getContextPath()%>/ClassesUserController?action=/listUCL" class="nav-link">
                        <i class="fas fa-chalkboard"></i>
                        <p style="margin-left: 9px;">User Detail</p>
                    </a>
                </li>
            </ul>
        </nav>
        <!-- /.sidebar-menu -->
    </div>
    <!-- /.sidebar -->
</aside>

<script>
    $(document).ready(function() {
        // Ensure the sidebar is collapsed by default
        $('body').addClass('sidebar-collapse');

        $(".nav-link").click(function() {
            $(".nav-link").removeClass("active");
            $(this).addClass("active");
        });

        // Handle the click event for the remove notification button
        $(".remove-notification").click(function(event) {
            event.stopPropagation();
            var notificationId = $(this).data('notification-id');
            var $notificationItem = $(this).closest('.notification-item');

            // Send AJAX request to server to remove the notification
            $.ajax({
                url: '<%=request.getContextPath()%>/NotificationController', // Update this URL with your actual controller endpoint
                method: 'POST',
                data: { action: 'remove', id: notificationId },
                success: function(response) {
                    // Remove the notification item from the dropdown
                    $notificationItem.remove();
                    
                    // Update the notification badge count
                    var newCount = parseInt($('.navbar-badge').text()) - 1;
                    $('.navbar-badge').text(newCount);
                    
                    // Update the notification count in the dropdown header
                    $('.notification-count').text(newCount);
                    
                    // If no notifications left, show "No Notifications" but keep the header
                    if (newCount === 0) {
                        $('.dropdown-menu').html('<span class="dropdown-item dropdown-header">No Notifications</span>');
                        $('.navbar-badge').text(0);
                    }
                },
                error: function() {
                    alert('Error removing notification.');
                }
            });
        });
    });
</script>
