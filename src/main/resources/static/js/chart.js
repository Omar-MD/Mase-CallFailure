'use strict';

const chartStack = [];
let startChart = null;
let currentChart = null;

const saveChartToStack = function() {
    let chartData;
    if (chartStack.length === 0) {
        chartData = startChart == null ? currentChart : startChart;
    } else {
        chartData = currentChart;
    }
    const immutableChartData = JSON.parse(JSON.stringify(chartData));
    immutableChartData.clickHandler = chartData.clickHandler;
    immutableChartData.chartDetails.options = chartData.chartDetails.options;
    chartStack.push(immutableChartData);
}

// 1 - Creates Modal, Renders Starting Chart, and Saves it.
const addChart = function(chartData) {
    addMondal(chartData.whereToAdd, chartData.modalName);
    renderChart(chartData.modalName, chartData.title, chartData.chartDetails, chartData.clickHandler);
    startChart = chartData;
}

// Creates Modal
const addMondal = function(whereToAdd, modalName) {
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
                            <button type="button" class="" id="chart-backButton" style="display: none;"><i class="fas fa-arrow-left back-icon"></i></button>
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

// Renders Chart
const renderChart = function(modalName, title, chartDetails, clickHandler) {
	
    $('#' + modalName + '-chart-title').text(title);
    const canvas = $('#' + modalName + "-chart")[0];
    const ctx = canvas.getContext('2d');
    
    const mergedOptions = $.extend(true, {}, defaultChartOptions, chartDetails.options);
    
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

// Handles Drill Down
const handleDrillDown = function(chartData) {
    saveChartToStack();
    renderChart(chartData.modalName, chartData.title, chartData.chartDetails, chartData.clickHandler);
    showBackButton();
    currentChart = chartData;
    startChart = null;
};

const showBackButton = function() {
    $('#chart-backButton').show().off('click');
    $('#chart-backButton').on('click', function() {
        renderChartFromStack();
        if (chartStack.length === 0) {
            $('#chart-backButton').hide();
        }
    });
};

const renderChartFromStack = function() {
    if (chartStack.length > 0) {
        let c = chartStack.pop();
        currentChart = c;
        renderChart(c.modalName, c.title, c.chartDetails, c.clickHandler);
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