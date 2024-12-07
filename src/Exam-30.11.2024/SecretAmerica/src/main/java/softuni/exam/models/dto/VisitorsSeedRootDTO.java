package softuni.exam.models.dto;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.List;

@XmlRootElement(name = "visitors")
@XmlAccessorType(XmlAccessType.FIELD)
public class VisitorsSeedRootDTO {
    @XmlElement(name = "visitor")
    private List<VisitorsSeedDTO> visitorsSeedDTOList;

    public List<VisitorsSeedDTO> getVisitorsSeedDTOList() {
        return visitorsSeedDTOList;
    }

    public void setVisitorsSeedDTOList(List<VisitorsSeedDTO> visitorsSeedDTOList) {
        this.visitorsSeedDTOList = visitorsSeedDTOList;
    }
}
