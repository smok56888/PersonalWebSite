package com.smok.web.utils;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import java.io.File;

/**
 * Created by smok on 2015/12/28.
 */
public class XmlUtil {

    public static Document parseDocument(String text) throws Exception {
        return DocumentHelper.parseText(text);
    }

    public static Document loadDocument(String path) throws Exception {
        File file = new File(path);
        if (file.exists()) {
            SAXReader reader = new SAXReader();
            return reader.read(file);
        }
        return null;
    }

    public static Document loadDocument(File file) throws Exception {
        if (file.exists()) {
            SAXReader reader = new SAXReader();
            return reader.read(file);
        }
        return null;
    }

    public static Element selectSingleNode(Document doc, String xpath) {
        return (Element) doc.selectSingleNode(xpath);
    }

    public static String selectSingleNodeValue(Document doc, String xpath) {
        Node node = doc.selectSingleNode(xpath);
        if (node != null)
            return node.getText();
        return null;
    }

}
