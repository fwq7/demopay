package com.fwq.demopay.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
@CrossOrigin
@Controller
public class TestController {

    private final String APP_ID = "2021000117610530";
    private final String APP_PRIVATE_KEY = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCENmbBZG3KazSMOMEsXi1ESgymmiYpXvWrxfb49/xdvl2i4iYjAUP07o8DZrpTwbc8iI/jb3GBzye29wHHQxv+bl6NFStrvtvcr2aYP2PjvfOkKRKB9laaB48pxtbYkTDH0f7s0PjkGEO0CemmU4s0jRffsUZf1l+BE6ZrURUDEcRsISbpCO7Hp+0CWzZKCwQ1PCsk6C259ctKZX0coibRjuzBJZ8VOkoemkGr46T+NEpAoxlgynTXCLeJ7ahG25zdnsKhyGhtMU9CX7x7EN99/iNNlcOY4ZV/x+OlI17dbEqnyW1Wy7mTohxkpjtb2rre7KgQvL2KMWaNWiJNlGUNAgMBAAECggEAVQe73GMfrVB3cjtKGaPUDxhkvH1lT7Qv4jKmnQa4ev3ksM4rYtX8g7ktpy1ZZmau1zKo/8f7w1WcoDKKJUJG7cLD8o6L53bPZkY0gHQ8ZQsUeB6mNYldtyBndaHZKV1vdwPapSnc+K2WVWMOrxcJ71lS4l3b+eUgdR5TKK/0CI1lEXpDN++kZJPPMw6YbfmQK5v64ZTx3PxPRMWNqC53tBEmRCmTaobxWC1r0ZkRQz71odx5aYnruLiztkwFQRtEPYkB/HzpJVxkgPrWkTmnaaeCoeofZSgus3MtTc2TfBOX3vvJZOxWDXB8/V8whiZk0hz9OnhCBsBnTRLnjpPxwQKBgQDRpB36er4HU9zJe7dQHg9OmDitJ0jDw71DCFiEf+XSGY9nz4sR2ar9DCHqAWUFrrqA1X+wR5dxdS0EfkItX9GejYBKkgw8VUGR2neRehCpGw6j/+3MSdZ485nqeA5r/SBYxotxEcGEcyVTf/7uuHZCC0hQDBP6Eu5lLnkm8ZPoUQKBgQChcwPhRIferKwiSafR26Dbt/e/rJzljkZOS9k+t2X+fk4WKGIfo2ruva08/X+VMiNxphCLQ0tDjY/ZN/9MvFBDj3EavgUuI4DrjTDzp2sqkLBqB9vW7Z+8T+rLMsknRRnqihGKvGbU2qxTtU7ZTYkHmp4DUVrXVTXDru3ICKS9/QKBgQClHrHqDvFLzjbxj3skJHkD+4twfqemYa7ZdGJcAmze3fkA2Ax+g8ZUx1CTAoQyRJxrSz3md40F9Tf++P9gw77UNjmdAomWn8QkgEF7GPy0ZTZT4AnJxUKjQ6OwdN1PGzF2GvPLnCOjEhqNORxvpGSowkAzpD+70Vtp4H/WN6K68QKBgHXCaKAU1m2q0NspkkjVw5y/sOuTwdUGU22kkU7XgoOAKQCsb/XXwpVksEl+NWLjcUrh78tESE97n+K2n3afHIRKyhrRYwHmT5CAKaZvBRHE+4g7NzSrmglG2WFhJ6uHEYIrNuRUy0SSQ4PDCUIi9cIgGwUcf//0XQTgQPvYoiEZAoGAaxB4IavL2tkfFKVl6jCxy5Vd1RjKHftm517Us+kvs7M8ChD+jcmS+8H2zm7l8epS+MFS1NHo5cgz3an1yxAHvZbrUYLS8DFuVZBDsjUPu5j6yRJku9oM3fU13hMBcF2+NYkHNUufwCKBCy8EK57TcXWCY4XAwwNrp9+rqzyyi58=";
    private final String CHARSET = "UTF-8";
    private final String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAkhZJnxH73xUQU8q2Dciq5UX+WXY70Tg+vWlT/xeXDb4DvKTGNR2aSPP8gHIbPG6kjtiuu5onQg2hMsTzvSSCGVwHsYDQOapkdc4tvVEMP1WuVXqdecUHJgacDMh0+GbTBTMw5AxvfdBgyiGK3KkxxD8TWG4Qeo5ESY/YCokirj1zc8vfyH9T5hfBiQd+nSZ5rjNlutdiCrPXAJrw0vSXe/eLcem3+W0ExPpwvOJAiBC24DRmYXDbSieDG+Q7yYPM+ezEsewhCqBqMle5Wl28mqTkOClVsBrWJO6KyJEVYFZeBhmkb+zLkbFQpf4G0E2WiNN0VehWrvAV/1e5sbh5IQIDAQAB";
    //这是沙箱接口路径,正式路径为https://openapi.alipay.com/gateway.do
    private final String GATEWAY_URL ="https://openapi.alipaydev.com/gateway.do";
    private final String FORMAT = "JSON";
    //签名方式
    private final String SIGN_TYPE = "RSA2";
    //支付宝异步通知路径,付款完毕后会异步调用本项目的方法,必须为公网地址
    private final String NOTIFY_URL = "http://37ie539352.qicp.vip/notifyUrl";
    //支付宝同步通知路径,也就是当付款完毕后跳转本项目的页面,可以不是公网地址
    private final String RETURN_URL = "http://37ie539352.qicp.vip/returnUrl";


