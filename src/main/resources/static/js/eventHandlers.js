
// Application Event Handlers
$(document).ready(function() {
    // Login
    $('#loginSubmit').on('click', function(event) {
        event.preventDefault();
        login();
    });
    
    // Landing Page
    $('#nav-bar').on('click', '#header-content', function() {
        $('#landing-window').removeClass('d-none');
    });
    
    // Import
    $('#sidebar-content').on('click', '#import-data-sidebar', function() {
        $('#landing-window').addClass('d-none');
        $('#sys-adm-create-user-window').addClass('d-none');
        $('#sys-adm-import-window').removeClass('d-none');
    });
    $('#import-btn').on('click', function(event) {
        event.preventDefault();
        importDataset();
    });
    
    // Create User
    $('#sidebar-content').on('click', '#create-user-sidebar', function() {
        $('#sys-adm-import-window').addClass('d-none');
        $('#landing-window').addClass('d-none');
        $('#sys-adm-create-user-window').removeClass('d-none');
    });
    $('#create-account-btn').on('click', function(event) {
        event.preventDefault();
        createAccount();
    });
    
    // IMSI Failures
    $('#sidebar-content').on('click', '#imsi-failures-sidebar', function() {
        $('#landing-window').addClass('d-none');
        $('#sys-adm-import-window').addClass('d-none');
        $('#sys-adm-create-user-window').addClass('d-none');
        $('#imsi-query-failure-window').removeClass('d-none');
        addImsiDropdown();
    });
    $('#imsiFailures-btn').on('click', function(event) {
        $('#imsi-query-failure-window').addClass('d-none');
        $('#imsi-datatable-failure-window').removeClass('d-none');
        event.preventDefault();
        getIMSIFailures();
    });
});

