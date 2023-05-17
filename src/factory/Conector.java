package factory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import util.PropertiesUtil;

/**
 * classe responsavel pela conexao com o banco de dados
 * @author wilson.simoes
 * @param <AjaxBehaviorEvent>
 */
public class Conector<AjaxBehaviorEvent> {
       private static Connection conn;
                 
       /**
        * responsavel pela conexao ao banco de dados
        * @return
        * @throws ClassNotFoundException 
        */
       public static Connection getConnection() throws ClassNotFoundException{
        try{   
        // Configuração dos parâmetros de conexão
        String server   = PropertiesUtil.getProperty("banco.server");
        String port     = PropertiesUtil.getProperty("banco.porta");
        String database = PropertiesUtil.getProperty("banco.database");
        // Configuração dos parâmetros de autenticação
        String user     = PropertiesUtil.getProperty("banco.usuario");
        String passwd   = PropertiesUtil.getProperty("banco.senha");
        String banco    = PropertiesUtil.getProperty("banco.instancia");
      
        // Configuração dos parâmetros de conexão
        //server   = "192.168.0.250";
        //port     = "1521";
        //database = "ora10g";
        // Configuração dos parâmetros de autenticação
        //user     = "ftbtra";
        //passwd   = "ftbtra";
        //banco  = "teste";
        
        
        Class.forName("oracle.jdbc.driver.OracleDriver");        
        String url = "jdbc:oracle:thin:@" + server + ":" + port + ":" + database;	  
        conn = DriverManager.getConnection(url, user, passwd);
         if(banco != null && !banco.isEmpty() ){   
            conn.createStatement().execute("alter session set current_schema="+banco);
         }
        System.out.println("conectado ao banco");
        
        return conn;
        }catch(SQLException ex){
            System.out.println("Erro no sistema -> " + ex.getMessage());
            return null;
        }           
       }

    
}
