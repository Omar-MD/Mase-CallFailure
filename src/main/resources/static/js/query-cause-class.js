
// List Failure Cause Classes
const addFailureCauseCodeDropdown = function(dropdownID) {
    let cause_class_dropdown = $(dropdownID);
    cause_class_dropdown.empty()

    cause_class_dropdown.select2({
        placeholder: "Begin typing Failure Cause Class to search..",
        allowClear: true,
        width: '100%',
        minimumInputLength: 0
    });

    $.ajax({
        type: 'GET',
        url: rootUrl + "/query/failure-cause-classes",
        contentType: 'application/json',
        dataType: "json",
        success: function(res) {
            if (res.status == "Success") {
                res.data.forEach(cause_class => {
                    cause_class_dropdown.append("<option value=" + cause_class.id + "><strong>" + cause_class.id + ":</strong> " + cause_class.description + "</option>");
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

// Query 4.5
const getIMSIFailureForFailureCauseClass = function() {

    let dropdown = $("#cause-failure-imsi-list-dropdown");
    let failureClass = dropdown.val();

    $.ajax({
        type: 'GET',
        url: rootUrl + "/query/imsi-failures-class/" + failureClass,
        contentType: 'application/json',
        dataType: "json",
        success: function(res) {
            if (res.status == "Success") {
                updateDataTable('cause-failure-imsi', res.data, []);
                let selectElement = document.getElementById("cause-failure-imsi-list-dropdown");
                let selectedOption = selectElement.options[selectElement.selectedIndex];
                let selectedText = selectedOption.text;
                $("#failure-class-datatable-caption").text(selectedText);
            } else {
                console.log("Error:", res.error);
            }
        },
        error: function(error) {
            console.error("Error:", error);
        }
    });
};

// Query #7
const getTop10MocCombinations = function() {
    let startDate = $("#top10-moc-combinations-start-date").val();
    let endDate = $("#top10-moc-combinations-end-date").val();

    $.ajax({
        type: "GET",
        url: rootUrl + "/query/top10-market-operator-cellid-combinations",
        data: { startDate: startDate, endDate: endDate },
        success: function(res) {
            if (res.status == "Success") {
                updateDataTable('top10-moc-combinations', res.data, ['mcc', 'mnc', 'cell_id', 'failure_count']);
                $("#top10-moc-combinations-datatable-caption").text("Top 10 MOC Combinations For Date Range - " + startDate.replace('T', ' ') + "  to  " + endDate.replace('T', ' '));
            } else {
                console.log("Error:", res.error);
            }
        },
        error: function(error) {
            console.error("Error in AJAX request:", error);
        }
    });
}