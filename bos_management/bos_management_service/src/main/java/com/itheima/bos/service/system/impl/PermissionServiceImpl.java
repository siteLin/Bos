package com.itheima.bos.service.system.impl;

import com.itheima.bos.dao.system.PermissionRepository;
import com.itheima.bos.domain.system.Permission;
import com.itheima.bos.service.system.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private PermissionRepository permissionRepository;

    @Override
    public void save(Permission permission) {

        permissionRepository.save(permission);
    }

    // 分页查询
    @Override
    public Page<Permission> findAll(Pageable pageable) {

        return permissionRepository.findAll(pageable);
    }
}
