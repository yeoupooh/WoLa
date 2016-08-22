package com.subakstudio.wola.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jinwoomin on 8/22/16.
 */
public class WolaConfig {
    @JsonProperty
    public
    List<Host> hosts = new ArrayList<Host>();

    @Override
    public String toString() {
        return String.format("%s{hosts={%s}}", this.getClass().getSimpleName(), hosts);
    }
}
