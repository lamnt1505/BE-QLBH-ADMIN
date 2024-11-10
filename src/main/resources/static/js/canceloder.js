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
                    alert("Đơn hàng đã được hủy thành công");
                    button.text("Đã hủy");
                    button.prop("disabled", true);
                    button.removeClass("cancel-order-button");
                },
                error: function (xhr, status, error) {
                    if (xhr.status === 404) {
                        alert("Lỗi: Đơn hàng không tìm thấy");
                    } else if (xhr.status === 400) {
                        alert("Lỗi: Đơn hàng không thể hủy bỏ");
                    } else {
                        alert("Lỗi: " + xhr.responseText);
                    }
                }
            });
        }
    });
});