package com.wahson.xml;

import com.wahson.domain.Fund;
import com.wahson.utils.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by wahsonleung on 15/4/13.
 */
public class FundDom {

    private Document document;
    private String filePath;

    public FundDom(String filePath) {
        this.document = getDocument(filePath);
        this.filePath = filePath;
    }

    private void addElement(Element parent,Fund fund){
        Element element = parent.addElement("fund");

        Field[] fundFields = fund.getClass().getDeclaredFields();

        for (int i = 0; i < fundFields.length; i++) {
            String fieldName = fundFields[i].getName();
            Object result = getFieldValue(fund, fieldName);
            String res = result != null ? result.toString() : "";
            element.addElement(fieldName).addText(res + "");
        }
    }

    public void addFunds(List<Fund> funds){
        Iterator<Fund> it = funds.iterator();
        while (it.hasNext()) {
            addFund(it.next());
        }
        write(document);
    }

    public void addFund(Fund fund) {
        Element parent = document.getRootElement();
        addElement(parent, fund);
        write(document);
    }

    public void createXml(String filePath) {
        document.clearContent();
        addRootElem(document);
        write(document);
    }


    public List<?> parseXml() {
        List<Fund> resultFundList = new ArrayList<Fund>();
        Element fundsElem = document.getRootElement();
        List<Element> fundList = fundsElem.elements();
        Iterator<Element> it = fundList.iterator();
        while (it.hasNext()) {
            Element fund = it.next();
            Iterator<Element> fundIt = fund.elementIterator();
            Fund f = new Fund();
            while (fundIt.hasNext()) {
                Element fundEle = fundIt.next();
                setFieldValue(f, fundEle.getName(), fundEle.getTextTrim());

            }
            resultFundList.add(f);
        }

        return resultFundList;
    }

    private Document getDocument(String filePath) {
        Document dom = null;
        File xmlFile = new File(filePath);
        if (xmlFile.exists()) {
            SAXReader reader = new SAXReader();
            try {
                dom = reader.read(xmlFile);
            } catch (DocumentException e) {
                e.printStackTrace();
            }
        } else {
            dom = DocumentHelper.createDocument();
            dom = addRootElem(dom);
        }

        return dom;
    }

    public Document addRootElem(Document document) {
        document.addElement("funds");
        return document;
    }

    public void write(Document document) {
        try {
            XMLWriter xmlWriter = new XMLWriter(new FileWriter(this.filePath));
            xmlWriter.write(document);
            xmlWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void save() {
        write(this.document);
    }

    private Object getFieldValue(Fund fund, String fieldName) {
        Object result = new Object();
        try {
            String methodName = "get" + StringUtils.firstLetter2UpperCase(fieldName);
            result = fund.getClass().getMethod(methodName).invoke(fund);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        return result;
    }

    private Fund setFieldValue(Fund fund, String fieldName, Object... args) {
        Object result = new Object();
        String methodName = "set" + StringUtils.firstLetter2UpperCase(fieldName);
        try {
            Field field = fund.getClass().getDeclaredField(fieldName);
            result = fund.getClass().getMethod(methodName, field.getType()).invoke(fund, args);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return fund;
    }
}
