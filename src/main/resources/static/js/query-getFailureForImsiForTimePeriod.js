const getIMSIFailuresTimeCount = function() {
    console.log("getIMSIFailuresTimeCount");

    let imsi = $("#imsi-failure-time-count-imsi-dropdown").val();
    var startDate = $("#imsi-failure-time-count-start-date").val();
    var endDate = $("#imsi-failure-time-count-end-date").val();

    $.ajax({
        type: "GET",
        url: rootUrl + "/query/imsi-failures-time-count",
        data: { imsi: imsi, startDate: startDate, endDate: endDate },
        success: function(res) {
            console.log(res.data);
            var oldResult = $("#imsi-failure-time-count-result")
            if(oldResult.length) {
                oldResult.remove()
            }
            $('#imsi-failure-time-count-container').append(`
                <div class="card" id="imsi-failure-time-count-result">
                    <div class="card-body">
                        <h2 class="card-title">IMSIs Failures Count</h2>
                        <p class="card-text">
                            IMSI: ${imsi}<br>
                            Start Date: ${startDate.replace('T', ' ')}<br>
                            End Date: ${endDate.replace('T', ' ')}<br>
                            <h3>Count: ${res.data}</h3>
                        </p>
                    </div>
                </div>
            `);
        },
        error: function(error) {
            console.error("Error in AJAX request:", error);
        }
    });
};