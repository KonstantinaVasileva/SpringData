package com.example.football.models.dto.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "stats")
@XmlAccessorType(XmlAccessType.FIELD)
public class StatSeedRootDTO {
    @XmlElement(name = "stat")
    private List<StatSeedDTO> statSeedDTOList;

    public List<StatSeedDTO> getStatSeedDTOList() {
        return statSeedDTOList;
    }

    public void setStatSeedDTOList(List<StatSeedDTO> statSeedDTOList) {
        this.statSeedDTOList = statSeedDTOList;
    }
}
