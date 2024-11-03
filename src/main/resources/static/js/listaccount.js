$(document).ready(function () {
    $.ajax({
        url: "/api/v1/account/Listgetall",
        type: "GET",
        dataType: "json",
        success: function (data) {
            $.each(data, function (index, account) {
                var row = "<tr>" +
                    "<td><img src='data:image/png;base64," + account.imageBase64 + "' alt='Account Image' style='width: 50px; height: 50px; border-radius: 50%;'></td>" +
                    "<td>" + account.accountName + "</td>" +
                    "<td>" + account.username + "</td>" +
                    "<td>" + account.email + "</td>" +
                    "<td>" + account.phoneNumber + "</td>" +
                    "<td>" + account.dateOfBirth + "</td>" +
                    "<td>" + account.local + "</td>" +
                    "<td>" +
                    "<button class='btn btn-info' onclick=\"showAccountDetails(" + account.accountID + ")\"><i class='fas fa-check'></i> Chi tiết</button>" +
                    "</td>" +
                    "<td>" +
                    "<button class='btn btn-primary' onclick=\"showUpdateAccountForm(" + account.accountID + ")\"><i class='fas fa-info-circle'></i> Cập nhật</button>" +
                    "</td>" +
                    "<td>" +
                    "<button class='btn btn-danger' onclick=\"deleteAccount(" + account.accountID + ")\"><i class='fas fa-trash'></i> Xóa</button>" +
                    "</td>" +
                    "</tr>";
                $("#account-table").append(row);
            });
        },
        error: function () {
            alert("Lỗi khi tải danh sách tài khoản");
        }
    });
});
