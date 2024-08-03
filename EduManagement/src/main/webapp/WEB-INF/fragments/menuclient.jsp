<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.List"%>
<%@ page import="com.joctopus.model.Notification"%>
<%@ page import="com.joctopus.dao.NotificationDao"%>
<%@ page import="com.joctopus.dao.NotificationDaoImpl"%>

<%
String type = (String) session.getAttribute("type");
NotificationDao notificationDao = new NotificationDaoImpl();
List<Notification> notifications = null;


	notifications = notificationDao.selectAllNotifications();
    request.setAttribute("notifications", notifications);

%>

<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<header class="header" data-header>
	<div class="container">

		<a href="#" class="logo"> <img
			src="<c:url value='/images/logo.svg'/>" width="162" height="50"
			alt="EduWeb logo">
		</a>

		<nav class="navbar" data-navbar>

			<div class="wrapper">
				<a href="#" class="logo"> <img
					src="<c:url value='/images/logo.svg'/>" width="162" height="50"
					alt="EduWeb logo">
				</a>

				<button class="nav-close-btn" aria-label="close menu"
					data-nav-toggler>
					<ion-icon name="close-outline" aria-hidden="true"></ion-icon>
				</button>
			</div>

			<ul class="navbar-list">

				<li class="navbar-item"><a
					href="<%=request.getContextPath()%>/HomeClientController"
					class="navbar-link" data-nav-link>Home</a></li>

				<%
				if (type != null) {
					if (type.equals("Parents")) {
				%>
				<li class="navbar-item"><a
					href="<%=request.getContextPath()%>/FindTutorClassController?action=/listClassParent"
					class="navbar-link" data-nav-link>Find a tutor</a></li>
				<%
				} else if (type.equals("Tutors")) {
				%>

				<li class="navbar-item"><a
					href="<%=request.getContextPath()%>/AdminssionClassController?action=/listClass"
					class="navbar-link" data-nav-link>Admission class</a></li>
				<%
				}
				}
				%>
				<li class="navbar-item"><a href="#" class="navbar-link"
					data-nav-link>Contact</a></li>

			</ul>

		</nav>

		<div class="header-actions" style="position: relative; left: -56px;">

			<button class="header-action-btn" aria-label="toggle search"
				title="Search">
				<ion-icon name="search-outline" aria-hidden="true"></ion-icon>
			</button>

			<%
			String firstName = (String) session.getAttribute("firstName");
			String lastName = (String) session.getAttribute("lastName");

			if (firstName != null && lastName != null) {
			%>

			<%
			if ("Tutors".equals(type)) {
			%>

			<%-- <div class="notification-wrapper">
				<button class="header-action-btn" aria-label="notifications"
					title="Notifications" id="notificationButton">
					<ion-icon name="notifications-outline" aria-hidden="true"></ion-icon>
					<span class="btn-badge"><%=notifications.size()%></span>
				</button>
				<div class="notification-dropdown" id="notificationDropdown">
					<h4>Notifications</h4>
					<ul>
						<!-- Notifications dynamically generated -->
						<%
						if (notifications.size() > 0) {
						%>
						<%
						for (Notification notification : notifications) {
						%>
						<li class="notification-item"
							data-notification-id="<%=notification.getId()%>"><a
							href="<%=request.getContextPath()%>/AdminssionClassController?action=/listClass">
								<i class="fas fa-envelope mr-2"></i> <%=notification.getMessage()%>
						</a>
							<button type="button" class="close remove-notification"
								aria-label="Close">
								<span aria-hidden="true">&times;</span>
							</button></li>
						<%
						}
						%>
						<%
						} else {
						%>
						<li class="notification-item"><span>No Notifications</span></li>
						<%
						}
						%>
					</ul>
					<a href="#" class="see-all">See All Notifications</a>
				</div>
			</div> --%>
			<%
			}
			%>

			<%
			if ("Parents".equals(type)) {
			%>
			<!-- <div class="notification-wrapper">
				<button class="header-action-btn" aria-label="notifications"
					title="Notifications" id="notificationButton">
					<ion-icon name="notifications-outline" aria-hidden="true"></ion-icon>
					<span class="btn-badge">0</span>
				</button>
				<div class="notification-dropdown" id="notificationDropdown">
					<button class="notification-close-btn"
						aria-label="close notifications">
						<ion-icon name="close-outline" aria-hidden="true"></ion-icon>
					</button>
					<h4>Notifications</h4>
					<ul>
						<li><a href="#"><i class="fas fa-envelope mr-2"></i></a> new
							notifications</li>
					</ul>
					<a href="#" class="see-all">See All Notifications</a>
				</div>
			</div> -->
			<%
			}
			%>

			<button class="header-action-btn">
				<ion-icon name="person-circle-outline" aria-hidden="true"
					style="
                            position: relative;
                            font-size: 27px;
                            top: 14px;
                        "></ion-icon>
				<span class="user-name"
					style="position: relative; right: -34px; top: -12px; font-size: 16px;"><%=firstName%>
					<%=lastName%> <!-- <ion-icon name="chevron-down-outline"
						aria-hidden="true" class="submenu-toggle"
						style="
                                position: absolute;
                                left: 65px;
                                top: 7px;
                            "></ion-icon> -->
					<ul class="dropdown">
						<li><a href="#" class="dropdown_pro">Profile</a></li>
						<li><a href="<%=request.getContextPath()%>/logout"
							class="dropdown_log">Logout</a></li>
					</ul> </span>
			</button>
			<%
			} else {
			%>
			<a href="<%=request.getContextPath()%>/login" class="btn has-before">
				<span class="span">Login</span> <ion-icon
					name="arrow-forward-outline" aria-hidden="true"></ion-icon> <%
 }
 %>
			</a>

			<button class="header-action-btn" aria-label="open menu"
				data-nav-toggler>
				<ion-icon name="menu-outline" aria-hidden="true"></ion-icon>
			</button>

		</div>

		<div class="overlay" data-nav-toggler data-overlay></div>

	</div>
