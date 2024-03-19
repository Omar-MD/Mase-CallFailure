'use strict';

const modalData = {}; // Object to store modal-specific data

const saveChartToStack = function(modalName) {
    const currentModal = modalData[modalName];
    const immutableChartData = JSON.parse(JSON.stringify(currentModal.currentChart));
    immutableChartData.clickHandler = currentModal.currentChart.clickHandler;
    immutableChartData.chartDetails.options = currentModal.currentChart.chartDetails.options;
    currentModal.chartStack.push(immutableChartData);
}

const addChart = function(chartData) {
    const modalName = chartData.modalName;
    resetModalData(modalName);
    $('#' + modalName + '-backButton').hide();
    addModal(chartData.whereToAdd, modalName);
    renderChart(modalName, chartData.title, chartData.chartDetails, chartData.clickHandler);
    modalData[modalName].currentChart = chartData;
}

// Reset modalData
const resetModalData = function(modalName) {
    if (!modalData[modalName]) {
        modalData[modalName] = {
            chartStack: [],
            currentChart: null
        };
    } else {
        modalData[modalName].chartStack.length = 0;
        modalData[modalName].currentChart = null;
    }
}

// Creates Modal
const addModal = function(whereToAdd, modalName) {
    let oldChart = $('#' + modalName + "-chart");
    if (oldChart.length) {
        // Remove old chart
        oldChart.remove();
        // Add new chart
        $("#" + modalName + "-chart-container").append(`<canvas class="chart" id="${modalName}-chart"></canvas>`)
    } else {
        let modal = `
        <div class="d-flex justify-content-end">
            <button type="button" class="btn btn-dark" data-toggle="modal" data-target="#${modalName}-chart-modal" >Visualise</button>
        </div>
        <div class="modal fade" id="${modalName}-chart-modal">
            <div class="modal-dialog modal-dialog-centered modal-xl">
                <div class="modal-content">
                    <!-- Modal Title -->
                    <div class="modal-header">      
                        <div class="d-flex align-items-center" style="width: 100%;">
                            <button type="button" class="back-button" id="${modalName}-backButton" style="display: none;"><i class="fas fa-arrow-left back-icon"></i></button>
                            <h4 class="modal-title" id="${modalName}-chart-title" style="margin: auto;">Graph Title</h4>
                            <button type="button" class="close custom-close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                        </div>
                    </div>
                    <div class="modal-body" id="${modalName}-chart-container">
                        <canvas class="chart" id="${modalName}-chart"></canvas>
                    </div>
                </div>
            </div>
        </div>`

        // Adds a '#' if one is not supplied
        if (whereToAdd.length > 0 && whereToAdd.charAt(0) != '#') {
            whereToAdd = "#" + whereToAdd;
        }
        $(whereToAdd).append(modal);
    }
}

const renderChart = function(modalName, title, chartDetails, clickHandler) {
    $('#' + modalName + '-chart-title').text(title);
    const canvas = $('#' + modalName + "-chart")[0];
    const ctx = canvas.getContext('2d');
    const mergedOptions = $.extend(true, {}, defaultChartOptions, chartDetails.options);

    sortData(chartDetails);

    if (canvas.chartInstance) {
        canvas.chartInstance.config.data = chartDetails.data;
        canvas.chartInstance.config.options = mergedOptions;
        canvas.chartInstance.update();
    } else {
        canvas.chartInstance = new Chart(ctx, {
            ...chartDetails,
            options: mergedOptions
        });
    }

    if (clickHandler) {
        $(canvas).off('click').on('click', clickHandler);
    } else {
        $(canvas).off('click');
    }
};

const sortData = function(chartDetails) {
    let xData = chartDetails.data.datasets[0].data;
    let yData = chartDetails.data.labels;

    let indices = Array.from(xData.keys());
    indices.sort((a, b) => xData[b] - xData[a]);

    // Use the sorted indices to rearrange both lists
    xData = indices.map(index => xData[index]);
    yData = indices.map(index => yData[index]);

    chartDetails.data.datasets[0].data = xData;
    chartDetails.data.labels = yData;
}

const handleDrillDown = function(chartData) {
    const modalName = chartData.modalName;
    saveChartToStack(modalName);
    renderChart(modalName, chartData.title, chartData.chartDetails, chartData.clickHandler);
    showBackButton(modalName);
    modalData[modalName].currentChart = chartData;
};

const showBackButton = function(modalName) {
    $('#' + modalName + '-backButton').show().off('click');
    $('#' + modalName + '-backButton').on('click', function() {
        renderChartFromStack(modalName);
        if (modalData[modalName].chartStack.length === 0) {
            $('#' + modalName + '-backButton').hide();
        }
    });
};

const renderChartFromStack = function(modalName) {
    const modal = modalData[modalName];
    if (modal.chartStack.length > 0) {
        let c = modal.chartStack.pop();
        modal.currentChart = c;
        renderChart(modalName, c.title, c.chartDetails, c.clickHandler);
    }
};

const defaultChartOptions = {
    scales: {
        x: {
            ticks: {
                font: {
                    size: 14
                }
            },
            title: {
                display: true,
                font: {
                    size: 18,
                }
            }
        },
        y: {
            beginAtZero: true,
            title: {
                display: true,
                font: {
                    size: 18,
                }
            },
            ticks: {
                fontSize: 14
            }
        }
    },
    onHover: (event) => {
        event.native.target.style.cursor = 'default';
    }
};