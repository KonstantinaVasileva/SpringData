package softuni.exam.models.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "offers")
@XmlAccessorType(XmlAccessType.FIELD)
public class OfferSeedRootDTO {
    @XmlElement(name = "offer")
    private List<OfferSeedDTO> offerSeedDTOList;

    public List<OfferSeedDTO> getOfferSeedDTOList() {
        return offerSeedDTOList;
    }

    public void setOfferSeedDTOList(List<OfferSeedDTO> offerSeedDTOList) {
        this.offerSeedDTOList = offerSeedDTOList;
    }
}
