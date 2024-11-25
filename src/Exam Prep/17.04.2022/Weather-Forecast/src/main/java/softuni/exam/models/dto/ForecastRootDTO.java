package softuni.exam.models.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "forecasts")
@XmlAccessorType(XmlAccessType.FIELD)
public class ForecastRootDTO {
    @XmlElement(name = "forecast")
    private List<ForecastDTO> forecastDTOList;

    public List<ForecastDTO> getForecastDTOList() {
        return forecastDTOList;
    }

    public void setForecastDTOList(List<ForecastDTO> forecastDTOList) {
        this.forecastDTOList = forecastDTOList;
    }
}
