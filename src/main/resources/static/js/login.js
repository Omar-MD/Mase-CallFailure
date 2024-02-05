let rootUrl = "http://localhost:8081";


const login = function() {
    let username = $('#username').val();
    let password = $('#password').val();

    $.ajax({
        type: 'POST',
        url: rootUrl + "/login",
        contentType: 'application/json',
        data: JSON.stringify({ "username": username, "password": password }),
        dataType: "json",
        success: function(response) {
            switch (response.data) {
                case 'SYSTEM_ADMINISTRATOR':
                    console.log("At switch..")
                    loadContentForRole(RoleType.ADMIN);
                    showHome();
                    break;
                case 'CUSTOMER_SERVICE_REP':
                    loadContentForRole(RoleType.CUSTOMER_SERVICE_REP);
                    showHome();
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

const showHome = function() {
    $('#login-container').addClass("d-none");
    $('#home-section').removeClass("d-none");
}