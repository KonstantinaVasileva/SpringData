package softuni.exam.util;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class XmlParserImpl implements XmlParser {
    @Override
    public <E> E fromFile(String file, Class<E> eClass) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(eClass);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        return (E) unmarshaller.unmarshal(new File(file));
    }
}
