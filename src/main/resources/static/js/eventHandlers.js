
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

	// Model Failure Types
	$('#sidebar-content').on('click', '#modelFailureTypesCount-sidebar', function() {
		homeNav('#modelFailureTypesCount-window');
		addModelDropdown();
	});
	$('#modelFailureTypesCount-btn').on('click', function(event) {
		homeNav('#modelFailureTypesCount-datatable-window');
		event.preventDefault();
		getModelFailureTypesWithCount();
	});

	// IMSI Failure Time
	$('#sidebar-content').on('click', '#imsi-failures-time-sidebar', function() {
		homeNav('#imsi-failure-time-window');
	});

	$("#imsi-failure-time-btn").on('click', function(event) {
		homeNav('#imsi-failure-time-datatable-window');
		event.preventDefault();
		getIMSIFailuresTime();
	});
	
	// Load the datatable for getting the IMSI failure in a given date range
	$("#imsi-failure-time-btn").on('click', function(event) {
		homeNav('#imsi-failure-time-datatable-window');
		event.preventDefault();
		getIMSIFailuresTime();
	});

	//Model Faliure Count
	$('#sidebar-content').on('click', '#model-failure-count-sidebar', function() {
		addModelCountDropdown();
		homeNav('#model-failure-count-window');
	});
	$("#model-failure-count-btn").on('click', function(event) {
		event.preventDefault();
		getModelFailureCount();
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

<<<<<<< HEAD
const homeNav = function(pageID) {
	$('.home-content').addClass('d-none');
	$(pageID).removeClass('d-none');
}
=======
    // Call failure count and duration for each IMSI for time range
    
    $('#sidebar-content').on('click', '#callFailureCount-sidebar', function() {
		homeNav('#callFailureCount-window');
	});

	// Call failure count and duration  DATATABLE for each IMSI for time range
	$("#callFailureCount-btn").on('click', function(event) {
		homeNav('#callFailureCount-datatable-window');
		event.preventDefault();
		getCallFailureCount();
	});



>>>>>>> bacc62659d0f2b1a8f8e27aa3a0913c6f1f844aa
