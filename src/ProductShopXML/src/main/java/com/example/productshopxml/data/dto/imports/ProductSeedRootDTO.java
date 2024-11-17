package com.example.productshopxml.data.dto.imports;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@XmlRootElement(name = "products")
@XmlAccessorType(XmlAccessType.FIELD)
@Setter
@Getter
public class ProductSeedRootDTO implements Serializable {

    @XmlElement(name = "product")
    private List<ProductSeedDTO> productDTOS;
}
