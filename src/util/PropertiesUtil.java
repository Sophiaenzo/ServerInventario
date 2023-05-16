package util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


/**
 *
 * @author wilson.simoes
 */
public class PropertiesUtil {
 private static Properties props = null;
 
    private static Properties getProperties() throws IOException {
       if(props == null){
           try (InputStream i = PropertiesUtil.class.getResourceAsStream("/config.properties")) {
               props = new Properties();
               props.load(i);
           }
       }
       return props;
    }
 
    public static String getProperty(String chave){ 
        try {
            return getProperties().getProperty(chave); 
        } catch (Exception e) { 
            e.printStackTrace(); 
            return null;
        }
    } 
    
     
 
}