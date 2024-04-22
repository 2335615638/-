package com.project.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.project.entity.PhysicalFunctions;

/**
* @Description:  (服务层)
*/
public interface IPhysicalFunctionsService extends IService<PhysicalFunctions> {

    /**
     * 维护身体机能
     * @param entity
     */
    void maintenance(PhysicalFunctions entity);

}