</header>

<style>
.notification-wrapper {
	position: relative;
}

.notification-dropdown {
	display: none;
	position: absolute;
	right: 0;
	top: 40px;
	width: 300px;
	background: white;
	border: 1px solid #ccc;
	box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
	z-index: 1000;
}

.notification-close-btn {
	position: absolute;
	top: 5px;
	right: 5px;
	background: none;
	border: none;
	cursor: pointer;
	font-size: 20px;
}

.notification-dropdown h4 {
	margin: 0;
	padding: 10px;
	background: #f5f5f5;
	border-bottom: 1px solid #ddd;
}

.notification-dropdown ul {
	list-style: none;
	margin: 0;
	padding: 10px;
}

.notification-dropdown ul li {
	margin: 5px 0;
	position: relative;
}

.notification-dropdown ul li a {
	text-decoration: none;
	color: #333;
}

.notification-dropdown ul li a:hover {
	text-decoration: underline;
}

.notification-dropdown .see-all {
	display: block;
	text-align: center;
	padding: 10px;
	background: #f5f5f5;
	border-top: 1px solid #ddd;
	text-decoration: none;
	color: #333;
}

.notification-dropdown .see-all:hover {
	background: #eee;
}

.notification-dropdown .remove-notification {
	position: absolute;
	top: 0;
	right: 0;
	background: none;
	border: none;
	font-size: 16px;
	cursor: pointer;
	color: #888;
}

.notification-dropdown .remove-notification:hover {
	color: #333;
}
</style>

<script>
$(document).ready(function() {
    $('#notificationButton').on('click', function() {
        $('#notificationDropdown').toggle();
    });

    $('.remove-notification').on('click', function() {
        var notificationId = $(this).closest('.notification-item').data('notification-id');
        $.ajax({
            url: '<%=request.getContextPath()%>/RemoveNotificationController',
            method: 'POST',
            data: { id: notificationId },
            success: function(response) {
                // Reload notifications or update the UI as needed
            }
        });
        $(this).closest('.notification-item').remove();
    });

    $(document).on('click', function(event) {
        if (!$(event.target).closest('#notificationButton, #notificationDropdown').length) {
            $('#notificationDropdown').hide();
        }
    });
});
</script>
