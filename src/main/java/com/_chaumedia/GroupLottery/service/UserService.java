package com._chaumedia.GroupLottery.service;

import com._chaumedia.GroupLottery.model.User;
import com._chaumedia.GroupLottery.model.DTO.UserDTO;
import com._chaumedia.GroupLottery.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class UserService {
	private final UserRepository userRepository;

	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public User handleSaveAUser(User user) {
		return this.userRepository.save(user);
	}

	public List<User> handleGetAllUser() {
		return this.userRepository.findAll();
	}

	public List<UserDTO> handleGetAllUserDTO() {
		List<User> users = this.userRepository.findAll();
		List<UserDTO> userDTOs = new ArrayList();

		for (User user : users) {
			UserDTO userDTO = new UserDTO(user.getId(), user.getSurname(), user.getName(), user.getPhone(),
					user.getEmail(), user.getCreate_at(), user.getUpdate_at(), user.getDate_of_birth(),
					user.getGender().toString(), user.getStatus().toString(), user.getAvatar(), user.getLast_login(),
					user.getAddress(), user.getRole().getName());
			userDTOs.add(userDTO);
		}

		return userDTOs;
	}
}
