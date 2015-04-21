package com.wahson.client;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wahson.domain.Fund;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wahsonleung on 15/4/14.
 */
public class FundGrabberService {
    public String addFund(Object param) {
        //JSObject to JSONObject
//        JSONObject paramObj = (JSONObject)param;
        JSONObject paramObj = JSONObject.parseObject(param.toString());
        System.out.println(paramObj.getString("fundCode"));
        List<Fund> rs = new ArrayList<Fund>();
        Fund f1 = new Fund();
        f1.setFundCode("000409");
        Fund f2 = new Fund();
        f2.setFundCode("161025");
        rs.add(f1);
        rs.add(f2);
        JSONArray array = new JSONArray();
        array.addAll(rs);
        paramObj.put("test",rs);

        System.out.println(paramObj.toJSONString());
        return paramObj.toJSONString();
    }
}