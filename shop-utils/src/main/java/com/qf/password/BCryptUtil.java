package com.qf.password;

import org.mindrot.jbcrypt.BCrypt;

/**
 * @version 1.0
 * @user 36043
 * @date 2019/7/19 21:36
 */
public class BCryptUtil {
    //明文密码加密
    public static String hashPassword(String password){

        return BCrypt.hashpw(password,BCrypt.gensalt());
    }

    //将明文密码与数据库的密文密码校验
    public static boolean checkPassword(String password, String hashPassword) {

        return BCrypt.checkpw(password,hashPassword);
    }

    public static void main(String[] args) {
        String password = "123456";
        String hashword = hashPassword(password);
        System.out.println(hashword);//$2a$10$8BLz/Np5Lk1rqakXUdJ0meIfYe8qydImOp6GhOtkLZiOxHxsFTMBG

        long begin = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            boolean flag = checkPassword(password,"$2a$10$wck7ien.Hy7NxyqbjHi7ge21LA4EC.dHmRTib0ysqDGX2jaoWQpse");
        }
        long end = System.currentTimeMillis();
        System.out.println("耗时："+(end-begin)/1000);
    }
}
