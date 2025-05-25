$(document).ready(function () {
    var orderid = 0;

    // Xử lý sự kiện click vào nút sửa
    $(".editmodal").click(function (e) {
        e.preventDefault();
        var status = $(this).parents('tr').find('.status').text();
        orderid = $(this).data('orderid');
        $(".optionorder").val(status).change();
    });

    // Xử lý sự kiện click vào nút cập nhật
    $(".submitstatus").click(function (e) {
        e.preventDefault();
        var newstatus = $(".optionorder").val();

        $.ajax({
            url: "/dossier-statistic/--update-status",
            type: "POST",
            data: {
                orderid: orderid,
                status: newstatus
            },
            success: function (response) {
                let toastOptions = {
                    duration: 3000,
                    gravity: "top",
                    position: "right",
                    stopOnFocus: true,
                };

                switch (response) {
                    case "SUCCESS":
                        Toastify({
                            ...toastOptions,
                            text: "Cập nhật trạng thái thành công!",
                            backgroundColor: "linear-gradient(to right, #56ab2f, #a8e063)",
                        }).showToast();
                        break;

                    case "ORDERID_NOT_FOUND":
                        Toastify({
                            ...toastOptions,
                            text: "Không tìm thấy đơn hàng! Vui lòng thử lại...",
                            backgroundColor: "linear-gradient(to right, #ff5f6d, #ffc371)",
                        }).showToast();
                        break;

                    case "INSUFFICIENT_QUANTITY":
                        Toastify({
                            ...toastOptions,
                            text: "Số lượng trong kho không đủ!",
                            backgroundColor: "linear-gradient(to right, #ff5f6d, #ffc371)",
                        }).showToast();
                        break;

                    case "STORAGE_NOT_FOUND":
                        Toastify({
                            ...toastOptions,
                            text: "Sản phẩm chưa được cập nhật trong kho!",
                            backgroundColor: "linear-gradient(to right, #ff5f6d, #ffc371)",
                        }).showToast();
                        break;

                    default:
                        Toastify({
                            ...toastOptions,
                            text: "Có lỗi xảy ra! Vui lòng thử lại.",
                            backgroundColor: "linear-gradient(to right, #ff5f6d, #ffc371)",
                        }).showToast();
                }

                setTimeout(function () {
                    window.location.reload();
                }, 2000);
            },
            error: function (xhr, status, error) {
                console.error("Error:", error);
                Toastify({
                    text: "Không thể kết nối đến server. Vui lòng thử lại sau!",
                    duration: 3000,
                    gravity: "top",
                    position: "right",
                    backgroundColor: "linear-gradient(to right, #ff5f6d, #ffc371)",
                    stopOnFocus: true,
                }).showToast();
            }
        });
    });
});
