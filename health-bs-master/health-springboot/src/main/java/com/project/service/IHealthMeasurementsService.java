package com.project.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.project.domain.dto.HealthMeasurementsAddOrUpdateDto;
import com.project.domain.dto.HealthMeasurementsPageSearchDto;
import com.project.domain.vo.HealthMeasurementsPageVo;
import com.project.domain.vo.StatisticVo;
import com.project.entity.HealthMeasurements;

/**

* @Description:  (服务层)
*/
public interface IHealthMeasurementsService extends IService<HealthMeasurements> {

    /**
    * 分页列表
    * @param pageNum
    * @param pageSize
    * @param searchDto
    * @return
    */
    IPage<HealthMeasurementsPageVo> userPageList(int pageNum, int pageSize, HealthMeasurementsPageSearchDto searchDto);

    /**
    * 添加和修改
    * @param dto
    */
    void userSaveOrUpdate(HealthMeasurementsAddOrUpdateDto dto);

    /**
    * 删除
    * @param id
    */
    void userRemove(Integer id);

    /**
     * 用户统计
     * @param userId
     * @return
     */
    StatisticVo statistics(Integer userId);

}