package com.project.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.project.domain.dto.AskPageSearchDto;
import com.project.domain.dto.ConsultingServiceDto;
import com.project.domain.dto.DoctorReplyDto;
import com.project.domain.vo.AskPageVo;
import com.project.entity.Ask;

/**
* @Description:  (服务层)
*/
public interface IAskService extends IService<Ask> {

    /**
    * 用户咨询分页列表
    * @param pageNum
    * @param pageSize
    * @param searchDto
    * @return
    */
    IPage<AskPageVo> userPageList(int pageNum, int pageSize, AskPageSearchDto searchDto);

    /**
     * 医生咨询分页列表
     *
     * @param pageNum
     * @param pageSize
     * @param searchDto
     * @return
     */
    IPage<AskPageVo> doctorPageList(int pageNum, int pageSize, AskPageSearchDto searchDto);

    /**
     * 用户咨询
     * @param dto
     */
    void consultingService(ConsultingServiceDto dto);

    /**
    * 删除
    * @param id
    */
    void remove(Integer id);

    /**
     * 医生回复
     * @param dto
     */
    void doctorReply(DoctorReplyDto dto);
}