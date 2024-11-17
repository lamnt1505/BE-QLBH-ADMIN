$(document).ready(function () {
    $(".cancel-order-button").click(function () {
        var orderID = $(this).data("orderid");
        var button = $(this);

        if (confirm("Bạn có chắc muốn hủy đơn hàng?")) {
            $.ajax({
                type: "POST",
                url: "/dossier-statistic/cancel-order",
                data: {
                    orderID: orderID
                },
                success: function (response) {
                    Toastify({
                        text: "Đơn hàng đã được hủy thành công",
                        duration: 3000,
                        gravity: "top",
                        position: "right",
                        backgroundColor: "linear-gradient(to right, #4CAF50, #2E7D32)",
                        close: true
                    }).showToast();
                    button.text("Đã hủy");
                    button.prop("disabled", true);
                    button.removeClass("cancel-order-button");
                },
                error: function (xhr, status, error) {
                    let errorMessage = "";
                    if (xhr.status === 404) {
                        errorMessage = "Lỗi: Đơn hàng không tìm thấy";
                    } else if (xhr.status === 400) {
                        errorMessage = "Lỗi: Đơn hàng không thể hủy bỏ";
                    } else {
                        errorMessage = "Lỗi: " + xhr.responseText;
                    }
                    Toastify({
                        text: errorMessage,
                        duration: 3000,
                        gravity: "top",
                        position: "right",
                        backgroundColor: "linear-gradient(to right, #FF5F6D, #FFC371)",
                        close: true
                    }).showToast();
                }
            });
        }
    });
});
