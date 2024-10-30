$(document).ready(function() {
    $('#logoutButton').click(function() {
        $.ajax({
            url: '/api/v1/account/logout',
            type: 'POST',
            dataType: 'json',
            xhrFields: {
                withCredentials: true
            },
            success: function(response) {
                Swal.fire({
                    title: "Đăng xuất thành công!",
                    text: "Bạn đã được đăng xuất.",
                    type: "success",
                    confirmButtonText: "OK"
                }).then(function() {
                    window.location.href = '/login';
                });
            },
            error: function(xhr, status, error) {
                Swal.fire({
                    title: "Đăng xuất không thành công!",
                    text: "Lỗi: " + error,
                    type: "error",
                    confirmButtonText: "Đóng"
                });
            }
        });
    });
});

