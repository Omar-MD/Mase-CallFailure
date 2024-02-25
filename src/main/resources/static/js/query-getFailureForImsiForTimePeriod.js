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
            $('#imsi-failure-time-count-container').append(`
                <div class="mb-3">
                    <h2>Count for Failures for ${imsi} for dates ${startDate} ~ ${endDate} is ${res.data}</h2>
                </div>
            `);
        },
        error: function(error) {
            console.error("Error in AJAX request:", error);
        }
    });
};