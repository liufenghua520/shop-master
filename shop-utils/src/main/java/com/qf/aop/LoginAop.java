package com.qf.aop;

import com.qf.entity.User;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.URLEncoder;

/**
 * @version 1.0
 * @user 36043
 * @date 2019/7/21 15:54
 */
@Aspect
@Component
public class LoginAop {

    @Autowired
    private RedisTemplate redisTemplate;

    @Around("@annotation(IsLogin)")
    public Object handleLogin(ProceedingJoinPoint proceedingJoinPoint){

        //1.获得request，cookie
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();

        //通过request获得Cookie，判断cookie中有无登陆凭证
        String loginToken = null;
        Cookie[] cookies = request.getCookies();
        if (cookies!=null){
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("loginToken")){
                    loginToken = cookie.getValue();
                    break;
                }
            }
        }

        //2.根据登陆凭证时，去redis中获取用户信息
        User loginUser = null;
        if (loginToken!=null){
            loginUser = (User) redisTemplate.opsForValue().get(loginToken);
        }

        //3.判断当前用户是否登陆，未登录时，判断IsLogin中是否需要强制登陆（判断mustLogin是否为true）
        if (loginUser==null){
            //获取增强的目标方法签名
            MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
            //通过签名，获取目标方法对象
            Method method = signature.getMethod();
            //通过目标对象获取方法的注解对象
            IsLogin isLogin = method.getAnnotation(IsLogin.class);

            //通过注解判断是否需要强制登陆
            if (isLogin.mustLogin()){
                //获取当前请求路径
                String url = request.getRequestURL().toString();
                try {
                    url = URLEncoder.encode(url,"UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                return "redirect:http://localhost:8084/sso/tologin?returnUrl="+url;
            }
        }

        //已经登陆或者无需强制登陆
        //获取目标方法的参数列表
        Object[] args = proceedingJoinPoint.getArgs();
        for (int i = 0; i < args.length; i++) {
            if (args[i]!=null && args[i].getClass()==User.class){
                args[i] = loginUser;
                break;
            }
        }

        //使用我的实际参数调用目标方法——将User参数放入目标方法中
        Object result = null;
        try {
            result = proceedingJoinPoint.proceed(args);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return result;
    }
}
