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
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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

    @RequestMapping(value="data/download")
    public void download(HttpServletRequest request, HttpServletResponse response){
        init(request);
        try {
            String downloadfFileName = request.getParameter("filename");
            downloadfFileName = new String(downloadfFileName.getBytes("iso-8859-1"),"utf-8");
            String fileName = downloadfFileName.substring(downloadfFileName.indexOf("_")+1);
            String userAgent = request.getHeader("User-Agent");
            byte[] bytes = userAgent.contains("MSIE") ? fileName.getBytes() : fileName.getBytes("UTF-8");
            fileName = new String(bytes, "ISO-8859-1");
            response.setHeader("Content-disposition", String.format("attachment; filename=\"%s\"", fileName));
            FileOperateUtil.download(downloadfFileName, response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
