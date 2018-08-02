package com.grape.supermarket.common.util;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * Created by LXP on 2017/8/3.
 * xml解析工具
 */

public class XmlUtils {

    /**
     * xml转换成map，xml中的属性会带有@
     *
     * @param xml xml
     * @return 转换好的map
     */
    public static Map<String, Object> toMap(String xml,String charset) {
        SAXReader reader = new SAXReader();
        try {
            XMLReader xmlReader = reader.getXMLReader();
            xmlReader.setFeature("http://xml.org/sax/features/external-general-entities",false);
            xmlReader.setFeature("http://xml.org/sax/features/external-parameter-entities",false);
        } catch (SAXException e) {
            throw new RuntimeException();
        }
        try {
            ByteArrayInputStream inputStream = new ByteArrayInputStream(xml.getBytes(charset));
            Document document = reader.read(inputStream);

            Map<String, Object> data = new HashMap<>();
            Element rootElement = document.getRootElement();
            List<Attribute> attributes = rootElement.attributes();
            for (Attribute attribute : attributes) {
                data.put("@" + attribute.getName(), attribute.getValue());
            }
            boolean needValue = true;
            Iterator<Element> iterator = rootElement.elementIterator();
            while (iterator.hasNext()) {
                needValue = false;
                Element next = iterator.next();
                Object nextValue = toMap(next);
                addValue(data,next.getName(),nextValue);
//                if (data.containsKey(next.getName())) {
//                    Object o = data.get(next.getName());
//
//                    if(o instanceof List){
//                        ((List) o).add(nextValue);
//                    }else{
//                        List list = new ArrayList();
//                        list.add(o);
//                        list.add(nextValue);
//                        data.put(next.getName(), list);
//                    }
//                } else {
//                    data.put(next.getName(), nextValue);
//                }
            }

            if (needValue) {
                data.put(rootElement.getName(), rootElement.getStringValue());
            }

            return data;
        } catch (DocumentException | UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    private static Object toMap(Element rootElement) {
        Map<String, Object> dataMap = new HashMap<>();
        String name = rootElement.getName();

        //添加属性
        List<Attribute> attributes = rootElement.attributes();
        for (Attribute attribute : attributes) {
            dataMap.put("@" + attribute.getName(), attribute.getValue());
        }

//        Map<String, Object> tempMap = new HashMap<>();
        Iterator<Element> iterator = rootElement.elementIterator();
        while (iterator.hasNext()) {
            Element childElement = iterator.next();
            String childName = childElement.getName();
            Object childData = toMap(childElement);

            int type = 0;
            if (childData instanceof Map) {
                Map<String, Object> m = ((Map) childData);
                for (Map.Entry<String, Object> stringObjectEntry : m.entrySet()) {
                    if (stringObjectEntry.getKey().startsWith("@")) {
                        type = 1;
                    } else {
                        type = 2;
                        break;
                    }
                }
            }

            Object childValue;
            if (type == 0) {//没有下级子元素
                childValue = childElement.getStringValue();
            } else if (type == 1) {
                ((Map) childData).put(childName, childElement.getStringValue());
                childValue = childData;
            } else {
                childValue = childData;
            }

            addValue(dataMap,childName,childValue);
//            if (tempMap.containsKey(childName)) {
//                Object o = tempMap.get(childName);
//                if (o instanceof List) {
//                    ((List) o).add(childValue);
//                } else {
//                    List list = new ArrayList();
//                    list.add(o);
//                    list.add(childValue);
//                    tempMap.put(childName, list);
//                }
//            } else {
//                tempMap.put(childName, childValue);
//            }
        }

        int type = 0;
        for (Map.Entry<String, Object> stringObjectEntry : dataMap.entrySet()) {
            if (stringObjectEntry.getKey().startsWith("@")) {
                type = 1;
            } else {
                type = 2;
                break;
            }
        }

        if (type == 0) {
            return rootElement.getStringValue();
        } else if (type == 2) {
            return dataMap;
        }else{
            dataMap.put(name, rootElement.getStringValue());
            return dataMap;
        }

    }

    private static void addValue(Map<String,Object> container,String key,Object value){
        if(container.containsKey(key)){
            Object existsObj = container.get(key);
            if(existsObj instanceof List){
                boolean needWarp = false;
                if(value instanceof Map){
                    needWarp = true;
                }
                if(!needWarp) {
                    for (Object o : ((List) existsObj)) {
                        if (o instanceof Map) {
                            needWarp = true;
                            break;
                        }
                    }
                }
                if(needWarp){
                    List temp = new ArrayList(((List) existsObj).size());
                    for (Object o : ((List) existsObj)) {
                        temp.add(warpMap(key,o));
                    }
                    ((List) existsObj).clear();
                    ((List) existsObj).addAll(temp);
                    ((List) existsObj).add(warpMap(key,value));
                }else {
                    ((List) existsObj).add(value);
                }
            }else{
                List list = new ArrayList();

                //检查需要升级为map
                boolean needWarp = false;
                if(value instanceof Map || existsObj instanceof Map){
                    needWarp = true;
                }
                if(needWarp){
                    value = warpMap(key,value);
                    existsObj = warpMap(key,existsObj);
                }

                list.add(existsObj);
                list.add(value);
                container.put(key,list);
            }
        }else{
            container.put(key,value);
        }
    }

    private static Object warpMap(String key,Object o){
        if(o != null && !(o instanceof Map)){
            Map m = new HashMap(3);
            m.put(key,o);
            return m;
        }

        return o;
    }

    /**
     * map转xml
     * @param map map
     * @param rootName 根节点名
     * @return xml
     */
    public static String toXml(Map map,String rootName){
        XmlMapper xml = new XmlMapper();
        StringWriter sw = new StringWriter();
        try {
            xml.writeValue(sw,new HashMap<>(map));
            return sw.toString().replace("HashMap",rootName).replaceAll("\\s*xmlns=\"\"\\s*","");
        } catch (IOException e) {
            return null;
        }
    }
}
