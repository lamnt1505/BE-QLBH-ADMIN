function login() {
    var accountName = $("#accountName").val();
    var accountPass = $("#accountPass").val();

    $.ajax({
        type: "POST",
        url: "/api/v1/account/login",
        contentType: "application/json",
        data: JSON.stringify({ accountName: accountName, accountPass: accountPass }),
        success: function(response) {
            if (response.success) {
                console.log("Login successful");
                sessionStorage.setItem("accountName", accountName);
                sessionStorage.setItem("typeAccount", response.typeAccount);

                Toastify({
                    text: "Đăng Nhập Thành Công",
                    duration: 3000,
                    close: true,
                    gravity: "top",
                    position: "center",
                    backgroundColor: "linear-gradient(to right, #00b09b, #96c93d)",
                    stopOnFocus: true,
                }).showToast();

                setTimeout(function() {
                    if (response.isAdmin) {
                        window.location.href = "/manager";
                    } else if (response.isUser) {
                        window.location.href = "/index";
                    } else if (response.isUserVip) {
                        window.location.href = "/index";
                    } else {
                        window.location.href = "/";
                    }
                }, 1000);
            } else {
                $("#error").text(response.message);
            }
        },
        error: function(error) {
            console.log(error);
            if (error.status === 401) {
                $("#error").text("Session expired. Please log in again.");
                setTimeout(function() {
                    window.location.href = "/login";
                }, 2000);
            } else {
                $("#error").text("An error occurred. Please try again.");
            }
        }
    });
}