package com._chaumedia.GroupLottery.service;

import com._chaumedia.GroupLottery.model.User;
import com._chaumedia.GroupLottery.model.DTO.UserDTO;
import com._chaumedia.GroupLottery.repository.UserRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import static com._chaumedia.GroupLottery.service.specification.UserSpecs.*;

@Service
public class UserService {
	private final UserRepository userRepository;

	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public Optional<User> findUserById(long id) {
		return this.userRepository.findById(id);
	}

	@Transactional
	public User handleSaveAUser(User user) {
		return this.userRepository.save(user);
	}

	public List<User> handleGetAllUser() {
		return this.userRepository.findAll();
	}

	public Page<UserDTO> getFilteredUserDTOs(int pageIndex, int pageSize, String sortColumn, String sortDirection,
			String searchValue, long roleId, String status) {
		Sort sort = sortDirection.equals("asc")
				? Sort.by(sortColumn).ascending()
				: Sort.by(sortColumn).descending();

		PageRequest pageRequest = PageRequest.of(pageIndex, pageSize, sort);

		Specification<User> spec = Specification.where(null);

		if (searchValue != null && !searchValue.isEmpty()) {
			spec = spec.and(hasName(searchValue));
		}
		if (roleId > 0) {
			spec = spec.and(hasRole(roleId));
		}
		if (status != null && !status.isEmpty()) {
			spec = spec.and(hasStatus(status));
		}

		Page<User> usersPage = userRepository.findAll(spec, pageRequest);

		return usersPage.map(this::convertToUserDTO);
	}

	private UserDTO convertToUserDTO(User user) {
		return new UserDTO(
				user.getId(),
				user.getSurname(),
				user.getName(),
				user.getPhone(),
				user.getEmail(),
				user.getCreate_at(),
				user.getUpdate_at(),
				user.getDate_of_birth(),
				user.getGender().toString(),
				user.getStatus().toString(),
				user.getAvatar(),
				user.getLast_login(),
				user.getAddress(),
				user.getRole().getId());
	}

	@Transactional
	public void deleteAUserById(Long id) {
		this.userRepository.deleteById(id);

	}
}
