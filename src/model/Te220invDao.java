package model;

import factory.Conector;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelDominio.Te220inv;
import modelDominio.Te220sal;

/**
 *
 * @author wilson.simoes
 */
public class Te220invDao {
    
    private Connection con;
    private Connection conn;
    private boolean incluir;
    
    public Te220invDao() throws ClassNotFoundException{
       con = Conector.getConnection();
    }
    
    public ArrayList<Te220inv> getListaDados(String cdata) throws ClassNotFoundException, SQLException {        
           ArrayList<Te220inv> listaDados = new ArrayList<>(); 
           Statement stmt;
                   
           try
           {                
                 stmt = con.createStatement();
                 String sql = "select * from E220INV where datinv = '" + cdata +"'" ;
                 ResultSet rs = stmt.executeQuery(sql);
                 
                 while(rs.next()){
                     Te220inv items = new Te220inv(rs.getInt("CODEMP"),
                                                   rs.getDate("DATINV"),
                                                   rs.getString("CODDEP"));
                     listaDados.add(items);
                                                            
                 }
              
                rs.close();
                stmt.close();
                con.close();
                return listaDados;
                 
           }   
            catch (SQLException ErroSql)
           {
            System.out.println("Erro ao selecionar registros: " + ErroSql);
            return null;
           }    
            finally
           {
                
            }  
       
    }
    
    
    
    public ArrayList<Te220sal> getListaSaldo(String deposito, String cdata) throws ClassNotFoundException, SQLException {        
           ArrayList<Te220sal> listaDados = new ArrayList<>(); 
           Statement stmt;
           try
           {                
                 stmt = con.createStatement();
                
                  String sql = "SELECT USU_CODDEP deposito,USU_DATINV, " +
                          "SUM(CASE WHEN USU_INDBIP <> 'A' THEN 1 ELSE 0 END) esto, " +
                          "SUM(CASE WHEN USU_INDBIP = 'S' THEN 1 ELSE 0 END) certo, " +
                          "SUM(CASE WHEN USU_INDBIP = 'N' THEN 1 ELSE 0 END) errado " +
                          "FROM usu_te220con " +
                         "WHERE USU_CODDEP = '"+deposito+"' AND USU_DATINV = '"+cdata+"' " +       
                         "GROUP BY USU_CODDEP,USU_DATINV"  ;
                          
                 ResultSet rs = stmt.executeQuery(sql);
                 //System.out.println(deposito);
                 while(rs.next()){
                     Te220sal items = new Te220sal(rs.getString("deposito"),
                                                   rs.getFloat("esto"),
                                                   rs.getFloat("certo"),
                                                   rs.getFloat("errado"));
                     listaDados.add(items);
                     //System.out.println("Adicionando itens na lista " + items );
                                        
                 }
                 System.out.println("Terminou: servidor linha 49" );
                rs.close();
                stmt.close();
                con.close();
                return listaDados;
                 
           }   
            catch (SQLException ErroSql)
           {
            System.out.println("Erro ao selecionar registros: " + ErroSql);
            return null;
           }    
            finally
           {
                
          } 
       
    }
    
    
    
    
    
    
    
}
