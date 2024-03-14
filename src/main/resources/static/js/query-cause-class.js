
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
        headers: { "Authorization": 'Bearer ' + localStorage.getItem('token') },
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
        headers: { "Authorization": 'Bearer ' + localStorage.getItem('token') },
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

/*// Query #7
const getTop10MocCombinations = function() {
    let startDate = $("#top10-moc-combinations-start-date").val();
    let endDate = $("#top10-moc-combinations-end-date").val();

    $.ajax({
        type: "GET",
        url: rootUrl + "/query/top10-market-operator-cellid-combinations",
        headers: { "Authorization": 'Bearer ' + localStorage.getItem('token') },
        
        data: { startDate: startDate, endDate: endDate },
        success: function(res) {
			console.log(res)
            if (res.status == "Success") {
                updateDataTable('top10-moc-combinations', res.data, ['mcc', 'mnc', 'cell_id', 'failure_count']);
                $("#top10-moc-combinations-datatable-caption").text("Top 10 MOC Combinations For Date Range - " + startDate.replace('T', ' ') + "  to  " + endDate.replace('T', ' '));
           
           	// top10-moc-combinations-container
           
            } else {
                console.log("Error:", res.error);
            }
        },
        error: function(error) {
            console.error("Error in AJAX request:", error);
        }
    });
}*/

// Query #7B
const getTop10MocCombinations = function() {
    let startDate = $("#top10-moc-combinations-start-date").val();
    let endDate = $("#top10-moc-combinations-end-date").val();

    $.ajax({
        type: "GET",
        url: rootUrl + "/query/top10-market-operator-cellid-combinations",
        data: { startDate: startDate, endDate: endDate },
        headers: { "Authorization": 'Bearer ' + localStorage.getItem('token') },
        success: function(res) {
            if (res.status == "Success") {
                updateDataTable('top10-moc-combinations', res.data, ["mcc", "mnc", "cell_id", "failure_count"]);
                $("#top10-moc-combinations-datatable-caption").text("top10-moc-combinations-datatable-caption").text("Top 10 MOC Combinations For Date Range - " + startDate.replace('T', ' ') + "  to  " + endDate.replace('T', ' '));
                
                
                const imsiList = res.data.map(entry => (entry.cell_id+"/"+ entry.mcc+"/"+ entry.mnc));
                const failureCountList = res.data.map(entry => entry.failure_count);
                const totalFailures = failureCountList.reduce((a,b) => a+b, 0);
                const failurePercentages = res.data.map(entry => (entry.failure_count / totalFailures) * 100);

                console.log(res);
                console.log(imsiList);
                console.log(failureCountList);
                console.log(failurePercentages);

                // =================================================================
				addChart({
					whereToAdd: "top10-moc-combinations-datatable-window",
					modalName: "top10-moc-combinations",
					title: "Top 10 Market/Operator/Cell ID Combinations",
					chartDetails: {
						type: 'bar',
						data: {
							labels: imsiList,
							datasets: [{
								label: "Failure Count",
								data: failureCountList,
								failurePercentages: failurePercentages,
								backgroundColor: 'gray',
								borderColor: 'red',
								borderWidth: 1,
								barPercentage: 1,
								categoryPercentage: 1,
								borderRadius: 5,
							}]
                        },
                        options: {
                            scales: {
                                x: {
                                    title: {
                                        display: true,
                                        text: "cell_id/mcc/mnc" 
                                        
                                        
                                    }
                                },
                                y: {
                                    beginAtZero: true,
                                    title: {
                                        display: true,
                                        text: "# of Failures"
                                    }
                                }
                                  
                            },
							plugins: {
								legend: {
									display: false,
								},
								tooltip: {
									callbacks: {
										title: function(tooltipItems) {
											console.log(tooltipItems)
											//console.log(data)
										   if (!tooltipItems.length) {
												return '';
											}
											let index = tooltipItems[0];
											let x = index.parsed.x;
											console.log(x)
											let failurePercentage = failurePercentages[x];
											console.log(failurePercentages);
											return `Failure Percentage: ${failurePercentage.toFixed(2)}%`;
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
