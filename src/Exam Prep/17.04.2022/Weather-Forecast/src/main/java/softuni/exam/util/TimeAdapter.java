package softuni.exam.util;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.time.LocalTime;

public class TimeAdapter extends XmlAdapter <String, LocalTime>{
    @Override
    public LocalTime unmarshal(String string) throws Exception {
        return LocalTime.parse(string);
    }

    @Override
    public String marshal(LocalTime localTime) throws Exception {
        return localTime.toString();
    }
}
