const btnRegister = document.getElementById('btn-register-submit');

btnRegister.addEventListener('click', () => {
    const data = {
        id: document.getElementById('input-id').value,
        password: document.getElementById('input-password').value,
        name: document.getElementById('input-name').value,
        email: document.getElementById('input-email').value,
        birthday: document.getElementById('input-birthday').value,
        phoneNumber: document.getElementById('input-phone-number').value,
        role: 'OWNER',
    };

    fetch(`/api/v1/user`, API_OPTIONS['post'](data));
});