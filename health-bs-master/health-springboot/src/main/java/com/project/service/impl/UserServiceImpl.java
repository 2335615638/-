package com.project.service.impl;

import cn.hutool.core.lang.Assert;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.project.domain.dto.*;
import com.project.domain.vo.UserLoginVo;
import com.project.domain.vo.UserPageVo;
import com.project.domain.vo.UserVo;
import com.project.entity.Role;
import com.project.entity.User;
import com.project.enums.UserRoleEnum;
import com.project.mapper.UserMapper;
import com.project.service.IRoleService;
import com.project.service.IUserService;
import com.project.util.JwtUtil;
import com.project.util.UserContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @Author: zhoujinchuan
 * @Description: (系统 - 用户表)
 * @Date: 2023-12-01 16:48:35
 */
@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    private IRoleService roleService;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Value("${project.file.baseUrl}")
    private String baseUrl;

    @Override
    public UserLoginVo login(UserLoginDto dto) {
        User user = lambdaQuery().eq(User::getUserName, dto.getUserName()).one();
        Assert.notNull(user, "用户不存在");
        Assert.isTrue("1".equals(user.getStatus()), "用户未激活，请联系管理员");
        Assert.isTrue(user.getPassword().equals(SecureUtil.md5().digestHex(dto.getPassword())), "密码错误");
        String token = JwtUtil.createJWT(JSONUtil.toJsonStr(user));
        UserLoginVo vo = new UserLoginVo();
        UserVo userVo = new UserVo();
        BeanUtils.copyProperties(user, userVo);
        vo.setUser(userVo);
        Role role = roleService.lambdaQuery()
                .eq(Role::getUserId, user.getId())
                .one();
        if (role != null) {
            userVo.setRole(role.getRoleStr());
        }
        redisTemplate.opsForValue().set("user:" + user.getId(), JSONUtil.toJsonStr(userVo), JwtUtil.JWT_TTL, TimeUnit.SECONDS);
        vo.setToken(token);
        return vo;
    }

    @Override
    public void logout() {
        Integer userId = UserContext.getUserId();
        redisTemplate.delete("user:" + userId);
    }

    @Override
    public void updateInfo(UserUpdateDto dto) {
        Integer userId = UserContext.getUserId();
        User user = new User();
        BeanUtils.copyProperties(dto, user);
        user.setId(userId);
        updateById(user);
    }

    @Override
    public void updatePassword(PasswordDto dto) {
        Integer userId = UserContext.getUserId();
        User user = getById(userId);
        Assert.isTrue(user.getPassword().equals(SecureUtil.md5().digestHex(dto.getOldPassword())), "旧密码错误");
        user.setPassword(SecureUtil.md5().digestHex(dto.getNewPassword()));
        updateById(user);
    }

    @Override
    public void userRegister(UserRegisterDto dto) {
        User user = lambdaQuery().eq(User::getUserName, dto.getUserName()).one();
        Assert.isNull(user, "用户名重复");
        user = new User();
        BeanUtils.copyProperties(dto, user);
        if (StringUtils.isBlank(user.getAvatarUrl())) {
            user.setAvatarUrl(baseUrl + "/common/download/image?type=user-avatar&name=cdb7f043-7811-4678-925c-b7471c5eda14.png");
        }
        user.setPassword(SecureUtil.md5().digestHex(dto.getPassword()));
        user.setStatus("1");
        user.setNickName(dto.getUserName());
        user.setSex("1");
        save(user);
        Role role = new Role();
        role.setUserId(user.getId());
        role.setRoleStr(UserRoleEnum.USER);
        roleService.save(role);
    }

    @Override
    public IPage<UserPageVo> pageList(Integer pageNum, Integer pageSize, String search) {
        Page<User> page = new Page<>(pageNum, pageSize);
        List<UserPageVo> list = baseMapper.pageList(page, search,null);
        IPage<UserPageVo> userPageVoIPage = new Page<>();
        BeanUtils.copyProperties(page, userPageVoIPage, "records");
        userPageVoIPage.setRecords(list);
        return userPageVoIPage;
    }

    @Override
    public IPage<UserPageVo> userPageList(Integer pageNum, Integer pageSize, String search) {
        Page<User> page = new Page<>(pageNum, pageSize);
        List<UserPageVo> list = baseMapper.pageList(page, search,"USER");
        IPage<UserPageVo> userPageVoIPage = new Page<>();
        BeanUtils.copyProperties(page, userPageVoIPage, "records");
        userPageVoIPage.setRecords(list);
        return userPageVoIPage;
    }

    @Override
    public IPage<UserPageVo> doctorPageList(Integer pageNum, Integer pageSize, String search) {
        Page<User> page = new Page<>(pageNum, pageSize);
        List<UserPageVo> list = baseMapper.pageList(page, search,"DOCTOR");
        IPage<UserPageVo> userPageVoIPage = new Page<>();
        BeanUtils.copyProperties(page, userPageVoIPage, "records");
        userPageVoIPage.setRecords(list);
        return userPageVoIPage;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeUser(Integer id) {
        User user = getById(id);
        Assert.notNull(user, "用户不存在");
        Assert.isTrue(user.getId() != 1, "超级管理员不能删除");
        removeById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void backSaveOrUpdate(BackUserAddOrUpdateDto dto) {
        Assert.isTrue(dto.getId() == null || dto.getRole() != null, "用户角色不能为空");
        User user = new User();
        BeanUtils.copyProperties(dto, user);
        if (dto.getId() == null) {
            user.setPassword(SecureUtil.md5().digestHex("123456"));
            user.setAvatarUrl(baseUrl + "/common/download/image?type=user-avatar&name=cdb7f043-7811-4678-925c-b7471c5eda14.png");
            user.setStatus("0");
        }
        saveOrUpdate(user);
        if (dto.getId() == null) {
            UserRoleEnum roleEnum = dto.getRole();
            Role role = new Role();
            role.setUserId(user.getId());
            role.setRoleStr(roleEnum);
            roleService.save(role);
        }
    }

    @Override
    public void changeStatus(Integer id) {
        User user = getById(id);
        Assert.notNull(user, "用户信息不存在");
        user.setStatus("1".equals(user.getStatus()) ? "0" : "1");
        updateById(user);
    }


}