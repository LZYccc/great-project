package org.example.controller;

import org.example.service.TestService;
import org.example.utils.ApiUtils;
import org.example.utils.SpringContextUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpRequest;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@RestController
public class HelloWorld {

    @Value("${file.path}")
    private String filePath;


    @Autowired
    private TestService testService;

    private static FileOutputStream fileOutputStream;

    @PostConstruct
    public void init(){
        try {
            fileOutputStream = new FileOutputStream(new File(filePath));
        }catch (Exception e){

        }
    }

  @RequestMapping("api/hello")
  Map<String,String> hello() throws Exception {
      Map<String,String> map = new HashMap<>();
      map.put("result","create by lizhangyu！");

      fileOutputStream.write("hhhh\n".getBytes());
      fileOutputStream.flush();

      return map;
  }

    @RequestMapping("api/testInternet")
    Map<String,String> testInternet() throws Exception {
        Map<String,String> map = new HashMap<>();
        map.put("result","create by lizhangyu！");
        map.put("testResult",ApiUtils.appSign());
        return map;
    }


    @RequestMapping("asyncMath1")
    Map<String,String> asyncMath1(HttpServletRequest request) {
        Principal principal = request.getUserPrincipal();
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
    Map<String,String> asyncMath2(HttpServletRequest request) {

        System.out.println(""+request.getHeader("X-Request-Foo"));
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
        map.put("result","asyncMath3");
        return map;
    }

    @PreDestroy
    public void destroyMethod() throws Exception {
        fileOutputStream.close();
    }
}
