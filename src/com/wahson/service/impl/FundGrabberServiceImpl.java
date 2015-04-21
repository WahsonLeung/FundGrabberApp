package com.wahson.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.wahson.FundGrabber;
import com.wahson.domain.Fund;
import com.wahson.service.IFundGrabberService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by wahsonleung on 15/4/14.
 */
public class FundGrabberServiceImpl implements IFundGrabberService {

    private static final Logger logger = LogManager.getLogger(FundGrabberServiceImpl.class);
    @Override
    public String addFund(Object fund) {
        logger.info("add fund:"+fund.toString());
        JSONObject param = JSONObject.parseObject(fund.toString());
        System.out.println(param);
        Fund f = new Fund();
        f.setFundName(param.getString("fundName"));
        f.setFundCode(param.getString("fundCode"));
        f.setShare(param.getString("share"));
        f.setBuyCosts(param.getString("buyCosts"));
        f.setBuyingRate(param.getString("buyingRate"));
        f.setRedemptionRate(param.getString("redemptionRate"));
        f.setBuyDate(param.getString("buyDate"));
        f.setBuyNet(param.getString("buyNet"));
        FundGrabber.addFund(f);
        return "SUCCESS";
    }

    @Override
    public String saveFund(Object fund) {
        return null;
    }

    @Override
    public String deleteFund(Object fund) {
        return null;
    }

    @Override
    public String listFunds() {
        List<Fund> funds =FundGrabber.localFundList();
        logger.info("listFunds:"+funds.size());
        List<Fund> remoteFunds = new ArrayList<Fund>(funds.size());
        Iterator<Fund> it = funds.iterator();
        while (it.hasNext()) {
            Fund f = it.next();
            remoteFunds.add(FundGrabber.getRemoteFund(f));
        }
        JSONObject result = new JSONObject();
        result.put("funds", remoteFunds);
        logger.info("listFunds:"+result);
        return result.toJSONString();
    }
}
