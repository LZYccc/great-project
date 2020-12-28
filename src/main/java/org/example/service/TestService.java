package org.example.service;

import org.example.utils.SpringContextUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

@Service
public class TestService {

    @Async("name1")
    public void asyncMath1(){
        asyncMath2();
    }


    @Async("name2")
    public void asyncMath2(){
        synchronized (TestService.class){
            try {
                System.out.println(Thread.currentThread().getName()+"睡眠开始");
                Thread.sleep(5000);
                System.out.println(Thread.currentThread().getName()+"睡眠结束");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Async
    public void asyncMath3(){
        asyncMath2();
    }
}
