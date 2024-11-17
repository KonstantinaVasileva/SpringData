package com.example.productshopxml.data.dto.exports.categoriesbyproductscount;

import jakarta.xml.bind.annotation.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

@XmlRootElement(name = "category")
@XmlAccessorType(XmlAccessType.FIELD)
@Setter
@Getter
public class CategoryDTO implements Serializable {
    @XmlAttribute
    private String name;
    @XmlElement(name = "products-count")
    private int productsCount;
    @XmlElement(name = "average-price")
    private BigDecimal avrPrice;
    @XmlElement(name = "total-revenue")
    private BigDecimal totalRevenue;
}
