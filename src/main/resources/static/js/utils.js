
const RoleType = {
    ADMIN: 'SYSTEM_ADMINISTRATOR',
    CUSTOMER_SERVICE_REP: 'CUSTOMER_SERVICE_REP',
    NETWORK_ENGINEER: 'NETWORK_ENGINEER',
    SUPPORT_ENGINEER: 'SUPPORT_ENGINEER'
};


const loadContentForRole = function(role, username) {

    // Get references to the dynamic elements
    const header = $('#header-content');
    const sidebar = $('#sidebar-content');
    const userRole = $('#user-role')
    // Set the User Content
    $('#landing-username').html($('<h3>').addClass('mb-1').text(`Welcome ` + username + `...`))

    // Set Role Content
    switch (role) {
        case RoleType.ADMIN:
            header.html(
                `<h2>System Admin Control Panel<h2>`
            );

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

            sidebar.html(
                ` <button type="button" id="imsi-failures-sidebar" class="dashbd-btn" onclick="handleButtonClick(this)">IMSI Failures</button>
            `);

            userRole.html(
                `<h4 class="mb-1" id="user-role">Customer Service Rep</h4>`
            );
            break;
        
        case RoleType.SUPPORT_ENGINEER:
            header.html(
                `Support Engineer Control Panel`
            );

            sidebar.html(
                ` <button type="button" id="imsi-failures-time-sidebar" class="dashbd-btn" onclick="handleButtonClick(this)">IMSI Failures (Time)</button>
            `);

            userRole.html(
                `<h4 class="mb-1" id="user-role">Support Engineer</h4>`
            );
            break;
    }
}


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
