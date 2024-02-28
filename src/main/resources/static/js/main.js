'use strict';

const rootUrl = "http://localhost:8081";

const RoleType = {
    ADMIN: 'SYSTEM_ADMINISTRATOR',
    CUSTOMER_SERVICE_REP: 'CUSTOMER_SERVICE_REP',
    NETWORK_ENGINEER: 'NETWORK_ENGINEER',
    SUPPORT_ENGINEER: 'SUPPORT_ENGINEER'
}

const login = function() {
    let username = $('#username').val();
    let password = $('#password').val();
    let errorMsg = $('#errorMsg');

    errorMsg.remove();

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
                    errorMsg.remove();
                    $('#login-card').append("<div id=\"errorMsg\" class=\"alert alert-danger\"><strong>Error!</strong> Incorrect Username or password</div>").show();
                    break;
            }
        },
        error: function() {
            alert('Error during request. Incorrect username or password');
        }
    });
};

const loadContentForRole = function(role, username) {

    const header = $('#header-content');
    const userRole = $('#user-role');

    $('#landing-username').html($('<h3>').addClass('mb-1').text(`Welcome ` + username + `...`))

    switch (role) {
        case RoleType.ADMIN:
            header.html(
                `<h2>System Admin Control Panel<h2>`
            );
    
            const sidebar = $('#sidebar-content');
            sidebar.html(
                `<button type="button" id="import-data-sidebar" class="dashbd-btn" onclick="handleButtonClick(this)">Import Data</button>
                 <button type="button" id="create-user-sidebar" class="dashbd-btn" onclick="handleButtonClick(this)">Create User</button>`
            );

            userRole.html(
                `<h4 class="mb-1" id="user-role">System Administrator</h4>`
            );

            break;

        case RoleType.CUSTOMER_SERVICE_REP:
            header.html(
                `Customer Service Representive Control Panel`
            ); 

            updateSideBar(role);

            userRole.html(
                `<h4 class="mb-1" id="user-role">Customer Service Rep</h4>`
            );
            break;

        case RoleType.SUPPORT_ENGINEER:
            header.html(
                `Support Engineer Control Panel`
            );

            updateSideBar(role);

            userRole.html(
                `<h4 class="mb-1" id="user-role">Support Engineer</h4>`
            );

            break;

        case RoleType.NETWORK_ENGINEER:
            header.html(
                `Network Engineer Control Panel`
            );

            updateSideBar(role);

            userRole.html(
                `<h4 class="mb-1" id="user-role">Network Engineer</h4>`
            );
    }
}

const updateSideBar = function(role) {

    const sidebar = $('#sidebar-content');

    // Clear the sidebar
    sidebar.html("");

    // No breaks;
    switch (role) {
        case RoleType.NETWORK_ENGINEER:
            sidebar.append(`
                <button type="button" id="imsi-failures-count-duration-sidebar" class="dashbd-btn" onclick="handleButtonClick(this)">IMSI Failures Count & Duration</button>
                <button type="button" id="model-failures-type-count-sidebar" class="dashbd-btn" onclick="handleButtonClick(this)">Model Failure Types</button>
            `);
        case RoleType.SUPPORT_ENGINEER:
            sidebar.append(`
                <button type="button" id="imsi-failures-time-sidebar" class="dashbd-btn" onclick="handleButtonClick(this)">IMSI Failures (Time)</button>
                <button type="button" id="model-failure-count-sidebar" class="dashbd-btn" onclick="handleButtonClick(this)">Model Failure Count</button>
            `);
        case RoleType.CUSTOMER_SERVICE_REP:
            sidebar.append(`
                <button type="button" id="imsi-failures-sidebar" class="dashbd-btn" onclick="handleButtonClick(this)">IMSI Failure Events</button>
                <button type="button" id="imsi-failure-count-time-sidebar" class="dashbd-btn" onclick="handleButtonClick(this)">IMSI Failure Count (Time)</button>
            `);
        default:
            break;
    }
}

const updateDataTable = function(tableId, data, headers) {
    // Check if DataTable is already initialized
    let datatable = $(`#${tableId}-datatable`).DataTable();
    if (datatable) {
        datatable.clear().draw();
    } else {
        datatable = $(`#${tableId}-datatable`).DataTable({
            "sScrollY": "50vh",
            "bScrollCollapse": true
        });
    }

    data.forEach(function(item) {
        if (headers && headers.length > 0) {
            let rowData = [];
            headers.forEach(header => {
                rowData.push(item[header]);
            });
            datatable.row.add(rowData);
        } else {
            // If no headers provided, append data directly as a single-column row
            datatable.row.add([item]);
        }
    });

    datatable.draw();
};


function move() {
    let i = 0;
    if (i == 0) {
        i = 1;
        let elem = document.getElementById("progressBarFG");
        let width = 1;
        let id = setInterval(frame, 10);
        function frame() {
            if (width >= 100) {
                clearInterval(id);
                i = 0;
            } else {
                width++;
                elem.style.width = width + "%";
            }
        }
    }
}

let selectedButton = null;
function handleButtonClick(button) {
    if (selectedButton) {
        selectedButton.classList.remove('selected');
    }
    button.classList.add('selected');
    selectedButton = button;
}