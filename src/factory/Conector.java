package factory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import util.Funcoes;

/**
 * classe responsavel pela conexao com o banco de dados
 * @author wilson.simoes
 * @param <AjaxBehaviorEvent>
 */
public class Conector<AjaxBehaviorEvent> {
       private static Connection conn;
       Funcoes funcoes = new Funcoes();
                 
       /**
        * responsavel pela conexao ao banco de dados
        * @return
        * @throws ClassNotFoundException 
        */
       public static Connection getConnection() throws ClassNotFoundException{
        try{   
        // Configuração dos parâmetros de conexão
        String server   = Funcoes.GetProp("banco.server");
        String port     = Funcoes.GetProp("banco.porta");
        String database = Funcoes.GetProp("banco.database");
        // Configuração dos parâmetros de autenticação
        String user     = Funcoes.GetProp("banco.usuario");
        String passwd   = Funcoes.GetProp("banco.senha");
        String banco    = Funcoes.GetProp("banco.instancia");
       /* 
        // Configuração dos parâmetros de conexão
        String server   = "192.168.0.8";
        String port     = "1521";
        String database = "XE";
        // Configuração dos parâmetros de autenticação
        String user     = "SYSTEM";
        String passwd   = "Tramar@2022";
        String banco  = ""; //Configs.GetProp("Banco");
         
        */
        
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
