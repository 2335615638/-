package com.project.exception;

import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.project.config.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

/**
 * @Author: zhoujinchuan
 * @Description:  (全局异常处理器)
 * @Date: 2022/5/1 10:50
 */
@RestControllerAdvice(annotations = {RestController.class, Controller.class})
@ResponseBody
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 异常处理方法
     *
     * @return
     */
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public Result<String> ExceptionHandler(SQLIntegrityConstraintViolationException e) {
        log.error(e.getMessage());
        if (e.getMessage().contains("Duplicate entry")) {
            String[] s = e.getMessage().split(" ");
            return Result.error(s[2] + "已存在");
        }
        return Result.error("添加失败");
    }

    /**
     * 异常处理方法
     *
     * @return
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public Result<String> ExceptionHandler(IllegalArgumentException e) {
        log.error(e.getMessage());
        return Result.error(e.getMessage());
    }

    /**
     * 异常处理方法
     *
     * @return
     */
    @ExceptionHandler(MybatisPlusException.class)
    public Result<String> ExceptionHandler(MybatisPlusException e) {
        log.error(e.getMessage());
        return Result.error(e.getMessage());
    }

    /**
     * 入参校验错误
     * @param ex
     * @return
     */
    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    @ResponseBody
    public Result<String> ExceptionHandler(final MethodArgumentNotValidException ex) {
        BindingResult bindingResult = ex.getBindingResult();
        List<ObjectError> allErrors = bindingResult.getAllErrors();
        String msg = allErrors.get(0).getDefaultMessage();
        return Result.error(msg);
    }
}
