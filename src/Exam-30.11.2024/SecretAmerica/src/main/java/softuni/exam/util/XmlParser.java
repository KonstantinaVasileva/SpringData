package softuni.exam.util;

import jakarta.xml.bind.JAXBException;

public interface XmlParser {
    <E> E fromFile(String file, Class<E> eClass) throws JAXBException;
}
