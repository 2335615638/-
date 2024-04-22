package com.project.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.project.config.Auth;
import com.project.config.Result;
import com.project.domain.dto.IdDto;
import com.project.domain.dto.NoteAddOrUpdateDto;
import com.project.entity.Note;
import com.project.enums.UserRoleEnum;
import com.project.service.INoteService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
* @Author: zhoujinchuan
* @Description: （请添加描述）
* @CreateTime: 2023/12/6 11:17
*/
@RestController
@RequestMapping("/note")
@Api(tags = "用户备忘录")
public class NoteController {

    @Autowired
    private INoteService noteService;

    @GetMapping("notePageList")
    @ApiOperation("用户备忘录分页列表")
    @Auth({UserRoleEnum.USER})
    public Result<IPage<Note>> pageList(@RequestParam(required = false, defaultValue = "0") int pageNum,
                                        @RequestParam(required = false, defaultValue = "10") int pageSize){
        IPage<Note> page = noteService.pageList(pageNum,pageSize);
        return Result.success(page);
    }

    @PostMapping("saveOrUpdateNote")
    @ApiOperation("添加或修改备忘录")
    @Auth({UserRoleEnum.USER})
    public Result saveOrUpdateNote(@RequestBody @Valid NoteAddOrUpdateDto dto){
        noteService.saveOrUpdateNote(dto);
        return Result.success();
    }

    @PostMapping("removeNote")
    @ApiOperation("删除备忘录")
    @Auth({UserRoleEnum.USER})
    public Result removeNote(@RequestBody @Valid IdDto dto){
        noteService.removeNote(dto.getId());
        return Result.success();
    }

}
