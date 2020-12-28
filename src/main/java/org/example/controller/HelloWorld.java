package org.example.controller;

import org.example.service.TestService;
import org.example.utils.SpringContextUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class HelloWorld {
    @Autowired
    private TestService testService;
  @RequestMapping("api/hello")
  Map<String,String> hello() {
      Map<String,String> map = new HashMap<>();
      map.put("result","create by 迟墨！");
      return map;
  }
    @RequestMapping("asyncMath1")
    Map<String,String> asyncMath1() {
        System.out.println("调用service asyncMath1开始");
        ThreadPoolTaskExecutor threadPoolTaskExecutor = (ThreadPoolTaskExecutor)SpringContextUtils.getBean("name1");
        System.out.println("当前活动线程数：" + threadPoolTaskExecutor.getActiveCount());
        testService.asyncMath1();
        System.out.println("调用service asyncMath1结束");
        Map<String,String> map = new HashMap<>();
        map.put("result","asyncMath1");
        return map;
    }
    @RequestMapping("asyncMath2")
    Map<String,String> asyncMath2() {
        System.out.println("调用service asyncMath2开始");
        testService.asyncMath2();
        System.out.println("调用service asyncMath2结束");
        Map<String,String> map = new HashMap<>();
        map.put("result","asyncMath2");
        return map;
    }
    @RequestMapping("asyncMath3")
    Map<String,String> asyncMath3() {
        System.out.println("调用service asyncMath3开始");
        testService.asyncMath3();
        System.out.println("调用service asyncMath3结束");
        Map<String,String> map = new HashMap<>();
        map.put("result","asyncMath2");
        return map;
    }
}
