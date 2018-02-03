package com.itheima.bos.dao.system;

import com.itheima.bos.domain.system.Permission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PermissionRepository extends JpaRepository<Permission, Long> {

    /*@Query("select p from Permission p inner join p.role r inner join r.user u where user.id=?")*/
    @Query("select p from Permission p inner join p.roles r inner join r.users u where u.id = ?")
    List<Permission> findByUid(Long id);
}
