package db;

import factory.Conector;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import modelDominio.TetrefN;

/**
 *
 * @author wilson.simoes
 */
public class EtiquetaDB {    
       
    
    public static TetrefN selectBloqueio(TetrefN ret) throws ClassNotFoundException, SQLException{
         //BuscaInicio(ret);
        
        TetrefN dados = new TetrefN();
        String sql = "select * from USU_TE220INV where USU_CODEMP = 3 AND USU_DATINV = ? AND USU_CODDEP = ?";
         Connection conn = Conector.getConnection() ; //ConexaoFactory.getConexao();
         try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, ret.getRe_datinv());
            ps.setString(2,ret.getRe_coddep());
            ResultSet rs = ps.executeQuery(); //vazio no prepareStatement
            if(rs.next()){
                if(rs.getString("USU_BLOMOV").equals("1")){
                    dados.setRe_codemp(ret.getRe_codemp());
                    dados.setRe_datinv(ret.getRe_datinv());
                    dados.setRe_coddep(ret.getRe_coddep());
                    dados.setRe_codder(ret.getRe_codder());
                    dados.setRe_numcon(rs.getInt("USU_ULTCON"));
                    dados.setRe_usucon(ret.getRe_usucon());
                    dados.setRe_datcon(ret.getRe_datcon());
                    dados.setRe_horcon(ret.getRe_horcon());
                    dados.setRe_seqcon(ret.getRe_seqcon());
                    dados.setRe_mate(ret.getRe_mate());       
                    dados.setRe_codpro(ret.getRe_codpro());
                    dados.setRe_lote(ret.getRe_lote());
                    dados.setRe_qtdcon(ret.getRe_qtdcon());
                    dados.setRe_etiqueta(ret.getRe_etiqueta());
                    
                    dados.setRe_status("DANGER");
                    dados.setRe_indbip("N");
                    dados.setRe_obsbip("Inventario bloqueado pelo PCP");
                    dados.setRe_bloqueado(true);
                    dados.setContinua(false);
                    dados.setEti_valida(true);
                } else {
                    dados.setRe_codemp(ret.getRe_codemp());
                    dados.setRe_datinv(ret.getRe_datinv());
                    dados.setRe_coddep(ret.getRe_coddep());
                    dados.setRe_codder(ret.getRe_codder());
                    dados.setRe_numcon(rs.getInt("USU_ULTCON"));
                    dados.setRe_usucon(ret.getRe_usucon());
                    dados.setRe_datcon(ret.getRe_datcon());
                    dados.setRe_horcon(ret.getRe_horcon());
                    dados.setRe_seqcon(ret.getRe_seqcon());
                    dados.setRe_mate(ret.getRe_mate());       
                    dados.setRe_codpro(ret.getRe_codpro());
                    dados.setRe_lote(ret.getRe_lote());
                    dados.setRe_qtdcon(ret.getRe_qtdcon());
                    dados.setRe_etiqueta(ret.getRe_etiqueta());
                    
                    dados.setRe_status("SUCESSO");
                    dados.setRe_indbip("S");
                    dados.setRe_obsbip("Desbloqueado efetuar proxima validacao");
                    dados.setRe_bloqueado(false);
                    dados.setContinua(true);
                    dados.setEti_valida(true);
                }
            } else {
                    dados.setRe_codemp(ret.getRe_codemp());
                    dados.setRe_datinv(ret.getRe_datinv());
                    dados.setRe_coddep(ret.getRe_coddep());
                    dados.setRe_codder(ret.getRe_codder());
                    dados.setRe_numcon(rs.getInt("USU_ULTCON"));
                    dados.setRe_usucon(ret.getRe_usucon());
                    dados.setRe_datcon(ret.getRe_datcon());
                    dados.setRe_horcon(ret.getRe_horcon());
                    dados.setRe_seqcon(ret.getRe_seqcon());
                    dados.setRe_mate(ret.getRe_mate());       
                    dados.setRe_codpro(ret.getRe_codpro());
                    dados.setRe_lote(ret.getRe_lote());
                    dados.setRe_qtdcon(ret.getRe_qtdcon());
                    dados.setRe_etiqueta(ret.getRe_etiqueta());
                    
                    dados.setRe_status("DANGER");
                    dados.setRe_indbip("N");
                    dados.setRe_obsbip("Inventario bloqueado pelo PCP");
                    dados.setRe_bloqueado(true);
                    dados.setContinua(false);
                    dados.setEti_valida(true);
            }
            Conector.close(conn,ps,rs);
            return dados;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
       return null; 
    }
    
    
     public static TetrefN validarCarac(TetrefN ret){       
        TetrefN dados = new TetrefN();
        String codigo = ret.getRe_etiqueta();
        String texto1 = codigo.substring(0,1);
        String texto2 = codigo.substring(1,codigo.length());
       
        if(texto1.equals("$")){
                    dados.setRe_codemp(ret.getRe_codemp());
                    dados.setRe_datinv(ret.getRe_datinv());
                    dados.setRe_coddep(ret.getRe_coddep());
                    dados.setRe_codder(ret.getRe_codder());
                    dados.setRe_numcon(ret.getRe_numcon());
                    dados.setRe_usucon(ret.getRe_usucon());
                    dados.setRe_datcon(ret.getRe_datcon());
                    dados.setRe_horcon(ret.getRe_horcon());
                    dados.setRe_seqcon(ret.getRe_seqcon());
                    dados.setRe_mate(ret.getRe_mate());       
                    dados.setRe_codpro(ret.getRe_codpro());
                    dados.setRe_lote(ret.getRe_lote());
                    dados.setRe_qtdcon(ret.getRe_qtdcon());
                    dados.setRe_etiqueta(ret.getRe_etiqueta());
                    
                    dados.setRe_status("SUCESSO");
                    dados.setRe_indbip("S");
                    dados.setRe_obsbip("etiqueta valida");
                    dados.setRe_bloqueado(ret.isRe_bloqueado());
                    dados.setContinua(true);
                    dados.setEti_valida(true);
        }else{           
                    dados.setRe_codemp(ret.getRe_codemp());
                    dados.setRe_datinv(ret.getRe_datinv());
                    dados.setRe_coddep(ret.getRe_coddep());
                    dados.setRe_codder(".");
                    dados.setRe_numcon(ret.getRe_numcon());
                    dados.setRe_usucon(ret.getRe_usucon());
                    dados.setRe_datcon(ret.getRe_datcon());
                    dados.setRe_horcon(ret.getRe_horcon());
                    dados.setRe_seqcon(ret.getRe_seqcon());
                    dados.setRe_mate(ret.getRe_mate());       
                    dados.setRe_codpro(ret.getRe_codpro());
                    dados.setRe_lote(ret.getRe_lote());
                    dados.setRe_qtdcon(ret.getRe_qtdcon());
                    dados.setRe_etiqueta(ret.getRe_etiqueta());
                    
                    dados.setRe_status("DANGER");
                    dados.setRe_indbip("N");
                    dados.setRe_obsbip("etiqueta invalida");
                    dados.setRe_bloqueado(ret.isRe_bloqueado());
                    dados.setContinua(true);
                    dados.setEti_valida(false);
        }    
            
        if(dados.isEti_valida() == true){
            if(texto2.indexOf("$") != -1) {
                    dados.setRe_codemp(ret.getRe_codemp());
                    dados.setRe_datinv(ret.getRe_datinv());
                    dados.setRe_coddep(ret.getRe_coddep());
                    dados.setRe_codder(ret.getRe_codder());
                    dados.setRe_numcon(ret.getRe_numcon());
                    dados.setRe_usucon(ret.getRe_usucon());
                    dados.setRe_datcon(ret.getRe_datcon());
                    dados.setRe_horcon(ret.getRe_horcon());
                    dados.setRe_seqcon(ret.getRe_seqcon());
                    dados.setRe_mate(ret.getRe_mate());       
                    dados.setRe_codpro(ret.getRe_codpro());
                    dados.setRe_lote(ret.getRe_lote());
                    dados.setRe_qtdcon(ret.getRe_qtdcon());
                    dados.setRe_etiqueta(ret.getRe_etiqueta());
                    
                    dados.setRe_status("SUCESSO");
                    dados.setRe_indbip("S");
                    dados.setRe_obsbip("etiqueta valida");
                    dados.setRe_bloqueado(ret.isRe_bloqueado());
                    dados.setContinua(true);
                    dados.setEti_valida(true);

             }else {
                    dados.setRe_codemp(ret.getRe_codemp());
                    dados.setRe_datinv(ret.getRe_datinv());
                    dados.setRe_coddep(ret.getRe_coddep());
                    dados.setRe_codder(".");
                    dados.setRe_numcon(ret.getRe_numcon());
                    dados.setRe_usucon(ret.getRe_usucon());
                    dados.setRe_datcon(ret.getRe_datcon());
                    dados.setRe_horcon(ret.getRe_horcon());
                    dados.setRe_seqcon(ret.getRe_seqcon());
                    dados.setRe_mate(ret.getRe_mate());       
                    dados.setRe_codpro(ret.getRe_codpro());
                    dados.setRe_lote(ret.getRe_lote());
                    dados.setRe_qtdcon(ret.getRe_qtdcon());
                    dados.setRe_etiqueta(ret.getRe_etiqueta());
                    
                    dados.setRe_status("DANGER");
                    dados.setRe_indbip("N");
                    dados.setRe_obsbip("etiqueta invalida");
                    dados.setRe_bloqueado(ret.isRe_bloqueado());
                    dados.setContinua(true);
                    dados.setEti_valida(false);
             }
          }
      return dados;
    } 
       

     public static TetrefN validaPR(TetrefN ret) throws ClassNotFoundException{
        TetrefN dados = new TetrefN();
        String codigo = ret.getRe_etiqueta();
        String cOrigem = "";
        String cBase = "";
        int posi = 0;  
        float floatValue;
        String sql = "select * from USU_TETREF where USU_SEQUNI = ?";
         Connection conn = Conector.getConnection();
         try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, codigo);
            ResultSet rs = ps.executeQuery(); //vazio no prepareStatement
            if(rs.next()){
                   cOrigem  = rs.getString("USU_INFPRO");
                   cBase    = cOrigem.substring(1,cOrigem.length());
                   posi     = cBase.indexOf("@");
                   dados.setRe_codpro(cBase.substring(0,posi));
                   
                   cOrigem  = cBase.substring(posi+2,cBase.length());
                   cBase    = cOrigem;
                   posi     = cBase.indexOf("!");
                   dados.setRe_qtdcon(cBase.substring(0,posi));
                   
                   cOrigem  = cBase.substring(posi+2,cBase.length());
                   cBase    = cOrigem;
                   posi     = cBase.indexOf("#");
                   dados.setRe_lote(cBase.substring(0,cBase.length()-2));
                   dados.setRe_codder("PADRAO");
                  

                    dados.setRe_codemp(ret.getRe_codemp());
                    dados.setRe_datinv(ret.getRe_datinv());
                    dados.setRe_coddep(ret.getRe_coddep());
                    //dados.setRe_codder(ret.getRe_codder());
                    dados.setRe_numcon(ret.getRe_numcon());
                    dados.setRe_usucon(ret.getRe_usucon());
                    dados.setRe_datcon(ret.getRe_datcon());
                    dados.setRe_horcon(ret.getRe_horcon());
                    dados.setRe_seqcon(ret.getRe_seqcon());
                    dados.setRe_mate(ret.getRe_mate());       
                    //dados.setRe_codpro(ret.getRe_codpro());
                    //dados.setRe_lote(ret.getRe_lote());
                    //dados.setRe_qtdcon(ret.getRe_qtdcon());
                    dados.setRe_etiqueta(ret.getRe_etiqueta());
                    
                    dados.setRe_status("SUCESSO");
                    dados.setRe_indbip("S");
                    dados.setRe_obsbip("etiqueta valida");
                    dados.setRe_bloqueado(ret.isRe_bloqueado());
                    dados.setContinua(true);
                    dados.setEti_valida(ret.isEti_valida());

            } else {
                    dados.setRe_codemp(ret.getRe_codemp());
                    dados.setRe_datinv(ret.getRe_datinv());
                    dados.setRe_coddep(ret.getRe_coddep());
                    dados.setRe_codder(".");
                    dados.setRe_numcon(ret.getRe_numcon());
                    dados.setRe_usucon(ret.getRe_usucon());
                    dados.setRe_datcon(ret.getRe_datcon());
                    dados.setRe_horcon(ret.getRe_horcon());
                    dados.setRe_seqcon(ret.getRe_seqcon());
                    dados.setRe_mate(ret.getRe_mate());       
                    dados.setRe_codpro(ret.getRe_codpro());
                    dados.setRe_lote(ret.getRe_lote());
                    dados.setRe_qtdcon(ret.getRe_qtdcon());
                    dados.setRe_etiqueta(ret.getRe_etiqueta());
                    
                    dados.setRe_status("DANGER");
                    dados.setRe_indbip("N");
                    dados.setRe_obsbip("Etiqueta nao localizada - m.produzido");
                    dados.setRe_bloqueado(ret.isRe_bloqueado());
                    dados.setContinua(false);
                    dados.setEti_valida(ret.isEti_valida());

            }
            Conector.close(conn,ps,rs);
            return dados;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
       return null; 
    }
    
     public static TetrefN validaMP(TetrefN ret) throws ClassNotFoundException{
         TetrefN dados = new TetrefN();
         String codigo = ret.getRe_etiqueta();
         String sql = "select * from USU_TETQMPR where USU_SEQUNI = ?";
         Connection conn = Conector.getConnection();
         try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, codigo);
            ResultSet rs = ps.executeQuery(); //vazio no prepareStatement
            if(rs.next()){
                    dados.setRe_codemp(ret.getRe_codemp());
                    dados.setRe_datinv(ret.getRe_datinv());
                    dados.setRe_coddep(ret.getRe_coddep());
                    dados.setRe_codder(ret.getRe_codder());
                    dados.setRe_numcon(ret.getRe_numcon());
                    dados.setRe_usucon(ret.getRe_usucon());
                    dados.setRe_datcon(ret.getRe_datcon());
                    dados.setRe_horcon(ret.getRe_horcon());
                    dados.setRe_seqcon(ret.getRe_seqcon());
                    dados.setRe_mate(ret.getRe_mate());       
                    dados.setRe_codpro(rs.getString("USU_CODPRO"));
                    dados.setRe_lote(ret.getRe_lote());
                    dados.setRe_qtdcon(rs.getString("USU_QTDIND"));
                    dados.setRe_etiqueta(ret.getRe_etiqueta());
                    
                    dados.setRe_status("SUCESSO");
                    dados.setRe_indbip("S");
                    dados.setRe_obsbip("etiqueta materia prima valida");
                    dados.setRe_bloqueado(ret.isRe_bloqueado());
                    dados.setContinua(true);
                    dados.setEti_valida(ret.isEti_valida());
            } else {
                    dados.setRe_codemp(ret.getRe_codemp());
                    dados.setRe_datinv(ret.getRe_datinv());
                    dados.setRe_coddep(ret.getRe_coddep());
                    dados.setRe_codder(".");
                    dados.setRe_numcon(ret.getRe_numcon());
                    dados.setRe_usucon(ret.getRe_usucon());
                    dados.setRe_datcon(ret.getRe_datcon());
                    dados.setRe_horcon(ret.getRe_horcon());
                    dados.setRe_seqcon(ret.getRe_seqcon());
                    dados.setRe_mate(ret.getRe_mate());       
                    dados.setRe_codpro(ret.getRe_codpro());
                    dados.setRe_lote(ret.getRe_lote());
                    dados.setRe_qtdcon(ret.getRe_qtdcon());
                    dados.setRe_etiqueta(ret.getRe_etiqueta());
                    
                    dados.setRe_status("DANGER");
                    dados.setRe_indbip("N");
                    dados.setRe_obsbip("Etiqueta nao localizada - materia prima");
                    dados.setRe_bloqueado(ret.isRe_bloqueado());
                    dados.setContinua(false);
                    dados.setEti_valida(ret.isEti_valida());
            }
            Conector.close(conn,ps,rs);
            return dados;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
       return null; 
    }
     
    
    public static TetrefN ProdutoDepositoAtivo(TetrefN ret) throws ClassNotFoundException{
         TetrefN dados = new TetrefN();
         String sql = "select * from E220ITE where CODEMP = 3 and DATINV = ? and CODDEP = ? and CODPRO = ?";
         Connection conn =  Conector.getConnection();
         try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, ret.getRe_datinv());
            ps.setString(2, ret.getRe_coddep());
            ps.setString(3, ret.getRe_codpro());
            
            ResultSet rs = ps.executeQuery(); //vazio no prepareStatement
            if(rs.next()){
                    dados.setRe_codemp(ret.getRe_codemp());
                    dados.setRe_datinv(ret.getRe_datinv());
                    dados.setRe_coddep(ret.getRe_coddep());
                    dados.setRe_codder(ret.getRe_codder());
                    dados.setRe_numcon(ret.getRe_numcon());
                    dados.setRe_usucon(ret.getRe_usucon());
                    dados.setRe_datcon(ret.getRe_datcon());
                    dados.setRe_horcon(ret.getRe_horcon());
                    dados.setRe_seqcon(ret.getRe_seqcon());
                    dados.setRe_mate(ret.getRe_mate());       
                    dados.setRe_codpro(ret.getRe_codpro());
                    dados.setRe_lote(ret.getRe_lote());
                    dados.setRe_qtdcon(ret.getRe_qtdcon());
                    dados.setRe_etiqueta(ret.getRe_etiqueta());
                    
                    dados.setRe_status("SUCESSO");
                    dados.setRe_indbip("S");
                    dados.setRe_obsbip("Produto e Deposito ativos");
                    dados.setRe_bloqueado(ret.isRe_bloqueado());
                    dados.setContinua(true);
                    dados.setEti_valida(ret.isEti_valida());
            } else {
                    dados.setRe_codemp(ret.getRe_codemp());
                    dados.setRe_datinv(ret.getRe_datinv());
                    dados.setRe_coddep(ret.getRe_coddep());
                    dados.setRe_codder(ret.getRe_codder());
                    dados.setRe_numcon(ret.getRe_numcon());
                    dados.setRe_usucon(ret.getRe_usucon());
                    dados.setRe_datcon(ret.getRe_datcon());
                    dados.setRe_horcon(ret.getRe_horcon());
                    dados.setRe_seqcon(ret.getRe_seqcon());
                    dados.setRe_mate(ret.getRe_mate());       
                    dados.setRe_codpro(ret.getRe_codpro());
                    dados.setRe_lote(ret.getRe_lote());
                    dados.setRe_qtdcon(ret.getRe_qtdcon());
                    dados.setRe_etiqueta(ret.getRe_etiqueta());
                    
                    dados.setRe_status("DANGER");
                    dados.setRe_indbip("N");
                    dados.setRe_obsbip("Produto e Deposito inativos");
                    dados.setRe_bloqueado(ret.isRe_bloqueado());
                    dados.setContinua(false);
                    dados.setEti_valida(ret.isEti_valida());                
            }
            Conector.close(conn,ps,rs);
            return dados;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
       return null; 
    }
    
    
     public static TetrefN ProdutoAtivo(TetrefN ret) throws ClassNotFoundException{
         TetrefN dados = new TetrefN();
         String sql = "select codpro,sitpro from E075PRO where CODEMP = 3 AND SITPRO = 'A' AND CODPRO = ?";
         Connection conn = Conector.getConnection();
         try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, ret.getRe_codpro());
            
            ResultSet rs = ps.executeQuery(); //vazio no prepareStatement
            if(rs.next()){
                    dados.setRe_codemp(ret.getRe_codemp());
                    dados.setRe_datinv(ret.getRe_datinv());
                    dados.setRe_coddep(ret.getRe_coddep());
                    dados.setRe_codder(ret.getRe_codder());
                    dados.setRe_numcon(ret.getRe_numcon());
                    dados.setRe_usucon(ret.getRe_usucon());
                    dados.setRe_datcon(ret.getRe_datcon());
                    dados.setRe_horcon(ret.getRe_horcon());
                    dados.setRe_seqcon(ret.getRe_seqcon());
                    dados.setRe_mate(ret.getRe_mate());       
                    dados.setRe_codpro(ret.getRe_codpro());
                    dados.setRe_lote(ret.getRe_lote());
                    dados.setRe_qtdcon(ret.getRe_qtdcon());
                    dados.setRe_etiqueta(ret.getRe_etiqueta());
                    
                    dados.setRe_status("SUCESSO");
                    dados.setRe_indbip("S");
                    dados.setRe_obsbip("Produto ativo");
                    dados.setRe_bloqueado(ret.isRe_bloqueado());
                    dados.setContinua(true);
                    dados.setEti_valida(ret.isEti_valida());                
            } else {
                    dados.setRe_codemp(ret.getRe_codemp());
                    dados.setRe_datinv(ret.getRe_datinv());
                    dados.setRe_coddep(ret.getRe_coddep());
                    dados.setRe_codder(ret.getRe_codder());
                    dados.setRe_numcon(ret.getRe_numcon());
                    dados.setRe_usucon(ret.getRe_usucon());
                    dados.setRe_datcon(ret.getRe_datcon());
                    dados.setRe_horcon(ret.getRe_horcon());
                    dados.setRe_seqcon(ret.getRe_seqcon());
                    dados.setRe_mate(ret.getRe_mate());       
                    dados.setRe_codpro(ret.getRe_codpro());
                    dados.setRe_lote(ret.getRe_lote());
                    dados.setRe_qtdcon(ret.getRe_qtdcon());
                    dados.setRe_etiqueta(ret.getRe_etiqueta());
                    
                    dados.setRe_status("DANGER");
                    dados.setRe_indbip("N");
                    dados.setRe_obsbip("Produto inativo");
                    dados.setRe_bloqueado(ret.isRe_bloqueado());
                    dados.setContinua(false);
                    dados.setEti_valida(ret.isEti_valida());                
            }
            Conector.close(conn,ps,rs);
            return dados;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
       return null; 
    }
    
    
    public static TetrefN DepositoAtivo(TetrefN ret) throws ClassNotFoundException{
         TetrefN dados = new TetrefN();
         String sql = "select coddep,codpro,sitest from E210EST where CODEMP = 3 AND SITEST = 'I' AND CODDEP = ? AND CODPRO = ?";
         Connection conn = Conector.getConnection();
         try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, ret.getRe_coddep());
            ps.setString(2, ret.getRe_codpro());
            
            ResultSet rs = ps.executeQuery(); //vazio no prepareStatement
            if(rs.next()){
                    dados.setRe_codemp(ret.getRe_codemp());
                    dados.setRe_datinv(ret.getRe_datinv());
                    dados.setRe_coddep(ret.getRe_coddep());
                    dados.setRe_codder(ret.getRe_codder());
                    dados.setRe_numcon(ret.getRe_numcon());
                    dados.setRe_usucon(ret.getRe_usucon());
                    dados.setRe_datcon(ret.getRe_datcon());
                    dados.setRe_horcon(ret.getRe_horcon());
                    dados.setRe_seqcon(ret.getRe_seqcon());
                    dados.setRe_mate(ret.getRe_mate());       
                    dados.setRe_codpro(ret.getRe_codpro());
                    dados.setRe_lote(ret.getRe_lote());
                    dados.setRe_qtdcon(ret.getRe_qtdcon());
                    dados.setRe_etiqueta(ret.getRe_etiqueta());
                    
                    dados.setRe_status("SUCESSO");
                    dados.setRe_indbip("S");
                    dados.setRe_obsbip("Deposito Est. inativo");
                    dados.setRe_bloqueado(ret.isRe_bloqueado());
                    dados.setContinua(true);
                    dados.setEti_valida(ret.isEti_valida());                
            } else {
                    dados.setRe_codemp(ret.getRe_codemp());
                    dados.setRe_datinv(ret.getRe_datinv());
                    dados.setRe_coddep(ret.getRe_coddep());
                    dados.setRe_codder(ret.getRe_codder());
                    dados.setRe_numcon(ret.getRe_numcon());
                    dados.setRe_usucon(ret.getRe_usucon());
                    dados.setRe_datcon(ret.getRe_datcon());
                    dados.setRe_horcon(ret.getRe_horcon());
                    dados.setRe_seqcon(ret.getRe_seqcon());
                    dados.setRe_mate(ret.getRe_mate());       
                    dados.setRe_codpro(ret.getRe_codpro());
                    dados.setRe_lote(ret.getRe_lote());
                    dados.setRe_qtdcon(ret.getRe_qtdcon());
                    dados.setRe_etiqueta(ret.getRe_etiqueta());
                    
                    dados.setRe_status("DANGER");
                    dados.setRe_indbip("N");
                    dados.setRe_obsbip("Deposito Est. Ativo");
                    dados.setRe_bloqueado(ret.isRe_bloqueado());
                    dados.setContinua(false);
                    dados.setEti_valida(ret.isEti_valida());                
            }
            Conector.close(conn,ps,rs);
            return dados;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
       return null; 
    }
    
    
    public static TetrefN BuscaLancto(TetrefN ret) throws ClassNotFoundException{ 
         TetrefN dados = new TetrefN();
         String sql = "select * from USU_TE220CON where USU_CODEMP = 3 AND USU_DATINV = ? AND USU_CODDEP = ? AND USU_CODPRO = ? AND USU_ETIQUETA = ? and USU_NUMCON = ?" ;
         Connection conn = Conector.getConnection();
             
         
         try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, ret.getRe_datinv());
            ps.setString(2, ret.getRe_coddep());
            ps.setString(3, ret.getRe_codpro());
            ps.setString(4, ret.getRe_etiqueta());
            ps.setInt(5, ret.getRe_numcon());
            
            dados.setContinua(false);
            ResultSet rs = ps.executeQuery(); //vazio no prepareStatement
            if(rs.next()){
                   if(rs.getString("USU_INDBIP").equals("S")) {
                            dados.setRe_codemp(ret.getRe_codemp());
                            dados.setRe_datinv(ret.getRe_datinv());
                            dados.setRe_coddep(ret.getRe_coddep());
                            dados.setRe_codder(ret.getRe_codder());
                            dados.setRe_numcon(ret.getRe_numcon());
                            dados.setRe_usucon(ret.getRe_usucon());
                            dados.setRe_datcon(ret.getRe_datcon());
                            dados.setRe_horcon(ret.getRe_horcon());
                            dados.setRe_seqcon(ret.getRe_seqcon());
                            dados.setRe_mate(ret.getRe_mate());       
                            dados.setRe_codpro(ret.getRe_codpro());
                            dados.setRe_lote(ret.getRe_lote());
                            dados.setRe_qtdcon(ret.getRe_qtdcon());
                            dados.setRe_etiqueta(ret.getRe_etiqueta());

                            dados.setRe_status("ATENCAO");
                            dados.setRe_indbip("N");
                            dados.setRe_obsbip("Etiqueta correta e ja foi bipada neste inventario");
                            dados.setRe_bloqueado(ret.isRe_bloqueado());
                            dados.setContinua(false);
                            dados.setEti_valida(ret.isEti_valida()); 
                   } else {
                            dados.setRe_codemp(ret.getRe_codemp());
                            dados.setRe_datinv(ret.getRe_datinv());
                            dados.setRe_coddep(ret.getRe_coddep());
                            dados.setRe_codder(ret.getRe_codder());
                            dados.setRe_numcon(ret.getRe_numcon());
                            dados.setRe_usucon(ret.getRe_usucon());
                            dados.setRe_datcon(ret.getRe_datcon());
                            dados.setRe_horcon(ret.getRe_horcon());
                            dados.setRe_seqcon(ret.getRe_seqcon());
                            dados.setRe_mate(ret.getRe_mate());       
                            dados.setRe_codpro(ret.getRe_codpro());
                            dados.setRe_lote(ret.getRe_lote());
                            dados.setRe_qtdcon(ret.getRe_qtdcon());
                            dados.setRe_etiqueta(ret.getRe_etiqueta());

                            dados.setRe_status("ATENCAO");
                            dados.setRe_indbip("N");
                            dados.setRe_obsbip("Etiqueta errada e ja foi bipada neste inventario");
                            dados.setRe_bloqueado(ret.isRe_bloqueado());
                            dados.setContinua(false);
                            dados.setEti_valida(ret.isEti_valida());                        
                   }
            } else {
                    dados.setRe_codemp(ret.getRe_codemp());
                    dados.setRe_datinv(ret.getRe_datinv());
                    dados.setRe_coddep(ret.getRe_coddep());
                    dados.setRe_codder(ret.getRe_codder());
                    dados.setRe_numcon(ret.getRe_numcon());
                    dados.setRe_usucon(ret.getRe_usucon());
                    dados.setRe_datcon(ret.getRe_datcon());
                    dados.setRe_horcon(ret.getRe_horcon());
                    dados.setRe_seqcon(ret.getRe_seqcon());
                    dados.setRe_mate(ret.getRe_mate());       
                    dados.setRe_codpro(ret.getRe_codpro());
                    dados.setRe_lote(ret.getRe_lote());
                    dados.setRe_qtdcon(ret.getRe_qtdcon());
                    dados.setRe_etiqueta(ret.getRe_etiqueta());
                    
                    dados.setRe_status(ret.getRe_status());
                    dados.setRe_indbip(ret.getRe_indbip());
                    dados.setRe_obsbip(ret.getRe_obsbip());
                    dados.setRe_bloqueado(ret.isRe_bloqueado());
                    dados.setContinua(true);
                    dados.setEti_valida(ret.isEti_valida());  
                    
                    if(ret.getRe_indbip().equals("S")){
                        dados.setRe_obsbip("Lancamento efetuado com sucesso");
                    }
                    
                    
            }
            Conector.close(conn,ps,rs);
            return dados;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
       return null; 
    }
    
    
    public static TetrefN save(TetrefN etiquetadao) throws ClassNotFoundException, SQLException{
         TetrefN dados = new TetrefN();
         String qtdade;
         
         qtdade = etiquetadao.getRe_qtdcon();
         qtdade = qtdade.replaceAll( "," , "." );
         etiquetadao.setRe_qtdcon(qtdade);
         
         String sql = "insert into USU_TE220CON "
                              + "(USU_CODEMP, USU_DATINV, USU_CODDEP, USU_CODPRO, USU_CODDER,"
                              + " USU_NUMCON, USU_QTDCON, USU_USUCON, USU_DATCON, USU_SEQCON,"
                              + " USU_HORCON, USU_ETIQUETA, USU_INDBIP, USU_OBSBIP)"
                              + "values"
                              + "('" + etiquetadao.getRe_codemp()+ "',"
                              + " '" + etiquetadao.getRe_datinv()+ "',"
                              + " '" + etiquetadao.getRe_coddep()+ "',"
                              + " '" + etiquetadao.getRe_codpro()+ "',"
                              + " '" + etiquetadao.getRe_codder()+ "',"
                              + " "  + etiquetadao.getRe_numcon()+ ","
                              + " "  + Double.valueOf(etiquetadao.getRe_qtdcon())+ ","
                              + " "  + etiquetadao.getRe_usucon()+ ","
                              + " '" + etiquetadao.getRe_datcon()+ "',"
                              + " "  + etiquetadao.getRe_seqcon()+ ","
                              + " '" + etiquetadao.getRe_horcon()+ "',"
                              + " '" + etiquetadao.getRe_etiqueta()+ "',"
                              + " '" + etiquetadao.getRe_indbip()+ "',"
                              + " '" + etiquetadao.getRe_obsbip()+ "')";     
        Connection conn =  Conector.getConnection();
        Statement stmt = conn.createStatement();
        try {
      
            stmt.executeUpdate(sql);            
            
                    dados.setRe_codemp(etiquetadao.getRe_codemp());
                    dados.setRe_datinv(etiquetadao.getRe_datinv());
                    dados.setRe_coddep(etiquetadao.getRe_coddep());
                    dados.setRe_codder(etiquetadao.getRe_codder());
                    dados.setRe_numcon(etiquetadao.getRe_numcon());
                    dados.setRe_usucon(etiquetadao.getRe_usucon());
                    dados.setRe_datcon(etiquetadao.getRe_datcon());
                    dados.setRe_horcon(etiquetadao.getRe_horcon());
                    dados.setRe_seqcon(etiquetadao.getRe_seqcon());
                     dados.setRe_mate(etiquetadao.getRe_mate());       
                    dados.setRe_codpro(etiquetadao.getRe_codpro());
                    dados.setRe_lote(etiquetadao.getRe_lote());
                    dados.setRe_qtdcon(etiquetadao.getRe_qtdcon());
                    dados.setRe_etiqueta(etiquetadao.getRe_etiqueta());           
            

                    dados.setRe_status(etiquetadao.getRe_status());
                    dados.setRe_indbip(etiquetadao.getRe_indbip());
                    dados.setRe_obsbip(etiquetadao.getRe_obsbip());
                    dados.setContinua(true);
            
            
       //     Conector.close(conn, stmt);
       //     System.out.println("fechou a conexao voltar");       
            return dados;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }finally{
         Conector.close(conn, stmt);
          System.out.println("fechou a conexao voltar");       
        }
        return null;
    }
   
    
    
    
    public static TetrefN BuscaInicio(TetrefN ret) throws ClassNotFoundException, SQLException{ 
         TetrefN dados = new TetrefN();
         String sql;
         sql = "select * from USU_TE220INV where USU_CODEMP = 3 AND USU_DATINV = ? AND USU_CODDEP = ?" ;
         Connection conn = Conector.getConnection();
         Statement stmt = conn.createStatement(); 
         try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, ret.getRe_datinv());
            ps.setString(2, ret.getRe_coddep());
            
            dados.setContinua(false);
            ResultSet rs = ps.executeQuery(); //vazio no prepareStatement
            if(rs.next()){
            } else {
                   
                   sql = "insert into USU_TE220INV "
                                      + "(USU_CODEMP, USU_DATINV, USU_CODDEP, USU_ULTCON, USU_BLOMOV)"
                                      + "values"
                                      + "('3',"
                                      + " '" + ret.getRe_datinv() + "',"
                                      + " '" + ret.getRe_coddep() + "',"
                                      + " '1',"
                                      + " '0')";
                stmt.executeUpdate(sql);  
            }
            Conector.close(conn,ps,rs);
            Conector.close(conn,stmt);
            return dados;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
       return null; 
    }
     
    
    
    
    
}
