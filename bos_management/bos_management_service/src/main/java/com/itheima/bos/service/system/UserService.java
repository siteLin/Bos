package com.itheima.bos.service.system;

import com.itheima.bos.domain.system.User;

public interface UserService {
    /**
     * 保存用户
     * @param user
     * @param roleIds
     */
    void save(User user, Long[] roleIds);
}
