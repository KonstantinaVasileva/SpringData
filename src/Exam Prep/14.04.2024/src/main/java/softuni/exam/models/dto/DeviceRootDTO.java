package softuni.exam.models.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;

@XmlRootElement(name = "devices")
@XmlAccessorType(XmlAccessType.FIELD)
public class DeviceRootDTO implements Serializable {
    @XmlElement(name = "device")
    private List<DeviceDTO> deviceDTOList;

    public List<DeviceDTO> getDeviceDTOList() {
        return deviceDTOList;
    }

    public void setDeviceDTOList(List<DeviceDTO> deviceDTOList) {
        this.deviceDTOList = deviceDTOList;
    }
}
