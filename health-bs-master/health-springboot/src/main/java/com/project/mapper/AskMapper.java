package com.project.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.project.domain.dto.AskPageSearchDto;
import com.project.domain.vo.AskPageVo;
import com.project.entity.Ask;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @Author: zhoujinchuan
* @Description:  (Mapper)
*/
@Mapper
public interface AskMapper extends BaseMapper<Ask> {

    /**
     * 用户咨询列表
     * @param page
     * @param searchDto
     * @param userId
     * @return
     */
    List<AskPageVo> userPageList(@Param("page") Page<Ask> page,
                                 @Param("dto") AskPageSearchDto searchDto,
                                 @Param("userId") Integer userId);

    /**
     * 医生咨询列表
     * @param page
     * @param userId
     * @param searchDto
     * @return
     */
    List<AskPageVo> doctorPageList(@Param("page")Page<Ask> page,
                                   @Param("userId")Integer userId,
                                 @Param("dto") AskPageSearchDto searchDto);
}