    @GetMapping("/")
    public String Test(){
        return "index";
    }


    @RequestMapping("alipay")
    public void alipay(HttpServletResponse httpResponse) throws IOException {

        Random r=new Random();
        //实例化客户端,填入所需参数
        AlipayClient alipayClient = new DefaultAlipayClient(GATEWAY_URL, APP_ID, APP_PRIVATE_KEY, FORMAT, CHARSET, ALIPAY_PUBLIC_KEY, SIGN_TYPE);
        AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();
        //在公共参数中设置回跳和通知地址
        request.setReturnUrl(RETURN_URL);
        request.setNotifyUrl(NOTIFY_URL);

        //商户订单号，商户网站订单系统中唯一订单号，必填
        //生成随机Id
        String out_trade_no = UUID.randomUUID().toString();
        //付款金额，必填
        String total_amount =Integer.toString(r.nextInt(10)+10);
        //订单名称，必填
        String subject ="范伟强 2021款 190cm 60Kg  ";
        //商品描述，可空
        String body = "尊敬的会员欢迎购买范伟强 2021款 175cm 66Kg TFSl quattro豪华型";
        request.setBizContent("{\"out_trade_no\":\""+ out_trade_no +"\","
                + "\"total_amount\":\""+ total_amount +"\","
                + "\"subject\":\""+ subject +"\","
                + "\"body\":\""+ body +"\","
                + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");
        String form = "";
        try {
            form = alipayClient.pageExecute(request).getBody(); // 调用SDK生成表单
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        httpResponse.setContentType("text/html;charset=" + CHARSET);
        httpResponse.getWriter().write(form);// 直接将完整的表单html输出到页面
        httpResponse.getWriter().flush();
        httpResponse.getWriter().close();
    }

    @RequestMapping(value = "/returnUrl", method = RequestMethod.GET)
    public String returnUrl(HttpServletRequest request, HttpServletResponse response)
            throws IOException, AlipayApiException {
        System.out.println("=================================同步回调=====================================");

        // 获取支付宝GET过来反馈信息
        Map<String, String> params = new HashMap<String, String>();
        Map<String, String[]> requestParams = request.getParameterMap();
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
            }
            // 乱码解决，这段代码在出现乱码时使用
            valueStr = new String(valueStr.getBytes("utf-8"), "utf-8");
            params.put(name, valueStr);
        }

        System.out.println(params);//查看参数都有哪些
        boolean signVerified = AlipaySignature.rsaCheckV1(params, ALIPAY_PUBLIC_KEY, CHARSET, SIGN_TYPE); // 调用SDK验证签名
        //验证签名通过
        if(signVerified){
            // 商户订单号
            String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"), "UTF-8");

            // 支付宝交易号
            String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"), "UTF-8");

            // 付款金额
            String total_amount = new String(request.getParameter("total_amount").getBytes("ISO-8859-1"), "UTF-8");

            System.out.println("商户订单号="+out_trade_no);
            System.out.println("支付宝交易号="+trade_no);
            System.out.println("付款金额="+total_amount);

            //支付成功，修复支付状态
           //payService.updateById(Integer.valueOf(out_trade_no));
            System.out.println("支付成功");
            return "ok";//跳转付款成功页面
        }else{
            return "no";//跳转付款失败页面
        }

    }

    @RequestMapping(value = "/notifyUrl", method = RequestMethod.GET)
    public String notifyUrl(HttpServletRequest request, HttpServletResponse response)
            throws IOException, AlipayApiException {
        System.out.println("=================================异步回调=====================================");

        Map<String, String> parameters = new HashMap<>();
        parameters.put("charset", "UTF-8");
        parameters.put("sign", "GM0CbuqaEivqgb......");
        parameters.put("app_id", "2018091261392200");
        parameters.put("sign_type", "RSA2");
        parameters.put("isv_ticket", "");
        parameters.put("timestamp", "2020-03-25 16:27:08");
//... ... 接收到的所有参数放入一个Map中
//        Factory.Payment.Common().verifyNotify(parameters);
        return "notifyUrl";
    }
}

