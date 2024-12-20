package com._chaumedia.GroupLottery.repository;

import com._chaumedia.GroupLottery.model.User;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
	User save(User user);

	List<User> findAll();

	Page<User> findAll(Specification<User> spec, Pageable pageable);

	Optional<User> findById(long id);

	void deleteById(long id);

}
