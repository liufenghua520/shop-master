package com.qf.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @version 1.0
 * @user 36043
 * @date 2019/7/23 23:04
 */
@Component
public class OrderUtil {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 生成订单id（时间+用户id后四位+每日流水号）
     * @param uid
     * @return
     */
    public String createOrderId(Integer uid){

        StringBuffer stringBuffer = new StringBuffer("");

        //拼接当前时间（年月日）
        SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
        stringBuffer.append(sdf.format(new Date()));

        //获取用户id后四位,拼接
        stringBuffer.append(getUid(uid));

        //获取流水号并拼接
        String orderNumber = stringRedisTemplate.opsForValue().get("orderNumber");
        if (orderNumber==null){
            stringRedisTemplate.opsForValue().set("orderNumber","0");
        }

        //获得自增的流水号
        Long orderNumber1 = stringRedisTemplate.opsForValue().increment("orderNumber");
        stringBuffer.append(orderNumber1);

        return stringBuffer.toString();
    }

    /**
     * 获取用户id后四位
     * @param uid
     * @return
     */
    public String getUid(Integer uid){
        StringBuffer buffer = new StringBuffer("");
        String uidStr = uid + "";

        if (uidStr.length()<4){
            //不足4位补0
            for (int i = 0; i < (4 - uidStr.length()); i++) {
                buffer.append("0");
            }
            buffer.append(uidStr);
        }else {
            //截取后4位
            buffer.append(uidStr.substring(uidStr.length()-4));
        }

        return buffer.toString();
    }

    public Integer parseUid(String oid) {
        String uids = oid.substring(6,10);
        return Integer.parseInt(uids);
    }
}
