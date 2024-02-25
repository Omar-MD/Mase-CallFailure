
// Application Event Handlers
$(document).ready(function() {
    // Login
    $('#loginSubmit').on('click', function(event) {
        event.preventDefault();
        login();
    });

    // Landing Page
    $('#nav-bar').on('click', '#header-content', function() {
        homeNav('#landing-window');
    });

    // Import
    $('#sidebar-content').on('click', '#import-data-sidebar', function() {
        homeNav('#sys-adm-import-window');
    });
    $('#import-btn').on('click', function(event) {
        event.preventDefault();
        importDataset();
    });

    // Create User
    $('#sidebar-content').on('click', '#create-user-sidebar', function() {
        homeNav('#sys-adm-create-user-window');
    });
    $('#create-account-btn').on('click', function(event) {
        event.preventDefault();
        createAccount();
    });

    // IMSI Failures
    $('#sidebar-content').on('click', '#imsi-failures-sidebar', function() {
        homeNav('#imsi-query-failure-window');
        addImsiDropdown("#imsi-dropdown");
    });
    $('#imsiFailures-btn').on('click', function(event) {
        homeNav('#imsi-datatable-failure-window');
        event.preventDefault();
        getIMSIFailures();
    });

    // modelFailureTypesCount
    $('#sidebar-content').on('click', '#modelFailureTypesCount-sidebar', function() {
        homeNav('#modelFailureTypesCount-window');
        addModelDropdown();
    });
    $('#modelFailureTypesCount-btn').on('click', function(event) {
        homeNav('#modelFailureTypesCount-datatable-window');
        event.preventDefault();
        getModelFailureTypesWithCount();
    });
    
    // Load the input page geettign IMSI failure in datae range
    $('#sidebar-content').on('click', '#imsi-failures-time-sidebar', function() {
        homeNav('#imsi-failure-time-window');
    });

    // Load the datatable for getting the IMSI failure in a given date range
    $("#imsi-failure-time-btn").on('click', function(event) {
        homeNav('#imsi-failure-time-datatable-window');
        event.preventDefault();
        getIMSIFailuresTime();
    });

    // Get Count for given IMSI, the number of failures that occured in a given time period
    $('#sidebar-content').on('click', '#imsi-failure-time-count-sidebar', function() {
        addImsiDropdown('#imsi-failure-time-count-imsi-dropdown');
        homeNav('#imsi-failure-time-count-window');
    });

    $("#imsi-failure-time-count-btn").on('click', function(event) {
        event.preventDefault();
        getIMSIFailuresTimeCount();
    });
});

const homeNav = function(pageID) {
    $('.home-content').addClass('d-none');
    $(pageID).removeClass('d-none');
}
