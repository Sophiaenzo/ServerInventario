/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import factory.Conector;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import modelDominio.Te220cons;

/**
 *
 * @author wilson.simoes
 */
public class Te220consDao {
    private Connection con;
    
    public Te220consDao() throws ClassNotFoundException{
       con = Conector.getConnection();
    }
    
     public ArrayList<Te220cons> getContagem(String cdata, String cdeposito) throws ClassNotFoundException, SQLException {        
            ArrayList<Te220cons> listaDados = new ArrayList<>(); 
           Statement stmt;
           try
           {               
           
                
                 stmt = con.createStatement();
                 String sql = "select max(USU_NUMCON) USU_NUMCON from USU_TE220con where USU_CODEMP = 3 AND USU_DATINV = '" + cdata + "' AND USU_CODDEP = '" + cdeposito.trim() + "'";
                 ResultSet rs = stmt.executeQuery(sql);  
              
                 while(rs.next()){
                  
                     
                     Te220cons items = new Te220cons(rs.getInt("USU_NUMCON"));
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
      

    
    
    
}
