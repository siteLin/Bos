package com.itheima.bos.service.system;

import com.itheima.bos.domain.system.Permission;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface PermissionService {
    /**
     * 保存权限
     * @param permission
     */
    void save(Permission permission);

    Page<Permission> findAll(Pageable pageable);
}
