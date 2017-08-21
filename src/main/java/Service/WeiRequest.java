package Service;

import Dao.ManageDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by k on 8/5/17.
 */
@Service
public class WeiRequest {

    @Autowired
    ManageDao manageDao;

    //获取access——token
    public String access_token() {
        String urls = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&";
        String appId = "wxb959ca08d98bc454";
        String secret = "b36ede8b31c5a8b0875961515bf9545a";
        String params = "appId=" + appId + "&secret=" + secret;
        Map map = new HashMap();
        map.put("urls", urls);
        map.put("params", params);

        String result = getCustomerInfo(map);
        System.out.println(result);
        String[] name = result.split("\"");

        return name[3];
    }


    public String jsapi_ticket() {
//        从数据库获取access_token
        String access_token = getWeiXinAppScret();
        System.out.println(access_token + "+++++++++++++++++");
        String urls = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?";
        String params = "access_token=" + access_token + "&type=jsapi";
        Map map = new HashMap();
        map.put("urls", urls);
        map.put("params", params);
        String[] result = getCustomerInfo(map).split("\"");
        return result[9];
    }

    //    发送请求的
    public String getCustomerInfo(Map<String, Object> map) {
        String urls = (String) map.get("urls");
        String params = (String) map.get("params");

        String jsonObject = "";
        OutputStreamWriter out = null;
        StringBuffer buffer = new StringBuffer();
        try {
            //1.连接部分
            URL url = new URL(urls);
            // http协议传输
            HttpURLConnection httpUrlConn = (HttpURLConnection) url.openConnection();

            httpUrlConn.setDoOutput(true);
            httpUrlConn.setDoInput(true);
            httpUrlConn.setUseCaches(false);
            // 设置请求方式（GET/POST）
            httpUrlConn.setRequestMethod("GET");
            httpUrlConn.setRequestProperty("content-type", "application/x-www-form-urlencoded");

            //2.传入参数部分
            // 得到请求的输出流对象
            out = new OutputStreamWriter(httpUrlConn.getOutputStream(), "UTF-8");
            // 把数据写入请求的Body
            out.write(params); //参数形式跟在地址栏的一样
            out.flush();
            out.close();

            //3.获取数据
            // 将返回的输入流转换成字符串
            InputStream inputStream = httpUrlConn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String str = null;
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }
            bufferedReader.close();
            inputStreamReader.close();
            // 释放资源
            inputStream.close();
            inputStream = null;
            httpUrlConn.disconnect();
            jsonObject = buffer.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    //时间戳
    private String timeap() {
        return Long.toString(System.currentTimeMillis() / 1000);
    }

    //随机字符串
    private String noString() {
        return UUID.randomUUID().toString();
    }


    //    返回所有的信息
    public Map getAllInfo() {

        Map map = new HashMap();
        map.put("jsapi_ticket", jsapi_ticket());
        map.put("noncestr", noString());
        map.put("timestamp", timeap());
        return map;
    }


    public String getWeiXinAppScret() {
        String weiXinAppScret = manageDao.getWeiXinAppScret();
        System.out.println(weiXinAppScret + "<<<<<<<<<<<<<<<<");
        return weiXinAppScret;

    }

    public static void main(String[] args) {
        String name = new WeiRequest().access_token();
        System.out.println(name);
    }

}


