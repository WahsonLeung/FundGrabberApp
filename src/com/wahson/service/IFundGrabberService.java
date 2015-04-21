package com.wahson.service;

/**
 * Created by wahsonleung on 15/4/14.
 */
public interface IFundGrabberService {
    String addFund(Object fund);
    String saveFund(Object fund);
    String deleteFund(Object fund);
    String listFunds();
}
