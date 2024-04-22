package com.project.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.project.domain.dto.HealthKnowledgeAddOrUpdateDto;
import com.project.domain.vo.HealthKnowledgePageVo;
import com.project.entity.HealthKnowledge;

import java.util.List;

/**
* @Description:  (服务层)
*/
public interface IHealthKnowledgeService extends IService<HealthKnowledge> {

    /**
     * 分页列表
     *
     * @param pageNum
     * @param pageSize
     * @param type
     * @return
     */
    IPage<HealthKnowledgePageVo> pageList(int pageNum, int pageSize, String type);

    /**
    * 添加和修改
    * @param dto
    */
    void saveOrUpdate(HealthKnowledgeAddOrUpdateDto dto);

    /**
    * 删除
    * @param id
    */
    void remove(Integer id);

    /**
     * 根据类型获取知识
     * @param type
     * @return
     */
    List<HealthKnowledgePageVo> getListByType(String type);
}