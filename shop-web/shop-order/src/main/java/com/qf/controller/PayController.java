package com.qf.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.qf.entity.Order;
import com.qf.service.IOrderService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @version 1.0
 * @user 36043
 * @date 2019/7/25 15:12
 */
@Controller
@RequestMapping("/pay")
public class PayController {

    @Reference
    private IOrderService orderService;

    /**
     * 引入支付宝接口，调用支付业务
     * @param orderid
     * @param response
     */
    @RequestMapping("/alipay")
    public void alipay(String orderid, HttpServletResponse response){

        //根据订单id查询订单详情
        Order order = orderService.queryByOid(orderid);

        System.out.println(order);

        //创建一个阿里支付的客户端，所有的支付、退款、查询等等相关的请求都是由这个对象发送的
        AlipayClient alipayClient = new DefaultAlipayClient(
                "https://openapi.alipaydev.com/gateway.do",
                "2016100200644527",//APPID
                "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQDlB34DXDiTX3AHp/NQ+B5Th00XVj5xe2jJj0lUjB7X7w8+owZ9qfvSzD4Gt+KgNx5MiHRICRdOwwdQVHnjdzrjrWcvb5SdoOFnqlEER+JZmAe0YLxwPGDpKGr7Jvkq804x8oLOWvsW65mxTMZLVTDdAKGHiFPjzNdPqOTjNf/ta4px3IlHfC6XWECeN5VsTN5n7erufMi/6Dt/nqBBnmpwzFTTMOyNx2SDKXe0FzaWSGh3DYaanQYngL7cZe2g89Nti1igpgc7LlUhaM95QCtPq2IiMT1pz8UdvEjphvtlpLQbVOruhke0rASu4Ib52c38R4n5Xk3leiWHDCT6nSFZAgMBAAECggEAZ/O4nGnVO/7LaI4wgAW+LgdxVDBO808iRqoEjJ10umnFL5brMUpgBFOzq3T3JeUemXot3SxTKeqXfTx6fmX1krV36i3o9Iq7BERntVuzPxskFBj8Lq0ZB3rS49/SpE3+pej+ug7NfJ+/CJxiPHsUTJsmtc+I1BJ7f6qXCRHPccDL8SaMhZE210hG6+RgeazDq1tiVBL377nVKwO77cLBP1Nf8hvJA9EPVtvTLEqKbntWiemZ4Cqe3WCkSU9EumX2GDS30Jp6l1mL0eV/awWCcgc0sHZd09uXKZqc409+a6Zg/kr7x4/K0kl7nwjMxIpSWtOf7dLNMqSF+W8SiSkiCQKBgQD9jw7cryKahAyKCu4wqsfDLdclvnsavBUbU3z46iXC5AeVD7koSjUeXXVAeShczoAJTOZo07lA963KA3DC7NoYeVyYB894eZIA9NUbJxbmh6HFdsJSkgHPj+1cd5u2RAodR/zyqqUkyqt81yNZh5xIexppYgeIDGinnqq7SbkfuwKBgQDnO/oEIul4dmC+tyECl9rzQjiqB/Ewr3xm5A4tp4Uxk03M6xNtCPH/2kCDg7oJbseFAQOTFNclCuzHJiRUFcH6LGz9/953DmlESrjEwRq2KddlsjSz2PIP4O3MZWtmxYuKPM3fxGh5Y3M8HvXTQkteqd3XKvah/9DanTR4tyQ/+wKBgQDNlX12XyXOZHyKPHyNxxp63SMRPlUwAwmWA2ZTp/1//EVNjdcRnMFe4mXcJaAdR202nTxACFKvI2KsZYe69DGNIHF77vE//EnuLpTPFC2UMqpuoXDDZBDFyyuoCm6dJKOSxDiKROPrZcwPR5wozjRf7Dgo88upem4sYhCKFCdeWwKBgAodmpNhv5rwesz9D+mgIQjxRa9Sh5+cnbGWmnQu1unTSt0DD6d+c6/AqX/XXGUbPok4ASMHD15kevU/Wul+xiyekuoNrnrA26QMLMcWt4dujyi4wgt4GybMrTp62Vb2n2Mdw9fdLMDas+jWZeSnCssKXVUTYRS3LzjRFcGOKXzPAoGBAOFp7xGYZjNdoiFCmRigqtpPDk1kibnGZ44ABmkmLqDoj8J+r+D9/Xho1t5Vcy8U1V0hoEiSfBGXvOMGbDdylpNxYvY0x1xiAj3yqYdpybdJRPy11Ud42b3BdzikVzFlA3wYmpsyXwNTQNJxnO/LAYKEsNIVbaHrbicDL/tTZCLJ",//开发者私钥
                "json",
                "UTF-8",
                "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA+M3c6XZBtVhjCLZcx/upMGvbKiEhtyvM9KJtFmIK76GWfR0X2D6DBHLBv2jeW64bDir7+1nyUisgtR6SXHGigd68A/k9UcCCk5rIb2ggeJFJrJkk0ggDf3tOLtS+Ku2wXfdusA5GOGf2MPfhR3+DYl9bJWcG+MN5rzUMum01bds4ihmEYnVm2iStIjB1077wiOvW2+qdYGRQ9Msuvq9DD421D1LXPjFm0MOtga4+cA9GKOGETjVB09tlaX2/UwgOOkO3GXqZSHrVquCeIQFL0C8CdEs3xcd23zXT1dinmIM7vupGUIyGOuEgfVtWMUHaMYK0r0JPj3TUqL8OcYPRhwIDAQAB",//阿里的公钥
                "RSA2"); //获得初始化的AlipayClient

        //申请支付页面的请求对象
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();//创建API对应的request

        //设置同步返回的url - 用户支付完成后跳转到哪里页面 GET
        alipayRequest.setReturnUrl("http://localhost:8081");

        //设置异步返回的url - 用户支付结果的通知url POST
        alipayRequest.setNotifyUrl("http://verygoodwlk.xicp.net/pay/paycompent");//在公共参数中设置回跳和通知地址

        //支付请求体
        alipayRequest.setBizContent("{" +
                "    \"out_trade_no\":\"" + orderid + "\"," +
                "    \"product_code\":\"FAST_INSTANT_TRADE_PAY\"," +
                "    \"total_amount\":" + order.getAllprice().doubleValue() + "," +
                "    \"subject\":\"" + orderid + "\"," +
                "    \"body\":\"" + orderid + "\"," +
                "    \"extend_params\":{" +
                "    \"sys_service_provider_id\":\"2088511833207846\"" +
                "    }"+
                "  }");//填充业务参数

        //生成支付的表单页面 - 一串HTML文本
        String form="";
        try {
            form = alipayClient.pageExecute(alipayRequest).getBody(); //调用SDK生成表单
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        response.setContentType("text/html;charset=UTF-8");
        try {
            //将支付的页面写入用户的浏览器中
            response.getWriter().write(form);//直接将完整的表单html输出到页面
            response.getWriter().flush();
            response.getWriter().close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 此处必须校验是否是支付宝公司发来的异步请求信息（必须采用支付宝公钥验证）
     * @param out_trade_no
     * @param trade_status
     */
    @RequestMapping("/paycompent")
    @ResponseBody
    public void payResult(String out_trade_no,String trade_status){

        if (trade_status.equals("TRADE_SUCCESS")){
            //说明交易支付成功，可以去修改订单状态
            orderService.updateOrderStatus(out_trade_no,1);
        }
    }
}
