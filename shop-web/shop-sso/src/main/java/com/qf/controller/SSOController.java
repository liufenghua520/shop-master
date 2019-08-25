package com.qf.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.qf.entity.Email;
import com.qf.entity.User;
import com.qf.service.IShopCartService;
import com.qf.service.IUserService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @version 1.0
 * @user 36043
 * @date 2019/7/18 20:01
 */
@Controller
@RequestMapping("/sso")
public class SSOController {

    @Reference
    private IUserService userService;

    @Reference
    private IShopCartService cartService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 去登陆页面
     * @return
     */
    @RequestMapping("/tologin")
    public String tologin(){
        return "login";
    }

    /**
     * 去注册页面
     * @return
     */
    @RequestMapping("/toregister")
    public String toregister(){
        return "register";
    }

    /**
     * 发送验证码至邮箱
     * @param email
     * @return
     */
    @RequestMapping("/sendCode")
    @ResponseBody
    public String sendCode(String email){
        String content = "注册的验证码为：%d,如果不是本人操作，请忽略！";
        int code = (int) (Math.random()*9000)+1000;
        content = String.format(content,code);

        Email emailObj = new Email(email,"淘奋网验证码",content);

        redisTemplate.opsForValue().set(email+"_code",code);
        rabbitTemplate.convertAndSend("email_exchange","",emailObj);

        return "succ";//发送验证码成功
    }

    /**
     * 进行用户注册
     * @param user
     * @param code
     * @return
     */
    @RequestMapping("/register")
    public String register(User user,int code){
        //判断验证码是否正确
        Integer sendCode = (Integer) redisTemplate.opsForValue().get(user.getEmail()+"_code");

        if (sendCode==null || sendCode!=code){
            return "redirect:/sso/toregister?error=-1"; //-1:验证码错误
        }

        //进行注册
        int result = userService.register(user);

        if (result>0){
            return "redirect:/sso/tologin"; //跳转至登陆页面
        }
        return "redirect:/sso/toregister?error="+result;    //-2:用户名已存在  -3:邮箱已存在
    }

    //-------------------------------------------------------------------------------------------------

    /**
     * 跳转至找回密码页面
     * @return
     */
    @RequestMapping("/toFindPassword")
    public String toFindPassword(){

        return "findpassword";
    }

    /**
     * 给用户发送修改密码的邮件链接
     * @param username
     * @return
     */
    @RequestMapping("/sendPassMail")
    @ResponseBody
    public Map<String,Object> sendPassMail(String username){
        Map<String,Object> map = new HashMap<>();

        User user = userService.queryByUserName(username);
        //判断用户是否存在？
        if (user==null){
            map.put("code","1000");   //用户不存在
            return map;
        }

        //如果用户存在则给用户邮箱发送邮件
        //生成一个修改密码的凭证
        String token = UUID.randomUUID().toString();

        //将凭证放入redis中并设置有效时间
        redisTemplate.opsForValue().set(username+"_token",token);
        redisTemplate.expire(username+"_token",5, TimeUnit.MINUTES);

        //生成邮件的链接Url
        String url = "http://localhost:8084/sso/toChangePassword?username=" + username + "&token=" + token;
        //封装邮件
        Email email = new Email(user.getEmail(),"韬奋网找回密码邮件",
                "找回密码请点击<a href='"+url+"'>这里</a>");
        //发送邮件
        rabbitTemplate.convertAndSend("email_exchange","",email);

        //设置用户注册邮件的通知
        String emailStr = user.getEmail();
        int index = emailStr.indexOf("@");
        String emailStr2 = emailStr.replace(emailStr.substring(3,index),"******");

        //设置去登陆邮箱的地址
        String goEmail = "mail."+emailStr.substring(index+1);

        map.put("code","0000");
        map.put("emailStr",emailStr2);
        map.put("goEmail",goEmail);

        return map;
    }

    /**
     * 跳转至修改密码的页面
     * @return
     */
    @RequestMapping("/toChangePassword")
    public String toChangePassword(){
        return "changePassword";
    }

    @RequestMapping("/changePassword")
    public String changePassword(String password,String username,String token){
        //获取服务器中的修改密码的凭证
        String utoken = (String) redisTemplate.opsForValue().get(username+"_token");

        //校验凭证
        if (utoken!=null && utoken.equals(token)){
            //认证通过，修改密码
            userService.updatePassword(username,password);
            return "redirect:/sso/tologin";
        }

        return "fail";
    }

    //-------------------------------------------------------------------------------------------------

    /**
     * 用户进行登陆
     * @return
     */
    @RequestMapping("/login")
    public String login(User user,String returnUrl, HttpServletResponse response,
                        @CookieValue(value = "cartToken",required = false)String cartToken){
        user = userService.login(user);
        System.out.println(user);
        if (user==null){
            return "redirect:/sso/tologin?error=1";
        }

        if (returnUrl==null || returnUrl.trim().equals("")){
            returnUrl = "http://localhost:8081";
        }
        //登陆校验成功后
        //生成一个登陆凭证()令牌
        String token = UUID.randomUUID().toString();

        redisTemplate.opsForValue().set(token,user);
        redisTemplate.expire(token,7,TimeUnit.DAYS);

        Cookie cookie = new Cookie("loginToken",token);
        cookie.setMaxAge(60*60*24*7);
        cookie.setPath("/");
        response.addCookie(cookie);

        //合并临时购物车
        int result = cartService.mergeCarts(cartToken,user);
        if (result==1){
            Cookie cookie1 = new Cookie("cartToken","");
            cookie1.setMaxAge(0);
            cookie.setPath("/");
            response.addCookie(cookie1);
        }

        return "redirect:" + returnUrl;
    }

    /**
     * 校验用户是否登陆: 采用jsonp 方式解决服务器跨域问题
     * @return
     */
    @RequestMapping("/checkLogined")
    @ResponseBody
    public String checkLogined(String callback,
                               @CookieValue(name = "loginToken",required = false)String loginToken){
        User user =null;
        if (loginToken!=null){
            user = (User) redisTemplate.opsForValue().get(loginToken);
        }
        System.out.println(user);
        String userJson = user!=null ? JSON.toJSONString(user) : null ;

        return callback !=null ? callback+"("+userJson+")" : userJson ;
    }

    /**
     * 注销
     */
    @RequestMapping("/logout")
    public String logout(@CookieValue(name = "loginToken",required = false)String loginToken,
                         HttpServletResponse response){
        if (loginToken!=null){
            //删除redis中的登陆凭证
            redisTemplate.delete(loginToken);
        }
        //删除cookie
        Cookie cookie = new Cookie("loginToken","");
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);

        return "redirect:/sso/tologin";
    }


}
