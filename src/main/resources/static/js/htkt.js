const firebaseConfig = {
    apiKey: "AIzaSyCC4_5CItunwdlQF7fzLO_Ftx2hVfAAEak",
    databaseURL: "https://htkt-33179-default-rtdb.firebaseio.com",
    projectId: "htkt-33179",
};
firebase.initializeApp(firebaseConfig);
const db = firebase.database();
document.addEventListener("DOMContentLoaded", function () {
    const dropdown = document.getElementById("chat-dropdown");

    db.ref("chat/conversations").limitToLast(10).on("child_added", function (snapshot) {
        const msg = snapshot.val();
        if (!msg) return;

        const time = new Date(msg.timestamp).toLocaleString("vi-VN");
        const messageHtml = `
                <a class="dropdown-item d-flex align-items-center message-item" data-sender="${msg.sender}" href="#">
                    <div class="dropdown-list-image mr-3">
                        <img class="rounded-circle" src="https://source.unsplash.com/random/60x60?sig=${Math.floor(Math.random() * 100)}" alt="...">
                        <div class="status-indicator bg-success"></div>
                    </div>
                    <div class="font-weight-bold">
                        <div class="text-truncate">${msg.content}</div>
                        <div class="small text-gray-500">${msg.sender} · ${time}</div>
                    </div>
                </a>
            `;
        dropdown.insertAdjacentHTML("beforeend", messageHtml);
    });
});