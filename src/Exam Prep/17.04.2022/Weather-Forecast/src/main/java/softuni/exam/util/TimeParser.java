//package softuni.exam.util;
//
//import org.springframework.core.convert.converter.Converter;
//
//import java.sql.Time;
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//
//public class TimeParser implements Converter<String, Time> {
//
//    private static final String TIME_PATTERN = "HH:mm:ss";
//
//    @Override
//    public Time convert(String source) {
//        if (source.trim().isEmpty()) {
//            return null;
//        }
//        try {
//            SimpleDateFormat formatter = new SimpleDateFormat(TIME_PATTERN);
//            formatter.setLenient(false);
//            long milliseconds = formatter.parse(source).getTime();
//            return new Time(milliseconds);
//        } catch (ParseException e) {
//            throw new IllegalArgumentException("Invalid time format. Expected format: " + TIME_PATTERN, e);
//        }
//    }
//}
//
