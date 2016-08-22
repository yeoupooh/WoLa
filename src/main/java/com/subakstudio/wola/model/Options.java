package com.subakstudio.wola.model;

import com.fasterxml.jackson.annotation.JsonAnySetter;

import java.util.*;

/**
 * Created by jinwoomin on 8/22/16.
 */
public class Options {
    private Map<String, String> map = new HashMap<String, String>();

    @JsonAnySetter
    public void setDynamicProperty(String key, String value) {
        map.put(key, value);
    }

    public String getValue(String key) {
        return map.get(key);
    }

    @Override
    public String toString() {
        List<CharSequence> kvs = new ArrayList<CharSequence>();
        for (String key : map.keySet()) {
            kvs.add(String.format("%s=[%s]", key, map.get(key)));
        }

        return String.format("%s{%s}", getClass().getSimpleName(), String.join(",", kvs));
    }
}
