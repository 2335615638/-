package com.project.util;

import cn.hutool.core.lang.Assert;
import com.project.entity.User;

/**
 * @Author: zhoujinchuan
 * @Description: （请添加描述）
 * @CreateTime: 2023/12/4 10:09
 */
public class UserContext {

    private static final ThreadLocal<User> userThreadLocal = new ThreadLocal<>();

    public static User getCurrentUser() {
        User user = userThreadLocal.get();
        Assert.notNull(user, "用户未登录");
        return user;
    }

    public static void setCurrentUser(User user) {
        userThreadLocal.set(user);
    }

    public static void clearCurrentUser() {
        userThreadLocal.remove();
    }

    public static Integer getUserId(){
        User user = getCurrentUser();
        return user.getId();
    }

    public static Integer getNotSafeUserId() {
        User user = userThreadLocal.get();
        if (user != null){
            return user.getId();
        }
        return null;
    }


}
