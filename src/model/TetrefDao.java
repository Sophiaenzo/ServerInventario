
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
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelDominio.Tetref;
import util.Funcoes;
import static util.Funcoes.DataHoraAtual;

/**
 *
 * @author wilson.simoes
 */
public class TetrefDao {
   private Connection con;
   private Connection conn;
   private String cOrigem;
   private String cBase;
   private int posi;  
   private boolean continua;
   private boolean etiqAtivo;
   private boolean proAtivo;
   private boolean depAtivo;
   private boolean invAtivo;
   private boolean incluir;
   private String sql;
   
   private int    cRe_codemp;
   private String cRe_datinv;
   private String cRe_coddep;
   private String cRe_codpro;
   private String cRe_codder;
   private int    cRe_numcon;
   private String cRe_qtdcon;
   private String cRe_usucon;
   private String cRe_datcon; 
   private int    cRe_seqcon; 
   private String cRe_horcon;
   private String cRe_etiqueta;
   private String cRe_indbip;
   private String cRe_obsbip;
   private String cRe_lote; 
   private String cRe_status;   
   private String cRe_mate;
   private boolean bloqueado;   
   private String vbloqueio;   
   
   /**
    * Leitura dos codigos de barras
    * validacoes efetuadas
    * 1 - validar se a etiqueta e valida - contem o caracter $ no inicio e no fim
    * 2 - verificar se o codigo esta cadastrado na tabela 
    * @throws ClassNotFoundException 
    */
   
