package com.example.football.models.dto.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "players")
@XmlAccessorType(XmlAccessType.FIELD)
public class PlayerSeedRootDTO {
    @XmlElement(name = "player")
    private List<PlayerSeedDTO> playerSeedDTOS;

    public List<PlayerSeedDTO> getPlayerSeedDTOS() {
        return playerSeedDTOS;
    }

    public void setPlayerSeedDTOS(List<PlayerSeedDTO> playerSeedDTOS) {
        this.playerSeedDTOS = playerSeedDTOS;
    }
}
