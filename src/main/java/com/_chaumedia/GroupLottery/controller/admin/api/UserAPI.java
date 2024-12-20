package com._chaumedia.GroupLottery.controller.admin.api;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
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
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;

@Controller
@RequestMapping("/api/admin/user")
public class UserAPI {
	private final RoleService roleService;
	private final UploadImgService uploadImgService;
	private final UserService userService;
	private final PasswordEncoder passwordEncoder;

	public UserAPI(RoleService roleService, UploadImgService uploadImgService, UserService userService,
			PasswordEncoder passwordEncoder) {
		this.roleService = roleService;
		this.uploadImgService = uploadImgService;
		this.userService = userService;
		this.passwordEncoder = passwordEncoder;
	}

	@GetMapping("/users")
	@ResponseBody
	public Map<String, Object> getAllUser(
			@RequestParam(defaultValue = "0") int start,
			@RequestParam(defaultValue = "10") int length,
			@RequestParam(value = "order[0][column]", defaultValue = "0") int columnIndex,
			@RequestParam(value = "order[0][dir]", defaultValue = "asc") String sortDirection,
			@RequestParam(defaultValue = "0") int draw,
			@RequestParam(value = "search[value]", required = false) String searchValue,
			@RequestParam Map<String, String> allParams) {

		String sortColumn = switch (columnIndex) {
			case 4 -> "status";
			case 5 -> "role";
			default -> "id";
		};

		Long roleId = 0L;
		String status = allParams.get("status");
		if (allParams.get("role") != null && !allParams.get("role").isEmpty()) {
			roleId = Long.parseLong(allParams.get("role"));
		}

		int pageIndex = start / length;

		Page<UserDTO> userDTOPage = userService.getFilteredUserDTOs(pageIndex, length, sortColumn, sortDirection,
				searchValue, roleId, status);

		Map<String, Object> response = new HashMap<>();
		response.put("draw", draw);
		response.put("recordsTotal", userDTOPage.getTotalElements());
		response.put("recordsFiltered", userDTOPage.getTotalElements());
		response.put("data", userDTOPage.getContent());

		return response;
	}

	@PostMapping("/create")
	@ResponseBody
	public ResponseEntity<?> createUser(@RequestParam String dataJson,
			@RequestParam(required = false) MultipartFile file) {
		ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

		try {
			User user = objectMapper.readValue(dataJson, User.class);

			Set<ConstraintViolation<User>> violations = Validation.buildDefaultValidatorFactory()
					.getValidator().validate(user);

			if (!violations.isEmpty()) {
				Map<String, String> errors = new HashMap<>();
				violations.forEach(
						violation -> errors.put(violation.getPropertyPath().toString(), violation.getMessage()));

				return ResponseEntity.badRequest().body(errors);
			}

			if (file != null) {
				user.setAvatar(this.uploadImgService.handleSaveUploadImg(file, "avatar"));
			}

			user.setPassword(passwordEncoder.encode(user.getPassword()));

			this.userService.handleSaveAUser(user);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing JSON");
		}
		return ResponseEntity.ok().build();
	}

	@PostMapping("/update")
	@ResponseBody
	public ResponseEntity<?> updateUser(@RequestParam String dataJson,
			@RequestParam(required = false) MultipartFile file) {
		ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

		try {
			User user = objectMapper.readValue(dataJson, User.class);

			Set<ConstraintViolation<User>> violations = Validation.buildDefaultValidatorFactory().getValidator()
					.validate(user);
			if (!violations.isEmpty()) {
				Map<String, String> errors = new HashMap<>();
				for (ConstraintViolation<User> violation : violations) {
					errors.put(violation.getPropertyPath().toString(), violation.getMessage());
				}
				return ResponseEntity.badRequest().body(errors);
			}

			Optional<User> currentUserOpt = this.userService.findUserById(user.getId());
			if (currentUserOpt.isPresent()) {
				User currentUser = currentUserOpt.get();
				if (file != null) {
					currentUser.setAvatar(this.uploadImgService.handleSaveUploadImg(file, "avatar"));
				}

				updateUserDetails(currentUser, user);
				this.userService.handleSaveAUser(currentUser);
			}
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing JSON");
		}
		return ResponseEntity.ok().build();
	}

	private void updateUserDetails(User currentUser, User user) {
		currentUser.setSurname(user.getSurname());
		currentUser.setName(user.getName());
		currentUser.setEmail(user.getEmail());
		currentUser.setAddress(user.getAddress());
		currentUser.setPhone(user.getPhone());
		currentUser.setGender(user.getGender());
		currentUser.setStatus(user.getStatus());
		currentUser.setDate_of_birth(user.getDate_of_birth());
		Optional<Role> optionalRole = this.roleService.findRoleById(user.getRole().getId());
		optionalRole.ifPresentOrElse(currentUser::setRole, () -> {
			throw new RuntimeException("Role not found for ID: " + user.getRole().getId());
		});
	}

	@PostMapping("/delete")
	@ResponseBody
	public void deleteUser(@RequestParam Long id) {
		this.userService.deleteAUserById(id);
	}

}
