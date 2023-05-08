package model;

import factory.Conector;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import modelDominio.Te220con;

/**
 *
 * @author wilson
 */
public class Te220conDao {
    
    private Connection con;
    
    public Te220conDao() throws ClassNotFoundException{
       con = Conector.getConnection();
    }
    
    public ArrayList<Te220con> getListaDados(String cdata) throws ClassNotFoundException, SQLException {        
           ArrayList<Te220con> listaDados = new ArrayList<>(); 
           Statement stmt;
          
           try
           {              
                 stmt = con.createStatement();
                 String sql = "select * from USU_TE220con where USU_DATINV = '" + cdata +"'" ;
                 ResultSet rs = stmt.executeQuery(sql);
                 
                 while(rs.next()){
                     Te220con items = new Te220con(rs.getString("USU_CODDEP"),
                                                   rs.getString("USU_CODPRO"),
                                                    rs.getString("USU_ETIQUETA"), 
                                                   rs.getFloat("USU_QTDCON"),
                                                    rs.getDate("USU_DATCON"),
                                                   rs.getInt("USU_USUCON"),                                                                                                                                                      
                                                   rs.getString("USU_INDBIP"),        
                                                   rs.getString("USU_OBSBIP"));
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

