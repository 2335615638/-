package com.project.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.project.domain.dto.AskPageSearchDto;
import com.project.domain.dto.ConsultingServiceDto;
import com.project.domain.dto.DoctorReplyDto;
import com.project.domain.vo.AskPageVo;
import com.project.entity.Ask;
import com.project.entity.Role;
import com.project.enums.UserRoleEnum;
import com.project.mapper.AskMapper;
import com.project.service.IAskService;
import com.project.service.IRoleService;
import com.project.util.UserContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.List;


/**
* @Author: zhoujinchuan
* @Description:  ()
*/
@Slf4j
@Service
public class AskServiceImpl extends ServiceImpl<AskMapper, Ask> implements IAskService {

    @Autowired
    private IRoleService roleService;

    @Override
    public IPage<AskPageVo> userPageList(int pageNum, int pageSize, AskPageSearchDto searchDto) {
        Integer userId = UserContext.getUserId();
        Page<Ask> page = new Page<>(pageNum, pageSize);
        List<AskPageVo> resultList = baseMapper.userPageList(page,searchDto,userId);
        IPage<AskPageVo> voIPage = new Page<>();
        BeanUtils.copyProperties(page, voIPage,"records");
        voIPage.setRecords(resultList);
        return voIPage;
    }

    @Override
    public IPage<AskPageVo> doctorPageList(int pageNum, int pageSize, AskPageSearchDto searchDto) {
        Integer userId = UserContext.getUserId();
        Page<Ask> page = new Page<>(pageNum, pageSize);
        List<AskPageVo> resultList = baseMapper.doctorPageList(page,userId,searchDto);
        IPage<AskPageVo> voIPage = new Page<>();
        BeanUtils.copyProperties(page, voIPage,"records");
        voIPage.setRecords(resultList);
        return voIPage;
    }

    @Override
    public void consultingService(ConsultingServiceDto dto) {
        Integer userId = UserContext.getUserId();
        Role role = roleService.lambdaQuery().eq(Role::getUserId, dto.getDoctorId()).eq(Role::getRoleStr, UserRoleEnum.DOCTOR).one();
        Assert.notNull(role,"医生信息不存在");
        Ask ask = new Ask();
        ask.setConsultationContent(dto.getConsultationContent());
        ask.setDoctorId(dto.getDoctorId());
        ask.setUserId(userId);
        save(ask);
    }

    @Override
    public void remove(Integer id) {
        Integer userId = UserContext.getUserId();
        Ask entity = getById(id);
        Assert.isTrue(entity != null, "咨询信息不存在");
        Assert.isTrue(userId.equals(entity.getUserId()), "只能删除自己的");
        removeById(id);
    }

    @Override
    public void doctorReply(DoctorReplyDto dto) {
        Ask ask = getById(dto.getId());
        Assert.notNull(ask,"咨询信息不存在");
        Assert.isNull(ask.getReplayTime(),"已经回复过了");
        ask.setReplyContent(dto.getReplyContent());
        ask.setReplayTime(LocalDateTime.now());
        updateById(ask);
    }

}