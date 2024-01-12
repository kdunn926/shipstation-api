package com.apex.shipstation.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class Weight {

    private Long value;
    private String units;
    private Long WeightUnits;

}
