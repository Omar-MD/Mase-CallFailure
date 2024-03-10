'use strict';

// Application Event Handlers
$(document).ready(function() {

    //------------------------------------------------------------------
    //------------------------  LOGIN & HOME  ------------------------
    //------------------------------------------------------------------

    // Login
    $('#loginSubmit').on('click', function(event) {
        event.preventDefault();
        login();
    });

    // Landing Page
    $('#nav-bar').on('click', '#header-content', function() {
        homeNav('#landing-window');
    });

    //------------------------------------------------------------------
    //------------------------  SYSTEM ADMIN  -----------------------
    //------------------------------------------------------------------

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

    //----------------------------------------------------------------
    //------------------------  QUERIES  -----------------------------
    //----------------------------------------------------------------

    // Query #1
    $('#sidebar-content').on('click', '#imsi-failures-sidebar', function() {
        addImsiDropdown("#imsi-dropdown");
        homeNav('#imsi-query-failure-window');
    });
    $('#imsiFailures-btn').on('click', function(event) {
        event.preventDefault();
        getIMSIFailures();
        homeNav('#imsi-datatable-failure-window');
    });


    // Query #2
    $('#sidebar-content').on('click', '#imsi-failure-count-time-sidebar', function() {
        addImsiDropdown('#imsi-failure-count-time-dropdown');
        homeNav('#imsi-failure-count-time-window');
    });
    $("#imsi-failure-count-time-btn").on('click', function(event) {
        event.preventDefault();
        getIMSIFailureCountTime();
    });


    // Query #3
    $('#sidebar-content').on('click', '#imsi-failures-time-sidebar', function() {
        homeNav('#imsi-failures-time-window');
    });
    $("#imsi-failures-time-btn").on('click', function(event) {
        event.preventDefault();
        getIMSIFailuresTime();
        homeNav('#imsi-failures-time-datatable-window');
    });


    // Query #4
    $('#sidebar-content').on('click', '#model-failure-count-sidebar', function() {
        addModelDropdown('#model-failure-count-dropdown');
        homeNav('#model-failure-count-window');
    });
    $("#model-failure-count-btn").on('click', function(event) {
        event.preventDefault();
        getModelFailureCount();
    });

    // Query 4.5
    $('#sidebar-content').on('click', '#cause-failure-imsi-list-sidebar', function() {
        addFailureCauseCodeDropdown('#cause-failure-imsi-list-dropdown');
        homeNav('#cause-failure-imsi-list-window');
    });
    $("#cause-failure-imsi-list-btn").on('click', function(event) {
        event.preventDefault();
        homeNav('#cause-failure-imsi-datatable-window');
        getIMSIFailureForFailureCauseClass();
    });


    // Query #5
    $('#sidebar-content').on('click', '#model-failures-type-count-sidebar', function() {
        addModelDropdown('#model-failures-type-count-dropdown');
        homeNav('#model-failures-type-count-window');
    });
    $('#model-failures-type-count-btn').on('click', function(event) {
        event.preventDefault();
        getModelFailuresTypeCount();
        homeNav('#model-failures-type-count-datatable-window');
    });


    // Query #6
    $('#sidebar-content').on('click', '#imsi-failures-count-duration-sidebar', function() {
        homeNav('#imsi-failures-count-duration-window');
    });
    $("#imsi-failures-count-duration-btn").on('click', function(event) {
        event.preventDefault();
        getIMSIFailuresCountDuration();
        homeNav('#imsi-failures-count-duration-datatable-window');
    });
    

    // Query #9
     $('#sidebar-content').on('click', '#top10-imsi-failure-time-sidebar', function() {
        homeNav('#top10-imsi-failure-time-window');
    });
    $("#top10-imsi-failure-time-btn").on('click', function(event) {
        event.preventDefault();
        getTop10ImsiFailureTime();
        homeNav('#top10-imsi-failure-time-datatable-window');
    });

    // Query #7
    $('#sidebar-content').on('click', '#top10-moc-combinations-sidebar', function() {
        homeNav('#top10-moc-combinations-window');
    });
    $("#top10-moc-combinations-btn").on('click', function(event) {
        event.preventDefault();
        getTop10MocCombinations();
        homeNav('#top10-moc-combinations-datatable-window');
    });

	//Query #8
	$('#sidebar-content').on('click', '#imsi-unique-failure-sidebar', function() {
		addImsiDropdown('#imsi-unique-dropdown');
		homeNav('#imsi-uniqe-query-failure-window');
	});
	$("#imsiUniqueFailures-btn").on('click', function(event) {
		event.preventDefault();
		getIMSIUniqueCauseCodeFailure();
		homeNav('#imsi-datatable-unique-failure-window');
	});

});

const homeNav = function(pageID) {
    $('.home-content').addClass('d-none');
    $(pageID).removeClass('d-none');
}

const showHome = function() {
    $('#login-section').addClass("d-none");
    $('#home-section').removeClass("d-none");
}


