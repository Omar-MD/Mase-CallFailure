const rootUrl = "http://localhost:8081";


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
                    loadContentForRole(RoleType.ADMIN, username);
                    showHome();
                    break;
                case 'CUSTOMER_SERVICE_REP':
                    loadContentForRole(RoleType.CUSTOMER_SERVICE_REP, username);
                    showHome();
                    break;
                case 'NETWORK_ENGINEER':
                    loadContentForRole(RoleType.NETWORK_ENGINEER, username);
                    showHome();
                    break;
                case 'SUPPORT_ENGINEER':
                    loadContentForRole(RoleType.SUPPORT_ENGINEER, username);
                    showHome();
                    break;
                default:
                    $('#login-card')
                    .append("<div id=\"errorMsg\" class=\"alert alert-danger\"><strong>Error!</strong> Incorrect Username or password</div>").show();
                    break;
            }
        },
        error: function() {
            alert('Error during request. Incorrect username or password');
        }
    });
};

const showHome = function() {
    $('#login-section').addClass("d-none");
    $('#home-section').removeClass("d-none");
}