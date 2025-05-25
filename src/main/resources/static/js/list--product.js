$(document).ready(function () {
    var pageSize = 6;
    var allProducts = [];

    function formatCurrency(value) {
        return new Intl.NumberFormat('vi-VN', {
            style: 'currency',
            currency: 'VND',
            minimumFractionDigits: 0
        }).format(value);
    }

    $('#pageSize').change(function () {
        pageSize = $(this).val();
        renderPagination();
    });

    function renderPagination() {
        $.ajax({
            url: "/api/v1/product/Listgetall",
            type: "GET",
            dataType: "json",
            success: function (data) {
                allProducts = data;
                var table = $("#product-table");
                table.empty();
                var currentPageProducts = allProducts.slice(0, pageSize);
                $.each(currentPageProducts, function (index, product) {
                    var rawPrice = product.price;
                    var formattedPrice = formatCurrency(Number.isNaN(Number(rawPrice)) ? 0 : Number(rawPrice));
                    console.log("Raw Price: ", rawPrice);
                    console.log("Formatted Price: ", formattedPrice);
                    var row = "<tr>" +
                        "<td>" + product.name + "</td>" +
                        "<td>" +
                        "<img src='data:image/png;base64," + product.imageBase64 + "' width='60' height='60'>" +
                        "</td>" +
                        "<td>" + product.description + "</td>" +
                        "<td>" + formattedPrice + "</td>" +
                        "<td>" + product.date_product + "</td>" +
                        "<td>" + product.categoryname + "</td>" +
                        "<td>" + product.tradeName + "</td>" +
                        "<input type='hidden' name='productId' value='" + product.id + "'>" +
                        "<td>" +
                        "<button class='btn btn-info' onclick=\"showProductDetails(" + product.id + ")\"><i class='fas fa-info-circle'></i> Chi tiết</button>" +
                        "</td>" +
                        "<td>" +
                        "<button class='btn btn-primary' onclick=\"showUpdateProductForm(" + product.id + ")\"><i class='fas fa-pen-square'></i> Cập nhật</button>" +
                        "</td>" +
                        "<td>" +
                        "<button class='btn btn-danger' onclick=\"deleteProduct(" + product.id + ")\"><i class='fas fa-trash'></i> Xóa</button>" +
                        "</td>" +
                        "</tr>";
                    table.append(row);
                    console.log("Formatted Price: ", formattedPrice);
                });
            },
            error: function () {
                alert("Error fetching products.");
            }
        });
    }
    renderPagination();
});
