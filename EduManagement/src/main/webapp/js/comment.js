// Function to show the reply form
function showReplyForm(commentId) {
	  console.log("Comment ID received: ", commentId);
    var replyForm = document.getElementById('reply-form-' + commentId);
 	console.log("Reply form: ", replyForm);

    // Kiểm tra nếu phần tử tồn tại
    if (replyForm) {
        // Toggle hiển thị
        replyForm.style.display = (replyForm.style.display === 'none' || replyForm.style.display === '') 
            ? 'block' 
            : 'none';
    } else {
        console.error('Reply form not found for comment id: ' + commentId);
    }
}


function submitReply(commentId) {
    var replyTextElem = document.getElementById("reply-text-" + commentId);
    if (!replyTextElem) {
        console.error("Reply textarea not found for comment ID: " + commentId);
        return;
    }

    var replyText = replyTextElem.value.trim();
    if (!replyText) {
        console.warn("Reply text is empty. Please enter a valid reply.");
        return;
    }

    var replyForm = document.getElementById("reply-form-" + commentId);
    if (!replyForm) {
        console.error("Reply form not found for comment ID: " + commentId);
        return;
    }

    var classId = replyForm.getAttribute("data-class-id");
    if (!classId) {
        console.error("Class ID is missing in the reply form for comment ID: " + commentId);
        return;
    }

    var xhr = new XMLHttpRequest();
    xhr.open("POST", "CommentAdminssionController?action=/postComment", true);
    xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");

    xhr.onreadystatechange = function() {
        if (xhr.readyState === XMLHttpRequest.DONE) {
            if (xhr.status === 200) {
                try {
                    var response = JSON.parse(xhr.responseText);
                    console.log("Parsed response:", response);

                    if (response.success) {
                        if (response.redirectUrl) {
                            console.log("Redirecting to:", response.redirectUrl);
                            window.location.href = response.redirectUrl;
                        } else {
                            console.error("Redirect URL not found.");
                            Swal.fire({
                                icon: 'error',
                                title: 'Lỗi',
                                text: 'Không tìm thấy URL chuyển hướng.',
                            });
                        }
                    } else {
                        console.error("Failed to post comment:", response.error);
                        Swal.fire({
                            icon: 'error',
                            title: 'Thất bại',
                            text: response.error || 'Không thể gửi bình luận.',
                        });
                    }
                } catch (e) {
                    console.error("Error parsing JSON:", xhr.responseText);
                    Swal.fire({
                        icon: 'error',
                        title: 'Lỗi',
                        text: 'Phản hồi từ server không hợp lệ.',
                    });
                }
            } else {
                console.error("Server error:", xhr.status, xhr.statusText);
                Swal.fire({
                    icon: 'error',
                    title: 'Lỗi Server',
                    text: `Mã lỗi: ${xhr.status}`,
                });
            }
        }
    };

    var requestData =
        "content=" + encodeURIComponent(replyText) +
        "&class_id=" + encodeURIComponent(classId) +
        "&parent_comment_id=" + encodeURIComponent(commentId);

    console.log("Sending data:", requestData);
    xhr.send(requestData);

    // Reset form ngay lập tức
    replyTextElem.value = "";
    replyForm.style.display = "none";
}



function editComment(anchorElement, commentId) {
    // Lấy phần tử nội dung cần chỉnh sửa từ anchorElement
    const commentContainer = anchorElement.closest('.media-body');
    const commentText = commentContainer.querySelector(`#comment-content-${commentId}`);
    const originalText = commentText.innerText;

    // Thay thế nội dung bằng trường nhập liệu
    commentText.innerHTML = `
        <textarea id="edit-textarea-${commentId}" style="width: 100%; margin-bottom: 10px;">${originalText}</textarea>
        <div class="btn-container">
            <button class="btn-save button_" onclick="saveComment('${commentId}', this)">Save</button>
            <button class="btn-cancel button_" onclick="cancelEdit('${commentId}', '${originalText}')">Cancel</button>
        </div>
    `;
}

function saveComment(commentId, buttonElement) {
    const commentContainer = buttonElement.closest('.media-body');
    const newContent = commentContainer.querySelector(`#edit-textarea-${commentId}`).value;

    fetch('CommentAdminssionController?action=/updateComment', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({
            id: commentId,
            content: newContent
        })
    })
    .then(response => response.json())
    .then(data => {
        if (data.success) {
            // Cập nhật nội dung hiển thị
            const commentText = commentContainer.querySelector(`#comment-content-${commentId}`);
            commentText.innerHTML = newContent;
        } else {
            alert('Failed to update comment: ' + data.error);
        }
    })
    .catch(error => {
        console.error('Error:', error);
    });
}

function cancelEdit(commentId, originalText) {
    // Khôi phục nội dung cũ nếu hủy chỉnh sửa
    const commentText = document.querySelector(`#comment-content-${commentId}`);
    commentText.innerHTML = originalText;
}
