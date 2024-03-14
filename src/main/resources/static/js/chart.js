
/*
 * 	This function creats a button that spawns a modal in the id of "whereToAdd"
 *
 *	The mondal has ids derived from the second parameter "modalName"
 */
const addMondal = function (whereToAdd, modalName) {

	
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
						<h2 class="modal-title" id="${modalName}-chart-title">Graph Title</h2>
						<!-- 'x' button in the top right -->
						<button type="button" class="close custom-close" data-dismiss="modal" aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
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

/*
 * This function takes in 4 arguments
 *	1. The title of the chart, this will be displayed in the header of the modal
 *  2. The chart key. This will be used in the key for the chart to denote what the chart data is
 *  3&4. The x and y data, this must be in an array and not a map or JSON object
 */
var renderChart = function (modalName, title, chartDetails) {
	$('#' + modalName + '-chart-title').text(title);
	// console.log(xData);
	// console.log(yData);
	const ctx = document.getElementById(modalName + "-chart").getContext('2d');
	new Chart(ctx, chartDetails);
};


const addChart = function (chartData) {
	addMondal(chartData.whereToAdd, chartData.modalName);
	renderChart(chartData.modalName, chartData.title, chartData.chartDetails);
}

