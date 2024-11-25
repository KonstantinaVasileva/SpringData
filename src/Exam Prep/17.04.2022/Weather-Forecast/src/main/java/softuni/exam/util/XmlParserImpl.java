package softuni.exam.util;

import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;

@Component
public class XmlParserImpl implements XmlParser {

    @Override
    public <E> E fromFile(String filePath, Class<E> eClass) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(eClass);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        return (E) unmarshaller.unmarshal(new File(filePath));
    }
}
