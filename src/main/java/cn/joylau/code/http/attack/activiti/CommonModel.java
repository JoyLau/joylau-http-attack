package cn.joylau.code.http.attack.activiti;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by JoyLau on 2017/6/12.
 * cn.joylau.code.http.attack.activiti
 * 2587038142@qq.com
 */
@Data
@Component
@ConfigurationProperties(prefix = "request.attack")
public class CommonModel {
    public List<String> urls;

    @Value("${user.username}")
    private String username;
    @Value("${user.password}")
    private String password;

    @Value("${user.identity}")
    private String identity;

    @Value("${exec.number}")
    private int execNumber;

    @Value("${exec.pool}")
    private int execPool;

    @Value("${try.number}")
    private int tNumber;

    @Value("${request.url.login}")
    private String loginURL;

}
