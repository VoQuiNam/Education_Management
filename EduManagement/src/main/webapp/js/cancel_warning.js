/*   để tạo một hộp thoại cảnh báo có thể tùy chỉnh 
    document.addEventListener('DOMContentLoaded', function() {
        document.querySelectorAll('.cancel-btn').forEach(function(button) {
            button.addEventListener('click', function(event) {
                event.preventDefault();
                const url = this.getAttribute('href');
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
                        window.location.href = url;
                    }
                });
            });
        });
    });*/