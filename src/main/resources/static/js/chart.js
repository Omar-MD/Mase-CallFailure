
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
                        <div class="d-flex align-items-center" style="width: 100%;">
                            <button type="button" class="btn btn-dark" id="chart-backButton" style="display: none;">Back</button>
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

/*
 * This function takes in 4 arguments
 */
var renderChart = function (modalName, title, chartDetails, clickHandler) {
	$('#' + modalName + '-chart-title').text(title);
	const canvas = $('#'+modalName + "-chart")[0];
	const ctx = canvas.getContext('2d');
	
    if (canvas.chartInstance) {
        canvas.chartInstance.data = chartDetails.data;
        canvas.chartInstance.options = chartDetails.options;
        canvas.chartInstance.update();
    } else {
        canvas.chartInstance = new Chart(ctx, chartDetails);
    }
    
    if (clickHandler) {
        $(document).on('click', '#' + modalName + '-chart', clickHandler);
    } else {
        $(document).off('click', '#' + modalName + '-chart');
    }
};


const addChart = function (chartData) {
	addMondal(chartData.whereToAdd, chartData.modalName);
	renderChart(chartData.modalName, chartData.title, chartData.chartDetails, chartData.clickHandler);
}

