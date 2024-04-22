package com.project.filter;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.project.config.Auth;
import com.project.domain.vo.UserVo;
import com.project.entity.User;
import com.project.service.IUserService;
import com.project.util.JwtUtil;
import com.project.util.UserContext;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.TimeUnit;

/**
 * @Author: zhoujinchuan
 * @Description:  (用一句话描述一下)
 * @Date: 2022/5/8 11:15
 */
@Slf4j
@Component
public class TokenInterceptor implements HandlerInterceptor {


    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private IUserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("token");
        System.out.println(request.getServletPath());
        // 获取处理方法上的 Auth 注解
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Auth auth = handlerMethod.getMethodAnnotation(Auth.class);
            if (auth != null) {
                //获取token
                Assert.isTrue(StrUtil.isNotBlank(token),"请登录");
            }
        }
        if (StringUtils.isNotBlank(token)){
            //解析token
            String userInfo;
            try {
                Claims claims = JwtUtil.parseJWT(token);
                userInfo = claims.getSubject();

            } catch (Exception e) {
                e.printStackTrace();
                //非法token
                throw new RuntimeException("token有误");
            }
            String jsonUser = redisTemplate.opsForValue().get("user:" + JSONUtil.toBean(userInfo, User.class).getId());
            Assert.isTrue(StrUtil.isNotBlank(jsonUser),"token已过期，请重新登录");
            UserVo userVo = JSONUtil.toBean(jsonUser, UserVo.class);
            redisTemplate.opsForValue().set("user:" + userVo.getId(),jsonUser, JwtUtil.JWT_TTL, TimeUnit.SECONDS);
            User user = userService.getById(userVo.getId());
            UserContext.setCurrentUser(user);
        }
        return true;
    }
}
