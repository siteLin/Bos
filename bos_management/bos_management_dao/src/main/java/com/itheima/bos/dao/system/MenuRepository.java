package com.itheima.bos.dao.system;

import com.itheima.bos.domain.system.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MenuRepository extends JpaRepository<Menu, Long> {
    /**
     * 查询所有父菜单
     */
    List<Menu> findByParentMenuIsNull();

    /**
     * 更具用户id获取，该用户相对于的菜单
     * @param id
     * @return
     */
    @Query("select m from Menu m inner join m.roles r inner join r.users u where u.id = ?")
    List<Menu> findMenuByUid(Long id);

}
