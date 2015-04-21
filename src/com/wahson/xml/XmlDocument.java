package com.wahson.xml;

import java.util.List;

/**
 * Created by wahsonleung on 15/4/13.
 */
public interface XmlDocument {

    public void createXml(String fileName);

    public List<?> parseXml(String fileName);
}
