package com._chaumedia.GroupLottery.repository;

import com._chaumedia.GroupLottery.model.User;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	User save(User user); 
	
	List<User> findAll();

}