    public TetrefDao() throws ClassNotFoundException{
       con = Conector.getConnection();
    }
     public ArrayList<Tetref> getListaDados(String cdata, String cdeposito,String cetiqueta,String cMate,String CodeUsu) throws ClassNotFoundException, SQLException {        
            ArrayList<Tetref> listaDados = new ArrayList<>(); 
            Statement stmt;
            bloqueado = false;
            continua = true;
            etiqAtivo = true;
            cRe_codemp =  3;
            cRe_datinv =  cdata;
            cRe_coddep =  cdeposito;
            cRe_codpro =  "Vazio" ;
            cRe_codder =  ".";
            cRe_numcon =   1;
            cRe_qtdcon =  "0";
            cRe_usucon =  CodeUsu;
            cRe_datcon =  DataHoraAtual("D"); 
            cRe_seqcon =  0; 
            cRe_horcon =  DataHoraAtual("H");
            cRe_etiqueta = cetiqueta;
            cRe_indbip =  "S";
            cRe_obsbip =  "";
            cRe_lote   =  ""; 
            cRe_status =  "SUCESSO"; 
            cRe_obsbip = "Lancamento efetuado com sucesso"; 
            cRe_mate   = cMate;  
            
            
            
            cRe_codpro = "Vazio";
            cRe_lote   = "";
            cRe_qtdcon ="0";
            cRe_status  = "DANGER";
            cRe_indbip  = "N";
            
            /*verifica se inventario esta bloqueado*/
            vbloqueio = Funcoes.GetProp("inventario.bloqueado");
            bloqueado = false;
            if(vbloqueio.equals("SIM")){
                 cRe_codpro = "Vazio";
                 cRe_lote   = "";
                 cRe_qtdcon ="0";
                 cRe_status  = "DANGER";
                 cRe_indbip  = "N";
            
                cRe_obsbip  = "Inventario bloqueado pelo PCP ";
                continua = false;            
            }
         
            /*verifica se a etiqueta e valida*/
            if (continua == true) {
                etiqAtivo = validarEtiqueta(cetiqueta);
                if (etiqAtivo == false){
                    cRe_codpro = "Vazio";
                    cRe_lote   = "";
                    cRe_qtdcon ="0";
                    cRe_status  = "DANGER";
                    cRe_indbip  = "N";
            
                    cRe_obsbip  = "Etiqueta invalida ";
                    continua = false;
                }                
            }
            
            /*verifica se a etiqueta esta cadastrada*/
            if (continua == true) {
                stmt = con.createStatement();
                /*produzido*/
                if(cMate.equals("PR")){
                        cRe_codder =  "PADRAO";
                        //query para produtos produzidos
                        sql = "select * from USU_TETREF where USU_SEQUNI = '" + cetiqueta +"'" ;
                        ResultSet rs = stmt.executeQuery(sql); 
                        if(rs.isBeforeFirst()){
                            while(rs.next()){
                                   continua = true;
                                   cOrigem  = rs.getString("USU_INFPRO");
                                   cBase    = cOrigem.substring(1,cOrigem.length());
                                   posi     = cBase.indexOf("@");
                                   cRe_codpro = cBase.substring(0,posi);  

                                   cOrigem  = cBase.substring(posi+2,cBase.length());
                                   cBase    = cOrigem;
                                   posi     = cBase.indexOf("!");
                                   cRe_qtdcon = cBase.substring(0,posi);

                                   cOrigem  = cBase.substring(posi+2,cBase.length());
                                   cBase    = cOrigem;
                                   posi     = cBase.indexOf("#");
                                   cRe_lote = cBase.substring(0,cBase.length()-2);

                                   cRe_status   = "SUCESSO";
                                   cRe_indbip  = "S";
                                   cRe_obsbip = "Lancamento efetuado com sucesso";
                                   
                            }
                        }else{
                            cRe_codpro = "Vazio";
                            cRe_lote   = "";
                            cRe_qtdcon ="0";
                            cRe_status  = "DANGER";
                            cRe_indbip  = "N";
            
                            continua = false;
                            rs.close();
                            stmt.close(); 
                            stmt = con.createStatement();
                            sql = "select * from USU_TETQMPR where USU_SEQUNI = '" + cetiqueta +"'" ;  
                            ResultSet rsA = stmt.executeQuery(sql);            
                            if(rsA.isBeforeFirst()){
                                cRe_obsbip = "Etiqueta invalida e  Materia Prima";
                            }else{    
                                cRe_obsbip = "Etiqueta nao localizada";
                            } 
                            rsA.close();    
                        }                           
                        stmt.close();
                    
                }else{/*materia prima*/
                    sql = "select * from USU_TETQMPR where USU_SEQUNI = '" + cetiqueta +"'" ;  
                        ResultSet rs = stmt.executeQuery(sql);            
                        if(rs.isBeforeFirst()){
                             while(rs.next()){
                                   continua = true;
                                   cRe_codpro = rs.getString("USU_CODPRO");
                                   cRe_lote = "Sem Lote";
                                   cRe_qtdcon = rs.getString("USU_QTDIND");
                                   cRe_status   = "SUCESSO";
                                   cRe_indbip  = "S";
                                   cRe_obsbip = "Lancamento efetuado com sucesso";  
                             }
                        }else{
                            cRe_codpro = "Vazio";
                            cRe_lote   = "";
                            cRe_qtdcon ="0";
                            cRe_status  = "DANGER";
                            cRe_indbip  = "N";
            
                            continua = false;
                            rs.close();
                            stmt.close(); 
                            stmt = con.createStatement();
                            sql = "select * from USU_TETREF where USU_SEQUNI = '" + cetiqueta +"'" ;
                            ResultSet rsA = stmt.executeQuery(sql);            
                            if(rsA.isBeforeFirst()){
                                 cRe_obsbip = "Etiqueta Invalida e M.Produzido";
                            }else{    
                                 cRe_obsbip = "Etiqueta nao localizada";
                            } 
                            rsA.close();  
                        }
                       stmt.close();
                }                
            }
            
            /*verifica se produto e deposito esta ativo*/
            if (continua == true) {
                invAtivo = InventarioAtivo(cdata,cdeposito,cRe_codpro);
                if(invAtivo == true){
                    continua = true;
                }else{
                    cRe_codpro = "Vazio";
                    cRe_lote   = "";
                    cRe_qtdcon ="0";
                    cRe_status  = "DANGER";
                    cRe_indbip  = "N";
            
                 continua = false;
                 cRe_obsbip = "Produto Inativo para inventario";
                }
            }
            
            
           if (continua == true) {
               proAtivo = ProduAtivo(cRe_codpro);
               if ( proAtivo == false) {
                    continua = false;
                    cRe_codpro = "Vazio";
                    cRe_lote   = "";
                    cRe_qtdcon ="0";
                    cRe_status  = "DANGER";
                    cRe_indbip  = "N";
            
                    cRe_obsbip = "Produto Inativo";
               }
           }

           
           if (continua == true) {
              depAtivo = DepositoAtivo(cdeposito,cRe_codpro);
              if(depAtivo == false){
                 continua = false;
                 cRe_codpro = "Vazio";
                 cRe_lote   = "";
                 cRe_qtdcon ="0";
                 cRe_status  = "DANGER";
                 cRe_indbip  = "N";
            
                 cRe_obsbip = "Produto Com Depostio Inativo";
              }
           }
           incluir = BuscaRegistro(cdata,cdeposito,cRe_codpro,cetiqueta);            
           if(incluir == true) {
                 IncluiRegistro();
           }else{
                cRe_status  = "ATENCAO";
           }    
                
            Tetref items = new Tetref(cetiqueta,
                     cRe_codpro,
                     cRe_lote,
                     cRe_qtdcon,
                     cRe_status,
                     cRe_obsbip);   
                 listaDados.add(items); 
                 
            return listaDados; 
     } 
       
   
     
     
     
     
   /**
    * classe para verificar se a etiqueta e valida
    * etiqueta valida contem o caracter $ no inicio e no fim do codigo
    * @param codigo - codigo de barras
    * @return 
    */       
   private static boolean validarEtiqueta(String codigo){       
        String texto1 = codigo.substring(0,1);
        String texto2 = codigo.substring(1,codigo.length());
        boolean valido = false; 
        if(texto1.equals("$")){
            valido = true;
        }else{
            valido = false;
        }
       
        if(valido == true){
          if(texto2.indexOf("$") != -1) {
              valido = true;
          }else {
              valido = false;
          }   
        }
      return valido;
    } 
       
   
   /**
    * class para verificar se o produto esta ativo
    * utiliza o campo SITPRO da tabela E075PRO
    * @param produto - Codigo do produto
    * @return 
    */   
   private boolean ProduAtivo(String produto){       
       boolean retorno=false;
       try {
           Statement stmtAux;
           stmtAux = con.createStatement();
           String sqlAux = "select * from E075PRO where CODEMP = 3 AND CODPRO = '" + produto +"'" ;
           ResultSet rsAux = stmtAux.executeQuery(sqlAux);
           if(rsAux.isBeforeFirst()){
               while(rsAux.next()){
                   if(rsAux.getString("SITPRO").equals("A")){
                       retorno = true;
                   }else{
                       retorno = false;
                   }               
               }
           }else{
               retorno = false;
           }
           rsAux.close();
       }catch(Exception e){
           System.out.println("TetreDao 380 -> " + e.getMessage());
       }
       return retorno;
      }           
   /**
    * Class para verificar se a ligacao produto deposito esta ativa
    * utiliza o campo SITEST da tabela E210EST
    * @param deposito - Deposito onde esta localizado o produto
    * @param produto  = produto que esta ligado ao deposito
    * @return 
    */  
   private boolean DepositoAtivo(String deposito,String produto){
       boolean retorno=false;
       try {
           Statement stmtAux;
           stmtAux = con.createStatement();
           String sqlAux = "select * from E210EST where CODEMP = 3 AND CODDEP = '" + deposito + "' AND CODPRO = '" + produto +"'" ;
           ResultSet rsAux = stmtAux.executeQuery(sqlAux);
           if(rsAux.isBeforeFirst()){
               while(rsAux.next()){
                   if(rsAux.getString("SITEST").equals("A")){
                       retorno = true;
                   }else{
                       retorno = true;
                   }               
               }
           }else{
               retorno = false;
           }
           rsAux.close();
       }catch(Exception e){
           System.out.println("TetreDao 411 -> " + e.getMessage());
       }
       return retorno;
      }           
  
