
const importDataset = function() {
    let filename = $('#filename').val();
    $('#importMsg').hide(); // Hide message div initially
    showProgressBar();
    $.ajax({
        type: 'POST',
        url: rootUrl + "/sysadmin/import",
        contentType: 'application/json',
        data: JSON.stringify({ "filename": filename }),
        dataType: "json",
        success: function(res) {
            hideProgressBar();
            if (res.status == "Success") {
                $('#importMsg').removeClass().addClass("alert alert-success").html("<strong>Success!</strong> Import Complete").show();
            } else {
                hideProgressBar();
                $('#importMsg').removeClass().addClass("alert alert-danger").html(`<strong>Error!</strong> ${res.error.errorMsg}<br/>${res.error.details}`).show();

            }
        },
        error: function() {
            hideProgressBar();
            $('#importMsg').removeClass().addClass("alert alert-danger").html("<strong>Error!</strong> Unexpected Import Error<br/>").show();
        }
    });
};


function handleFileSelection(input) {
    if (input?.files[0]) {
        let filename = input.files[0].name;
        $('#filename').val(filename);
    }
}

const showProgressBar = function() {
    $('#progressBarBG').show();
    $('#progressBarFG').show();
}

const hideProgressBar = function() {
    $('#progressBarBG').hide();
    $('#progressBarFG').hide();
}

$(document).ready(function() {
    $('#import-now').on('click', function(event) {
        event.preventDefault();
        importDataset();
    });
});
