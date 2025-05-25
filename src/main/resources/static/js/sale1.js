$(document).ready(function () {
    $.ajax({
        url: "/api/v1/product/sales",
        type: 'GET',
        dataType: 'json',
        success: function (data) {
            let salesStatistics = $("#salesStatistics");
            salesStatistics.empty();

            let totalQuantitySold = data.reduce((sum, product) => sum + product.quantitysold, 0);

            console.log("Total Quantity Sold:", totalQuantitySold);

            data.forEach(function (product) {
                let percent = totalQuantitySold > 0
                    ? (product.quantitysold / totalQuantitySold) * 100
                    : 0;

                console.log("Product:", product.name, "Quantity Sold:", product.quantitysold, "Percent:", percent);

                let progressBarClass = "";
                if (percent < 30) progressBarClass = "bg-danger";
                else if (percent < 60) progressBarClass = "bg-warning";
                else if (percent < 90) progressBarClass = "bg-info";
                else progressBarClass = "bg-success";

                // Tạo HTML động
                let productHtml = `
                    <h4 class="small font-weight-bold">${product.name}
                        <span class="float-right">${percent.toFixed(1)}%</span>
                    </h4>
                    <div class="progress mb-4">
                        <div class="progress-bar ${progressBarClass}" role="progressbar" 
                            style="width: ${percent.toFixed(1)}%" 
                            aria-valuenow="${percent.toFixed(1)}" 
                            aria-valuemin="0" aria-valuemax="100">
                        </div>
                    </div>
                `;

                // Thêm vào #salesStatistics
                salesStatistics.append(productHtml);
            });
        },
        error: function (xhr) {
            console.error("Error fetching sales data:", xhr);
            Toastify({
                text: "Không thể tải dữ liệu thống kê.",
                duration: 3000,
                gravity: "top",
                position: "right",
                backgroundColor: "linear-gradient(to right, #ff5f6d, #ffc371)",
            }).showToast();
        }
    });
});
