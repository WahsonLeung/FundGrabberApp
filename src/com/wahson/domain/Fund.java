package com.wahson.domain;

/**
 * Created by wahsonleung on 15/4/13.
 */
public class Fund {
    private String fundName;
    private String fundCode;
    private String buyNet;
    private String latestNet;
    private String buyDate;
    private String share;
    private String buyCosts;
    private String buyingRate;
    private String redemptionRate;
    private String earningBeforeRedemption;
    private String earning;
    private String sum;

    public String getFundName() {
        return fundName;
    }

    public void setFundName(String fundName) {
        this.fundName = fundName;
    }

    public String getLatestNet() {
        return latestNet;
    }

    public void setLatestNet(String latestNet) {
        this.latestNet = latestNet;
    }

    public String getFundCode() {
        return fundCode;
    }

    public void setFundCode(String fundCode) {
        this.fundCode = fundCode;
    }

    public String getBuyNet() {
        return buyNet;
    }

    public void setBuyNet(String buyNet) {
        this.buyNet = buyNet;
    }

    public String getBuyDate() {
        return buyDate;
    }

    public void setBuyDate(String buyString) {
        this.buyDate = buyString;
    }

    public String getShare() {
        return share;
    }

    public void setShare(String share) {
        this.share = share;
    }

    public String getBuyCosts() {
        return buyCosts;
    }

    public void setBuyCosts(String buyCosts) {
        this.buyCosts = buyCosts;
    }

    public String getBuyingRate() {
        return buyingRate;
    }

    public void setBuyingRate(String buyingRate) {
        this.buyingRate = buyingRate;
    }

    public String getRedemptionRate() {
        return redemptionRate;
    }

    public void setRedemptionRate(String redemptionRate) {
        this.redemptionRate = redemptionRate;
    }

    public String getEarningBeforeRedemption() {
        return earningBeforeRedemption;
    }

    public void setEarningBeforeRedemption(String earningBeforeRedemption) {
        this.earningBeforeRedemption = earningBeforeRedemption;
    }

    public String getEarning() {
        return earning;
    }

    public void setEarning(String earning) {
        this.earning = earning;
    }

    public String getSum() {
        return sum;
    }

    public void setSum(String sum) {
        this.sum = sum;
    }
}
