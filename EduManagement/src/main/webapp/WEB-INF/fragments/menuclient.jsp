<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.List"%>
<%@ page import="com.joctopus.model.Notification"%>
<%@ page import="com.joctopus.dao.NotificationDao"%>
<%@ page import="com.joctopus.dao.NotificationDaoImpl"%>
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">

<%
String type = (String) session.getAttribute("type");
NotificationDao notificationDao = new NotificationDaoImpl();
Integer userIdStr = (Integer) session.getAttribute("user_id");
if (userIdStr != null) {
	List<Notification> notifications = notificationDao.selectNotificationsByUserId(userIdStr);
	request.setAttribute("notifications", notifications);
} else {
	System.out.println("User ID is null");
	request.setAttribute("notifications", new ArrayList<>());
}
%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<style>
</style>
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




			<%
			String firstName = (String) session.getAttribute("firstName");
			String lastName = (String) session.getAttribute("lastName");

			if (firstName != null && lastName != null) {
			%>

			<%
			if ("Tutors".equals(type)) {
			%>


			<div class="search">
				<form
					action="<%=request.getContextPath()%>/AdminssionClassController"
					method="get" class="form-search">
					<input type="text" placeholder="Search Courses" name="search"
						value="<%=request.getAttribute("searchQuery") != null ? request.getAttribute("searchQuery") : ""%>">
					<input type="hidden" name="action" value="searchClass">
					<button type="submit" class="search-button">
						<i class="fas fa-search"></i>
						<!-- Font Awesome icon -->
					</button>
				</form>
			</div>


			<div class="notification-wrapper">
				<button class="header-action-btn" aria-label="notifications"
					title="Notifications" id="notificationButton">
					<ion-icon name="notifications-outline" aria-hidden="true"></ion-icon>
					<span class="btn-badge dropdown-item dropdown-header navbar-badge">${fn:length(notifications)}</span>
				</button>
				<div class="dropdown-menu notification-dropdown"
					id="notificationDropdown">
					<h4>Notifications</h4>
					<ul>
						<c:choose>
							<c:when test="${fn:length(notifications) > 0}">
								<c:forEach var="notification" items="${notifications}">
									<li class="notification-item"><a
										href="<%=request.getContextPath()%>/AdminssionClassController?action=/listClass">
											<i class="fas fa-envelope mr-2"></i> ${notification.message}
									</a>
										<button type="button" class="close remove-notification"
											aria-label="Close" data-notification-id="${notification.id}">
											<span aria-hidden="true">&times;</span>
										</button></li>
								</c:forEach>
							</c:when>
							<c:otherwise>
								<li class="notification-item"><span>No Notifications</span></li>
							</c:otherwise>
						</c:choose>
					</ul>
					<a href="#" class="see-all">See All Notifications</a>
				</div>
			</div>


			<%
			}
			%>

			<%
			if ("Parents".equals(type)) {
			%>
			<div class="search">
				<form
					action="<%=request.getContextPath()%>/FindTutorClassController"
					method="get" class="form-search">
					<input type="text" placeholder="Search Courses" name="search">
					<input type="hidden" name="action" value="searchClass">
					<button type="submit" class="search-button">
						<i class="fas fa-search"></i>
						<!-- Font Awesome icon -->
					</button>
				</form>
			</div>

			<div class="notification-wrapper">
				<button class="header-action-btn" aria-label="notifications"
					title="Notifications" id="notificationButton">
					<ion-icon name="notifications-outline" aria-hidden="true"></ion-icon>
					<span class="btn-badge dropdown-item dropdown-header navbar-badge">${fn:length(notifications)}</span>
				</button>
				<div class="dropdown-menu notification-dropdown"
					id="notificationDropdown">
					<h4>Notifications</h4>
					<ul>
						<c:choose>
							<c:when test="${fn:length(notifications) > 0}">
								<c:forEach var="notification" items="${notifications}">
									<li class="notification-item"><a
										href="<%=request.getContextPath()%>/FindTutorClassController?action=/listClass">
											<i class="fas fa-envelope mr-2"></i> ${notification.message}
									</a>
										<button type="button" class="close remove-notification"
											aria-label="Close" data-notification-id="${notification.id}">
											<span aria-hidden="true">&times;</span>
										</button></li>
								</c:forEach>
							</c:when>
							<c:otherwise>
								<li class="notification-item"><span>No Notifications</span></li>
							</c:otherwise>
						</c:choose>
					</ul>
					<a href="#" class="see-all">See All Notifications</a>
				</div>
			</div>

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
					style="position: relative; right: -34px; top: -12px; font-size: 16px;"> ${sessionScope.firstName} ${sessionScope.lastName} <!-- <ion-icon name="chevron-down-outline"
						aria-hidden="true" class="submenu-toggle"
						style="
                                position: absolute;
                                left: 65px;
                                top: 7px;
                            "></ion-icon> -->
					<ul class="dropdown">
						<li><a
							href="<%=request.getContextPath()%>/ProfileUserController?action=/editProfile&id=<%=session.getAttribute("user_id")%>"
							class="dropdown_pro"> Profile </a></li>
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
	width: 370px;
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

/* .notification-dropdown .remove-notification {
	position: absolute;
	top: 0;
	right: 0;
	background: none;
	border: none;
	font-size: 16px;
	cursor: pointer;
	color: #888;
} */
.notification-item {
	position: relative;
	padding-right: 30px;
}

.notification-item a {
	display: block;
	/*    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap; */
}

.remove-notification {
	position: absolute;
	top: 50%;
	right: 10px;
	transform: translateY(-50%);
	background: none;
	border: none;
	font-size: 16px;
	cursor: pointer;
	color: #888;
}

.notification-dropdown .remove-notification:hover {
	color: #333;
}

.header-action-btn {
	position: relative;
	z-index: 1;
}

.search-input {
	/*   display: none; */
	width: 250px;
	padding: 5px 10px;
	font-size: 16px;
	border: 1px solid #ccc;
	border-radius: 4px;
	transition: width 0.3s ease;
	box-sizing: border-box;
	z-index: 2;
	position: absolute;
	right: 31px;
}

.search {
	flex-grow: 1;
	display: flex;
	justify-content: flex-end;
	align-items: center;
}

.search input[type="text"] {
	padding: 6px;
	padding-right: 42px;
	margin-right: 10px;
	border: none;
	font-size: 17px;
	border-radius: 4px;
	background: #e5e2e2;
}

.search-button {
	position: absolute;
	right: 20px;
	top: 9px;
	background: none;
	border: none;
	cursor: pointer;
	font-size: 18px;
	color: #333;
	display: flex;
	align-items: center;
}

.search-button i {
	font-size: 20px; /* Adjust size as needed */
}

.form-search {
	display: flex;
	position: relative;
}
</style>

<script>
$(document).ready(function() {
	 var type = "<%=type != null ? type : "null"%>";
	  var userId = "<%=userIdStr != null ? userIdStr : "null"%>";
	    console.log("Type: " + type);
	    console.log("User ID: " + userId);
    $('#notificationButton').on('click', function() {
        $('#notificationDropdown').toggle();
    });

    $('.remove-notification').on('click', function(event) {
    	event.stopPropagation();
        var notificationId = $(this).data('notification-id');
        var $notificationItem = $(this).closest('.notification-item');
        
        console.log('id : ',notificationId);
        $.ajax({
            url: '<%=request.getContextPath()%>/NotificationController',
            method: 'POST',
            data: { action: 'remove',id: notificationId },
            contentType: 'application/x-www-form-urlencoded',
            success: function(response) {
            		console.log('vao day');
                  // Remove the notification item from the dropdown
                    $notificationItem.remove();
                    
                    // Update the notification badge count
                    var newCount = parseInt($('.navbar-badge').text()) - 1;
                    $('.navbar-badge').text(newCount);
                    
                    // Update the notification count in the dropdown header
                    $('.notification-count').text(newCount);
                    
                    // If no notifications left, show "No Notifications" but keep the header
                    if (newCount === 0) {
                    	 $('#notificationDropdown').html(`
                                 <h4>Notifications</h4>
                                 <ul>
                                     <li class="notification-item"><span>No Notifications</span></li>
                                 </ul>
                                 <a href="#" class="see-all">See All Notifications</a>
                             `);
                             $('.navbar-badge').text(0);
                    }
            },
            error: function() {
                alert('Error removing notification.');
                console.log('loi roi');
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
