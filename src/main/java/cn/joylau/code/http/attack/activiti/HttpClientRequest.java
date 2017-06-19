package cn.joylau.code.http.attack.activiti;

import org.apache.http.HeaderIterator;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * Created by JoyLau on 2017/6/12.
 * cn.joylau.code.http.attack.activiti
 * 2587038142@qq.com
 */
@Component
public class HttpClientRequest {

    @Autowired
    private CommonModel commonModel;

    private CloseableHttpClient client;

    public HttpClientRequest() {
        client = HttpClients.createDefault();
    }

    public void request(){
        // 直接创建client
//        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(commonModel.getLoginURL());

        UrlEncodedFormEntity postEntity = null;
        try {
            postEntity = new UrlEncodedFormEntity(getParam(createToken()), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        httpPost.setEntity(postEntity);

        try {
            // 执行post请求
            if (!isLogin(client)){
                HttpResponse httpResponse = client.execute(httpPost);
                printResponse(httpResponse);
            } else {
                System.out.println("已经登录过了。。。");
            }

            // 执行get请求
            execUrls(commonModel.getUrls(),client);


        } catch (Exception e) {
            e.printStackTrace();
        } /*finally {
            try {
                // 关闭流并释放资源
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }*/
    }


    private void printResponse(HttpResponse httpResponse){
        // 获取响应消息实体
        HttpEntity entity = httpResponse.getEntity();
        // 响应状态
        System.out.println("status:" + httpResponse.getStatusLine());
        HeaderIterator iterator = httpResponse.headerIterator();
        while (iterator.hasNext()) {
            System.out.println("\t" + iterator.next());
        }
        // 判断响应实体是否为空
        if (entity != null) {
            String responseString = null;
            try {
                responseString = EntityUtils.toString(entity);
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("response content:" + responseString);
        }
    }

    private List<NameValuePair> getParam(Map parameterMap) {
        List<NameValuePair> param = new ArrayList<>();
        for (Object o : parameterMap.entrySet()) {
            Map.Entry parmEntry = (Map.Entry) o;
            param.add(new BasicNameValuePair((String) parmEntry.getKey(),
                    (String) parmEntry.getValue()));
        }
        return param;
    }

    private HashMap<String,String> createToken(){
        HashMap<String,String> parameterMap = new HashMap<>();
        parameterMap.put("username", commonModel.getUsername());
        parameterMap.put("password", commonModel.getPassword());
        parameterMap.put("identity", commonModel.getIdentity());
        return parameterMap;
    }


    private void execUrls(List<String> urls, CloseableHttpClient client) {
        /*for (int i = 0; i < commonModel.getTNumber(); i++) {
            String url = urls.get(new Random().nextInt(urls.size()))+ "?id=" + i + "&voteId=" + i + "&url=https://www.360.cn/";
            System.out.println("请求的url:"+url);
            HttpGet httpGet = new HttpGet(url);
            HttpResponse httpResponse = null;
            try {
                httpResponse = client.execute(httpGet);
            } catch (IOException e) {
                e.printStackTrace();
            }
            printResponse(httpResponse);
        }*/

        for (int i = 0; i < commonModel.getTNumber(); i++) {
            String url = "http://dphd.gtafe.com/dphd_web/projection/1/" + UUID.randomUUID().toString();
            System.out.println("URL是：" + url);
            HttpGet httpGet = new HttpGet(url);
            HttpResponse httpResponse = null;
            try {
                httpResponse = client.execute(httpGet);
            } catch (IOException e) {
                e.printStackTrace();
            }
            printResponse(httpResponse);
        }

    }

    private boolean isLogin(CloseableHttpClient client) throws IOException {
        HttpGet httpGet = new HttpGet("http://dphd.gtafe.com/dphd_web/main");
        HttpResponse httpResponse = client.execute(httpGet);
        String s = EntityUtils.toString(httpResponse.getEntity());
        return s.contains("修改密码");
    }
}
