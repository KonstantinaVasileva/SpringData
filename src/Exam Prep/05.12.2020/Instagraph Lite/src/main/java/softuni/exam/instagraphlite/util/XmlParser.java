package softuni.exam.instagraphlite.util;

import javax.xml.bind.JAXBException;

public interface XmlParser {
    <E> E fromFile(String file, Class<E> eClass) throws JAXBException;
}
