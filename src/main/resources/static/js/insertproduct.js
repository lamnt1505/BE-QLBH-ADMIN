$(document).on('click', '.procart', function (e) {
    e.preventDefault();
    console.log("Clicked");
    var amount = 1;
    var productID = $(this).data('productid');
    $.post("/dossier-statistic/insert-product", {
        productID: productID,
        amount: 1,
    }, function (data, status) {
        Toastify({
            text: 'Thêm sản phẩm vào giỏ hàng thành công!',
            duration: 1500,
            gravity: 'top',
            position: 'right',
            backgroundColor: 'green',
            stopOnFocus: true,
        }).showToast();
        $.get("/dossier-statistic/cart/quantity", function(cartQuantity) {
            $("#cartQuantity").text(cartQuantity);
        });
    })
        .fail(function (error) {
            Toastify({
                text: 'Đã xảy ra lỗi khi thêm sản phẩm vào giỏ hàng',
                duration: 3000,
                gravity: 'top',
                position: 'right',
                backgroundColor: 'red',
                stopOnFocus: true,
            }).showToast();
        });
});