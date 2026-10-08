package model;

import factory.Conector;
import static java.lang.System.out;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import modelDominio.Te220txt;
import util.ExportarTXT;
import static util.Funcoes.formatarFloat;
import static util.Funcoes.lpad;
import static util.Funcoes.parteData;
import static util.Funcoes.rpad;

/**
 *
 * @author wilson
 */
public class Te220txtDao{
    private Connection con;
    private String cPadrao = "";
    public Te220txtDao()throws ClassNotFoundException
    {
        con = Conector.getConnection();
    }
    
     public ArrayList<Te220txt> getListaDados(String cdata, String deposito) throws ClassNotFoundException, SQLException {        
           ArrayList<Te220txt> listaDados = new ArrayList<>(); 
           Statement stmt;
           SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");
           try
           {              
                 stmt = con.createStatement();
                 String sql = "select * from USU_TE220con where USU_INDBIP = 'S' AND USU_DATINV = '" + cdata +"' and USU_CODDEP = '" + deposito + "'" ;
                 ResultSet rs = stmt.executeQuery(sql);
//                 String cCaminho = "\\\\hefesto\\Inventario.Estoque\\arq_txt_2022\\";
//                 String cDia = parteData(cdata,"D");
//                 String cMes = parteData(cdata,"M");
//                 String cAno = parteData(cdata,"A");
//                 String url = cCaminho + "I" + cDia + cMes + cAno + deposito +".txt";
//                 int i = 0;
                 while(rs.next()){
                      
 //                    cPadrao = "";
 //                    if(deposito.equals("DEPGERAL") || deposito.equals("DEPKANBAN"))
 //                    {
 //                        cPadrao = "";
 //                    } else {
 //                        cPadrao = "PADRAO";
 //                    }                     
  //                    i++;
                      
                     
                      Te220txt items = new Te220txt(rs.getInt("USU_codemp"),
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

