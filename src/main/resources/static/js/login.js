let rootUrl = "http://localhost:8081";


login = function() {
    let username = $('#username').val();
    let password = $('#password').val();
    
    $.ajax({
        type: 'POST',
        url: rootUrl + "/login",
        contentType: 'application/json',
        data: JSON.stringify({ "username": username, "password": password }),
        dataType: "json",
        success: function(res) {
            console.log(res);
            if (res.status == "Success") {
                // Navigate to Role page
                switch (res.data) {
                    case "SYSTEM_ADMINISTRATOR":
                        console.log("navigating to SysAdmin");
                        showHomeSection();
                        $('#banner-text').text('System Admin Import Page');
                        break;
                    default:
                        break;
                }
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

function showHomeSection() {
    $('#login-section').hide();
    $('#home-section').show();
    sessionStorage.setItem('loggedIn', 'true');

}

function showLoginSection() {
    $('#home-section').hide();
    $('#login-section').show();
    sessionStorage.setItem('loggedIn', 'false');
}

$(document).ready(function() {
    let loggedIn = sessionStorage.getItem('loggedIn');
    if (loggedIn === 'true') {
        showHomeSection();      // Show SysAdmin section if logged in
    } else {
        showLoginSection();     // Show login section if not logged in
    }

    $('#loginSubmit').on('click', function(event) {
        console.log("Attempting login");
        event.preventDefault();
        login();
    });
});
