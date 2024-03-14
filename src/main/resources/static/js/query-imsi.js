'use strict';

// List of IMSI IDs
const addImsiDropdown = function(dropdownId) {
    let imsi_dropdown = $(dropdownId);
    imsi_dropdown.empty()

    imsi_dropdown.select2({
        placeholder: "Begin typing IMSI to search..",
        allowClear: true,
        width: '100%',
        minimumInputLength: 0
    });

    $.ajax({
        type: 'GET',
        url: rootUrl + "/query/imsi-failures",
        contentType: 'application/json',
        dataType: "json",
        headers: { "Authorization": 'Bearer ' + localStorage.getItem('token') },
        success: function(res) {
            if (res.status == "Success") {
                res.data.forEach(imsi => {
                    imsi_dropdown.append("<option value=" + imsi + ">" + imsi + "</option>");
                });

            } else {
                console.log("Error:", res.error);
            }
        },
        error: function(error) {
            console.error("Error:", error);
        }
    });
}


// Query #1 
const getIMSIFailures = function() {
    let imsi = $("#imsi-dropdown").val();

    $.ajax({
        type: 'GET',
        url: rootUrl + "/query/imsi-failures/" + imsi,
        contentType: 'application/json',
        dataType: "json",
        headers: { "Authorization": 'Bearer ' + localStorage.getItem('token') },
        success: function(res) {
            if (res.status == "Success") {
                updateDataTable('imsi-failure', res.data, ['eventId', 'causeCode', 'description']);
                $("#imsi-id").text(imsi);
            } else {
                console.log("Error:", res.error);
            }
        },
        error: function(error) {
            console.error("Error:", error);
        }
    });
};


// Query #2
const getIMSIFailureCountTime = function() {
    let imsi = $("#imsi-failure-count-time-dropdown").val();
    let startDate = $("#imsi-failure-count-time-start-date").val();
    let endDate = $("#imsi-failure-count-time-end-date").val();
    let msg = $("#imsi-failure-count-time-result");

    msg.html("");

    $.ajax({
        type: "GET",
        url: rootUrl + "/query/imsi-failure-count-time",
        data: { imsi: imsi, startDate: startDate, endDate: endDate },
        headers: { "Authorization": 'Bearer ' + localStorage.getItem('token') },
        success: function(res) {
            if (res.statusCode === 200) {
                msg.removeClass().addClass("alert alert-info")
                    .html(` <div class="card-body">
                            <h4 class="card-title">IMSIs Failures Count</h4>
                            <p class="card-text">
                                IMSI: ${imsi}<br>
                                Start Date: ${startDate.replace('T', ' ')}<br>
                                End Date: ${endDate.replace('T', ' ')}<br>
                                <h5>Count: ${res.data}</h5>
                            </p>
                        </div>`
                    ).show();

            } else {
                msg.removeClass().addClass("alert alert-danger")
                    .html(` <div class="card-body">
                            <strong>${res.error.errorMsg}</strong> ${res.error.details}
                        </div>`
                    ).show();
            }
        },
        error: function(err) {
            console.log(err);
        }
    });
};


// Query #3
const getIMSIFailuresTime = function() {
    let startDate = $("#imsi-failures-time-start-date").val();
    let endDate = $("#imsi-failures-time-end-date").val();

    $.ajax({
        type: "GET",
        url: rootUrl + "/query/imsi-failures-time",
        data: { startDate: startDate, endDate: endDate },
        headers: { "Authorization": 'Bearer ' + localStorage.getItem('token') },
        success: function(res) {
            if (res.status == "Success") {
                updateDataTable('imsi-failures-time', res.data, []);
                $("#imsi-failures-time-datatable-caption").text("IMSI Failure For Date Range - " + startDate.replace('T', ' ') + "  to  " + endDate.replace('T', ' '));
            } else {
                console.log("Error:", res.error);
            }
        },
        error: function(error) {
            console.error("Error in AJAX request:", error);
        }
    });
}


