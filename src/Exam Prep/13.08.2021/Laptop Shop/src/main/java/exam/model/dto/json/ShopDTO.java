package exam.model.dto.json;

import com.google.gson.annotations.Expose;

public class ShopDTO {
    @Expose
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
