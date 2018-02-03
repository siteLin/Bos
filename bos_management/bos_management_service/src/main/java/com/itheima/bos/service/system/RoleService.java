package com.itheima.bos.service.system;

import com.itheima.bos.domain.system.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface RoleService {
    /**
     * 分页查询
     * @param pageable
     * @return
     */
    Page<Role> findAll(Pageable pageable);

    /**
     * 保存
     * @param role
     * * @param menuIds
     * * @param permissionIds
     */
 void save(Role role, String menuIds, Long[] permissionIds);
}
