
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

// Query #7
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
                const nodeCellList = res.data.map(entry => (entry.mcc + "/" + entry.mnc + '/' + entry.cell_id));
                const failureCountList = res.data.map(entry => entry.failure_count);
                const totalFailures = failureCountList.reduce((a, b) => a + b, 0);
                const failurePercentages = res.data.map(entry => (entry.failure_count / totalFailures) * 100);

                addChart({
                    whereToAdd: "top10-moc-combinations-container",
                    modalName: "top10-moc-combinations",
                    title: "Top 10 Market/Operator/Cell ID Combinations",
                    chartDetails: {
                        type: 'bar',
                        data: {
                            labels: nodeCellList,
                            datasets: [{
                                label: "Failure Count",
                                data: failureCountList,
                                failurePercentages: failurePercentages,
                                backgroundColor: '#008080',
                                borderWidth: 1,

                            }]
                        },
                        options: {
                            scales: {
                                x: {
                                    title: {
                                        text: "Market/Operator/Cell ID Combinations",
                                    }
                                },
                                y: {
                                    title: {
                                        text: "Number of Call Failures",
                                    }
                                }
                            },
                            onHover: (event, chartElement) => {
                                event.native.target.style.cursor = chartElement[0] ? 'pointer' : 'default';
                            },
                            plugins: {
                                legend: {
                                    display: false,
                                },
                                tooltip: {
                                    callbacks: {
                                        title: function(tooltipItems) {
                                            if (!tooltipItems.length) {
                                                return '';
                                            }
                                            let index = tooltipItems[0];
                                            let x = index.parsed.x;
                                            let failurePercentage = failurePercentages[x];
                                            let node = nodeCellList[x].split('/');
                                            let mcc = node[0];
                                            let mnc = node[1];
                                            let cell = node[2];

                                            return `Cell Id: ${cell}\n` +
                                                `Node: { market: ${mcc}, operator: ${mnc}}\n` +
                                                `Failure Percentage: ${failurePercentage.toFixed(2)}%`;
                                        }
                                    }
                                }
                            }
                        }
                    },
                    clickHandler: (event) => {
                        let canvas = event.currentTarget;
                        let chartInstance = Chart.getChart(canvas);
                        let elements = chartInstance.getElementsAtEventForMode(event, 'nearest', { intersect: true }, false);

                        if (elements.length > 0) {
                            let index = elements[0].index;
                            let label = chartInstance.data.labels[index];
                            let cellId = label.split('/').pop();
                            failureCausesCountsByCellIdDrilldown(cellId);
                        }
                    },
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
};

const failureCausesCountsByCellIdDrilldown = function(cellId) {
    $.ajax({
        type: 'GET',
        url: rootUrl + "/query/failure-causes-counts-by-cellid",
        contentType: 'application/json',
        dataType: "json",
        headers: { "Authorization": 'Bearer ' + localStorage.getItem('token') },
        data: { "cellId": cellId },
        success: function(res) {
            const failureCause = res.data.map(entry => entry.failureCause);
            const failureCount = res.data.map(entry => entry.failureCount);

            handleDrillDown({
                modalName: "top10-moc-combinations",
                title: "Failure Causes and Counts By Cell ID",
                chartDetails: {
                    type: 'bar',
                    data: {
                        labels: failureCause,
                        datasets: [{
                            label: "Number of Failures for Cell ID #(" + cellId + ')',
                            data: failureCount,
                            fill: false,
                            backgroundColor: '#800000',
                            borderWidth: 2
                        }]
                    },
                    options: {
                        indexAxis: 'y',
                        scales: {
                            x: {
                                title: {
                                    text: 'Number of Failures',
                                }
                            },
                            y: {
                                title: {
                                    text: 'Failure Cause',
                                }
                            }
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