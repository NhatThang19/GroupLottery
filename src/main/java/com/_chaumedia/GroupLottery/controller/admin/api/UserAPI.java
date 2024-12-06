package com._chaumedia.GroupLottery.controller.admin.api;

import java.util.List;

import javax.management.relation.RoleNotFoundException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com._chaumedia.GroupLottery.model.Role;
import com._chaumedia.GroupLottery.model.User;
import com._chaumedia.GroupLottery.model.DTO.UserDTO;
import com._chaumedia.GroupLottery.service.RoleService;
import com._chaumedia.GroupLottery.service.UploadImgService;
import com._chaumedia.GroupLottery.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/api/admin/user")
public class UserAPI {
	private final RoleService roleService;
	private final UploadImgService uploadImgService;
	private final UserService userService;

	public UserAPI(RoleService roleService, UploadImgService uploadImgService, UserService userService) {
		this.roleService = roleService;
		this.uploadImgService = uploadImgService;
		this.userService = userService;
	}

	@GetMapping("/users")
	@ResponseBody
	public List<UserDTO> getAllUser() {
		return this.userService.handleGetAllUserDTO();
	}

	@PostMapping("/create")
	@ResponseBody
	public void createUser(@RequestParam String dataJson, @RequestParam(required = false) MultipartFile file) {
		System.out.println(dataJson);

		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());

		User user = new User();
		try {
			user = objectMapper.readValue(dataJson, User.class);

		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (file != null) {
			user.setAvatar(this.uploadImgService.handleSaveUploadImg(file, "avatar"));
		}

		System.out.println(user);
		this.userService.handleSaveAUser(user);
	}

}