   /**
    * class que verifica se existe um inventario ativo
    * para este codigo nesta data e neste deposito
    * @param data   - data aberta para o inventario
    * @param deposito - deposito a ser inventariado
    * @param produto - produto a ser inventariado
    * @return 
    */  
private boolean InventarioAtivo(String data,String deposito,String produto){
       boolean retorno=false;
       try {
           Statement stmtAux;
           stmtAux = con.createStatement();
           String sqlAux = "select * from E220ITE where CODEMP = 3 AND DATINV = '" + data + "' AND CODDEP = '" + deposito + "' AND CODPRO = '" + produto +"'" ;
           ResultSet rsAux = stmtAux.executeQuery(sqlAux);
           if(rsAux.isBeforeFirst())
               {
                 retorno = true;
               }else{
                retorno = false;
               }
           rsAux.close();
       }catch(Exception e){
           System.out.println("TetreDao 440 -> " + e.getMessage());
       }
       return retorno;
      }           
  
    
    
    /**
     * class que verifica se a etiqueta lida ja nao foi bipada
     * para evitar uma leitura em duplicidade
     * utilizada na hora do insert da tabela USU_TE220CON
     * @param cdata   - data aberta para inventario
     * @param deposito - deposito para inventario
     * @param produto - codigo do produto
     * @param cEtiqueta - etiqueta com o codigo de barra
     * @param numero  - numero sequencia de leitura
     * @return 
     */ 
     private boolean BuscaRegistro(String data,String deposito,String produto,String etiqueta){         
         boolean resposta = false;
         try {
           Statement stmtAux;
           stmtAux = con.createStatement();
           String sqlAux = "select * from USU_TE220CON where USU_CODEMP = 3 AND USU_DATINV = '" + data + "' AND USU_CODDEP = '" + deposito + "' AND USU_CODPRO = '" + produto +"' AND USU_ETIQUETA = '"+ etiqueta + "' and USU_NUMCON = 1" ;
           ResultSet rsAux = stmtAux.executeQuery(sqlAux);
           if (rsAux.isBeforeFirst()) {
                cRe_status  = "ATENCAO";
                cRe_obsbip = "Etiqueta errada e ja foi bipada neste inventario";
                while(rsAux.next()){
                    if(rsAux.getString("USU_INDBIP").equals("S")) {
                      cRe_obsbip = "Etiqueta correta e ja foi bipada neste inventario";
                    }
                }
                resposta = false;
           }else {
            resposta = true;
           }           
                        
       } catch (SQLException ex) {
           Logger.getLogger(TetrefDao.class.getName()).log(Level.SEVERE, null, ex);
       }
       return resposta;
    }   
    
