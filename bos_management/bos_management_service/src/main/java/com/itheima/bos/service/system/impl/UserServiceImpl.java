package com.itheima.bos.service.system.impl;

import com.itheima.bos.dao.system.UserRepository;
import com.itheima.bos.domain.system.Role;
import com.itheima.bos.domain.system.User;
import com.itheima.bos.service.system.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Override
    public void save(User user, Long[] roleIds) {
        if (roleIds != null && roleIds.length > 0) {
            for (Long roleId :roleIds) {
                Role role = new Role();
                role.setId(roleId);
                user.getRoles().add(role);
            }
        }
        userRepository.save(user);
    }
}
