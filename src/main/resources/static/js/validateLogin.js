function clear() {
    document.getElementById("accountName").innerHTML = "";
    document.getElementById("accountName").innerHTML = "";
}

function checkempty1(form) {
    clear();
    if (form.accountName.value.trim() == "") {
        document.getElementById("accountName").innerHTML = "Not to empty account name!";
        return false;
    }
    else if (form.accountPass.value.trim() == "") {
        document.getElementById("accountPass").innerHTML = "Not to empty password!";
        return false;
    }
    else {
        return true;
    }
}