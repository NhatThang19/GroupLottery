$(document).ready(function() {
	maxDate();
	imagePreview();
	btnResestFormCreate();
});

// Lấy ngày tháng tối đa hiện tại
function maxDate() {
	var currentDate = new Date();
	var formattedDate = currentDate.toISOString().split('T')[0];
	$('#date_of_birth').attr('max', formattedDate);
}

// Xem trước ảnh
function imagePreview() {
	const avatarFile = $(".avatar-input");

	avatarFile.change(function(e) {
		const imgURL = URL.createObjectURL(e.target.files[0]);
		$(".avatarPreview").attr("src", imgURL);
		$(".avatarPreview").css({ "display": "block" });
	});
}

//Đặt lại form
function btnResestFormCreate() {
	$("#btn-resest").click(function() {
		$("#create-user-form")[0].reset();
		$("#avatarPreview").css({ "display": "none" });
	});
}
