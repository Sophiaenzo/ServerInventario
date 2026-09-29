/*
 * The MIT License
 *
 * Copyright 2023 wilson.simoes.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package model;

import factory.Conector;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import modelDominio.Te220ite;

/**
 *
 * @author wilson.simoes
 */
public class Te220iteDao {
    
     private Connection con;
    
    public Te220iteDao() throws ClassNotFoundException{
       con = Conector.getConnection();
    }
    
    public ArrayList<Te220ite> getListaSaldo(String cdata,String deposito) throws ClassNotFoundException, SQLException {        
           ArrayList<Te220ite> listaDados = new ArrayList<>(); 
           Statement stmt;
           try
           {                
                 stmt = con.createStatement();
                 
                  System.out.println("select - pegar dados com a data -> " + cdata);
                /*
                 String sql = "SELECT A.CODEMP codemp,A.DATINV datinv,A.CODDEP coddep,A.CODPRO codpro,A.QTDEST qtdest ,SUM(B.USU_QTDCON) qtdcon,(SUM(B.USU_QTDCON) - A.QTDEST) qtdsal "+
                              "from E220ITE A " +
                              "INNER JOIN USU_TE220CON B ON B.USU_CODEMP = A.CODEMP AND B.USU_DATINV = A.DATINV AND B.USU_CODPRO = A.CODPRO "+
                              "WHERE A.CODEMP = 3 AND A.DATINV = '"+cdata+"' and B.USU_INDBIP = 'S'" +
                              "GROUP BY A.CODEMP,A.DATINV,A.CODDEP,A.CODPRO,A.QTDEST ";
                 */
                 String sql = "SELECT a.USU_CODEMP codemp,a.USU_DATINV datinv,a.usu_codpro codpro,a.usu_coddep coddep,B.QTDEST qtdest,sum(A.USU_QTDCON) qtdcon,(B.QTDEST - SUM(A.USU_QTDCON)) qtdsal "+
                              "FROM USU_TE220CON a "+
                              "INNER JOIN E220ite b ON b.codemp = a.USU_CODEMP AND b.CODPRO = a.USU_CODPRO AND b.CODDEP = a.usu_coddep and a.USU_DATINV = b.datinv "+
                              "WHERE a.USU_DATINV = '"+cdata+"' and a.USU_INDBIP = 'S' "+
                              "GROUP BY a.USU_CODEMP,a.USU_DATINV,a.usu_codpro,a.usu_coddep,B.QTDEST "+
                              "ORDER BY a.USU_CODPRO,a.usu_coddep ";

                 
                          
                 ResultSet rs = stmt.executeQuery(sql);
                 //System.out.println(deposito);
                 while(rs.next()){
                     Te220ite items = new Te220ite(rs.getDate("datinv"),
                                                   rs.getString("coddep"),
                                                   rs.getString("codpro"),
                                                   rs.getFloat("qtdest"),
                                                   rs.getFloat("qtdcon"),
                                                   rs.getFloat("qtdsal"));
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
