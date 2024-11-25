package softuni.exam.util;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LocalDateAdaptor extends XmlAdapter<String, LocalDate> {
    @Override
    public LocalDate unmarshal(String string) throws Exception {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return LocalDate.parse(string, formatter);
    }

    @Override
    public String marshal(LocalDate date) throws Exception {
        return date.toString();
    }
}
