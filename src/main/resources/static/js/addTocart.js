$(document).ready(function () {
        $(".product-remove").click(function (e) {
			
        e.preventDefault();
        
        var amount = 0;

        var productID = $(this).data('productid');
        var product = {
            productID: productID,
            amount: amount
        };
        $.post("/dossier-statistic/update--quantities", product).done(function(data, status) {
                if (data == "0") {
                    Toastify({
                        text: 'Có lỗi hoặc số lượng đang nhỏ hơn 0! Vui lòng thử lại...',
                        duration: 3000,
                        gravity: 'top',
                        position: 'right',
                        backgroundColor: 'red',
                        stopOnFocus: true,
                    }).showToast();
                } else if (data == "1") {
                    Toastify({
                        text: 'Cập nhật thành công!',
                        duration: 1500,
                        gravity: 'top',
                        position: 'right',
                        backgroundColor: 'green',
                        stopOnFocus: true,
                    }).showToast();
                } else if (data == "2") {
                    Toastify({
                        text: 'Sản phẩm đã được loại khỏi giỏ hàng!',
                        duration: 1500,
                        gravity: 'top',
                        position: 'right',
                        backgroundColor: 'green',
                        stopOnFocus: true,
                        callback: function() {
                            window.location.reload();
                        }
                    }).showToast();
                } else {
                    Toastify({
                        text: 'Có gì đó sai!',
                        duration: 3000,
                        gravity: 'top',
                        position: 'right',
                        backgroundColor: 'red',
                        stopOnFocus: true,
                    }).showToast();
                }
            });
    });
    $("#submitorders").click(function (e) {
        $.post("/dossier-statistic/orders")
            .done(function (data, status) {
                if (data == "0") {
                    Toastify({
                        text: 'Bạn phải đăng nhập!',
                        duration: 1000,
                        gravity: 'top',
                        position: 'right',
                        backgroundColor: 'red',
                        stopOnFocus: true,
                        callback: function () {
                            window.location = "/login";
                        }
                    }).showToast();
                } else if (data == "-1") {
                    Toastify({
                        text: 'Chưa chọn sản phẩm vào giỏ hàng!',
                        duration: 3000,
                        gravity: 'top',
                        position: 'right',
                        backgroundColor: 'red',
                        stopOnFocus: true,
                    }).showToast();
                } else if (data == "1") {
                    Toastify({
                        text: 'Đặt hàng thành công!',
                        duration: 1000,
                        gravity: 'top',
                        position: 'right',
                        backgroundColor: 'green',
                        stopOnFocus: true,
                        callback: function () {
                            window.location.reload();
                        }
                    }).showToast();
                } else {
                    Toastify({
                        text: 'Đặt hàng thất bại!',
                        duration: 3000,
                        gravity: 'top',
                        position: 'right',
                        backgroundColor: 'red',
                        stopOnFocus: true,
                    }).showToast();
                }
            })
            .fail(function (error) {
                Toastify({
                    text: 'Đặt hàng thất bại! Có lỗi xảy ra khi gửi yêu cầu đặt hàng!',
                    duration: 3000,
                    gravity: 'top',
                    position: 'right',
                    backgroundColor: 'red',
                    stopOnFocus: true,
                }).showToast();
            });
    });
        $('.quantityinput').change(function () {
            var amount = $(this).val();
            var price = $(this).parents('tr').find('.price').text();
            $(this).parents('tr').find('.total').text(price * amount);
        });
        
        $(".updateclick").click(function (e) {
            e.preventDefault();
            var amount = $(this).parents('tr').find('.quantityinput').val();
            var productID = $(this).data('productid');
            var product = {
                productID: productID,
                amount: amount
         };
            $.post("/dossier-statistic/update--quantities", product).done(function(data, status) {
                if (data == "0") {
                    Toastify({
                        text: 'Có lỗi hoặc số lượng đang nhỏ hơn 0! Vui lòng thử lại...',
                        duration: 3000,
                        gravity: 'top',
                        position: 'right',
                        backgroundColor: 'red',
                        stopOnFocus: true,
                    }).showToast();
                } else if (data == "1") {
                    Toastify({
                        text: 'Cập nhật thành công!',
                        duration: 1500,
                        gravity: 'top',
                        position: 'right',
                        backgroundColor: 'green',
                        stopOnFocus: true,
                    }).showToast();
                } else if (data == "2") {
                    Toastify({
                        text: 'Sản phẩm đã được loại khỏi giỏ hàng!',
                        duration: 1500,
                        gravity: 'top',
                        position: 'right',
                        backgroundColor: 'green',
                        stopOnFocus: true,
                        callback: function () {
                            window.location.reload();
                        }
                    }).showToast();
                } else {
                    Toastify({
                        text: 'Có lỗi xảy ra...^^ !',
                        duration: 3000,
                        gravity: 'top',
                        position: 'right',
                        backgroundColor: 'red',
                        stopOnFocus: true,
                    }).showToast();
                }
            });
        });
});

