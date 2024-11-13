package com.example.json.data.dtos.imports;

import com.google.gson.annotations.Expose;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

@Setter
@Getter
public class ProductSeedDTO implements Serializable {

    @Expose
    @Size(min = 3)
    private String name;

    @Expose
    private BigDecimal price;

}
