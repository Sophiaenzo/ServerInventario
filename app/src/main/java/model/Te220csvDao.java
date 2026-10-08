package model;

import factory.Conector;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import modelDominio.Te220csv;
import util.ExportarCSV;
import static util.Funcoes.formatarFloat0;
import static util.Funcoes.lpad;
import static util.Funcoes.parteData;
import static util.Funcoes.rpad;
/**
 *
 * @author wilson
 */
public class Te220csvDao {
     private Connection con;
     
     public Te220csvDao()throws ClassNotFoundException{
        con = Conector.getConnection();
    }
    
public ArrayList<Te220csv> getListaDados(String cdata, String deposito) throws ClassNotFoundException, SQLException {        
           ArrayList<Te220csv> listaDados = new ArrayList<>(); 
           Statement stmt;
           SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");
           try
           {              
                 stmt = con.createStatement();
                 String sql = "select * from USU_TE220con where USU_DATINV = '" + cdata +"' and USU_CODDEP = '" + deposito + "'" ;
                 ResultSet rs = stmt.executeQuery(sql);
                 /*
                 String cCaminho = "\\\\hefesto\\Inventario.Estoque\\arq_txt_2022\\";
                 String cDia = parteData(cdata,"D");
                 String cMes = parteData(cdata,"M");
                 String cAno = parteData(cdata,"A");
                 String url = cCaminho + "I" + cDia + cMes + cAno + deposito +".csv";                 
                 
                 ExportarCSV dd = new ExportarCSV();
                 dd.Exportaccc("USU_CODEMP","USU_DATINV","USU_CODDEP","USU_CODPRO","USU_QTDCON","USU_USUCON", "USU_DATCON",url);
                 */
                 
                 while(rs.next()){       
                     /*
                      String colu101 = lpad(rs.getString("USU_CODEMP"),"0",4);
                      String colu102 = formatador.format(rs.getDate("USU_DATINV"));
                      String colu103 = rpad(rs.getString("USU_CODDEP")," ",10); 
                      String colu104 = rpad(rs.getString("USU_CODPRO")," ",14);
                      String colu105 = formatarFloat0(rs.getFloat("USU_QTDCON"));                     
                      String colu106 = lpad(rs.getString("USU_USUCON"),"0",7);
                      String colu107 = formatador.format(rs.getDate("USU_DATCON"));
                                            
                      dd.Exportaccc(colu101,colu102,colu103,colu104,colu105,colu106,colu107,url);
                      */
                      
                      
                      Te220csv items = new Te220csv(rs.getInt("USU_codemp"),
                                                    rs.getDate("USU_datinv"),                    
                                                    rs.getString("USU_coddep"),
                                                    rs.getString("USU_codpro"),
                                                    rs.getString("USU_codder"),
                                                    rs.getInt("USU_NUMCON"),
                                                    rs.getFloat("USU_qtdcon"),
                                                    rs.getInt("USU_usucon"),
                                                    rs.getDate("USU_datcon"),
                                                    rs.getString("USU_ETIQUETA"),
                                                    rs.getString("USU_INDBIP"),
                                                    rs.getString("USU_OBSBIP"));
                      
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
