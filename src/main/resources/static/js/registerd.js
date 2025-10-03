function registerAccount() {
    var formData = new FormData(document.getElementById("registrationForm"));
    $.ajax({
        type: "POST",
        url: "/api/v1/account/add",
        data: formData,
        contentType: false,
        processData: false,
        success: function(response) {
            Toastify({
                text: "Đã Đăng Ký Thành Công",
                duration: 3000,
                close: true,
                gravity: "top",
                position: "center",
                backgroundColor: "linear-gradient(to right, #00b09b, #96c93d)",
                stopOnFocus: true,
            }).showToast();
        },
        error: function(xhr, status, error) {
            console.log("Status: " + status);
            console.log("Error: " + error);
            console.log("Response: " + xhr.responseText);
            Toastify({
                text: "Đăng Ký Thất Bại: " + xhr.responseText,
                duration: 3000,
                close: true,
                gravity: "top",
                position: "center",
                backgroundColor: "linear-gradient(to right, #ff5f6d, #ffc371)",
                stopOnFocus: true,
            }).showToast();
        }
    });
}