

let currentChartStr = ""

/*
 * Pass in the name for the chart eg "imsi-failures-count-duration"
 * This function will
 *  1. Remove all the chart elements
 *  2. Get the chart container by appending "-chart-container" to the input
 *  3. Create a new chart inside the chart container with the id being the input with "-chart" appeneded
 *  4. Save current chart name into currentChartStr
 */ 
const buildChart = function (chartName) {
    $(".chart").remove();
    $("#"+chartName+"-chart-container").append(`<canvas id="${chartName+"-chart"}"></canvas>`)
    currentChartStr = chartName
}



/*
 *
 *
 *
 *
 *
 *
 *
 *
 */
var renderChart = function (xData, yData) {
	//console.log(plotData);
	$('#'+currentChartStr+'-chart-title').text("IMSI Failure Counts and Duration");
	// destroyAndRecreate();
	// console.log(plotData);
	// const brand = plotData.map((x) => x.id);
	// const quantity = plotData.map((x) => x.quantity);
	// console.log(brand);
	//console.log(quantity);
	console.log(xData);
	console.log(yData);
	const ctx = document.getElementById(currentChartStr+"-chart").getContext('2d');
	new Chart(ctx, {
		type: 'bar',
		data: {
			labels: xData,
			datasets: [{
				label: 'Quantity of items per order (Order id)', 
				data: yData, 
				backgroundColor: '#198754',
				borderWidth: 1
			}]
		},
		options: {
			scales: {
				y: {
					beginAtZero: true
				}
			}
		}
	});
};