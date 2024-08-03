<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Create New Class</title>
<link rel="stylesheet" href="<c:url value='/css/style.css'/>">
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@10"></script>
<style>
body {
    font-family: 'Poppins', sans-serif;
    background-color: #f8f9fa;
    margin: 0;
    padding: 0;
}

.container {
    max-width: 600px;
    margin: 50px auto;
    background-color: #ffffff;
    padding: 20px;
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
    border-radius: 8px;
}

h2 {
    text-align: center;
    color: #343a40;
    margin-bottom: 20px;
}

.form-group {
    margin-bottom: 15px;
}

label {
    display: block;
    margin-bottom: 5px;
    font-weight: 500;
    color: #495057;
}

.form-control {
    width: 100%;
    padding: 10px;
    border: 1px solid #ced4da;
    border-radius: 5px;
    font-size: 16px;
}

.form-control:focus {
    border-color: #80bdff;
    outline: none;
    box-shadow: 0 0 5px rgba(0, 123, 255, 0.25);
}

.text-danger {
    color: #dc3545;
    font-size: 14px;
}

.btn {
    display: inline-block;
    padding: 10px 20px;
    font-size: 16px;
    font-weight: 600;
    text-align: center;
    border: none;
    border-radius: 5px;
    cursor: pointer;
}

.btn-success {
    background-color: #28a745;
    color: #fff;
}

.btn-success:hover {
    background-color: #218838;
}

select.form-control {
    height: 45px;
}

.border-0 {
    border: 0;
}
</style>
</head>
<body>
    <div class="container">
        <h2>Create a New Class</h2>
        <form id="create-class-form"
            action="<%=request.getContextPath()%>/FindTutorClassController?action=/createClassParent"
            method="post">
            <fieldset class="form-group border-0">
                <label>Class Name</label>
                <input type="text" class="form-control" name="class_name">
                <span class="text-danger">${requestScope.class_name_error}</span>
            </fieldset>

            <fieldset class="form-group border-0">
                <label>Class</label>
                <input type="text" class="form-control" name="class">
                <span class="text-danger">${requestScope.class_error}</span>
            </fieldset>

            <fieldset class="form-group border-0">
                <label>Time</label>
                <input type="date" class="form-control" name="study_time">
                <span class="text-danger">${requestScope.study_time_error}</span>
            </fieldset>

            <fieldset class="form-group border-0">
                <label>Subject</label>
                <input type="text" class="form-control" name="subject">
                <span class="text-danger">${requestScope.subject_error}</span>
            </fieldset>

            <fieldset class="form-group border-0">
                <label>Address</label>
                <input type="text" class="form-control" name="address">
                <span class="text-danger">${requestScope.address_error}</span>
            </fieldset>

            <fieldset class="form-group border-0">
                <label>Session</label>
                <select class="form-control" name="session">
                    <option value="">Select</option>
                    <option value="Morning">Morning</option>
                    <option value="Afternoon">Afternoon</option>
                    <option value="Evening">Evening</option>
                </select>
                <span class="text-danger">${requestScope.session_error}</span>
            </fieldset>

             <fieldset class="form-group border-0">
                <label>Status</label>
                <select class="form-control" name="status" disabled>
                    <option value="New" selected>New</option>
                    <option value="Teaching">Teaching</option>
                    <option value="Success">Success</option>
                    <option value="Cancel">Cancel</option>
                </select>
                <input type="hidden" name="status" value="New"> <!-- Hidden input to submit the status -->
                <span class="text-danger">${requestScope.status_error}</span>
            </fieldset>
            
            <fieldset class="form-group border-0">
                <label>Request Status</label>
                <select class="form-control" name="requeststatus" disabled>
                    <option value="Unapproved" selected>Unapproved</option>
                    <option value="Approved">Approved</option>
                </select>
                <input type="hidden" name="requeststatus" value="Unapproved"> <!-- Hidden input to submit the status -->
                <span class="text-danger">${requestScope.requeststatus_error}</span>
            </fieldset>
            
          	<fieldset class="form-group border-0">
                <input type="hidden" name="users" value="${sessionScope.loggedInUser.id}">
            </fieldset>
            
            <button type="submit" class="btn btn-success">Save</button>
        </form>
    </div>

    <script>
        document.addEventListener('DOMContentLoaded', function() {
            document.getElementById('create-class-form').addEventListener('submit', function(event) {
                event.preventDefault();
                const form = this;

                fetch(form.action, {
                    method: 'POST',
                    body: new URLSearchParams(new FormData(form)),
                    headers: {
                        'Content-Type': 'application/x-www-form-urlencoded',
                    },
                })
                .then(response => response.text())
                .then(data => {
                    Swal.fire(
                        'Success!',
                        'The form has been submitted successfully.',
                        'success',
                    ).then(() => {
                    	window.location.href = '<%=request.getContextPath()%>/FindTutorClassController?action=/listClassParent';
                    });
                })
                .catch(error => {
                    Swal.fire(
                        'Error!',
                        'There was a problem submitting the form.',
                        'error'
                    );
                });
            });
        });
    </script>
</body>
</html>
