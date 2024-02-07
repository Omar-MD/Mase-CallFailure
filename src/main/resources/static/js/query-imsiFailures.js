// import DataTable from 'datatables.net-dt';

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
