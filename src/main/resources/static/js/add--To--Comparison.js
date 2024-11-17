var listProduct = [];
var comparedProducts = [];
function addToComparison(id) {
    var product = listProduct.find(item => item.id === id);
    if (!product) {
        alert('Không tìm thấy sản phẩm cần so sánh.');
        return;
    }
    var categoryID = product.categoryID;
    if (!compareProductTypes(categoryID)) {
        console.log('Sản phẩm có id ' + id + ' không thuộc loại cần so sánh.');
        return;
    }
    if (comparedProducts.includes(id)) {
        alert('Sản phẩm đã tồn tại trong danh sách so sánh.');
        return;
    }
    if (comparedProducts.length > 0) {
        var firstProduct = listProduct.find(item => item.id === comparedProducts[0]);
        if (firstProduct && firstProduct.categoryID !== categoryID) {
            comparedProducts = [];
        }
    }
    comparedProducts.push(id);
    $('#compareModal').modal('show');
    displayComparedProducts();
}
function displayComparedProducts() {
    var container = $('#compareModal .modal-content');
    container.empty();

    for (var i = 0; i < comparedProducts.length; i++) {
        var id = comparedProducts[i];
        var product = listProduct.find(item => item.id === id);
        if (product) {
            var html = '<div class="product" style="width: 100%; position: relative; border: 1px solid #ddd; padding: 16px; margin-bottom: 16px; border-radius: 8px; box-shadow: 0 2px 8px rgba(0,0,0,0.1);">' +
                '<h2 class="product-name" style="text-align: center; font-size: 16px; margin-bottom: 8px;">' + product.name + '</h2>' +
                '<div style="display: flex; justify-content: center;">' +
                '<img class="product-image" src="data:image/png;base64,' + product.imageBase64 + '" alt="Product Image" style="max-width: 100%; height: auto; border-radius: 8px; object-fit: cover;">' +
                '</div>' +
                '</div>';
            container.append(html);
        }
    }
    if (comparedProducts.length === 2) {
        var compareDiv = '<div class="compare-section" style="position: fixed; top: 16px; right: 16px;">' +
            '<button class="btn btn-primary cmprBtn compareTwoProducts">So Sánh</button>' +
            '</div>';
        container.append(compareDiv);
    }
}
function getProductDetailsFromAPI(id) {
    $.ajax({
        url: '/api/v1/product/' + id + '/get',
        type: 'GET',
        dataType: 'json',
        success: function (product) {
            if (product) {
                var categoryID = product.categoryID;
                var isExistInList = compareProductTypes(categoryID);
                if (isExistInList) {
                    if (listProduct.some(item => item.id === id)) {
                        addToComparison(id);
                    } else {
                        listProduct.push(product);
                        addToComparison(id);
                    }
                } else {
                    console.log('Sản phẩm có ' + id + ' không thuộc loại cần so sánh.');
                }
            } else {
                console.log('Không tìm thấy sản phẩm có id ' + id);
            }
        },
        error: function () {
            console.log('Đã xảy ra lỗi khi tìm nạp dữ liệu sản phẩm.');
        }
    });
}
function compareProductTypes(categoryID) {
    var productTypes = [1, 2];
    return true;
}
$(document).on('click', '.addToCompare', function() {
    var id = $(this).data('productid');
    getProductDetailsFromAPI(id);
});