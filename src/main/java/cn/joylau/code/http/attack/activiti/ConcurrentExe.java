package cn.joylau.code.http.attack.activiti;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by JoyLau on 2017/6/12.
 * cn.joylau.code.http.attack.activiti
 * 2587038142@qq.com
 */
@Component
public class ConcurrentExe {

    @Autowired
    private CommonModel commonModel;

    @Autowired
    private HttpClientRequest httpClientRequest;

    public void run(){
        // 锁住所有线程，等待并发执行
        final CountDownLatch begin = new CountDownLatch(1);
        final ExecutorService exec = Executors.newFixedThreadPool(commonModel.getExecPool());
        for (int index = 0; index < commonModel.getExecNumber(); index++) {
            Runnable run = new Runnable() {
                public void run() {
                    try {
                        // 等待，所有一起执行
                        begin.await();
                        //等待执行。。。
                        httpClientRequest.request();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };
            exec.submit(run);
        }
        // begin减一，开始并发执行
        begin.countDown();
        //关闭执行
        exec.shutdown();
    }
}
