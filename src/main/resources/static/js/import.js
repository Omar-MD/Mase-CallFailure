
importDataset = function() {
    let filename = $('#filename').val();
    console.log(filename)

    console.log('Attempting File import', filename);
    $.ajax({
        type: 'POST',
        url: rootUrl + "/sysadmin/import",
        contentType: 'application/json',
        data: JSON.stringify({ "filename": filename }),
        dataType: "json",
        success: function(res) {
            console.log(res);
            if (res.status == "Success") {
                alert('Import successful!');

            } else {
                alert('Import unsuccessful!');
                err = res.error
                console.log(err.errorMsg, err.details);
            }
            hideProgressBar();
        },
        error: function() {
            alert('Error during request.');
        }
    });
};


function handleFileSelection(input) {
    if (input.files && input.files[0]) {
        var filename = input.files[0].name;
        $('#filename').val(filename);
    }
}

showProgressBar = function(){
   $('#progressBarBG').show();
   $('#progressBarFG').show();
}

hideProgressBar = function(){
   $('#progressBarBG').hide();
   $('#progressBarFG').hide();
}

$(document).ready(function() {
    $('#import-now').on('click', function(event) {
        console.log("Import Dataset...");
        event.preventDefault();
        showProgressBar();
        importDataset();
    });
});
