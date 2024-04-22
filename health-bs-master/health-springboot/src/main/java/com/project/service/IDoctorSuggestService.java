package com.project.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.project.domain.dto.DoctorSuggestAddDto;
import com.project.domain.vo.DoctorSuggestPageVo;
import com.project.entity.DoctorSuggest;

/**
* @Description:  (服务层)
*/
public interface IDoctorSuggestService extends IService<DoctorSuggest> {

    /**
    * 分页列表
    * @param pageNum
    * @param pageSize
    * @return
    */
    IPage<DoctorSuggestPageVo> userPageList(int pageNum, int pageSize);

    /**
    * 医生保存建议
    * @param dto
    */
    void saveSuggest(DoctorSuggestAddDto dto);

    /**
    * 删除
    * @param id
    */
    void remove(Integer id);

}