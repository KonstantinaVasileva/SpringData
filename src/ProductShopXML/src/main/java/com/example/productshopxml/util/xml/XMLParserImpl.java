package com.example.productshopxml.util.xml;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class XMLParserImpl implements XMLParser{
    @Override
    public <E> E parse(Class<E> clazz, String path) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(clazz);

        Unmarshaller unmarshaller = context.createUnmarshaller();
        return (E) unmarshaller.unmarshal(new File(path));
    }

    @Override
    public <E> void exportToFile(Class<E> clazz, E object, String path) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(clazz);

        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(object, new File(path));
    }

    @Override
    public <E> void exportToFile(E object, String path) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(object.getClass());

        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(object, new File(path));
    }
}
