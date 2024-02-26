
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
        success: function(res) {
            if (res.status == "Success") {
                console.log(res.data);
                res.data.forEach(imsi => {
                    imsi_dropdown.append("<option value=" + imsi + ">" + imsi + "</option>");
                });
                initSelect2();
            } else {
                console.log("Error:", res.error);
            }
        },
        error: function(error) {
            console.error("Error:", error);
        }
    });
}

function initSelect2() {
    let imsi_dropdown = $("#imsi-dropdown");
    imsi_dropdown.select2({
        placeholder: "Begin typing IMSI to search..",
        width: '100%',
        minimumInputLength: 0
    });
}

function updateDataTable(tableId, data, headers) {
     // Check if DataTable is already initialized
    let datatable = $(`#${tableId}-datatable`).DataTable();
    if (datatable) {
        datatable.clear().draw();
    } else {
        datatable = $(`#${tableId}-datatable`).DataTable({
            "sScrollY": "50vh",
            "bScrollCollapse": true
        });
    }
    data.forEach(function(item) {
        if (headers && headers.length > 0) {
            let rowData = [];
            headers.forEach(header => {
                rowData.push(item[header]);
            });
            datatable.row.add(rowData);
        } else {
            // If no headers provided, append data directly as a single-column row
            datatable.row.add([item]);
        }
    });

    datatable.draw();
};


const getIMSIFailures = function() {
    let imsi = $("#imsi-dropdown").val();
     
    $.ajax({
        type: 'GET',
        url: rootUrl + "/query/imsi-failures/" + imsi,
        contentType: 'application/json',
        dataType: "json",
        success: function(res) {
            if (res.status == "Success") {
                console.log(res.data);
                updateDataTable('imsi-failure', res.data, ['eventId', 'causeCode', 'description']);
            } else {
                console.log("Error:", res.error);
            }
        },
        error: function(error) {
            console.error("Error:", error);
        }
    });
};

const getIMSIFailuresTime = function() {
    let startDate = $("#imsi-failure-time-start-date").val();
    let endDate = $("#imsi-failure-time-end-date").val();

    $.ajax({
        type: "GET",
        url: rootUrl + "/query/imsi-failures-time",
        data: { startDate: startDate, endDate: endDate },
        success: function(res) {
            console.log(res.data);
            updateDataTable('imsi-failures-time', res.data, []);
            $("#imsi-failures-time-datatable-caption").text(startDate.replace('T', ' ') + "  to  " + endDate.replace('T', ' '));
        },
        error: function(error) {
            console.error("Error in AJAX request:", error);
        }
    });
}

const getCallFailureCount = function() {
    var startDate = $("#callFailureCount-start-date").val();
    var endDate = $("#callFailureCount-end-date").val();

    $.ajax({
        type: "GET",
        url: rootUrl + "/query/call-failure-count",
        data: { startDate: startDate, endDate: endDate },
        success: function(res) {
            console.log(res.data);
            updateImsiTimeTable(res.data)
            $("#callFailureCount-datatable-caption").append(startDate.replace('T', ' ') + "  to  " + endDate.replace('T', ' '));
        },
        error: function(error) {
            console.error("Error in AJAX request:", error);
        }
    });
}

function updateCallFailureCountTable(data) {
    $('#callFailureCount-datatable-body').empty();
    data.forEach(function(item) {
        $('#callFailureCount-datatable-body').append(`<tr>
            <td>${item.imsi}</td>
            <td>${item.failureCount}</td>
            <td>${item.duration}</td>
        </tr>`);
    });
    
    if(datatable) {
        datatable.destroy();
    }
    
    datatable = $("#callFailureCount-datatable").DataTable({
        "sScrollY": "50vh",
        "bScrollCollapse": true
    });
}

const getIMSIFailureCountDuration = function() {
    let startDate = $("#imsi-count-duration-start-date").val();
    let endDate = $("#imsi-count-duration-end-date").val();
    
    $.ajax({
        type: "GET",
        url: rootUrl + "/query/imsi-failures-count-duration",
        data: { startDate: startDate, endDate: endDate},
        success: function(res) {
            console.log(res);
           if (res.status == "Success") {
                updateDataTable('imsi-count-duration', res.data, ["imsi", "failureCount", "totalDuration"]);
                $("#imsi-count-duration-datatable-caption").text("IMSI Failure Count and Duration - " + startDate.replace('T', ' ') + " to " + endDate.replace('T', ' '));
            }else{
                console.log("Error:", res.error);
            }
        },
        error: function(error) {
            console.error("Error in AJAX request:", error);
        }
    });
}
