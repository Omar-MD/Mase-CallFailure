
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
            console.log(res);
            if (res.status == "Success") {
                res.data.forEach(imsi => {
                    imsi_dropdown.append("<option value="+imsi+">"+imsi+"</option>");
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

const getIMSIFailures = function() {
    let imsi = $("#imsi-dropdown").val();
    console.log("IMSI: " + imsi);
    $(".imsi").text(imsi);
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

function updateTable(data) {
    $('#imsi-failure-datatable').DataTable().rows().remove().draw();
    data.forEach(function(item) {
        $('#imsi-failure-datatable').DataTable().row.add([
            item.eventId,
            item.causeCode,
            item.description
        ]).draw(false);
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
            $("#imsi-failures-time-datatable-caption").html(startDate.replace('T', ' ') + "  to  " + endDate.replace('T', ' '));
        },
        error: function(error) {
            console.error("Error in AJAX request:", error);
        }
    });
}

function updateImsiTimeTable(data) {
    if(datatable) {
        datatable.destroy();
    }
    $('#imsi-failures-time-datatable-body').empty();
    data.forEach(function(imsiFailure) {
        $('#imsi-failures-time-datatable-body').append(`<tr>
            <td>${imsiFailure}</td>
        </tr>`);
    });
    datatable = $("#imsi-failures-time-datatable").DataTable({
        "sScrollY": "50vh",
        "bScrollCollapse": true
    });
}
