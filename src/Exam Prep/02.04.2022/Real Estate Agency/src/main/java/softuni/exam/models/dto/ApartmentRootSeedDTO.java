package softuni.exam.models.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "apartments")
@XmlAccessorType(XmlAccessType.FIELD)
public class ApartmentRootSeedDTO {
    @XmlElement(name = "apartment")
    private List<ApartmentSeedDTO> apartmentSeedDTOList;

    public List<ApartmentSeedDTO> getApartmentSeedDTOList() {
        return apartmentSeedDTOList;
    }

    public void setApartmentSeedDTOList(List<ApartmentSeedDTO> apartmentSeedDTOList) {
        this.apartmentSeedDTOList = apartmentSeedDTOList;
    }
}
