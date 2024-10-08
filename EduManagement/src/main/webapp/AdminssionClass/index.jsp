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


	<div class="tutor-classes-container">
		<div class="filter-container">
			<i class="fas fa-filter filter-icon" id="filter-icon"></i>
			<div class="filter-dropdown" id="filter-dropdown">
				<a href="#" data-status="All" class="filter-option">All</a> <a href="#"
					data-status="New" class="filter-option">New</a> <a href="#"
					data-status="Teaching" class="filter-option">Teaching</a> <a
					href="#" data-status="Success" class="filter-option">Success</a>
			</div>
		</div>


		<a type="button" class="create-btn"
			href="<%=request.getContextPath()%>/AdminssionClassController?action=/newClass"
			style="margin-top: 73px;">Create Class</a>

		<ul class="tutor-classes-list">
			<c:forEach var="classes" items="${listClass}">
				<c:if test="${classes.requeststatus ne 'Unapproved'}">
					<li class="tutor-class-item">
						<h2>Class Name: ${classes.class_name}</h2>
						<p>
							<strong>Class:</strong> ${classes.eduClass}
						</p>
						<p>
							<strong>Study Time:</strong> ${classes.study_time}
						</p>
						<p>
							<strong>Subjects:</strong> ${classes.subject}
						</p>
						<p>
							<strong>Location:</strong> ${classes.address}
						</p>
						<p>
							<strong>Session:</strong> ${classes.session}
						</p>
						<p>
							<strong>Status:</strong> ${classes.status}
						</p> <c:choose>
							<c:when test="${classes.users.type eq 'Tutors'}">
								<p>
									<strong>Number of students:</strong>
									${classes.numberOfStudents}
								</p>
							</c:when>
						</c:choose>


						<form
							action="<%=request.getContextPath()%>/AdminssionClassController"
							method="post" style="display: inline-block;">

							<input type="hidden" name="id" value="${classes.id}">
							<c:choose>
								<c:when test="${classes.users.type eq 'Tutors'}">
									<input type="hidden" name="action" value="confirmTeach">
									 <button type="submit" class="register-btn confirm" data-class-id="${classes.id}"
                                <c:if test="${classes.status eq 'Success'}">disabled</c:if>>Confirm Class</button>
								</c:when>
								<c:otherwise>
									<input type="hidden" name="action" value="registerClass">
									<button type="submit" class="register-btn register">Register
										Class</button>
								</c:otherwise>
							</c:choose>
						</form>

						<form
							action="<%=request.getContextPath()%>/AdminssionClassController"
							method="post" style="display: inline-block;">
							<input type="hidden" name="id" value="${classes.id}"> <input
								type="hidden" name="action" value="cancelClass">
							  <button type="submit" class="cancel-btn" data-class-id="${classes.id}"
                        <c:if test="${classes.status eq 'Success'}">disabled</c:if>>Cancel Class</button>
						</form>
					</li>
				</c:if>
			</c:forEach>
		</ul>
	</div>

	<script>
	document.addEventListener('DOMContentLoaded', function () {
	    attachEventListeners(); // Initial attachment of event listeners

	    // Check for success or error message in session
	    <c:if test="${not empty sessionScope.successMessage}">
	        Swal.fire({
	            title: 'Success',
	            text: '${sessionScope.successMessage}',
	            icon: 'success'
	        });
	        <c:remove var="successMessage" scope="session"/>
	    </c:if>

	    <c:if test="${not empty sessionScope.errorMessage}">
	        Swal.fire({
	            title: 'Error',
	            text: '${sessionScope.errorMessage}',
	            icon: 'error'
	        });
	        <c:remove var="errorMessage" scope="session"/>
	    </c:if>
	});

	//Đính kèm lại trình xử lý sự kiện: Bạn cần đính kèm lại trình xử 
	//lý sự kiện cho nút "Xác nhận" và "Hủy" mỗi khi danh sách lớp được cập nhật. 
	//Bạn có thể đạt được điều này bằng cách di chuyển logic đính kèm của 
	//trình nghe sự kiện vào một hàm riêng biệt và gọi nó sau khi danh sách lớp được cập nhật.(nếu ko đính kèm thì sự kiện lắng nghe sẽ bị xóa)
	function attachEventListeners() {
	    document.querySelectorAll('.cancel-btn').forEach(function (button) {
	        button.addEventListener('click', function (event) {
	            event.preventDefault();
	            const classId = this.getAttribute('data-class-id');
	            Swal.fire({
	                title: 'Are you sure you want to cancel?',
	                text: "You will not be able to undo this action!",
	                icon: 'warning',
	                showCancelButton: true,
	                confirmButtonColor: '#3085d6',
	                cancelButtonColor: '#d33',
	                confirmButtonText: 'Yes',
	                cancelButtonText: 'Cancel'
	            }).then((result) => {
	                if (result.isConfirmed) {
	                    // Submit the form
	                    this.closest('form').submit();
	                }
	            });
	        });
	    });

	    document.querySelectorAll('.confirm').forEach(function (button) {
	        button.addEventListener('click', function (event) {
	            event.preventDefault();
	            const classId = this.getAttribute('data-class-id');
	            Swal.fire({
	                title: 'Are you sure you want to confirm teaching this class?',
	                text: "This will send a request for approval.",
	                icon: 'warning',
	                showCancelButton: true,
	                confirmButtonColor: '#3085d6',
	                cancelButtonColor: '#d33',
	                confirmButtonText: 'Yes, confirm',
	                cancelButtonText: 'Cancel'
	            }).then((result) => {
	                if (result.isConfirmed) {
	                    fetch('<%=request.getContextPath()%>/AdminssionClassController', {
	                        method: 'POST',
	                        headers: {
	                            'Content-Type': 'application/x-www-form-urlencoded',
	                        },
	                        body: new URLSearchParams({
	                            action: 'confirmTeach',
	                            id: classId
	                        })
	                    })
	                    .then(response => response.text().then(text => ({ status: response.status, text })))
	                    .then(({ status, text }) => {
	                        if (status === 200 && text === 'Notification sent') {
	                            Swal.fire(
	                                'Sent!',
	                                'Notification has been sent to the admin.',
	                                'success'
	                            ).then(() => {
	                                window.location.reload(); // Reload the page after confirmation
	                            });
	                        } else {
	                            let errorMessage = 'There was a problem sending the notification.';
	                            if (status === 400) {
	                                errorMessage = 'Requires registered students.';
	                            } else if (status === 403) {
	                                errorMessage = 'Only the creator of the class can confirm it.';
	                            }
	                            Swal.fire(
	                                'Error!',
	                                errorMessage,
	                                'error'
	                            );
	                        }
	                    })
	                    .catch(error => {
	                        console.error('Error:', error);
	                        Swal.fire(
	                            'Error!',
	                            'There was a problem sending the notification.',
	                            'error'
	                        );
	                    });
	                }
	            });
	        });
	    });
	}

	document.addEventListener('DOMContentLoaded', function () {
	    const filterIcon = document.getElementById('filter-icon');
	    const filterDropdown = document.getElementById('filter-dropdown');
	    const filterOptions = document.querySelectorAll('.filter-option');

	    // Toggle the filter dropdown
	    filterIcon.addEventListener('click', function () {
	        filterDropdown.style.display = filterDropdown.style.display === 'block' ? 'none' : 'block';
	    });

	    // Apply filter when an option is selected
	    filterOptions.forEach(option => {
	        option.addEventListener('click', function (event) {
	            event.preventDefault();
	            const status = this.getAttribute('data-status');

	            // Update the page with the selected status
	            updateClasses(status);

	            // Close the dropdown
	            filterDropdown.style.display = 'none';
	        });
	    });

	    function updateClasses(status) {
	        fetch('<%=request.getContextPath()%>/AdminssionClassController?action=filterClasses&status=' + encodeURIComponent(status))
	            .then(response => response.text())
	            .then(data => {
	                // Update only the class list part of the page
	                const parser = new DOMParser();
	                const htmlDoc = parser.parseFromString(data, 'text/html');
	                const newClassList = htmlDoc.querySelector('.tutor-classes-list');
	                document.querySelector('.tutor-classes-list').innerHTML = newClassList.innerHTML;

	                // Re-attach event listeners to the new elements
	                attachEventListeners();
	            })
	            .catch(error => console.error('Error:', error));
	    }
	});

    </script>

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
	<script type="module"
		src="https://unpkg.com/ionicons@5.5.2/dist/ionicons/ionicons.esm.js"></script>
	<script nomodule
		src="https://unpkg.com/ionicons@5.5.2/dist/ionicons/ionicons.js"></script>
</body>

</html>
