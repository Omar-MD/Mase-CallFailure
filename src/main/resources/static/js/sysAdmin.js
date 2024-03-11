'use strict';

const createAccount = function() {
    let username = $('#new-user-username').val();
    let password = $('#new-user-password').val();
    let role = $('#new-user-role').val();

    $('#accountMsg').remove();

    $.ajax({
        type: 'POST',
        url: rootUrl + "/sysadmin/accounts",
        contentType: 'application/json',
        headers: {"Authorization":  'Bearer ' + localStorage.getItem('token')},
        data: JSON.stringify({ "username": username, "password": password, "role": role }),
        dataType: "json",
        success: function(res) {

            if (res.data != null) {
                $('#create-user-form').after("<div id=\"accountMsg\" class=\"alert alert-success\"><strong>Success!</strong> New User Account Created</div>").show();
                $('#created-users').append(`
                    <li class="list-group-item">
                        <div class="d-flex justify-content-between">
                            <div>
                                <strong>Username:</strong> ${res.data.username}
                            </div>
                            <div>
                                <strong>Role:</strong> ${res.data.role}
                            </div>
                        </div>
                    </li>`
                )
            }

            if (res.error != null) {
                $('#create-user-form').after(
                    `<div id="accountMsg" class="alert alert-danger"><strong>Error!</strong> ${res.error.errorMsg}<br/>${res.error.details}</div>`
                ).show();
            }
        },
        error: function() {
            $('#create-user-form').after(
                `<div id="accountMsg" class="alert alert-danger"><strong>Error!</strong> ${res.error.errorMsg}<br/>${res.error.details}</div>`
            ).show();
        }
    });
};


const importDataset = function() {
    let filename = $('#filename').val();
    let importMsg = $('#importMsg');

    importMsg.hide();

    showProgressBar();
    $.ajax({
        type: 'POST',
        url: rootUrl + "/sysadmin/import",
        contentType: 'application/json',
        headers: {"Authorization":  'Bearer ' + localStorage.getItem('token')},
        data: JSON.stringify({ "filename": filename }),
        dataType: "json",
        success: function(res) {
            hideProgressBar();
            if (res.status == "Success") {
                importMsg.removeClass().addClass("alert alert-success").html(`<strong>Success!</strong><br/>${res.data}`).show();

            } else {
                hideProgressBar();
                importMsg.removeClass().addClass("alert alert-danger").html(`<strong>Error!</strong> ${res.error.errorMsg}<br/>${res.error.details}`).show();
            }
        },
        error: function() {
            hideProgressBar();
            importMsg.removeClass().addClass("alert alert-danger").html("<strong>Error!</strong> Unexpected Import Error<br/>").show();
        }
    });
};


function handleFileSelection(input) {
    if (input?.files[0]) {
        let filename = input.files[0].name;
        $('#filename').val(filename);
    }
}

const showProgressBar = function() {
    $('#progressBarBG').show();
    $('#progressBarFG').show();
}

const hideProgressBar = function() {
    $('#progressBarBG').hide();
    $('#progressBarFG').hide();
}

const checkImportStatus = function() {
    let importStatus = $('#autoImportSummary');
    $.ajax({
        type: 'GET',
        url: rootUrl + "/sysadmin/auto-import-status",
        contentType: 'application/json',
        headers: {"Authorization":  'Bearer ' + localStorage.getItem('token')},
        success: function(res) {
            if (res.status == "Success") {
                importStatus.html(res.data);
            }
        },
        error: function() {
            importStatus.removeClass().addClass("alert alert-danger").html("<strong>Error!</strong> Unexpected Import Error<br/>").show();
        }
    });
}