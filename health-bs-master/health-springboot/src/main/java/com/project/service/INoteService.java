package com.project.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.project.domain.dto.NoteAddOrUpdateDto;
import com.project.entity.Note;

/**
* @Author: zhoujinchuan
* @Description:  (服务层)
*/
public interface INoteService extends IService<Note> {

    /**
    * 分页列表
    * @param pageNum
    * @param pageSize
    * @return
    */
    IPage<Note> pageList(int pageNum, int pageSize);

    /**
    * 添加和修改
    * @param dto
    */
    void saveOrUpdateNote(NoteAddOrUpdateDto dto);

    /**
    * 删除
    * @param id
    */
    void removeNote(Integer id);

}