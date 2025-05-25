
document.addEventListener("DOMContentLoaded", function () {
    document.querySelectorAll(".vote-btn").forEach(btn => {
        btn.addEventListener("click", function () {
            const productId = this.getAttribute("data-productid");
            console.log("Product ID: ", productId);
            const voteModal = document.getElementById("voteModal");
            voteModal.style.display = "block";
        });
    });
    document.querySelector(".close").addEventListener("click", function () {
        document.getElementById("voteModal").style.display = "none";
    });
});