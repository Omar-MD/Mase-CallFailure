let rootUrl = "http://localhost:8081/accounts";  


createAccount = function() {
    var username = $('#username').val();
    var password = $('#password').val();
    var role = $('#role').val();

    console.log('Attempting account creation', username, password, role);
    $.ajax({
        type: 'POST',
        url: rootUrl,
        contentType: 'application/json',
        data: JSON.stringify({"id": null, "username": username, "password": password, "role": role}),
        dataType: "json",
        success: function(data) {
            console.log(data);
            alert('New Account created successful');
        },
        error: function() {
            alert('Error during request.');
        }
    });
};


$(document).ready(function() {
    $('#createAccount').on('click', function(event) {
        console.log("Account Creation");
        event.preventDefault();
        // hide error msg at new login attempt
        createAccount();
    });
});
