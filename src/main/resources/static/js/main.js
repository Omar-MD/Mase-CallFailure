'use strict';

const rootUrl = "http://localhost:8081";

const RoleType = {
    SYSTEM_ADMINISTRATOR: 'SYSTEM_ADMINISTRATOR',
    CUSTOMER_SERVICE_REP: 'CUSTOMER_SERVICE_REP',
    NETWORK_ENGINEER: 'NETWORK_ENGINEER',
    SUPPORT_ENGINEER: 'SUPPORT_ENGINEER'
}

let interval_ID;

const login = function() {
    let username = $('#username').val();
    let password = $('#password').val();
    let errorMsg = $('#errorMsg');

    errorMsg.remove();

    $.ajax({
        type: 'POST',
        url: rootUrl + "/authenticate",
        contentType: 'application/json',
        data: JSON.stringify({ "username": username, "password": password }),
        dataType: "json",
        success: function(response) {
            if (response.statusCode === 200) {
                localStorage.setItem('token', response.data.token);
                localStorage.setItem('role', response.data.role);

                switch (response.data.role) {
                    case 'SYSTEM_ADMINISTRATOR':
                        loadContentForRole(RoleType.SYSTEM_ADMINISTRATOR, username);
                        showHome();
                        interval_ID = setInterval(checkImportStatus, 5000);
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
                }
            } else {
                errorMsg.remove();
                $('#login-card').append(`<div id=\"errorMsg\" class=\"alert alert-danger\"><strong>${response.error.errorMsg}!</strong> ${response.error.details}</div>`).show();
            }
        },
        error: function(err) {
            console.log(err);
        }
    });
};

const loadContentForRole = function(role, username) {

    const header = $('#header-content');
    const userRole = $('#user-role');

    $('#landing-username').html($('<h3>').addClass('mb-1').text(`Welcome ` + username + `...`))

    switch (role) {
        case RoleType.SYSTEM_ADMINISTRATOR:
            header.html(
                `<h2>System Admin Control Panel<h2>`
            );

            const sidebar = $('#sidebar-content');
            sidebar.html(`
                 <hr class="pt-1 pb-1 style-hr"/>
                 <button type="button" id="import-data-sidebar" class="dashbd-btn" onclick="handleButtonClick(this)">Import Data</button>
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
    sidebar.html("");

    switch (role) {
        case RoleType.NETWORK_ENGINEER:
            sidebar.append(`
                <hr class="pt-1 pb-1 style-hr"/>
                <button type="button" id="top10-imsi-failure-time-sidebar" class="dashbd-btn" onclick="handleButtonClick(this)">Top 10 (IMSI Failures)</button>
                <button type="button" id="top10-moc-combinations-sidebar" class="dashbd-btn" onclick="handleButtonClick(this)">Top 10 (Market, Operator, Cell)</button>
                <button type="button" id="imsi-failures-count-duration-sidebar" class="dashbd-btn" onclick="handleButtonClick(this)">IMSI Failures (Count, Duration)</button>
                <button type="button" id="model-failures-type-count-sidebar" class="dashbd-btn" onclick="handleButtonClick(this)">Model Failure (Cause, Event, Count)</button>
                
            `);
        case RoleType.SUPPORT_ENGINEER:
            sidebar.append(`
                <hr class="pt-1 pb-1 style-hr"/>
                <button type="button" id="imsi-failures-time-sidebar" class="dashbd-btn" onclick="handleButtonClick(this)">IMSI Failures (Time)</button>
                <button type="button" id="cause-failure-imsi-list-sidebar" class="dashbd-btn" onclick="handleButtonClick(this)">IMSI Failures (Cause Code)</button>
                <button type="button" id="model-failure-count-sidebar" class="dashbd-btn" onclick="handleButtonClick(this)">Model Failure (Count)</button>
            `);
        case RoleType.CUSTOMER_SERVICE_REP:
            sidebar.append(`
                <hr class="pt-1 pb-1 style-hr"/>
                <button type="button" id="imsi-unique-failure-sidebar" class="dashbd-btn" onclick="handleButtonClick(this)">IMSI Failure (Unique)</button>
                <button type="button" id="imsi-failures-sidebar" class="dashbd-btn" onclick="handleButtonClick(this)">IMSI Failure (All)</button>
                <button type="button" id="imsi-failure-count-time-sidebar" class="dashbd-btn" onclick="handleButtonClick(this)">IMSI Failure (Count)</button>
            `);
        default:
            break;
    }
}

const updateDataTable = function(tableId, data, headers) {

    let datatable = $(`#${tableId}-datatable`).DataTable();
    if (datatable) {
        datatable.clear().draw();
    } else {
        datatable = $(`#${tableId}-datatable`).DataTable({
            sScrollY: "50vh",
            bScrollCollapse: true,
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
