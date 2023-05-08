/*
 * The MIT License
 *
 * Copyright 2023 wilson.
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
import modelDominio.R999usu;
/**
 *
 * @author wilson
 */
public class R999usuDao {
    private Connection con;
    
    
    public R999usuDao() throws ClassNotFoundException{
       con = Conector.getConnection();
}
    
    
public ArrayList<R999usu> getListaUsu(String cCodigo) throws ClassNotFoundException, SQLException {        
           ArrayList<R999usu> listaDados = new ArrayList<>(); 
           Statement stmt;
          
           try
           {              
                 stmt = con.createStatement();
                 String sql = "select * from R999usu where NOMUSU = '" + cCodigo +"'" ;
                 ResultSet rs = stmt.executeQuery(sql);
                 System.out.println("variavel a procurar " + cCodigo);
                 while(rs.next()){
                     System.out.println("usuario " + rs.getString("NOMUSU" ));
                     System.out.println("codigo  " + rs.getString("CODUSU"));
                     R999usu items = new R999usu(rs.getString("CODUSU"),
                                                 rs.getString("NOMUSU"));
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