   /**
    * class define o deposito padrao 
    * @param mate tipo de material produzido ou materia prima
    * @param indbip indica o retorno da biágem - S ok N houve algum erro
    * @return PARAO ou BRANCO
    */
   private static String PegaCodder(String mate,String indbip){
       String retorno = "";
       if(indbip.equals("N")){
           retorno = ".";
       }else{
           if(mate.equals("PR")){
               retorno = "PADRAO";
           }else{
               retorno = ".";
           }
       }
       return retorno;       
   }
     
    /**
     * class que incluir os registros bipados na tabela USU_TE220CON
     */     
    
    private void IncluiRegistro(){
            
       try {
         cRe_codder = PegaCodder(cRe_mate,cRe_indbip); 
         conn = Conector.getConnection();   
            
           String sqA = "insert into USU_TE220CON "
                   + "(USU_CODEMP, USU_DATINV, USU_CODDEP, USU_CODPRO, USU_CODDER,"
                   + " USU_NUMCON, USU_QTDCON, USU_USUCON, USU_DATCON, USU_SEQCON,"
                   + " USU_HORCON, USU_ETIQUETA, USU_INDBIP, USU_OBSBIP)"
                   + "values"
                   + "(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
           
           cRe_qtdcon = cRe_qtdcon.replaceAll( "," , "." );
           
           PreparedStatement st = conn.prepareStatement(sqA);                   
           st.setInt(1,cRe_codemp);
           st.setString(2,cRe_datinv);
           st.setString(3,cRe_coddep);
           st.setString(4,cRe_codpro);
           st.setString(5,cRe_codder);
           st.setInt(6,cRe_numcon);
           st.setDouble(7,Double.parseDouble(cRe_qtdcon));
           st.setString(8,cRe_usucon);
           st.setString(9,cRe_datcon);
           st.setInt(10,cRe_seqcon);
           st.setString(11,cRe_horcon);
           st.setString(12,cRe_etiqueta);
           st.setString(13,cRe_indbip);
           st.setString(14,cRe_obsbip);
           st.executeUpdate();
           
           st.close();
           conn.close();          
} catch (SQLException | ClassNotFoundException e) {

     System.out.println("erro " + e.getMessage());

}//Fim try             
                   
                   
    }                  
                   
    
    }
     
    
  
  
   
