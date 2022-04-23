package com.library.lendit_book_kiosk.Reports;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Report<T extends Map<String, Object>> implements Serializable {
    private static final Logger log  = LoggerFactory.getLogger(Report.class);
    T map = (T) new HashMap<String, Object>();
    Report(){}
    Report(T map){this.map = map;}
    Report(String key, Object value){
        map.put(key,value);
    }
    void set(String key, Object value){
        map.put(key,value);
    }
    void set(T map){
        this.map = map;;
    }
    Object get(String key){
        return this.map.get(key);
    }
    T getMap(){
        return this.map;
    }
}
