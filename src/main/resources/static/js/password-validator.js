const password = document.getElementById('password');
const confirm_password = document.getElementById('confirm_password');

function validatePassword() {
  if (password.value != confirm_password.value) {
    password.setCustomValidity("Hasła się nie zgadzają.");
  } else {
    password.setCustomValidity('');
  }
}

password.onchange = validatePassword;
confirm_password.onkeyup = validatePassword;