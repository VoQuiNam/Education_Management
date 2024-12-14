//Đính kèm lại trình xử lý sự kiện: Bạn cần đính kèm lại trình xử 
//lý sự kiện cho nút "Xác nhận" và "Hủy" mỗi khi danh sách lớp được cập nhật. 
//Bạn có thể đạt được điều này bằng cách di chuyển logic đính kèm của 
//trình nghe sự kiện vào một hàm riêng biệt và gọi nó sau khi danh sách lớp được cập nhật.(nếu ko đính kèm thì sự kiện lắng nghe sẽ bị xóa)
function attachEventListeners() {
	document.querySelectorAll('.cancel-btn').forEach(function(button) {
		button.addEventListener('click', function(event) {
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

	document.querySelectorAll('.confirm').forEach(function(button) {
		button.addEventListener('click', function(event) {
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

	// Select modal, close button, and comment icons
	/*const modal = document.getElementById('commentModal');
	const closeBtn = document.querySelector('.close-btn');
	const commentIcons = document.querySelectorAll('.icon_comment');*/


	/*// Add event listeners to each comment icon
	commentIcons.forEach((icon) => {
		icon.addEventListener('click', function() {
			modal.style.display = 'flex'; // Use flex display to center modal
			document.body.classList.add('modal-open'); // Optional: Add class to disable body scroll
		});
	});

	// Close modal when clicking the "X" button
	closeBtn.addEventListener('click', function() {
		modal.style.display = 'none';
		document.body.classList.remove('modal-open'); // Optional: Remove body scroll class
	});*/

	// Close modal when clicking outside the modal content
	/*window.addEventListener('click', function(event) {
		if (event.target === modal) {
			modal.style.display = 'none';
			document.body.classList.remove('modal-open'); // Optional: Remove body scroll class
		}
	});

	//comment
	// Array to store comments
	let comments = [
		{
			name: 'Corvo Attano',
			text: 'Greetings, fellow colleagues. I would like to share my insights on this task.',
			time: '10 Jun, 18:45',
			image: 'https://via.placeholder.com/40'
		},
		{
			name: 'Daisy Fitzroy',
			text: "Hi, Corvo. Let's just do what we are supposed to do to get the result.",
			time: '12 Jun, 19:40',
			image: 'https://via.placeholder.com/40'
		}
	];



	// Function to render comments
	function renderComments() {
		commentsSection.innerHTML = ''; // Clear current comments
		comments.forEach((comment) => {
			const commentDiv = document.createElement('div');
			commentDiv.className = 'comment';
			commentDiv.innerHTML = `
			<img src="${comment.image}" alt="${comment.name}">
			<div class="content">
				<div class="name">${comment.name}</div>
				<div class="text">${comment.text}</div>
				<div class="time">${comment.time}</div>
			</div>
		`;
			commentsSection.appendChild(commentDiv);
		});
	}

	renderComments();*/

	
}



