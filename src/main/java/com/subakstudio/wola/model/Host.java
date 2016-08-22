package com.subakstudio.wola.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by jinwoomin on 8/22/16.
 */
public class Host {
    @JsonProperty
    String type;
    @JsonProperty
    public
    String name;
    @JsonProperty
    public
    Options options;

    @Override
    public String toString() {
        return String.format("%s{type=[%s],name=[%s],options=[%s]}",
                getClass().getSimpleName(),
                type,
                name,
                options
        );
    }
}
