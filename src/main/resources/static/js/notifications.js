const notifications = document.getElementById('notifications');

if (notifications != null) {
    notifications.addEventListener("click", () => {
        notifications.style.display = 'none';
    });

    setTimeout(function() {
        if (notifications != null) {
            notifications.style.display = 'none';
        }
    }, 3000)
}