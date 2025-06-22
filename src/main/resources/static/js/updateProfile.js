$(document).ready(function() {
    var accountID = $('#accountID').val();
    if (accountID) {
        $.ajax({
            url: `/api/v1/account/${accountID}/get`,
            type: 'GET',
            success: function(accountDTO) {
                console.log("Username nhận được:", accountDTO.username);
                $('#accountName').val(accountDTO.accountName);
                $('#username').val(accountDTO.username);
                $('#phoneNumber').val(accountDTO.phoneNumber);
                $('#email').val(accountDTO.email);
                $('#local').val(accountDTO.local);
                if (accountDTO.dateOfBirth) {
                    const dateObj = new Date(accountDTO.dateOfBirth);
                    const formattedDate = dateObj.toISOString().split('T')[0];
                    $('#dateOfBirth').val(formattedDate);
                }
                if (accountDTO.image) {
                    $('#imagePreview').attr('src', accountDTO.image);
                }
            },
            error: function(xhr, status, error) {
                Toastify({
                    text: "Không thể tải thông tin tài khoản: " + xhr.responseText,
                    duration: 3000,
                    close: true,
                    gravity: "top",
                    position: "right",
                    backgroundColor: "#ff0000",
                }).showToast();
            }
        });
    }

    $('#updateButton').click(function(e) {
        e.preventDefault();
        if (!accountID) {
            console.error("No accountID found");
            Toastify({
                text: "Không tìm thấy ID tài khoản",
                duration: 3000,
                close: true,
                gravity: "top",
                position: "right",
                backgroundColor: "#ff0000",
            }).showToast();
            return;
        }

        const accountName = $('#accountName').val();
        const username = $('#username').val();
        const phoneNumber = $('#phoneNumber').val();
        const email = $('#email').val();
        const local = $('#local').val();
        const dateOfBirth = $('#dateOfBirth').val();
        const accountPass = $('#accountPass').val();
        const confirmPassword = $('#confirmAccountPass').val();

        if (accountPass && !validatePassword()) {
            Toastify({
                text: "Mật khẩu không khớp. Vui lòng kiểm tra lại.",
                duration: 3000,
                close: true,
                gravity: "top",
                position: "right",
                backgroundColor: "#ff0000",
            }).showToast();
            return;
        }

        const formData = new FormData();
        formData.append('accountName', accountName);
        formData.append('username', username);
        formData.append('phoneNumber', phoneNumber);
        formData.append('email', email);
        formData.append('local', local);
        formData.append('dateOfBirth', dateOfBirth);
        if (accountPass) {
            formData.append('accountPass', accountPass);
        }
        const imageFile = $('#image')[0]?.files[0];
        if (imageFile) {
            formData.append('image', imageFile);
        }

        for (let pair of formData.entries()) {
            console.log(pair[0] + ': ' + pair[1]);
        }
        $.ajax({
            url: `/api/v1/account/update/${accountID}`,
            type: 'PUT',
            data: formData,
            processData: false,
            contentType: false,
            dataType: 'text',
            success: function(response) {
                Toastify({
                    text: response,
                    duration: 3000,
                    close: true,
                    gravity: "top",
                    position: "right",
                    backgroundColor: "#4caf50",
                }).showToast();
                setTimeout(() => {
                    window.location.reload();
                }, 1500);
            },
            error: function(xhr) {
                console.error("Lỗi cập nhật:", {
                    status: xhr.status,
                    statusText: xhr.statusText,
                    response: xhr.responseText
                });
                Toastify({
                    text: "Có lỗi xảy ra khi cập nhật tài khoản: " + xhr.responseText,
                    duration: 3000,
                    close: true,
                    gravity: "top",
                    position: "right",
                    backgroundColor: "#ff0000",
                }).showToast();
            }
        });
    });
});

function validatePassword() {
    const password = $('#accountPass').val();
    const confirmPassword = $('#confirmAccountPass').val();
    return password === confirmPassword;
}