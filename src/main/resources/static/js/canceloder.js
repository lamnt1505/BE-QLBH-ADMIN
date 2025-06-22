$(document).ready(function () {
    $(".cancel-order-button").click(function () {
        var orderID = $(this).data("orderid");
        var button = $(this);

        Swal.fire({
            title: "Bạn có chắc chắn?",
            text: "Đơn hàng sẽ bị hủy và không thể khôi phục.",
            icon: "warning",
            showCancelButton: true,
            confirmButtonColor: "#d33",
            cancelButtonColor: "#3085d6",
            confirmButtonText: "Có, hủy đơn hàng!",
            cancelButtonText: "Không"
        }).then((result) => {
            if (result.isConfirmed) {
                $.ajax({
                    type: "POST",
                    url: "/dossier-statistic/cancel-order",
                    data: {
                        orderID: orderID
                    },
                    success: function (response) {
                        let successMessage = response.message || "✅ Đơn hàng đã được hủy thành công";
                        Toastify({
                            text: successMessage,
                            duration: 3000,
                            gravity: "top",
                            position: "right",
                            backgroundColor: "linear-gradient(to right, #4CAF50, #2E7D32)",
                        }).showToast();
                        button.text("Đã hủy");
                        button.removeClass("cancel-order-button");
                    },
                    error: function (xhr, status, error) {
                        let errorMessage = "";
                        if (xhr.status === 404) {
                            errorMessage = "❌ Lỗi: Đơn hàng không tìm thấy";
                        } else if (xhr.status === 400) {
                            errorMessage = "⚠️ Lỗi: Đơn hàng không thể hủy bỏ";
                        } else {
                            errorMessage = "⚠️ Lỗi: " + xhr.responseText;
                        }

                        Toastify({
                            text: errorMessage,
                            duration: 3000,
                            gravity: "top",
                            position: "right",
                            backgroundColor: "linear-gradient(to right, #FF5F6D, #FFC371)",
                        }).showToast();
                    }
                });
            }
        });
    });
});
