$(document).ready(function () {
	ajaxGetAllUser();
	ajaxCreateUser();
	ajaxUpdateUser();
	ajaxDeleteUser();
});

function ajaxGetAllUser() {
	let currentStatus;
	let currentRole;
	$('#statusFilter').on('change', function () {
		currentStatus = $(this).val();
		table.ajax.reload(null, false);
	});

	$('#roleFilter').on('change', function () {
		currentRole = $(this).val();
		table.ajax.reload(null, false);
	});

	const table = $('#userTable').DataTable({
		...tableConfig,
		processing: true,
		serverSide: true,
		ajax: {
			url: "/api/admin/user/users",
			type: "GET",
			data: function (d) {
				d.status = currentStatus;
				d.role = currentRole;
			},
			dataSrc: "data"
		},
		columns: [
			{ "data": "id" },
			{
				"data": "avatar",
				"orderable": false,
				"render": data => `<img src="/admin/assets/img/avatar/${data}" width="60" height="60" />`
			},
			{ "data": "surname" },
			{ "data": "name" },
			{ "data": "status" },
			{
				"data": "role",
				"render": function (data) {
					return data === 1 ? "Admin" : "User";
				}
			},
			{ "data": "update_at", "orderable": false },
			{ "data": "last_login", "orderable": false },
			{
				"data": null,
				"orderable": false,
				"render": function (data, type, row) {
					return `
                        <div class="button-action">
                            <button type="button" class="infor-btn btn btn-link btn-infor btn-lg">
                                <i class="fa fa-info"></i>
                            </button>
                            <button type="button" class="delete-btn btn btn-link btn-danger" data-id="${row.id}">
                                <i class="fas fa-trash-alt"></i>
                            </button>
                        </div>
                    `;
				}
			},
			{ "data": "email", "visible": false },
			{ "data": "phone", "visible": false },
			{ "data": "create_at", "visible": false },
			{ "data": "address", "visible": false },
			{ "data": "gender", "visible": false },
			{ "data": "date_of_birth", "visible": false },
		]
	});

	$('#userTable tbody').on('click', '.infor-btn', function () {
		const rowData = table.row($(this).parents('tr')).data();
		fillUserInfoModal(rowData);
		$('#userInfoModal').modal('show');
	});
}

function fillUserInfoModal(data) {
	$('#update-user-form input[name="id"]').val(data.id);
	$('#update-user-form input[name="surname"]').val(data.surname);
	$('#update-user-form input[name="name"]').val(data.name);
	$('#update-user-form input[name="email"]').val(data.email);
	$('#update-user-form input[name="address"]').val(data.address);
	$('#update-user-form input[name="phone"]').val(data.phone);
	$('#update-user-form select[name="gender"]').val(data.gender);
	$('#update-user-form select[name="status"]').val(data.status);
	$('#update-user-form input[name="date_of_birth"]').val(changeToLocalDateJS(data.date_of_birth));
	$('#update-user-form select[name="role"]').val(data.role);

	if (data.avatar) {
		$('.avatarPreviewInfor').attr('src', '/admin/assets/img/avatar/' + data.avatar).css({ "display": "block" });
	} else {
		$('.avatarPreviewInfor').hide();
	}
}

function ajaxCreateUser() {
	$("#btn-create-user").click(function () {
		const formData = $("#create-user-form").serializeArray();
		const user = {};

		$.each(formData, function (i, field) {
			if (field.name === "role") {
				if (!user.role) {
					user.role = {};
				}
				user.role.id = field.value;
			} else {
				user[field.name] = field.value;
			}
		});

		const uploadData = new FormData();
		uploadData.append('dataJson', JSON.stringify(user));

		const fileInput = $("input[name='avatar']")[0].files[0];
		if (fileInput) {
			uploadData.append('file', fileInput);
		}

		$.ajax({
			url: "/api/admin/user/create",
			type: "POST",
			data: uploadData,
			processData: false,
			contentType: false,
			success: function (response) {
				$("#create-user-form")[0].reset();
				$(".avatarPreview").css({ "display": "none" });
				showToast("Thêm người dùng thành công!", 'Thành công', 'success', '#9EC600');
				$('#userTable').DataTable().ajax.reload(null, false);
			},
			error: function (xhr) {
				if (xhr.status === 400) {
					const errors = JSON.parse(xhr.responseText);
					$(".is-invalid").removeClass("is-invalid");
					$(".invalid-feedback").remove();
					let errorMessages = '';
					Object.keys(errors).forEach(field => {
						const inputField = $(`#create-user-form [name='${field}']`);
						inputField.addClass("is-invalid");
						const errorFeedback = `<div class="invalid-feedback">${errors[field]}</div>`;
						inputField.after(errorFeedback);
						errorMessages += `${errors[field]}<br>`;
					});
					showToast(errorMessages, 'Thất bại', 'error', '#c70000');
				}
			}
		});
	});

	$('#modal-create').on('shown.bs.modal', function () {
		$('#create-user-form input[name="surname"]').focus();
	});
}

function ajaxUpdateUser() {
	$("#btn-edit-user").click(function () {
		const formData = $("#update-user-form").serializeArray();
		const user = {};

		$.each(formData, function (i, field) {
			if (field.name === "role") {
				if (!user.role) {
					user.role = {};
				}
				user.role.id = field.value;
			} else {
				user[field.name] = field.value;
			}
		});

		const uploadData = new FormData();
		uploadData.append('dataJson', JSON.stringify(user));

		const fileInput = $("input[name='avatarInfor']")[0].files[0];
		if (fileInput) {
			uploadData.append('file', fileInput);
		}

		$.ajax({
			url: "/api/admin/user/update",
			type: "POST",
			data: uploadData,
			processData: false,
			contentType: false,
			success: function (response) {
				showToast("Sửa người dùng thành công!", 'Thành công', 'success', '#9EC600');
				$('#userTable').DataTable().ajax.reload(null, false);
			},
			error: function (xhr) {
				if (xhr.status === 400) {
					errors = JSON.parse(xhr.responseText);
					$(".is-invalid").removeClass("is-invalid");
					$(".invalid-feedback").remove();
					let errorMessages = '';
					Object.keys(errors).forEach(field => {
						errorMessages += `${errors[field]}<br>`;
					});
					showToast(errorMessages, 'Thất bại', 'error', '#c70000');
				}
			}
		});
	});
}

function ajaxDeleteUser() {
	$('#userTable tbody').on('click', '.delete-btn', function () {
		const id = $(this).data('id');
		swal({
			title: `Bạn có chắc chắn muốn xóa người dùng id là ${id} ?`,
			text: 'Hành động này không thể hoàn tác!',
			icon: 'error',
			buttons: ["Hủy", "OK"]
		}).then((willDelete) => {
			if (willDelete) {
				$.ajax({
					url: "/api/admin/user/delete",
					type: "POST",
					data: { id: id },
					success: function (response) {
						showToast("Xóa người dùng thành công!", 'Thành công', 'success', '#9EC600');
						$('#userTable').DataTable().ajax.reload(null, false);
					},
					error: function () {
						showToast("Xóa người dùng thất bại!", 'Thất bại', 'error', '#c70000');
					}
				});
			}
		});
	});
}