// Query #6
const getIMSIFailuresCountDuration = function() {
    let startDate = $("#imsi-failures-count-duration-start-date").val();
    let endDate = $("#imsi-failures-count-duration-end-date").val();

    $.ajax({
        type: "GET",
        url: rootUrl + "/query/imsi-failures-count-duration",
        data: { startDate: startDate, endDate: endDate },
        headers: { "Authorization": 'Bearer ' + localStorage.getItem('token') },
        success: function(res) {
            if (res.status == "Success") {
                updateDataTable('imsi-count-duration', res.data, ["imsi", "failureCount", "totalDuration"]);
                $("#imsi-failures-count-duration-datatable-caption").text("IMSI Failures Count and Duration - " + startDate.replace('T', ' ') + " to " + endDate.replace('T', ' '));
                const imsiList = res.data.map(entry => entry.imsi);
                const durationList = res.data.map(entry => entry.totalDuration);
                const failureCountList = res.data.map(entry => entry.failureCount);

                // =================================================================
                addChart({
                    whereToAdd: "imsi-failures-count-duration-container", 
                    modalName: "imsi-failures-count-duration", 
                    title: "IMSI Failure Counts and Duration", 
                    chartDetails: {
                        type: 'bar',
                        data: {
                            labels: durationList,
                            datasets: [{
                                label: "Number of Failures",
                                data: failureCountList,
                                backgroundColor: '#198754',
                                borderWidth: 1
                            }]
                        },
                        options: {
                            scales: {
                                x: {
                                    title: {
                                        display: true,
                                        text: "imsi"  
                                    }
                                },
                                y: {
                                    beginAtZero: true,
                                    title: {
                                        display: true,
                                        text: "# of Failures"
                                    }
                                }
                            }
                        }
                    },
                    clickHandler: (event) => {
                        let canvas = event.currentTarget;

                        // Get the chart instance associated with the canvas
                        let chartInstance = Chart.getChart(canvas);
                    
                        // Get the element under the click
                        let elements = chartInstance.getElementsAtEventForMode(event, 'nearest', { intersect: true }, false);
                    
                        if (elements.length > 0) {
                            let index = elements[0].index;
                            let datasetIndex = elements[0].datasetIndex;
                            let label = chartInstance.data.labels[index];
                            let value = chartInstance.data.datasets[datasetIndex].data[index];
                            console.log("Clicked on " + label + " with value " + value);
                        }
                    }
                });
                // =================================================================
                
            } else {
                console.log("Error:", res.error);
            }
        },
        error: function(error) {
            console.error("Error in AJAX request:", error);
        }
    });
}

//Query #8
const getIMSIUniqueCauseCodeFailure = function() {
    let imsi = $("#imsi-unique-dropdown").val();

    $.ajax({
        type: "GET",
        url: rootUrl + "/query/imsi-unique-failures/" + imsi,
        headers: { "Authorization": 'Bearer ' + localStorage.getItem('token') },
        success: function(res) {
            if (res.statusCode === 200) {
                updateDataTable('imsi-unique-failure', res.data, ['eventId', 'causeCode', 'description']);
            } else {
                console.log("Error:", res.error);
            }
        },
        error: function(err) {
            console.log(err);
        }
    });

}

//Query#9
const getTop10ImsiFailureTime = function() {
    let startDate = $("#top10-imsi-failure-time-start-date").val();
    let endDate = $("#top10-imsi-failure-time-end-date").val();

    $.ajax({
        type: "GET",
        url: rootUrl + "/query/top10-imsi-failures-time",
        headers: { "Authorization": 'Bearer ' + localStorage.getItem('token') },
        data: { startDate: startDate, endDate: endDate },
        success: function(res) {

            if (res.status == "Success") {
                updateDataTable('top10-imsi-failure-time', res.data, ["imsi", "failureCount"]);
                $("#top10-imsi-failure-time-datatable-caption").text("TOP 10 IMSI Failure For Date Range - " + startDate.replace('T', ' ') + "  to  " + endDate.replace('T', ' '));
            } else {
                console.log("Error:", res.error);
            }
        },
        error: function(error) {
            console.error("Error in AJAX request:", error);
        }
    });
}