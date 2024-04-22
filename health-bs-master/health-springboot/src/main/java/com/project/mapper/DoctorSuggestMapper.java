package com.project.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.project.domain.vo.DoctorSuggestPageVo;
import com.project.entity.DoctorSuggest;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @Author: zhoujinchuan
* @Description:  (医生诊断建议Mapper)
* @Date: 2024-03-06 15:42:55
*/
@Mapper
public interface DoctorSuggestMapper extends BaseMapper<DoctorSuggest> {

    /**
     * 用户诊断记录列表
     * @param page
     * @param userId
     * @return
     */
    List<DoctorSuggestPageVo> userPageList(@Param("page") Page<DoctorSuggest> page,
                                           @Param("userId") Integer userId);
}