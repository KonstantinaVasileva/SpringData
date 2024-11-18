package softuni.exam.models.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.JsonAdapter;
import softuni.exam.util.adapter.LocalDateTimeAdapter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

public class SaleDTO {
    @Expose
    private boolean discounted;
    @Expose
    @NotNull
    @Size(min = 7, max = 7)
    private String number;
    @Expose
    @NotNull
    @JsonAdapter(LocalDateTimeAdapter.class)
    private LocalDateTime saleDate;
    @Expose
    private long seller;

    public boolean isDiscounted() {
        return discounted;
    }

    public void setDiscounted(boolean discounted) {
        this.discounted = discounted;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber( String number) {
        this.number = number;
    }

    public LocalDateTime getSaleDate() {
        return saleDate;
    }

    public void setSaleDate(LocalDateTime saleDate) {
        this.saleDate = saleDate;
    }

    public long getSeller() {
        return seller;
    }

    public void setSeller(long seller) {
        this.seller = seller;
    }
}
