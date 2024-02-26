
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

function updateDataTable(tableId, data, headers) {
    
    // Get the DataTable instance
    let datatable = $(`#${tableId}-datatable`).DataTable();
    // Clear existing datatable
    datatable.clear().draw();
     
    // Populate the table with new data
    data.forEach(function(item) {
        let rowData = [];
        headers.forEach(header => {
            rowData.push(item[header]);
        });
        datatable.row.add(rowData);
    });

    // Redraw the table to reflect the changes
    datatable.draw();
}

/*function initSelect2() {
    let imsi_dropdown = $("#imsi-dropdown");
    imsi_dropdown.select2({
        placeholder: "Begin typing IMSI to search..",
        width: '100%',
        minimumInputLength: 0
    });
}*/

const getIMSIFailures = function() {
    let imsi = $("#imsi-dropdown").val();
    let headers = ['eventId', 'causeCode', 'description'];
     
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
               
                updateDataTable('imsi-failure', res.data, headers);
              /*  updateTable(res.data);*/
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

