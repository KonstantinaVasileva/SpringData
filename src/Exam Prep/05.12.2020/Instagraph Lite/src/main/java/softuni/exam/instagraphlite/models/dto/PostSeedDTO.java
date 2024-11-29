package softuni.exam.instagraphlite.models.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "post")
@XmlAccessorType(XmlAccessType.FIELD)
public class PostSeedDTO {
    @XmlElement
    @NotNull
    @Size(min = 21)
    private String caption;
    @XmlElement(name = "user")
    @NotNull
    private UserDTO userDTO;
    @XmlElement(name = "picture")
    @NotNull
    private PictureDTO pictureDTO;

    public PictureDTO getPictureDTO() {
        return pictureDTO;
    }

    public void setPictureDTO(PictureDTO pictureDTO) {
        this.pictureDTO = pictureDTO;
    }

    public UserDTO getUserDTO() {
        return userDTO;
    }

    public void setUserDTO(UserDTO userDTO) {
        this.userDTO = userDTO;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }
}
