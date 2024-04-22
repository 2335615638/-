package com.project.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.project.entity.PhysicalFunctions;
import com.project.mapper.PhysicalFunctionsMapper;
import com.project.service.IPhysicalFunctionsService;
import com.project.util.UserContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


/**
* @Author: zhoujinchuan
* @Description:  ()
*/
@Slf4j
@Service
public class PhysicalFunctionsServiceImpl extends ServiceImpl<PhysicalFunctionsMapper, PhysicalFunctions> implements IPhysicalFunctionsService {


    @Override
    public void maintenance(PhysicalFunctions entity) {
        Integer userId = UserContext.getUserId();
        PhysicalFunctions physicalFunctions = lambdaQuery().eq(PhysicalFunctions::getUserId, userId).one();
        if (physicalFunctions == null){
            entity.setId(null);
            entity.setUserId(userId);
            save(entity);
        }else {
            entity.setId(physicalFunctions.getId());
            entity.setUserId(userId);
            updateById(entity);
        }
    }
}