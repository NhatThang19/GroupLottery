var table;

$(document).ready(function() {
	ajaxGetAllUser();
	ajaxCreateUser();
});

function ajaxGetAllUser() {
	table = $('#userTable').DataTable({
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
		},
		ajax: {
			"url": "/api/admin/user/users",
			"dataSrc": ""
		},
		columns: [
			{ "data": "id" },
			{
				"data": "avatar",
				"orderable": false,
				"render": function(data, type, row) {
					return '<img src="/admin/assets/img/avatar/' + data + '" width="50" height="50" />';
				}
			},
			{
				"data": "surname",
				"orderable": false,
			},
			{
				"data": "name",
				"orderable": false
			},
			{ "data": "status" },
			{ "data": "role" },
			{ "data": "update_at", "orderable": false },
			{ "data": "last_login", "orderable": false },
			{
				"data": null,
				"orderable": false,
				"render": function(data, type, row) {
					return `
						<div class="button-action">
							<button type="button" class="infor-btn btn btn-link btn-infor btn-lg">
								<i class="fa fa-info"></i>
							</button>
                        	<button type="button" class="btn btn-link btn-danger" data-id="${row.id}">
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
		],
	});

	$('#userTable tbody').on('click', '.infor-btn', function() {
		var rowData = table.row($(this).closest('tr')).data();

		console.log(rowData)

		$('#userInfoModal input[name="surname"]').val(rowData.surname);
		$('#userInfoModal input[name="name"]').val(rowData.name);
		$('#userInfoModal select[name="status"]').val(rowData.status).change();
		if (rowData.role === "ADMIN") {
			$('#userInfoModal select[name="role"]').val(1).change();
		} else {
			$('#userInfoModal select[name="role"]').val(2).change();
		}
		$('#userInfoModal input[name="update_at"]').val(rowData.update_at);
		$('#userInfoModal input[name="email"]').val(rowData.email);
		$('#userInfoModal input[name="phone"]').val(rowData.phone);
		$('#userInfoModal input[name="address"]').val(rowData.address);
		$('#userInfoModal input[name="create_at"]').val(rowData.create_at);
		$('#userInfoModal select[name="gender"]').val(rowData.gender).change();
		var dateArray = rowData.date_of_birth; 

		var year = dateArray[0];
		var month = (dateArray[1] < 10 ? '0' : '') + dateArray[1];  
		var day = (dateArray[2] < 10 ? '0' : '') + dateArray[2];  

		var formattedDate = year + '-' + month + '-' + day;
		
		$('#userInfoModal input[name="date_of_birth"]').val(formattedDate);
		
		$('.avatarPreview').attr('src', '/admin/assets/img/avatar/' + rowData.avatar); 
		$(".avatarPreview").css({ "display": "block" });

		$('#userInfoModal').modal('show');
	});
};

function ajaxCreateUser() {
	$("#btn-create-user").click(function() {
		var formData = $("#create-user-form").serializeArray();
		var user = {};

		$.each(formData, function(i, field) {
			if (field.name === "role") {
				if (!user.role) {
					user.role = {};
				}
				user.role.id = field.value;
			} else {
				user[field.name] = field.value;
			}
		});

		var uploadData = new FormData();
		uploadData.append('dataJson', JSON.stringify(user));

		var fileInput = $("#avatar")[0].files[0];
		if (fileInput) {
			uploadData.append('file', fileInput);
		}

		$.ajax({
			url: "/api/admin/user/create",
			type: "POST",
			data: uploadData,
			processData: false,
			contentType: false,
			success: function(response) {
				$("#create-user-form")[0].reset();
				$("#avatarPreview").css({ "display": "none" });
				$.toast({
					text: "Thêm người dùng thành công!",
					heading: 'Thành công',
					icon: 'success',
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
					loaderBg: '#9EC600',
				});
				$('#userTable').DataTable().ajax.reload(null, false);
			},
			error: function() {
				$.toast({
					text: "Thêm người dùng thất bại!",
					heading: 'Thất bại',
					icon: 'error',
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
					loaderBg: '#c70000',
				});
			}
		});
	});
};

