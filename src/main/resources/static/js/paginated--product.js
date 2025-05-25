let currentPage = 0;
let pageSize = 5;
let totalPages = 0;

async function fetchProducts(page) {
    const url = `/api/v1/product/paginated?page=${page}&size=${pageSize}&sort=productID,asc`;

    try {
        const response = await fetch(url);

        if (!response.ok) {
            throw new Error("Network response was not ok");
        }

        const data = await response.json();

        renderTable(data.content);
        totalPages = data.totalPages;
        updatePaginationControls();
    } catch (error) {
        console.error("Error fetching products:", error);
        alert("Có lỗi xảy ra khi tải sản phẩm. Vui lòng thử lại sau.");
    }
}

function renderTable(products) {
    const tableBody = $("#product-table");
    tableBody.empty();

    products.forEach(product => {
        const row = `<tr>
            <td>${product.name}</td>
            <td><img src="${product.imageBase64 ? "data:image/png;base64," + product.imageBase64 : "placeholder.png"}" alt="${product.name}" style="width: 50px; height: auto;"></td>
            <td>${product.description}</td>
            <td>${product.price}</td>
            <td>${product.date_product}</td>
            <td>${product.categoryname}</td>
            <td>${product.tradeName}</td>
            <td>
                <button class='btn btn-info' onclick="showProductDetails(${product.id})"><i class='fas fa-info-circle'></i> Chi tiết</button>
            </td>
            <td>
                <button class='btn btn-primary' onclick="showUpdateProductForm(${product.id})"><i class='fas fa-pen-square'></i> Cập nhật</button>
            </td>
            <td>
                <button class='btn btn-danger' onclick="deleteProduct(${product.id})"><i class='fas fa-trash'></i> Xóa</button>
            </td>
        </tr>`;
        tableBody.append(row);
    });
}

function updatePaginationControls() {
    $("#first-page").toggleClass("disabled", currentPage === 0);
    $("#prev-page").toggleClass("disabled", currentPage === 0);
    $("#next-page").toggleClass("disabled", currentPage >= totalPages - 1);
    $("#last-page").toggleClass("disabled", currentPage >= totalPages - 1);

    $(".pagination li.page-number").remove();

    for (let i = 0; i < totalPages; i++) {
        const pageNumberItem = `<li class="page-item page-number ${i === currentPage ? "active" : ""}">
            <a class="page-link" href="#">${i + 1}</a>
        </li>`;
        $(pageNumberItem).insertBefore("#next-page");
    }
}

$(document).on("click", ".page-number", function (e) {
    e.preventDefault();
    const page = parseInt($(this).text()) - 1;
    if (page !== currentPage) {
        currentPage = page;
        fetchProducts(currentPage);
    }
});

$("#first-page").click((e) => {
    e.preventDefault();
    if (currentPage !== 0) {
        currentPage = 0;
        fetchProducts(currentPage);
    }
});

$("#prev-page").click((e) => {
    e.preventDefault();
    if (currentPage > 0) {
        currentPage--;
        fetchProducts(currentPage);
    }
});

$("#next-page").click((e) => {
    e.preventDefault();
    if (currentPage < totalPages - 1) {
        currentPage++;
        fetchProducts(currentPage);
    }
});

$("#last-page").click((e) => {
    e.preventDefault();
    if (currentPage !== totalPages - 1) {
        currentPage = totalPages - 1;
        fetchProducts(currentPage);
    }
});

fetchProducts(currentPage);