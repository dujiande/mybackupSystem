package com.djd.controller;

import com.djd.model.ResultBase;
import com.djd.utils.FileOperateUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
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

    private void init(HttpServletRequest request) {
        if(FileOperateUtil.FILEDIR == null){
            FileOperateUtil.FILEDIR = request.getSession().getServletContext().getRealPath("/") + "file/";
        }
    }

    @ResponseBody
    @RequestMapping(value="data/upload",method = RequestMethod.POST)
    public ResultBase upload(HttpServletRequest request){
        init(request);
        ResultBase resultBase = new ResultBase();
        try {
            MultipartHttpServletRequest mRequest = (MultipartHttpServletRequest) request;
            String mark = mRequest.getParameter("mark");
            FileOperateUtil.upload(request);
            resultBase.isSuccess = true;
            resultBase.setResponseMsg("上传文件成功"+":"+mark);
        } catch (Exception e) {
            e.printStackTrace();
            resultBase.isSuccess = false;
            resultBase.setResponseMsg("上传文件失败："+e.getCause().toString());
        }
        resultBase.setNumberLeft(0);
        resultBase.setResponseCode(0);
        return resultBase;
    }
}
