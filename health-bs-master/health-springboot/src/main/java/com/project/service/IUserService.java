package com.project.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.project.domain.dto.*;
import com.project.domain.vo.UserLoginVo;
import com.project.domain.vo.UserPageVo;
import com.project.entity.User;

/**
* @Description:  (系统-用户表服务层)
* @Date: 2023-12-01 16:48:35
*/
public interface IUserService extends IService<User> {

    /**
     * 用户登录
     * @param dto
     * @return
     */
    UserLoginVo login(UserLoginDto dto);

    /**
     * 注销
     */
    void logout();

    /**
     * 修改用户信息
     * @param dto
     */
    void updateInfo(UserUpdateDto dto);

    /**
     * 修改密码
     * @param dto
     */
    void updatePassword(PasswordDto dto);

    /**
     * 用户注册
     * @param dto
     */
    void userRegister(UserRegisterDto dto);

    /**
     * 用户列表
     * @param pageNum
     * @param pageSize
     * @param search
     * @return
     */
    IPage<UserPageVo> pageList(Integer pageNum, Integer pageSize, String search);

    /**
     * 医生用户列表
     * @param pageNum
     * @param pageSize
     * @param search
     * @return
     */
    IPage<UserPageVo> userPageList(Integer pageNum, Integer pageSize, String search);

    /**
     * 用户医生列表
     * @param pageNum
     * @param pageSize
     * @param search
     * @return
     */
    IPage<UserPageVo> doctorPageList(Integer pageNum, Integer pageSize, String search);

    /**
     * 删除用户
     * @param id
     */
    void removeUser(Integer id);

    /**
     * 后台添加修改用户
     * @param dto
     */
    void backSaveOrUpdate(BackUserAddOrUpdateDto dto);

    /**
     * 修改用户状态
     * @param id
     */
    void changeStatus(Integer id);

}