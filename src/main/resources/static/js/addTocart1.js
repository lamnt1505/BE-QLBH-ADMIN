$(document).ready(function () {
    function showToast(text, backgroundColor, duration, callback) {
        Toastify({
            text: text,
            duration: duration,
            gravity: 'top',
            position: 'right',
            backgroundColor: backgroundColor,
            stopOnFocus: true,
            callback: callback
        }).showToast();
    }

    $(".product-remove").click(function (e) {
        e.preventDefault();
        var amount = 0;
        var productID = $(this).data('productid');
        var product = {
            productID: productID,
            amount: amount
        };
        $.post("/updatequantities", product).done(function(data) {
            switch(data) {
                case "INVALID_AMOUNT":
                    showToast('Có lỗi hoặc số lượng đang nhỏ hơn 0! Vui lòng thử lại...', 'red', 3000);
                    break;
                case "QUANTITY_UPDATED":
                    showToast('Cập nhật thành công!', 'green', 1500);
                    break;
                case "PRODUCT_REMOVED":
                    showToast('Sản phẩm đã được loại khỏi giỏ hàng!', 'green', 1500, function() {
                        window.location.reload();
                    });
                    break;
                default:
                    showToast('Có gì đó sai!', 'red', 3000);
            }
        });
    });

    $("#submitorders").click(function (e) {
        $.post("/orders")
            .done(function (data) {
                switch(data) {
                    case "LOGIN_REQUIRED":
                        showToast('Bạn phải đăng nhập!', 'red', 1000, function () {
                            window.location = "/login";
                        });
                        break;
                    case "CART_EMPTY":
                        showToast('Chưa chọn sản phẩm vào giỏ hàng!', 'red', 3000);
                        break;
                    case "ORDER_SUCCESS":
                        showToast('Đặt hàng thành công!', 'green', 1000, function () {
                            window.location.reload();
                        });
                        break;
                    default:
                        showToast('Đặt hàng thất bại!', 'red', 3000);
                }
            })
            .fail(function () {
                showToast('Đặt hàng thất bại! Có lỗi xảy ra khi gửi yêu cầu đặt hàng!', 'red', 3000);
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
        
        $.post("/updatequantities", product).done(function(data) {
            switch(data) {
                case "INVALID_AMOUNT":
                    showToast('Có lỗi hoặc số lượng đang nhỏ hơn 0! Vui lòng thử lại...', 'red', 3000);
                    break;
                case "QUANTITY_UPDATED":
                    showToast('Cập nhật thành công!', 'green', 1500);
                    break;
                case "PRODUCT_REMOVED":
                    showToast('Sản phẩm đã được loại khỏi giỏ hàng!', 'green', 1500, function () {
                        window.location.reload();
                    });
                    break;
                default:
                    showToast('Có lỗi xảy ra...^^ !', 'red', 3000);
            }
        });
    });
});