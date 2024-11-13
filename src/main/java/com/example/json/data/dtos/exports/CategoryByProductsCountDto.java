package com.example.json.data.dtos.exports;

import com.google.gson.annotations.Expose;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
public class CategoryByProductsCountDto implements Serializable {
    @Expose
    private String name;
    @Expose
    private int productCount;
    @Expose
    private BigDecimal avrPrice;
    @Expose
    private BigDecimal totalRevenue;


}
