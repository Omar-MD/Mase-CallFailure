let rootUrl = "http://localhost:8081";


login = function() {
    var username = $('#username').val();
    var password = $('#password').val();

    console.log('Attempting login', username, password);
    $.ajax({
        type: 'POST',
        url: rootUrl + "/login",
        contentType: 'application/json',
        data: JSON.stringify({ "username": username, "password": password }),
        dataType: "json",
        success: function(res) {
            console.log(res);
            if (res.data == 'Admin') {
                alert('Admin login successful!');
                // We can Redirect to another page or perform other actions
            } else {
                alert('Login unsuccessful!');
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
