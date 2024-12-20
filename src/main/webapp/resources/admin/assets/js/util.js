$(document).ready(function () {
	maxDate();
	imagePreview();
	btnResetFormCreate();
});

function maxDate() {
	const currentDate = new Date();
	const formattedDate = currentDate.toISOString().split('T')[0];
	$('#date_of_birth').attr('max', formattedDate);
}

function imagePreview() {
	const avatarFile = $(".avatar-input");

	avatarFile.change(function (e) {
		const imgURL = URL.createObjectURL(e.target.files[0]);
		$(".avatarPreview, .avatarPreviewInfor").attr("src", imgURL).css({ "display": "block" });
	});
}

function btnResetFormCreate() {
	$("#btn-reset").click(function () {
		$("#create-user-form")[0].reset();
		$(".avatarPreview").css({ "display": "none" });
	});
}

function changeToLocalDateJS(dateArray) {
	const year = dateArray[0];
	const month = (dateArray[1] < 10 ? '0' : '') + dateArray[1];
	const day = (dateArray[2] < 10 ? '0' : '') + dateArray[2];

	return year + '-' + month + '-' + day;
}

const tableConfig = {
	scrollX: true,
	language: {
		processing: "Đang tải dữ liệu...",
		search: "Tìm kiếm",
		lengthMenu: "Hiển thị _MENU_ bản ghi",
		info: "Hiển thị _START_ đến _END_ trong tổng số _TOTAL_ bản ghi",
		infoEmpty: "Không có bản ghi nào để hiển thị",
		infoFiltered: "(Được lọc từ _MAX_ bản ghi gốc)",
		loadingRecords: "Đang tải dữ liệu...",
		zeroRecords: "Không có bản ghi nào để hiển thị",
		emptyTable: "Không có dữ liệu trong bảng",
		paginate: {
			first: "Đầu tiên",
			previous: "Trước",
			next: "Tiếp theo",
			last: "Cuối cùng"
		},
		aria: {
			sortAscending: ": kích hoạt để sắp xếp cột theo thứ tự tăng dần",
			sortDescending: ": kích hoạt để sắp xếp cột theo thứ tự giảm dần"
		}
	}
};

function showToast(message, heading, icon, loaderBg) {
	$.toast({
		text: message,
		heading: heading,
		icon: icon,
		showHideTransition: 'fade',
		allowToastClose: true,
		hideAfter: 3000,
		stack: 5,
		position: {
			top: 70,
			right: 10
		},
		textAlign: 'left',
		loader: true,
		loaderBg: loaderBg,
	});
}
