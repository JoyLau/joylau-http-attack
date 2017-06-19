package cn.joylau.code.http.attack.task;

import cn.joylau.code.http.attack.activiti.ConcurrentExe;
import cn.joylau.code.http.attack.activiti.HttpClientRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Created by JoyLau on 2017/6/12.
 * cn.joylau.code.http.attack.task
 * 2587038142@qq.com
 */
@Component
public class RunTask {

    @Autowired
    private ConcurrentExe concurrentExe;

    @Autowired
    private HttpClientRequest request;

    @Scheduled(fixedRate=20000)
    public void run(){
        concurrentExe.run();
    }
}
