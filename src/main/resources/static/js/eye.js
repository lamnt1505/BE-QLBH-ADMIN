const passwordField = document.getElementById("accountPass");
const togglePassIcon = document.getElementById("togglePassIcon");

togglePassIcon.addEventListener("click", function() {
    const currentType = passwordField.getAttribute("type");
    if (currentType === "password") {
        passwordField.setAttribute("type", "text");
        togglePassIcon.classList.remove("fa-eye");
        togglePassIcon.classList.add("fa-eye-slash");
    } else {
        passwordField.setAttribute("type", "password");
        togglePassIcon.classList.remove("fa-eye-slash");
        togglePassIcon.classList.add("fa-eye");
    }
});