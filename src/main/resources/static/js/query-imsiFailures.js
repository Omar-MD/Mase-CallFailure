
const addImsiDropdown = function() {
    let imsi_dropdown = $("#imsi-dropdown");
    imsi_dropdown.empty()
    
    $.ajax({
        type: 'GET',
        url: rootUrl + "/query/imsi-failures",
        contentType: 'application/json',
        dataType: "json",
        success: function(res) {
            console.log(res);
            if (res.status == "Success") {
                res.data.forEach(imsi => {
                    imsi_dropdown.append("<option value="+imsi+">"+imsi+"</option>");
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
const getIMSIFailures = function() {

    let imsi = $("#imsi-dropdown").val();
    console.log("IMSI: " + imsi);

    $.ajax({
        type: 'GET',
        url: rootUrl + "/query/imsi-failures/" + imsi,
        contentType: 'application/json',
        dataType: "json",
        success: function(res) {
            console.log(res);
            if (res.status == "Success") {
                updateTable(res.data);
            } else {
                console.log("Error:", res.error);
            }

        },
        error: function(error) {
            console.error("Error:", error);
        }
    });
};

let datatable;
function updateTable(data) {
    $('#imsi-failure-datatable-body').empty();
    data.forEach(function(item) {
        $('#imsi-failure-datatable-body').append(`<tr>
            <td>${item.eventId}</td>
            <td>${item.causeCode}</td>
            <td>${item.description}</td>
        </tr>`);
    });
    if(datatable) {
        datatable.destroy();
    }
    datatable = $("#imsi-failure-datatable").DataTable({
        "sScrollY": "50vh",
        "bScrollCollapse": true
    });
}



const getIMSIFailuresTime = function() {
    let startDate = $("#imsi-failure-time-start-date").val();
    let endDate = $("#imsi-failure-time-end-date").val();

    $.ajax({
        type: "GET",
        url: rootUrl + "/query/imsi-failures-time",
        data: { startDate: startDate, endDate: endDate },
        success: function(res) {
            console.log(res.data);
            updateImsiTimeTable(res.data)
            $("#imsi-failures-time-datatable-caption").append(startDate.replace('T', ' ') + "  to  " + endDate.replace('T', ' '));
        },
        error: function(error) {
            console.error("Error in AJAX request:", error);
        }
    });
}

function updateImsiTimeTable(data) {
    $('#imsi-failures-time-datatable-body').empty();
    data.forEach(function(imsiFailure) {
        $('#imsi-failures-time-datatable-body').append(`<tr>
            <td>${imsiFailure}</td>
        </tr>`);
    });
    if(datatable) {
        datatable.destroy();
    }
    datatable = $("#imsi-failures-time-datatable").DataTable({
        "sScrollY": "50vh",
        "bScrollCollapse": true
    });
}