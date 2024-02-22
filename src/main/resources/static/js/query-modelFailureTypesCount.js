
const addModelDropdown = function() {
    let model_dropdown = $("#modelFailureTypesCount-dropdown");
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
            console.log(res);
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

const getModelFailureTypesWithCount = function() {

    let model = $("#modelFailureTypesCount-dropdown").val();
    console.log("Model: " + model);

    $.ajax({
        type: 'GET',
        url: rootUrl + "/query/model-failures/" + model,
        contentType: 'application/json',
        dataType: "json",
        success: function(res) {
            console.log(res);
            if (res.status == "Success") {
                updateModelTable(res.data);
            } else {
                console.log("Error:", res.error);
            }

        },
        error: function(error) {
            console.error("Error:", error);
        }
    });
};

function updateModelTable(data) {
    $('#modelFailureTypesCount-datatable-body').empty();
    data.forEach(function(item) {
        $('#modelFailureTypesCount-datatable-body').append(`<tr>
            <td>${item.causeCode}</td>
            <td>${item.eventId}</td>
            <td>${item.failureCount}</td>
        </tr>`);
    });

    if (datatable) {
        datatable.destroy();
    }

    datatable = $("#modelFailureTypesCount-datatable").DataTable({
        "sScrollY": "50vh",
        "bScrollCollapse": true
    });
}
