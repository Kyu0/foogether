const API_OPTIONS = {
    'post' : (data) => {
        return {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(data)
        };
    }
}