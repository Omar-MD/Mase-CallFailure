let sysAdminUrl = "http://localhost:8081/sysadmin/accounts";  


createAccount = function() {
    var username = $('#new-user-username').val();
    var password = $('#new-user-password').val();
    var role = $('#new-user-role').val();

    console.log('Attempting account creation', username, password, role);
    $('#accountMsg').remove();
    $.ajax({
        type: 'POST',
        url: sysAdminUrl,
        contentType: 'application/json',
        data: JSON.stringify({"username": username, "password": password, "role": role}),
        dataType: "json",
        success: function(data) {
            
            console.log(data);
            if(data.data != null) {
                $('#create-user-form').after("<div id=\"accountMsg\" class=\"alert alert-success\"><strong>Success!</strong> New User Account Created</div>").show();
                $('#created-users').append(`
                    <li class="list-group-item">
                        <div class="d-flex justify-content-between">
                            <div>
                                <strong>ID:</strong> ${data.data.id}
                            </div>
                            <div>
                                <strong>Username:</strong> ${data.data.username}
                            </div>
                            <div>
                                <strong>Role:</strong> ${data.data.role}
                            </div>
                        </div>
                    </li>`
                )
            }

            if(data.error != null) {
                $('#create-user-form').after(
                    `<div id=\"accountMsg\" class=\"alert alert-danger\"><strong>Error!</strong> ${data.error.errorMsg}<br/>${data.error.details}</div>`
                ).show();
            }
        },
        error: function() {
            alert('Error during request.');
        }
    });
};


$(document).ready(function() {
    $('#create-account-btn').on('click', function(event) {
        console.log("Account Creation");
        event.preventDefault();
        createAccount();
    });

    $('#import-data-sidebar').click(function(event) {
        console.log("Import Data");
        $('#sys-adm-import-window').removeClass('d-none');
        $('#sys-adm-create-user-window').addClass('d-none');
    });

    $('#create-user-sidebar').click(function(event) {
        console.log("Create User");
        $('#sys-adm-import-window').addClass('d-none');
        $('#sys-adm-create-user-window').removeClass('d-none');
    });
});
