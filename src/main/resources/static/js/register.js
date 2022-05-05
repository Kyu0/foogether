const btnRegister = document.getElementById('btn-register-submit');

btnRegister.addEventListener('click', () => {
    // TODO : 서버로 전송
    const data = {
        id: document.getElementById('input-id').value,
        password: document.getElementById('input-password').value,
        email: document.getElementById('input-email').value,
        birthday: document.getElementById('input-birthday').value,
        phoneNumber: document.getElementById('input-phone-number').value
    };

    fetch(`/user`, API_OPTIONS['post'](data));
});