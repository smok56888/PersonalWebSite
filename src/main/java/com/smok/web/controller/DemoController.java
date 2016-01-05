package com.smok.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.smok.web.model.BaseModel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

/**
 * Created by smok on 2015/12/28.
 */
@Controller
@RequestMapping(value = "/demo/*")
public class DemoController {

    /**
     * 展示模板
     *
     * @return
     */
    @RequestMapping(value = "model", method = RequestMethod.GET)
    public String showModel(ModelMap model) {
        System.out.println("/demo/model  --  show model");
        model.put("hello", "hello guys~!");
        return "/demo/demo";
    }

    /**
     * 获取参数 -- 必须传递
     *
     * @return
     */
    @RequestMapping(value = "acceptparam1", method = RequestMethod.GET)
    public String acceptParam1(@RequestParam String param1, @RequestParam String param2, ModelMap model) {
        model.put("param1", param1);
        model.put("param2", param2);
        return "/demo/demo";
    }

    /**
     * 获取参数，命令对象
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "acceptparam3", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String acceptParam3(BaseModel model) {
        JSONObject json = new JSONObject();
        json.put("key", model.getKey());
        json.put("value", model.getValue());
        return json.toJSONString();
    }

    /**
     * 获取参数，返回json
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "acceptparam2", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String acceptParam2(HttpServletRequest request) {

        JSONObject json = new JSONObject();
        try {
            request.setCharacterEncoding("utf-8");

            String param1 = request.getParameter("param1");
            String param2 = request.getParameter("param2");
            String header1 = request.getHeader("header1"); // TODO header中的中文乱码问题

            json.put("param1", param1);
            json.put("param2", param2);
            json.put("header1", header1);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return json.toJSONString();
    }

    // restful风格接收参数
    @RequestMapping(value = "/acceptparam/{param1}/{param2}", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String acceptDynamicParam(@PathVariable int param1, @PathVariable int param2) {
        System.out.println("accept dynamic param1 : " + param1 + "  param2:" + param2);
        return String.valueOf(param1 + "-" + param2);
    }

    // 302跳转
    @RequestMapping(value = "/redirect")
    public String redirect(){
        return "redirect:/demo/model";
    }

}
