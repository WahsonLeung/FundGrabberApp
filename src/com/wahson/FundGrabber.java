package com.wahson;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wahson.constants.PropKeys;
import com.wahson.domain.Fund;
import com.wahson.utils.PropertyUtils;
import com.wahson.xml.FundDom;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by wahsonleung on 15/4/13.
 */
public class FundGrabber {

    private static final Logger logger = LogManager.getLogger(FundGrabber.class);

    private static FundDom fundDom = new FundDom(PropertyUtils.getValue(PropKeys.FILE_PATH));

    public static String getJsonResult(String url) throws IOException{
        logger.info("getJsonResult:"+url);
        CloseableHttpClient httpClient = HttpClients.createDefault();
        String resultStr = "";
        try{
            HttpGet httpGet = new HttpGet(url);
            CloseableHttpResponse response = httpClient.execute(httpGet);
            if (response.getStatusLine().getStatusCode()==HttpStatus.SC_OK){
                InputStream in = response.getEntity().getContent();
                resultStr = inSteam2String(in);
            }
            logger.info("result:"+resultStr);
        } finally {
            httpClient.close();
        }

        return resultStr;
    }

    /**
     * 获取基金最新净值
     * @param fundCode
     * @return
     * @throws Exception
     */
    public static JSONObject getFundInfo(String fundCode) throws Exception {
        String url = PropertyUtils.getValue(PropKeys.NET_URL)+fundCode;
        String result = getJsonResult(url);
        logger.info("getFundInfo:"+url);
        return JSONObject.parseObject(parse2JsonFormat(result));

    }

    public static String parse2JsonFormat(String jsonStr){
        String result = "{}";
        Pattern pattern = Pattern.compile("\\{.*\\}");

        Matcher matcher = pattern.matcher(jsonStr);
        if(matcher.find()){
            result = matcher.group();
        }

        return result;
    }

    /**
     * 获取基金实时估值
     * @param fundCode
     * @return
     * @throws Exception
     */
    public static JSONObject getFundRealValuation(String fundCode) throws Exception {

        String url = PropertyUtils.getValue(PropKeys.REAL_URL)+fundCode;
        String result = getJsonResult(url);
        logger.info("getFundRealValuation:"+url);
        return JSONObject.parseObject(parse2JsonFormat(result));
    }

    public static String inSteam2String(InputStream in) throws IOException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        byte[] buf = new byte[1024];
        int len = -1;
        while ((len = in.read(buf)) != -1) {
            os.write(buf,0,len);
        }

        return new String(os.toByteArray());
    }


    public static List<Fund> localFundList() {
        return (List<Fund>) fundDom.parseXml();

    }

    public static void addFund(Fund fund){
        fundDom.addFund(fund);
    }

    /**
     * 获取基金最新净值
     * @param localFund
     * @return Fund
     */
    public static Fund getRemoteFund(Fund localFund) {
        try {
            JSONObject data = getFundInfo(localFund.getFundCode());
            JSONArray fundData = data.getJSONArray("data");
            if(fundData.size() > 0){
                JSONObject fundObj = fundData.getJSONObject(0);
                String net = fundObj.getString("net");
                //TODO:get more infomation
                localFund.setLatestNet(net);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return localFund;
    }


    public static void main(String[] args) throws Exception {

        String fundCode = "000409";

        JSONObject data = getFundInfo(fundCode);
        JSONArray fundData = data.getJSONArray("data");
        JSONObject fundObj = fundData.getJSONObject(0);
        String net = fundObj.getString("net");
//
        System.out.println(net);

        JSONObject d = getFundRealValuation(fundCode);
//        {"data":{"000409":{"date":"2015-04-13","pre":"1.6370","time":1500,"value":"1.66933"}},"error":0,"message":"success"}
        System.out.println(d);

//        List<Fund> fundList = localFundList();
//        System.out.println(fundList.size());

    }

}
