 /*  để tạo một hộp thoại cảnh báo có thể tùy chỉnh */
    document.addEventListener('DOMContentLoaded', function() {
        document.querySelectorAll('.delete-button').forEach(function(button) {
            button.addEventListener('click', function(event) {
                event.preventDefault();
                const url = this.getAttribute('href');
                Swal.fire({
                    title: 'Are you sure you want to delete?',
                    text: "You will not be able to undo this action!",
                    icon: 'warning',
                    showCancelButton: true,
                    confirmButtonColor: '#3085d6',
                    cancelButtonColor: '#d33',
                    confirmButtonText: 'Yes',
                    cancelButtonText: 'Cancel'
                }).then((result) => {
                    if (result.isConfirmed) {
                        window.location.href = url;
                    }
                });
            });
        });
        
        
    });
    
    
    function confirmDelete(commentId, classId) {
		console.log('comment: ',commentId);
		console.log('class: ',classId);
	    // Check if commentId and classId are valid
	    if (!commentId || !classId || classId === "") {
	        Swal.fire({
	            title: 'Error',
	            text: 'Missing or invalid comment ID or class ID.',
	            icon: 'error'
	        });
	        return; // Stop further execution
	    }

	    Swal.fire({
	        title: 'Are you sure?',
	        text: "Do you really want to delete this comment?",
	        icon: 'warning',
	        showCancelButton: true,
	        confirmButtonColor: '#3085d6',
	        cancelButtonColor: '#d33',
	        confirmButtonText: 'Yes, delete it!'
	    }).then((result) => {
	        if (result.isConfirmed) {
	            // Redirect to the delete URL
	        	window.location.href = "/EduManagement/CommentAdminssionController?action=/deleteComment&id=" + commentId + "&class_id=" + classId;
	        }
	    });
	}

