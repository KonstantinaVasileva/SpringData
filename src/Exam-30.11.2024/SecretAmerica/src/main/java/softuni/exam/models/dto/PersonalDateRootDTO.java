package softuni.exam.models.dto;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.List;

@XmlRootElement(name = "personal_datas")
@XmlAccessorType(XmlAccessType.FIELD)
public class PersonalDateRootDTO {
    @XmlElement(name = "personal_data")
    private List<PersonalDateDTO> personalDateDTOList;

    public List<PersonalDateDTO> getPersonalDateDTOList() {
        return personalDateDTOList;
    }

    public void setPersonalDateDTOList(List<PersonalDateDTO> personalDateDTOList) {
        this.personalDateDTOList = personalDateDTOList;
    }
}
