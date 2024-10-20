$(document).ready(function () {
    var pageSize = 5; // Default page size

    // Event for changing the page size
    $('#pageSize').change(function () {
        pageSize = $(this).val();
        renderPagination(); // Call to re-render pagination
    });

    function renderPagination() {
        $.ajax({
            url: "/api/v1/storage/Listgetall",
            type: "GET",
            dataType: "json",
            success: function (data) {
                var table = $("#storage-table");
                table.empty();


                $.each(data.slice(0, pageSize), function (index, storage) {
                    var productName = storage.product_name ? storage.product_name : "N/A";
                    var row = "<tr>" +
                        "<td>" + (storage.users ? storage.users : "N/A") + "</td>" +
                        "<td>" + productName + "</td>" +
                        "<td>" + (storage.createDate ? new Date(storage.createDate).toLocaleDateString() : "N/A") + "</td>" +
                        "<td>" + (storage.updateDate ? new Date(storage.updateDate).toLocaleDateString() : "N/A") + "</td>" +
                        "<td>" + (storage.quantity ? storage.quantity : "N/A") + "</td>" + // Show quantity
                        "<td>" +
                        "<button class='btn btn-info' onclick=\"showStorageDetails(" + storage.id + ")\">Chi tiết</button>" +
                        "</td>" +
                        "<td>" +
                        "<button class='btn btn-primary' onclick=\"showUpdateStorageForm(" + storage.id + ")\">Cập nhật</button>" +
                        "</td>" +
                        "<td>" +
                        "<button class='btn btn-danger' onclick=\"deleteStorage(" + storage.id + ")\">Xóa</button>" +
                        "</td>" +
                        "</tr>";
                    table.append(row);
                });
            },
            error: function () {
                alert("Error while retrieving categories.");
            }
        });
    }
    renderPagination();
});
