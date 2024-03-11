

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
 * This function takes in 4 arguments
 *	1. The title of the chart, this will be displayed in the header of the modal
 *  2. The chart key. This will be used in the key for the chart to denote what the chart data is
 *  3&4. The x and y data, this must be in an array and not a map or JSON object
 */
var renderChart = function (title, key, xData, yData) {
	$('#'+currentChartStr+'-chart-title').text(title);
	// console.log(xData);
	// console.log(yData);
	const ctx = document.getElementById(currentChartStr+"-chart").getContext('2d');
	new Chart(ctx, {
		type: 'bar',
		data: {
			labels: xData,
			datasets: [{
				label: key, 
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