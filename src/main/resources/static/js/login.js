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
        success: function(response) {
            console.log(response);
            switch (response.data) {
                case 'SYSTEM_ADMINISTRATOR':
                    $('#login-container').addClass("d-none");
                    $('#home-section').removeClass("d-none");
                    break;
                case 'CUSTOMER_SERVICE_REP':
                    alert('CUSTOMER_SERVICE_REP login successful!');
                    break;
                case 'NETWORK_ENGINEER':
                    alert('NETWORK_ENGINEER login successful!');
                    break;
                case 'SUPPORT_ENGINEER':
                    alert('SUPPORT_ENGINEER login successful!');
                    break;
                default:
                    $('#login-card').append("<div id=\"errorMsg\" class=\"alert alert-danger\"><strong>Error!</strong> Incorrect Username or password</div>").show();
                    break;
            }
        },
        error: function() {
            alert('Error during request. Incorrect username or password');
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
    // let loggedIn = sessionStorage.getItem('loggedIn');
    // if (loggedIn === 'true') {
    //     showHomeSection();      // Show SysAdmin section if logged in
    // } else {
    //     showLoginSection();     // Show login section if not logged in
    // }

    $('#loginSubmit').on('click', function(event) {
        console.log("Attempting login");
        event.preventDefault();
        login();
    });
});
