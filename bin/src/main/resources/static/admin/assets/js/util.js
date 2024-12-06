$(document).ready(function () {
    maxDate();
    imagePreview()
});

// Lấy ngày tháng tối đa hiện tại
function maxDate() {
    var currentDate = new Date();
    var formattedDate = currentDate.toISOString().split('T')[0];
    $('#date_of_birth').attr('max', formattedDate);
}

function imagePreview() {
    const avatarFile = $("#avatar");

    avatarFile.change(function (e) {
        const imgURL = URL.createObjectURL(e.target.files[0]);
        $("#avatarPreview").attr("src", imgURL);
        $("#avatarPreview").css({ "display": "block" });
    });
}

