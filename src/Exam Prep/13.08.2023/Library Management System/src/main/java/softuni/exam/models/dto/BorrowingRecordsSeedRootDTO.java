package softuni.exam.models.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "borrowing_records")
@XmlAccessorType(XmlAccessType.FIELD)
public class BorrowingRecordsSeedRootDTO {
    @XmlElement(name = "borrowing_record")
    public List<BorrowingRecordsSeedDTO> borrowingRecordsSeedDTOList;

    public List<BorrowingRecordsSeedDTO> getBorrowingRecordsSeedDTOList() {
        return borrowingRecordsSeedDTOList;
    }

    public void setBorrowingRecordsSeedDTOList(List<BorrowingRecordsSeedDTO> borrowingRecordsSeedDTOList) {
        this.borrowingRecordsSeedDTOList = borrowingRecordsSeedDTOList;
    }
}
