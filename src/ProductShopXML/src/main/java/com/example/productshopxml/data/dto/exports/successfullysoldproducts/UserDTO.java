package com.example.productshopxml.data.dto.exports.successfullysoldproducts;

import jakarta.xml.bind.annotation.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@XmlRootElement(name = "user")
@XmlAccessorType(XmlAccessType.FIELD)
@Setter
@Getter
public class UserDTO implements Serializable {
    @XmlAttribute(name = "first-name")
    private String firstName;
    @XmlAttribute(name = "last-name")
    private String lastName;
    @XmlElement(name = "sold-products")
    private SoldProductRootDTO soldProductRootDTO;
}
