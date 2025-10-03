function validateForm() {
    var isValid = true;

    var accountName = $("#accountName").val().trim();
    if (accountName === "") {
        $("#usernameError").text("Tên đăng nhập không được để trống.");
        isValid = false;
    } else {
        $("#usernameError").text("");
    }

    var accountPass = $("#accountPass").val().trim();
    if (accountPass === "") {
        $("#passwordError").text("Mật khẩu không được để trống.");
        isValid = false;
    } else {
        $("#passwordError").text("");
    }

    var email = $("#email").val().trim();
    var emailPattern = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,6}$/;
    if (email === "" || !emailPattern.test(email)) {
        $("#emailError").text("Email không hợp lệ.");
        isValid = false;
    } else {
        $("#emailError").text("");
    }

    var phoneNumber = $("#phoneNumber").val().trim();
    if (phoneNumber === "" || !/^\d{10}$/.test(phoneNumber)) {
        $("#phoneError").text("Số điện thoại phải có 10 đến 11 chữ số.");
        isValid = false;
    } else {
        $("#phoneError").text("");
    }

    var dateOfBirth = $("#dateOfBirth").val();
    if (dateOfBirth === "") {
        $("#dateError").text("Ngày sinh không được để trống.");
        isValid = false;
    } else {
        $("#dateError").text("");
    }

    var local = $("#local").val().trim();
    if (local === "") {
        $("#localError").text("Địa phương không được để trống.");
        isValid = false;
    } else {
        $("#localError").text("");
    }

    return isValid;
}

function registerAccount() {
    if (!validateForm()) {
        return;
    }

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
