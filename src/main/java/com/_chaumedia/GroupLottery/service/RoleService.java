package com._chaumedia.GroupLottery.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com._chaumedia.GroupLottery.model.Role;
import com._chaumedia.GroupLottery.repository.RoleRepository;

@Service
public class RoleService {
    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role findRoleByName(String name) {
        return this.roleRepository.findByName(name);
    }
    
    public Optional findRoleById(Long id) {
        return this.roleRepository.findById(id);
    }

    public List<Role> getAllRoles() {
        return this.roleRepository.findAll();
    }

}
