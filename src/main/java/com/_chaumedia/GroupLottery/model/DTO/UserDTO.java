package com._chaumedia.GroupLottery.model.DTO;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com._chaumedia.GroupLottery.model.Role;
import com._chaumedia.GroupLottery.model.User.Gender;
import com._chaumedia.GroupLottery.model.User.Status;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDTO {
	private long id;
	private String surname;
	private String name;
	private String phone;
	private String email;
	private LocalDateTime create_at;
	private LocalDateTime update_at;
	private LocalDate date_of_birth;
	private String gender;
	private String status;
	private String avatar;
	private LocalDateTime last_login;
	private String address;
	private String role;
}
