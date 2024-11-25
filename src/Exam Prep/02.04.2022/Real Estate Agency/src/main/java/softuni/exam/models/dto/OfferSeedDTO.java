package softuni.exam.models.dto;

import softuni.exam.util.LocalDateAdaptor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.math.BigDecimal;
import java.time.LocalDate;

@XmlRootElement(name = "offer")
@XmlAccessorType(XmlAccessType.FIELD)
public class OfferSeedDTO {
    @XmlElement
    @Positive
    private BigDecimal price;
    @XmlElement(name = "agent")
    private AgentDTO agent;
    @XmlElement(name = "apartment")
    private ApartmentDTO apartment;
    @XmlElement
    @NotNull
    @XmlJavaTypeAdapter(LocalDateAdaptor.class)
    private LocalDate publishedOn;

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public AgentDTO getAgent() {
        return agent;
    }

    public void setAgent(AgentDTO agent) {
        this.agent = agent;
    }

    public ApartmentDTO getApartment() {
        return apartment;
    }

    public void setApartment(ApartmentDTO apartment) {
        this.apartment = apartment;
    }

    public LocalDate getPublishedOn() {
        return publishedOn;
    }

    public void setPublishedOn(LocalDate publishedOn) {
        this.publishedOn = publishedOn;
    }
}
