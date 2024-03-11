

let currentChartStr = ""

/*
 * Pass in the name for the chart eg "imsi-failures-count-duration"
 * This function will
 *  1. Remove all the chart elements
 *  2. Get the chart container by appending "-chart-container" to the input
 *  3. Create a new chart inside the chart container with the id being the input with "-chart" appeneded
 *  4. Save current chart name into currentChartStr
 */ 
// const buildChart = function (chartName) {
//     $(".chart").remove();
//     $("#"+chartName+"-chart-container").append(`<canvas id="${chartName+"-chart"}"></canvas>`)
//     currentChartStr = chartName
// }

/*
 * 	This function creats a button that spawns a modal in the id of "whereToAdd"
 *
 *	The mondal has ids derived from the second parameter "modalName"
 */
const addMondal = function(whereToAdd, modalName) {
	let mondal = `
		<div class="mb-3">
			<button type="button" class="btn btn-dark" data-toggle="modal"
				data-target="#${modalName}-chart-modal">Show Graph</button>
		</div>
		<div class="modal fade" id="${modalName}-chart-modal">
			<div class="modal-dialog modal-dialog-centered modal-lg">
				<div class="modal-content">
					<!-- Mondal Title -->
					<div class="modal-header">
						<h1 class="modal-title" id="${modalName}-chart-title">Graph Title</h1>
						<!-- 'x' button in the top right -->
						<button type="button" class="close" data-dismiss="modal" aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
					</div>
					<div class="modal-body" id="${modalName}-chart-container">
						<canvas class="chart" id="${modalName}-chart"></canvas>
					</div>
				</div>
			</div>
		</div>
	`
	// Adds a '#' if one is not supplied
	if (whereToAdd.length > 0 && whereToAdd.charAt(0) != '#') {
		whereToAdd = "#" + whereToAdd;
	}
	$(whereToAdd).append(mondal);

	// Saves the modal name so we render it later
	currentChartStr = modalName;
}

/*
 * This function takes in 4 arguments
 *	1. The title of the chart, this will be displayed in the header of the modal
 *  2. The chart key. This will be used in the key for the chart to denote what the chart data is
 *  3&4. The x and y data, this must be in an array and not a map or JSON object
 */
var renderChart = function(title, key, xData, yData) {
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


const addChart = function (whereToAdd, modalName, title, key, xData, yData) {
	addMondal(whereToAdd, modalName);
	renderChart(title, key, xData, yData);
}