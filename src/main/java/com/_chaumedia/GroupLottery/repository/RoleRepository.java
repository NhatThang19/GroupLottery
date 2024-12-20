package com._chaumedia.GroupLottery.repository;

import com._chaumedia.GroupLottery.model.Role;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);

    List<Role> findAll();

    Optional findById(Long id);
}
