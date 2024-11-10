let currentPage = 0;
let pageSize = 5;
let totalPages = 0;

async function fetchCategories(page) {
    const url = `/api/v1/category/paginated?page=${page}&size=${pageSize}&sort=categoryID,asc`;
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
        console.error("Error fetching categories:", error);
        alert("Có lỗi xảy ra khi tải danh mục. Vui lòng thử lại sau.");
    }
}

function renderTable(categories) {
    const tableBody = $("#category-table");
    tableBody.empty();

    categories.forEach(category => {
        const row = `<tr>
            <td>${category.name}</td>
            <td>
                <button class='btn btn-info' onclick="showCategoryDetails(${category.id})"><i class='fas fa-info-circle'></i>Chi tiết</button>
            </td>
            <td>
                <button class='btn btn-danger' onclick="deleteCategory(${category.id})"><i class='fas fa-trash'></i> Xóa</button>
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
        fetchCategories(currentPage);
    }
});

$("#first-page").click((e) => {
    e.preventDefault();
    if (currentPage !== 0) {
        currentPage = 0;
        fetchCategories(currentPage);
    }
});

$("#prev-page").click((e) => {
    e.preventDefault();
    if (currentPage > 0) {
        currentPage--;
        fetchCategories(currentPage);
    }
});

$("#next-page").click((e) => {
    e.preventDefault();
    if (currentPage < totalPages - 1) {
        currentPage++;
        fetchCategories(currentPage);
    }
});

$("#last-page").click((e) => {
    e.preventDefault();
    if (currentPage !== totalPages - 1) {
        currentPage = totalPages - 1;
        fetchCategories(currentPage);
    }
});

fetchCategories(currentPage);
