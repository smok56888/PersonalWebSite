package com.smok.web.controller;
/**
 * Created by smok on 2015/12/26.
 */

import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/user/*")
public class UserController {
    @RequestMapping(value = "init", method = RequestMethod.GET)
    public String init(ModelMap model) {
        System.out.println("user/init");
        return "user";
    }

    @RequestMapping(value = "resume", method = RequestMethod.GET)
    public String resume(ModelMap model) {
        System.out.println("user/resume");
        return "/user/resume";

    }

    @RequestMapping(value = "update", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String update(HttpServletRequest request) {
        String key = request.getParameter("key");
        String value = request.getParameter("value");
        JSONObject json = new JSONObject();
        json.put("key", key);
        json.put("value", value);
        return json.toJSONString();
    }

}