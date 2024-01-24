let rootUrl = "http://localhost:8080/login";  


login = function() {
    var username = $('#username').val();
    var password = $('#password').val();

    console.log('Attempting login', username, password);
    $.ajax({
        type: 'POST',
        url: rootUrl,
        contentType: 'application/json',
        data: JSON.stringify({ username: username, password: password }),
        dataType: "json",
        success: function(data) {
            if (data.success) {
                alert('Login successful!');
                // We can Redirect to another page or perform other actions
            } else {
                $('#errorMsg').text('Invalid username or password.').show();
            }
        },
        error: function() {
            $('#errorMsg').text('Error during request.').show();
        }
    });
};


$(document).ready(function() {
    $('#loginForm').on('submit', function(event) {
        event.preventDefault();
        // hide error msg at new login attempt
        login();
    });
});
