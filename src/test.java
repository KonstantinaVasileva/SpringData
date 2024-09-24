import java.util.Properties;

public class test {
    public static void main(String[] args) {
        Properties properties = new Properties();
        properties.setProperty("username", "myUser");
        properties.setProperty("password", "myPass");

        for(String key : properties.stringPropertyNames()) {
            String value = properties.getProperty(key);
            System.out.println("Key : " + key + ", Value : " + value);
        }
    }
}