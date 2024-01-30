let rootUrl = "http://localhost:8081/login";  


login = function() {
    var username = $('#username').val();
    var password = $('#password').val();

    console.log('Attempting login', username, password);
    $.ajax({
        type: 'POST',
        url: rootUrl,
        contentType: 'application/json',
        data: JSON.stringify({ "username": username, "password": password }),
        dataType: "json",
        success: function(data) {
            console.log(data);
            alert('Admin login successful!');
            if (data.role == 'Admin') {
                alert('SYSTEM_ADMINISTRATOR login successful!');
                // We can Redirect to another page or perform other actions
            } else {
                alert('Login successful!');
                // $('#errorMsg').text('Invalid username or password.').show();
            }
        },
        error: function() {
            alert('Error during request. Incorrect username or password');
            // $('#errorMsg').text('Error during request.').show();
        }
    });
};


$(document).ready(function() {
    $('#loginSubmit').on('click', function(event) {
        console.log("Attempting login");
        event.preventDefault();
        // hide error msg at new login attempt
        login();
    });
});
