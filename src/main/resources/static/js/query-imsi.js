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
                        type: 'scatter',
                        data: {
                            labels: durationList,
                            datasets: [{
                                label: "Number of Failures",
                                data: failureCountList,
                                backgroundColor: '#198754',
                                // backgroundColor: '#AF2C2C',
                                borderWidth: 1,
                            }]
                        },
                        options: {
                            scales: {
                                x: {
                                    title: { text: "Duration" }
                                },
                                y: {
                                    title: { text: "Number of Failures" }
                                }
                            },
                            plugins: {
                                tooltip: {
                                    callbacks: {
                                        title: function(tooltipItems) {
                                            if (!tooltipItems.length) {
                                                return '';
                                            }
                                            let index = tooltipItems[0];
                                            let x = index.dataIndex;
                                            let imsi = imsiList[x];
                                            return `IMSI: ${imsi}`;
                                        }
                                    }
                                }
                            }
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
                const top10imsiList = res.data.map(entry => entry.imsi);
                const top10ImsiFailureCountList = res.data.map(entry => entry.failureCount);

                //==========================graph visualization codes====================
                addChart({
                    whereToAdd: "top10-imsi-failure-time-container",
                    modalName: "top10-imsi-failure-time",
                    title: "TOP 10 IMSI Failure ",
                    chartDetails: {
                        type: 'bar',
                        data: {
                            labels: top10imsiList,
                            datasets: [{
                                label: "Number of Failures",
                                data: top10ImsiFailureCountList,
                                backgroundColor: '#008080',
                                borderWidth: 1
                            }]
                        },
                        options: {
                            scales: {
                                x: {
                                    title: { text: "TOP 10 IMSI" }
                                },
                                y: {
                                    title: { text: "Number of Failures" }
                                }
                            },
                            onHover: (event, chartElement) => {
                                event.native.target.style.cursor = chartElement[0] ? 'pointer' : 'default';
                            }
                        }
                    },
                    clickHandler: (click) => {
                        let chartInstance = Chart.getChart(click.currentTarget);
                        let bar = chartInstance.getElementsAtEventForMode(click, 'nearest', { intersect: true }, true);

                        if (bar.length > 0) {
                            let index = bar[0].index;
                            let label = chartInstance.data.labels[index];
                            imsiFailureRankingDrillDown(label);
                        }
                    },
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

const imsiFailureRankingDrillDown = function(imsi) {
    $.ajax({
        type: 'GET',
        url: rootUrl + "/query/imsi-failure-class/" + imsi,
        contentType: 'application/json',
        dataType: "json",
        headers: { "Authorization": 'Bearer ' + localStorage.getItem('token') },
        success: function(res) {
            const failureClass = res.data.map(entry => entry.failureClass);
            const failureCount = res.data.map(entry => entry.failureCount);

            handleDrillDown({
                modalName: "top10-imsi-failure-time",
                title: "IMSI (" + imsi + ") Failure Classes",
                chartDetails: {
                    type: 'bar',
                    data: {
                        labels: failureClass,
                        datasets: [{
                            label: "Imsi (" + imsi + ") Failure Classes",
                            data: failureCount,
                            fill: false,
                            backgroundColor: '#800000',
                            borderWidth: 2
                        }]
                    },
                    options: {
                        indexAxis: 'y',
                        scales: {
                            x: {
                                title: { text: 'Number of Failures' }
                            },
                            y: {
                                title: { text: 'Failure Class' }
                            }
                        },
                        onHover: (event, chartElement) => {
                            event.native.target.style.cursor = chartElement[0] ? 'pointer' : 'default';
                        }
                    }
                },
                clickHandler: (click) => {
                    let chartInstance = Chart.getChart(click.currentTarget);
                    let bar = chartInstance.getElementsAtEventForMode(click, 'nearest', { intersect: true }, true);

                    if (bar.length > 0) {
                        let index = bar[0].index;
                        let failureType = chartInstance.data.labels[index];
                        imsiFailureClassEventCauseDrillDown(imsi, failureType);
                    }
                },
            });
        },
        error: function(error) {
            console.error("Error:", error);
        }
    });
}


const imsiFailureClassEventCauseDrillDown = function(imsi, failureClass) {
    $.ajax({
        type: 'GET',
        url: rootUrl + "/query/imsi-failure-class-event-cause",
        contentType: 'application/json',
        dataType: "json",
        headers: { "Authorization": 'Bearer ' + localStorage.getItem('token') },
        data: {imsi, failureClass},
        success: function(res) {
            const eventCause = res.data.map(entry => entry.eventCause);
            const failureCount = res.data.map(entry => entry.failureCount);

            handleDrillDown({
                modalName: "top10-imsi-failure-time",
                title: "Imsi (" + imsi + ") Failure ("+ failureClass + ") Event Causes",
                chartDetails: {
                    type: 'bar',
                    data: {
                        labels: eventCause,
                        datasets: [{
                            label: "Imsi (" + imsi + ") Failure ("+ failureClass + ") Event Causes",
                            data: failureCount,
                            fill: false,
                            borderColor: '#008080',
                            borderWidth: 2
                        }]
                    },
                    options: {
                        scales: {
                            x: {
                                title: { text: 'Event - Cause' }
                            },
                            y: {
                                title: { text: 'Number of Failures' }
                            }
                        }
                    }
                }
            });
        },
        error: function(error) {
            console.error("Error:", error);
        }
    });
}