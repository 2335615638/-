package com.project.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.project.domain.dto.NoteAddOrUpdateDto;
import com.project.entity.Note;
import com.project.mapper.NoteMapper;
import com.project.service.INoteService;
import com.project.util.UserContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;


/**
* @Author: zhoujinchuan
* @Description:  ()
*/
@Slf4j
@Service
public class NoteServiceImpl extends ServiceImpl<NoteMapper, Note> implements INoteService {


    @Override
    public IPage<Note> pageList(int pageNum, int pageSize) {
        Integer userId = UserContext.getUserId();
        Page<Note> page = new Page<>(pageNum, pageSize);
        IPage<Note> iPage = lambdaQuery().eq(Note::getUserId,userId).orderByDesc(Note::getCreateTime).page(page);
        return iPage;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveOrUpdateNote(NoteAddOrUpdateDto dto) {
        Integer userId = UserContext.getUserId();
        Note entity;
        if(dto.getId() != null) {
            entity = getById(dto.getId());
            Assert.isTrue(entity != null, "信息不存在");
            Assert.isTrue(userId.equals(entity.getUserId()), "只能维护自己的");
        }else{
            entity = new Note();
        }
        BeanUtils.copyProperties(dto, entity);
        entity.setUserId(userId);
        saveOrUpdate(entity);
    }

    @Override
    public void removeNote(Integer id) {
        Integer userId = UserContext.getUserId();
        Note entity = getById(id);
        Assert.isTrue(entity != null, "信息不存在");
        Assert.isTrue(userId.equals(entity.getUserId()), "只能删除自己的");
        removeById(id);
    }

}