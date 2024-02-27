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
    console.log("Model: " + model);

    $.ajax({
        type: 'GET',
        url: rootUrl + "/query/model-failures/" + model,
        contentType: 'application/json',
        dataType: "json",
        success: function(res) {
            if (res.status == "Success") {
                updateDataTable('model-failures-type-count', res.data, ['eventId', 'causeCode', 'failureCount']);
                $("#model-id").text(model);
            } else {
                console.log("Error:", res.error);
            }
        },
        error: function(error) {
            console.error("Error:", error);
        }
    });
};