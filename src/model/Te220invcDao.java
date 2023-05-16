package model;

import factory.Conector;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import modelDominio.Te220invc;

/**
 *
 * @author wilson.simoes
 */
public class Te220invcDao {
    
    private Connection con;
    private Connection conn;
    private boolean incluir;
    
    public Te220invcDao() throws ClassNotFoundException{
       con = Conector.getConnection();
    }
    
   public ArrayList<Te220invc> getLanca(String cdata) throws ClassNotFoundException, SQLException {        
           ArrayList<Te220invc> listaDados = new ArrayList<>(); 
           Statement stmt;
                   
                try
                     {                
                           stmt = con.createStatement();
                           String sql = "select * from E220INV where datinv = '" + cdata +"'" ;
                           ResultSet rs = stmt.executeQuery(sql);
                           while(rs.next())
                                  { 
                                     incluir = BuscaRegistro(cdata,rs.getString("CODDEP"));             
                                     if(incluir == true) 
                                         {  
                                             IncluiRegistro(cdata,rs.getString("CODDEP"));
                                         } 
                                  }
                          rs.close();
                          stmt.close();
                     }   
                         catch (SQLException ErroSql)
                     {
                        System.out.println("Erro ao selecionar registros: " + ErroSql);
                         return null;
                     }    
                         finally
                     {
                 }   
                
                
                try
                     {                
                           stmt = con.createStatement();
                           String sql = "select * from USU_TE220INV where USU_DATINV = '" + cdata +"'" ;
                           ResultSet rs = stmt.executeQuery(sql);
                           while(rs.next())
                                  { 
                                      Te220invc items = new Te220invc(rs.getInt("USU_CODEMP"),
                                                   rs.getDate("USU_DATINV"),
                                                   rs.getString("USU_CODDEP"),
                                                   rs.getInt("USU_ULTCON"),
                                                   rs.getString("USU_BLOMOV")
                                      );
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
    
    
    private boolean BuscaRegistro(String data,String deposito){         
         boolean resposta = false;
         try {
           Statement stmtAux;
           stmtAux = con.createStatement();
           String sqlAux = "select * from USU_TE220INV where USU_CODEMP = 3 AND USU_DATINV = '" + data + "' AND USU_CODDEP = '" + deposito + "'" ;
           ResultSet rsAux = stmtAux.executeQuery(sqlAux);
           if (rsAux.isBeforeFirst()) {
                resposta = false;
           }else {
                resposta = true;
           }           
       } catch (SQLException ex) {
           System.out.println("Erro funcao buscaRegistro " + ex.getMessage());
       }
       return resposta;
    }   
    
   private void IncluiRegistro(String cdatinv,String ccoddep){            
                try {
                    conn = Conector.getConnection();  
                    String sqA = "insert into USU_TE220INV "
                            + "(USU_CODEMP, USU_DATINV, USU_CODDEP, USU_ULTCON,USU_BLOMOV)"
                            + "values"
                            + "(?,?,?,?,?)";

                    PreparedStatement st = conn.prepareStatement(sqA);                   
                    st.setInt(1,3);
                    st.setString(2,cdatinv);
                    st.setString(3,ccoddep);
                    st.setInt(4,1);
                    st.setString(5,"0");
                    st.executeUpdate();
                    st.close();
                    conn.close();          
                } catch (SQLException | ClassNotFoundException e) {

                     System.out.println("erro " + e.getMessage());
                }
            }        
             
  
        public ArrayList<Te220invc> getBloqueio(String cdata,String cdeposito,int cContagem, boolean cBloqueio) throws ClassNotFoundException, SQLException {        
                ArrayList<Te220invc> listaBloqueio = new ArrayList<>(); 
                UpdateRegistro(cdata,cdeposito,cContagem,cBloqueio);
             return null; 
        }

        private void UpdateRegistro(String cdata,String cdeposito,int ccontagem,boolean cbloqueio){

               try {
                            conn = Conector.getConnection();   

                            String sqA = "update USU_TE220INV SET "
                                    + "USU_ULTCON = ?,"
                                    + "USU_BLOMOV = ? "
                                    + "WHERE "
                                    + " USU_CODEMP = 3 AND "
                                    + " USU_DATINV = ? AND "
                                    + " USU_CODDEP = ? ";

                            PreparedStatement st = conn.prepareStatement(sqA);                   
                            st.setInt(1,ccontagem);
                            st.setBoolean(2,cbloqueio);
                            st.setString(3,cdata);
                            st.setString(4,cdeposito);
                            st.executeUpdate();

                            st.close();
                            conn.close();          
                    } catch (SQLException | ClassNotFoundException e) 
                    {
                         System.out.println("erro " + e.getMessage());
                    }//Fim try             

            }
        
        
        
        
   
}
