function compareProducts() {
    if (comparedProducts.length < 2) {
        alert("Vui lòng chọn ít nhất 2 sản phẩm để so sánh.");
        return;
    }
    var product1 = listProduct.find(item => item.id === comparedProducts[0]);
    var product2 = listProduct.find(item => item.id === comparedProducts[1]);

    if (!product1 || !product2) {
        return;
    }

    var compareHtml = `
        <div class="compare-result" style="position: relative;">
            <button id="closeCompareModal" style="
                position: absolute;
                top: 10px;
                right: 10px;
                font-size: 24px;
                background: transparent;
                border: none;
                cursor: pointer;
                color: #333;
            ">&times;</button>

            <h2 style="text-align: center; margin-top: 40px;">So sánh sản phẩm</h2>
            <div class="product-info" style="display: flex; gap: 16px; justify-content: space-between;">
                <div class="product" style="flex: 1; border: 1px solid #ddd; border-radius: 8px; padding: 16px; box-shadow: 0 2px 8px rgba(0,0,0,0.1);">
                    <h3 style="text-align: center;">${product1.name}</h3>
                    <img class="product-image" src="data:image/png;base64,${product1.imageBase64}" alt="Product Image" style="max-width: 100%; height: auto; border-radius: 8px; object-fit: cover;">
                    <p>Giá: ${product1.price}</p>
                    <p>Mô tả: ${product1.description}</p>
                    <p>Loại: ${product1.categoryname}</p>
                    <p>Sản Phẩm: ${product1.tradeName}</p>
                    <h2>CHI TIẾT</h2>
                    <p>Camera: ${product1.productCamera}</p>
                    <p>Màn Hình: ${product1.productScreen}</p>
                    <p>Wifi: ${product1.productWifi}</p>
                    <p>Bluetooth: ${product1.productBluetooth}</p>
                    <h2>PHIÊN BẢN</h2>
                    <p>Bộ Nhớ: ${product1.memory}</p>
                    <p>Màu: ${product1.color}</p>
                </div>
                <div class="product" style="flex: 1; border: 1px solid #ddd; border-radius: 8px; padding: 16px; box-shadow: 0 2px 8px rgba(0,0,0,0.1);">
                    <h3 style="text-align: center;">${product2.name}</h3>
                    <img class="product-image" src="data:image/png;base64,${product2.imageBase64}" alt="Product Image" style="max-width: 100%; height: auto; border-radius: 8px; object-fit: cover;">
                    <p>Giá: ${product2.price}</p>
                    <p>Mô tả: ${product2.description}</p>
                    <p>Loại: ${product2.categoryname}</p>
                    <p>Sản Phẩm: ${product2.tradeName}</p>
                    <h2>CHI TIẾT</h2>
                    <p>Camera: ${product2.productCamera}</p>
                    <p>Màn Hình: ${product2.productScreen}</p>
                    <p>Wifi: ${product2.productWifi}</p>
                    <p>Bluetooth: ${product2.productBluetooth}</p>
                    <h2>PHIÊN BẢN</h2>
                    <p>Bộ Nhớ: ${product2.memory}</p>
                    <p>Màu: ${product2.color}</p>
                </div>
            </div>
        </div>`;

    $('#compareModal .modal-content').html(compareHtml);
    $('#compareModal').modal('show');

    $(document).on('click', '#closeCompareModal', function () {
        $('#compareModal').modal('hide');
        comparedProducts = [];
    });
}
