function login() {
    var accountName = $("#accountName").val();
    var accountPass = $("#accountPass").val();
    var captchaInput = $("#captchaInput").val();

    $.ajax({
        type: "POST",
        url: "/api/v1/account/login",
        contentType: "application/json",
        data: JSON.stringify({ accountName: accountName, accountPass: accountPass, captcha: captchaInput }),
        success: function(response) {
            if (response.isCaptchaValid === false) {
                $("#error").text("Captcha không hợp lệ. Vui lòng thử lại.");
                refreshCaptcha();
            }else if (response.success) {
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
                    } else if (response.isEmployee) {
                        window.location.href = "/manager";
                    } else {
                        window.location.href = "/";
                    }
                }, 1000);
            } else {
                $("#error").text(response.message);
            }
        },
        error: function(error) {
            if (error.status === 400 && error.responseJSON) {
                $("#error").text(error.responseJSON.message || "Captcha không hợp lệ. Vui lòng thử lại.");
                refreshCaptcha();
            } else if (error.status === 401) {
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
function refreshCaptcha() {
    $("#captchaImage").attr("src", "/api/v1/account/captcha?" + new Date().getTime());
}