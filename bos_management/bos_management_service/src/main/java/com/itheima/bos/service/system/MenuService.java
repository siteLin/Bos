package com.itheima.bos.service.system;

import com.itheima.bos.domain.system.Menu;
import com.itheima.bos.domain.system.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MenuService {

    /**
     *获取所有的副菜单
     * @return
     */
    List<Menu> findLevelOne();

    /**
     * 保存menu菜单
     * @param menu
     */
    void save(Menu menu);


    /**
     * 分页查找所有菜单
     * @param pageable
     * @return
     */
    Page<Menu> findAll(Pageable pageable);

    /**
     * 根据用户查找出用户相对于的菜单
     * @param user
     * @return
     */
    List<Menu> findByUid(User user);
}
