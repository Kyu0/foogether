const loginButton = document.getElementById('btn-login');

loginButton.addEventListener('click', () => {
    const data = {
        id: document.getElementById('username').value,
        password: document.getElementById('password').value,
    };
    
    // fetch('/api/v1/login', API_OPTIONS['post'](data))
    //     .then( (response) => response.json())
    //     .then( (response) => {
    //         console.log(response);
    //     });
});