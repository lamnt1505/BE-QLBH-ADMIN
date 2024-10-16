
var regex_pass = /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{6,20}$/;
function clear() {
    document.getElementById("username").innerHTML = "";
    document.getElementById("password").innerHTML = "";
}
function checkempty(form) {
    clear();
    if (form.accountName.value.trim() == "") {
        document.getElementById("username").innerHTML = "Not to empty account name!";
        return false;
    }
    else if (form.accountPass.value.trim() == "") {
        document.getElementById("password").innerHTML = "Not to empty password!";
        return false;
    }
    else if (!(form.accountPass.value.match(regex_pass))) {
        document.getElementById("password").innerHTML =
            "Passwords between 6 and 20 characters contain at least one number, one uppercase letter, and one lowercase letter!";
        return false;
    }
    else {
        return true;
    }
}