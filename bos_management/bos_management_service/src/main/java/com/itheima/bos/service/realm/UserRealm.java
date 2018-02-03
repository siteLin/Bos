package com.itheima.bos.service.realm;

import com.itheima.bos.dao.system.PermissionRepository;
import com.itheima.bos.dao.system.RoleRepository;
import com.itheima.bos.dao.system.UserRepository;
import com.itheima.bos.domain.system.Permission;
import com.itheima.bos.domain.system.Role;
import com.itheima.bos.domain.system.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
public class UserRealm extends AuthorizingRealm {
    // 注入UserRepository
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PermissionRepository permissionRepository;
    @Autowired
    private RoleRepository roleRepository;

    // 授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
       /* info.addStringPermission("courier_pageQuery");
        info.addStringPermission("courier:delete");*/
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        // 判断是否为超级管理员
        if ("admin".equals(user.getUsername())) {
            // 是超级管理员
            List<Permission> permissions = permissionRepository.findAll();
            for (Permission permission : permissions) {
               /* System.out.println(permission.getKeyword());*/
                info.addStringPermission(permission.getKeyword());
            }
            List<Role> roles = roleRepository.findAll();
            for (Role role : roles) {
                /*System.out.println(role.getKeyword());*/
                info.addRole(role.getKeyword());
            }
        } else {
            //普通用户
            List<Permission> permissions = permissionRepository.findByUid(user.getId());
            for (Permission permission : permissions) {
                /*System.out.println(permission.getKeyword());*/
                info.addStringPermission(permission.getKeyword());
            }
            List<Role> roles = roleRepository.findByUid(user.getId());
            for (Role role : roles) {
                /*System.out.println(role.getKeyword());*/
                info.addRole(role.getKeyword());
            }
        }
        return info;
    }

    // 认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        // 获取用户名
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        String username = token.getUsername();
        User user = userRepository.findByUsername(username);
        if ( user != null) {
            String password = user.getPassword();
            AuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(user, password, getName());
            return authenticationInfo;
        }
        return null;
    }
}
