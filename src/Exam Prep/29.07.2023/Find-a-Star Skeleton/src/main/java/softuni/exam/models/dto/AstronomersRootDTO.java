package softuni.exam.models.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "astronomers")
@XmlAccessorType(XmlAccessType.FIELD)
public class AstronomersRootDTO {
    @XmlElement(name = "astronomer")
    private List<AstronomersDTO> astronomersDTOList;

    public List<AstronomersDTO> getAstronomersDTOList() {
        return astronomersDTOList;
    }

    public void setAstronomersDTOList(List<AstronomersDTO> astronomersDTOList) {
        this.astronomersDTOList = astronomersDTOList;
    }
}
