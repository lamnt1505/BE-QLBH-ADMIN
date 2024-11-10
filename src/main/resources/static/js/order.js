$(document).ready(function() {
    var orderid = 0;
    $(".editmodal").click(function(e) {
        e.preventDefault();
        var status = $(this).parents('tr').find('.status').text();
        orderid = $(this).data('orderid');
        $(".optionorder").val(status).change();
    });
    $(".submitstatus").click(function(e) {
        e.preventDefault();
        var newstatus = $(".optionorder").val();
        $.ajax({
            url: "/dossier-statistic/--update-status",
            type: "POST",
            data: {
                orderid: orderid,
                status: newstatus
            },
            success: function(response) {
                var responseCode = parseInt(response);
                switch(responseCode) {
                    case 1:
                        Swal.fire({
                            icon: 'success',
                            title: 'Cập nhật trạng thái thành công!'
                        });
                        break;
                    case 0:
                        Swal.fire({
                            icon: 'error',
                            title: 'Không tìm thấy đơn hàng! Vui lòng thử lại...'
                        });
                        break;
                    case -1:
                        Swal.fire({
                            icon: 'error',
                            title: 'Số lượng trong kho không đủ!'
                        });
                        break;
                    case -2:
                        Swal.fire({
                            icon: 'error',
                            title: 'Sản phẩm chưa được cập nhật trong kho!'
                        });
                        break;
                    case -3:
                        Swal.fire({
                            icon: 'error',
                            title: 'Không tìm thấy chi tiết đơn hàng!'
                        });
                        break;
                    default:
                        Swal.fire({
                            icon: 'error',
                            title: 'Có lỗi xảy ra! Vui lòng thử lại.'
                        });
                }

                setTimeout(function() {
                    window.location.reload();
                }, 2000);
            },
            error: function(xhr, status, error) {
                console.error("Error:", error);
                Swal.fire({
                    icon: 'error',
                    title: 'Lỗi kết nối',
                    text: 'Không thể kết nối đến server. Vui lòng thử lại sau!'
                });
            }
        });
    });
});