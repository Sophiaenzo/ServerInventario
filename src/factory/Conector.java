package factory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import util.Funcoes;

/**
 * classe responsavel pela conexao com o banco de dados
 * @author wilson.simoes
 * @param <AjaxBehaviorEvent>
 */
public class Conector<AjaxBehaviorEvent> {
         // sql = Connection, Statement, ResultSet
    
       //private static Connection conn;
            Funcoes funcoes = new Funcoes(); 
            
            /**
             * responsavel pela conexao ao banco de dados
             * @return
             * @throws ClassNotFoundException 
             */
        public static Connection getConnection() throws ClassNotFoundException{
                Funcoes funcoes = new Funcoes();  
                // Configuração dos parâmetros de conexão
                String server   = funcoes.GetProp("banco.server","N");
                String port     = funcoes.GetProp("banco.porta","N");
                String database = funcoes.GetProp("banco.database","N");
                // Configuração dos parâmetros de autenticação
                String user     = funcoes.GetProp("banco.usuario","N");
                String passwd   = funcoes.GetProp("banco.senha","S");
                String banco    = funcoes.GetProp("banco.instancia","N");
                String url = "jdbc:oracle:thin:@" + server + ":" + port + ":" + database;
            
                
                
        try{     
                Connection conn = DriverManager.getConnection(url, user, passwd);

                Class.forName("oracle.jdbc.driver.OracleDriver");        
                conn.createStatement().execute("alter session set nls_date_format='dd/mm/yyyy hh24:mi:ss'");
                
                 if(banco != null && !banco.isEmpty() ){   
                    conn.createStatement().execute("alter session set current_schema="+banco);
                 }
                //System.out.println("conectado ao banco");

                return conn;
        }catch(SQLException ex){
                System.out.println("Erro no sistema -> " + ex.getMessage());
        }           
        return null;
    } 
        
 
       
    public static void close(Connection connection){
         try {
           if(connection != null)  
                connection.close();
       } catch (SQLException ex) {
           ex.printStackTrace();
       }
    } 
    
    public static void close(Connection connection, Statement stmt){
        close(connection);
        try {
           if(stmt != null)  
                stmt.close();
       } catch (SQLException ex) {
           ex.printStackTrace();
       }
    } 
    
   public static void close(Connection connection, Statement stmt, ResultSet rs){
        close(connection, stmt);
        try {
           if(rs != null)  
                rs.close();
       } catch (SQLException ex) {
           ex.printStackTrace();
       }
    } 
     
    
}
