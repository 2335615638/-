package com.project.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.project.domain.dto.DoctorSuggestAddDto;
import com.project.domain.vo.DoctorSuggestPageVo;
import com.project.entity.DoctorSuggest;
import com.project.entity.User;
import com.project.mapper.DoctorSuggestMapper;
import com.project.service.IDoctorSuggestService;
import com.project.service.IUserService;
import com.project.util.UserContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;


/**
* @Author: zhoujinchuan
* @Description:  ()
*/
@Slf4j
@Service
public class DoctorSuggestServiceImpl extends ServiceImpl<DoctorSuggestMapper, DoctorSuggest> implements IDoctorSuggestService {


    @Autowired
    private IUserService userService;

    @Override
    public IPage<DoctorSuggestPageVo> userPageList(int pageNum, int pageSize) {
        Integer userId = UserContext.getUserId();
        Page<DoctorSuggest> page = new Page<>(pageNum, pageSize);
        List<DoctorSuggestPageVo> resultList = baseMapper.userPageList(page,userId);
        IPage<DoctorSuggestPageVo> voIPage = new Page<>();
        BeanUtils.copyProperties(page, voIPage,"records");
        voIPage.setRecords(resultList);
        return voIPage;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveSuggest(DoctorSuggestAddDto dto) {
        Integer userId = UserContext.getUserId();
        User user = userService.getById(dto.getUserId());
        Assert.notNull(user,"用户信息有误");
        DoctorSuggest entity = new DoctorSuggest();
        entity.setUserId(dto.getUserId());
        entity.setDiagnosisContent(dto.getDiagnosisContent());
        entity.setDoctorId(userId);
        save(entity);
    }

    @Override
    public void remove(Integer id) {
        Integer userId = UserContext.getUserId();
        DoctorSuggest entity = getById(id);
        Assert.isTrue(entity != null, "诊断记录不存在");
        Assert.isTrue(entity.getUserId().equals(userId),"只能删除自己的诊断记录");
        removeById(id);
    }

}