package com.project.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.project.util.UserContext;
import org.apache.ibatis.reflection.MetaObject;

import java.time.LocalDateTime;

/**
 * @Author: zhoujinchuan
 * @Description: （请添加描述）
 * @CreateTime: 2024/2/27 10:45
 */
public class MyMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        Integer userId = UserContext.getNotSafeUserId();
        LocalDateTime now = LocalDateTime.now();
        this.strictInsertFill(metaObject, "createTime", LocalDateTime.class, now);
        this.strictInsertFill(metaObject, "createBy", Integer.class, userId == null ? 1: userId);
        this.strictInsertFill(metaObject, "updateTime", LocalDateTime.class, now);
        this.strictInsertFill(metaObject, "updateBy", Integer.class, userId == null ? 1: userId);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        Integer userId = UserContext.getNotSafeUserId();
        LocalDateTime now = LocalDateTime.now();
        this.strictInsertFill(metaObject, "updateTime", LocalDateTime.class, now);
        this.strictInsertFill(metaObject, "updateBy", Integer.class, userId == null ? 1: userId);
    }
}
