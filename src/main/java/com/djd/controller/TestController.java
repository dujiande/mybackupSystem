package com.djd.controller;

import com.djd.model.CpuEntity;
import com.djd.model.TestBean;
import com.djd.repository.CpuRepository;
import com.djd.utils.CpuUtil;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by dujiande on 2017/1/11.
 */
@Controller
public class TestController {

    public static final int SHOW_ITEM_NUM = 10;
    ScheduledExecutorService service = null;

    private static List<CpuEntity> recordList = new ArrayList<CpuEntity>();

    // 自动装配数据库接口，不需要再写原始的Connection来操作数据库
    @Autowired
    CpuRepository cpuRepository;

    @RequestMapping(value ="/cpu",method = RequestMethod.GET)
    public String cpu(ModelMap modelMap){
//        double rate = CpuUtil.getCpuRatioForWindows();
//        CpuEntity cpuBean = new CpuEntity();
//        cpuBean.setRate(rate);
//        cpuBean.setTimestamp(System.currentTimeMillis());
//        cpuRepository.saveAndFlush(cpuBean);
        List<CpuEntity> lastRecord = cpuRepository.findAll();
//        int length = lastRecord.size();
//        if(length > SHOW_ITEM_NUM){
//            CpuEntity delItem = lastRecord.get(0);
//            // 删除id为userId的用户
//            cpuRepository.delete(delItem.getId());
//            // 立即刷新
//            cpuRepository.flush();
//            lastRecord.remove(0);
//        }
        String showListStr = new Gson().toJson(getShowList(lastRecord));
        modelMap.addAttribute("showListStr",showListStr);
        startService();
        return "test/cpu";
    }

    @RequestMapping(value ="/test",method = RequestMethod.GET)
    public String test(ModelMap modelMap){
        TestBean testBean = new TestBean();
        testBean.setName("哈哈");
        testBean.setTime(System.currentTimeMillis()+"");
        modelMap.addAttribute("test",testBean);

        double rate = CpuUtil.getCpuRatioForWindows();
        CpuEntity cpuBean = new CpuEntity();
        cpuBean.setRate(rate);
        cpuBean.setTimestamp(System.currentTimeMillis());

        recordList.add(cpuBean);
        int length = recordList.size();
        if(length > SHOW_ITEM_NUM){
            recordList.remove(0);
        }

        String showListStr = new Gson().toJson(getShowList(recordList));
        modelMap.addAttribute("showListStr",showListStr);
        return "test/test";
    }

    private Double[][] getShowList(List<CpuEntity> recordList){
        Double[][] showList = new Double[SHOW_ITEM_NUM][2];
        int length = recordList.size();
        for(int i=0;i<SHOW_ITEM_NUM;i++){
            showList[i] = new Double[2];
            if(i < length){
                showList[i][0] = recordList.get(i).getRate();
            }else{
                showList[i][0] = 0.0;
            }
            showList[i][1] = i*1.0;
        }
        return showList;
    }

    @ResponseBody
    @RequestMapping(value = "/testjson",method = RequestMethod.GET)
    public Map testjson(){
        Map map=new HashMap();
        map.put("result", "success");
        map.put("message","这是一条中文的信息。");
        return map;
    }

    @ResponseBody
    @RequestMapping(value = "/cpuListener",method = RequestMethod.GET)
    public List<CpuEntity> cpuListener(){
        double rate = CpuUtil.getCpuRatioForWindows();
//        Map map=new HashMap();
//        map.put("result", "success");
//        map.put("message","读取运行此次系统检查时的cpu占用率成功");
//        map.put("rate",rate);

        CpuEntity cpuBean = new CpuEntity();
        cpuBean.setRate(rate);
        cpuBean.setTimestamp(System.currentTimeMillis());
        recordList.add(cpuBean);

        return recordList;
    }

    @ResponseBody
    @RequestMapping(value = "/startService",method = RequestMethod.GET)
    public Map startService(){

        Map map=new HashMap();
        map.put("result", "success");
        map.put("message","cpu监听服务开始！");

        if(service == null){
            service = Executors.newSingleThreadScheduledExecutor();
            Runnable runnable = new Runnable() {
                public void run() {
                    double rate = CpuUtil.getCpuRatioForWindows();
                    CpuEntity cpuBean = new CpuEntity();
                    cpuBean.setRate(rate);
                    cpuBean.setTimestamp(System.currentTimeMillis());
                    cpuRepository.saveAndFlush(cpuBean);
                    List<CpuEntity> lastRecord = cpuRepository.findAll();
                    int length = lastRecord.size();
                    if(length > SHOW_ITEM_NUM){
                        CpuEntity delItem = lastRecord.get(0);
                        // 删除id为userId的用户
                        cpuRepository.delete(delItem.getId());
                        // 立即刷新
                        cpuRepository.flush();
                        lastRecord.remove(0);
                    }
                }
            };
            service.scheduleAtFixedRate(runnable,0,1, TimeUnit.SECONDS);
        }

        return map;
    }

    @ResponseBody
    @RequestMapping(value = "/stopService",method = RequestMethod.GET)
    public Map stopService(){

        Map map=new HashMap();
        map.put("result", "success");
        map.put("message","cpu监听服务停止！");

        if(service != null){
            service.shutdownNow();
            service = null;
        }

        return map;
    }

    @ResponseBody
    @RequestMapping(value = "/getchartdata",method = RequestMethod.POST)
    public Double[][] getchartdata(){
        List<CpuEntity> lastRecord = cpuRepository.findAll();
        return getShowList(lastRecord);
    }
}
