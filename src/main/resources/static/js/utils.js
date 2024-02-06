
const RoleType = {
    ADMIN: 'SYSTEM_ADMINISTRATOR',
    CUSTOMER_SERVICE_REP: 'CUSTOMER_SERVICE_REP',
    NETWORK_ENGINEER: 'NETWORK_ENGINEER',
    SUPPORT_ENGINEER: 'SUPPORT_ENGINEER'
};


const loadContentForRole = function(role) {

    // Get references to the Sidebar and LandingContent
    const sidebarContent = $('#sidebar-content');
    const landingContent = $('#landing-content');
    const header_content = $('#header-content');

    // Clear existing content
    sidebarContent.html('');
    landingContent.html('');

    switch (role) {
        case RoleType.ADMIN:
            sidebarContent.html(
                `<button type="button" id="import-data-sidebar" class="dashbd-btn" onclick="handleButtonClick(this)">Import Data</button>
                 <button type="button" id="create-user-sidebar" class="dashbd-btn" onclick="handleButtonClick(this)">Create User</button>`
            );

            landingContent.html(
                `<h1>Welcome Admin!</h1>
            `);

            header_content.html(
                `System Admin Control Panel`
            );

            break;

        case RoleType.CUSTOMER_SERVICE_REP:
            sidebarContent.html(
                ` <button type="button" id="imsi-failures-sidebar" class="dashbd-btn" onclick="handleButtonClick(this)">IMSI Failures</button>
            `);

            landingContent.html(
                `<h1>Welcome Customer Service Rep!</h1>
            `);

            header_content.html(
                `Customer Service Representive Control Panel`
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
