package com.djd.controller;

import com.djd.model.ResultBase;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by dujiande on 2017/2/6.
 */
@Controller
public class DataController {

    @ResponseBody
    @RequestMapping(value = "data/testjson",method = RequestMethod.POST)
    public ResultBase testjson(@RequestBody Map userBean){
       ResultBase resultBase = new ResultBase();
       resultBase.setNumberLeft(0);
       resultBase.isSuccess = true;
       resultBase.setResponseCode(0);
       //resultBase.setResponseMsg("成功"+userBean.getAccount()+":"+userBean.getPwd());
        resultBase.setResponseMsg("成功"+userBean.get("account")+":"+userBean.get("pwd"));
        return resultBase;
    }

    @ResponseBody
    @RequestMapping(value = "data/repos",method = RequestMethod.GET)
    public List<String> repos(){
        List<String> resultList = new ArrayList<String>();
        resultList.add("this");
        resultList.add("is");
        resultList.add("a");
        resultList.add("测试");
        return resultList;
    }
}
