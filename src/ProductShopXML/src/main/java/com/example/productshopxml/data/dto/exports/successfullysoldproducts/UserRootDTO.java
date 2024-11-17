package com.example.productshopxml.data.dto.exports.successfullysoldproducts;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@XmlRootElement(name = "users")
@XmlAccessorType(XmlAccessType.FIELD)
@Setter
@Getter
public class UserRootDTO implements Serializable {
    @XmlElement(name = "user")
    private List<UserDTO> userDTOList;
}