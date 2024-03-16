'use strict';

// List Model IDs
const addModelDropdown = function(dropdownID) {
    let model_dropdown = $(dropdownID);
    model_dropdown.empty()

    model_dropdown.select2({
        placeholder: "Begin typing Model to search..",
        allowClear: true,
        width: '100%',
        minimumInputLength: 0
    });

    $.ajax({
        type: 'GET',
        url: rootUrl + "/query/model-failures",
        contentType: 'application/json',
        dataType: "json",
        headers: { "Authorization": 'Bearer ' + localStorage.getItem('token') },
        success: function(res) {
            if (res.status == "Success") {
                res.data.forEach(model => {
                    model_dropdown.append("<option value=" + model + ">" + model + "</option>");
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


// Query #4
const getModelFailureCount = function() {
    let startDate = $('#model-failure-count-start-date').val();
    let endDate = $('#model-failure-count-end-date').val();
    let model = $('#model-failure-count-dropdown').val();
    let msg = $("#model-failure-count-result");

    msg.html("");
    $.ajax({
        type: "GET",
        url: rootUrl + "/query/model-failure-count",
        contentType: 'application/json',
        dataType: "json",
        headers: { "Authorization": 'Bearer ' + localStorage.getItem('token') },
        data: { "endDate": endDate, "startDate": startDate, "tac": model },
        success: function(res) {
            if (res.statusCode === 200) {
                msg.removeClass().addClass("alert alert-info").html(`
                        <h4>Model Call Failure Count</h4><br/>
                                Start Date: ${startDate.replace('T', ' ')}<br>
                                End Date: ${endDate.replace('T', ' ')}<br>
                                <strong>Model: </strong>${model}<br/>
                                <strong>Count: </strong>${res.data}<br/>
                `).show();

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
}


// Query #5
const getModelFailuresTypeCount = function() {
    let model = $("#model-failures-type-count-dropdown").val();

    $.ajax({
        type: 'GET',
        url: rootUrl + "/query/model-failures/" + model,
        contentType: 'application/json',
        dataType: "json",
        headers: { "Authorization": 'Bearer ' + localStorage.getItem('token') },
        success: function(res) {
            if (res.status == "Success") {

                updateDataTable('model-failures-type-count', res.data, ['eventId', 'causeCode', 'failureCount']);
                $("#model-id").text(model);
                let eventCauseList = res.data.map(entry => entry.eventId + '-' + entry.causeCode);
                let failureCountList = res.data.map(entry => entry.failureCount);
                // =================================================================
                addChart({
                    whereToAdd: "model-failures-type-count-container",
                    modalName: "model-failures-type-count",
                    title: "Model Failures Type Count",
                    chartDetails: {
                        type: 'bar',
                        data: {
                            labels: eventCauseList,
                            datasets: [{
                                label: "Number of Failures",
                                data: failureCountList,
                                backgroundColor: '#008080',
                                borderWidth: 1
                            }]
                        },
                        options: {
                            scales: {
                                x: {
                                    ticks: {
                                        font: {
                                            size: 14
                                        }
                                    },
                                    title: {
                                        display: true,
                                        text: "Event Id - Cause Code",
                                        font: {
                                            size: 18,
                                        }
                                    }
                                },
                                y: {
                                    beginAtZero: true,
                                    title: {
                                        display: true,
                                        text: "# of Failures",
                                        font: {
                                            size: 18,
                                        }
                                    },
                                    ticks: {
                                        fontSize: 14
                                    }
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
                            let label = chartInstance.data.labels[index].split('-');
                            eventCauseDrillDown(label[0], label[1]);
                        }
                    },

                });
                // =================================================================
            } else {
                console.log("Error:", res.error);
            }
        },
        error: function(error) {
            console.error("Error:", error);
        }
    });
};

const eventCauseDrillDown = function(eventId, causeCode) {
    $.ajax({
        type: 'GET',
        url: rootUrl + "/query/event-cause-failures-over-time",
        contentType: 'application/json',
        dataType: "json",
        headers: { "Authorization": 'Bearer ' + localStorage.getItem('token') },
        data: { "eventId": eventId, "causeCode": causeCode },
        success: function(res) {
            const dates = res.data.map(entry => entry.date);
            const failureCounts = res.data.map(entry => entry.failureCount);

            handleDrillDown({
                modalName: "model-failures-type-count",
                title: "Failures Over Time For Event Id-Cause Code",
                chartDetails: {
                    type: 'bar',
                    data: {
                        labels: dates,
                        datasets: [{
                            label: "Number of Failures for Event Id - Cause Code (" + eventId + '-' + causeCode + ')',
                            data: failureCounts,
                            fill: false,
                            borderColor: '#008080',
                            borderWidth: 2
                        }],
                    },
                    options: {
                        scales: {
                            x: {
                                title: {
                                    display: true,
                                    text: 'Date',
                                    font: {
                                        size: 18
                                    }
                                },
                                ticks: {
                                    font: {
                                        size: 14
                                    }
                                }
                            },
                            y: {
                                title: {
                                    display: true,
                                    text: 'Number of Failures',
                                    font: {
                                        size: 18
                                    }
                                },
                                ticks: {
                                    font: {
                                        size: 14
                                    }
                                }
                            }
                        },
                        onHover: (event) => {
                            event.native.target.style.cursor = 'default';
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