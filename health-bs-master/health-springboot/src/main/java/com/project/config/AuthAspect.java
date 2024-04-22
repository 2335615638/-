package com.project.config;

import com.project.entity.Role;
import com.project.enums.UserRoleEnum;
import com.project.service.IRoleService;
import com.project.util.UserContext;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

/**
 * @Author: zhoujinchuan
 * @Description: （请添加描述）
 * @CreateTime: 2024/2/27 8:40
 */
@Aspect
@Component
public class AuthAspect {


    @Autowired
    private IRoleService roleService;

    @Before("@annotation(auth)")
    public void checkAuthorization(Auth auth) {
        // 从Auth注解中提取值
        UserRoleEnum[] allowedRoles = auth.value();
        // 例如，假设你有一个方法来获取当前用户的角色
        UserRoleEnum userRole = getCurrentUserRole();
        Assert.isTrue(isUserRoleAllowed(userRole,allowedRoles),"没有权限");
    }

    private UserRoleEnum getCurrentUserRole() {
        Integer userId = UserContext.getUserId();
        Role role = roleService.lambdaQuery().eq(Role::getUserId, userId).one();
        Assert.isTrue(role != null,"没有权限");
        return role.getRoleStr();
    }

    private boolean isUserRoleAllowed(UserRoleEnum currentUserRole, UserRoleEnum[] allowedRoles) {
        // 实现检查当前用户角色列表是否包含允许的角色之一的逻辑
        // 例如，检查currentUserRoles是否与allowedRoles有交集
        for (UserRoleEnum allowedRole : allowedRoles) {
            if (allowedRole == currentUserRole) {
                return true;
            }
        }
        return false;
    }

}
