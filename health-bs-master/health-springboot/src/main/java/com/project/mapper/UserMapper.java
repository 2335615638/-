package com.project.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.project.domain.vo.UserPageVo;
import com.project.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @Author: zhoujinchuan
* @Description:  (系统-用户表Mapper)
* @Date: 2023-12-01 16:48:35
*/
@Mapper
public interface UserMapper extends BaseMapper<User> {

    /**
     * 用户列表
     * @param page
     * @param search
     * @return
     */
    List<UserPageVo> pageList(@Param("page") Page<User> page,
                              @Param("search") String search,
                              @Param("roleStr") String roleStr);

}