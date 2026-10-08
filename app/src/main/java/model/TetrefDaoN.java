package model;
import db.EtiquetaDB;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import modelDominio.Tetref;
import modelDominio.TetrefN;
import static util.Funcoes.DataHoraAtual;
/**
 *
 * @author wilson.simoes
 */
public class TetrefDaoN {
   private TetrefN ret = new TetrefN();
    
   public static void main(String[] args) throws ParseException{
   } 
    
    public ArrayList<Tetref> getDados(String cdata, String cdeposito,String cetiqueta,String cMate,String CodeUsu) throws ClassNotFoundException, SQLException{
       ArrayList<Tetref> listaDados = new ArrayList<>();  
          
       //TetrefN ret = new TetrefN();
   
       String cDatCon;
       String cHorCon;
     
       if(CodeUsu == null){
           CodeUsu = "0";       
       }
       
       if(CodeUsu.isEmpty()){
           CodeUsu = "0";       
       }
       
       cDatCon =  DataHoraAtual("D"); 
       cHorCon =  DataHoraAtual("H");
       
       ret.setRe_codemp(3);
       ret.setRe_datinv(cdata);
       ret.setRe_coddep(cdeposito);
       ret.setRe_codder("PADRAO");
       ret.setRe_numcon(0);
       ret.setRe_usucon(CodeUsu);
       ret.setRe_datcon(cDatCon);
       ret.setRe_horcon(cHorCon);
       ret.setRe_seqcon(0);
       ret.setRe_mate(cMate);       
       ret.setRe_codpro("vazio");
       ret.setRe_lote("sem");
       ret.setRe_qtdcon("0");
       ret.setRe_status("ATENCAO");
       ret.setRe_indbip("N");
       ret.setRe_obsbip("sem observaoes");
       ret.setRe_etiqueta(cetiqueta);
       ret.setRe_bloqueado(false);
       ret.setContinua(true);
       ret.setEti_valida(true);
       ret.setRe_contagem(0);
       
           
      //verifica se esta deposito esta bloqueado para inventario
       TetrefN rr0 = Tbloqueio(ret);
     
       
       ret.setRe_codemp(rr0.getRe_codemp());
       ret.setRe_datinv(rr0.getRe_datinv());
       ret.setRe_coddep(rr0.getRe_coddep());
       ret.setRe_codder(rr0.getRe_codder());
       ret.setRe_numcon(rr0.getRe_numcon());
       ret.setRe_usucon(rr0.getRe_usucon());
       ret.setRe_datcon(rr0.getRe_datcon());
       ret.setRe_horcon(rr0.getRe_horcon());
       ret.setRe_seqcon(rr0.getRe_seqcon());
       ret.setRe_mate(rr0.getRe_mate());       
       ret.setRe_codpro(rr0.getRe_codpro());
       ret.setRe_lote(rr0.getRe_lote());
       ret.setRe_qtdcon(rr0.getRe_qtdcon());
       ret.setRe_status(rr0.getRe_status());
       ret.setRe_indbip(rr0.getRe_indbip());
       ret.setRe_obsbip(rr0.getRe_obsbip());
       ret.setRe_etiqueta(rr0.getRe_etiqueta());
       ret.setRe_bloqueado(rr0.isRe_bloqueado());
       ret.setContinua(rr0.isContinua());
       ret.setEti_valida(rr0.isEti_valida());
       //ret.setRe_contagem(rr0.getRe_contagem());
       
           
       
       if(ret.isContinua() == true){       
            TetrefN rr1 = Validar(ret);
            // System.out.println("VERIFICA ETIQUETA");            
       ret.setRe_codemp(rr1.getRe_codemp());
       ret.setRe_datinv(rr1.getRe_datinv());
       ret.setRe_coddep(rr1.getRe_coddep());
       ret.setRe_codder(rr1.getRe_codder());
       ret.setRe_numcon(rr1.getRe_numcon());
       ret.setRe_usucon(rr1.getRe_usucon());
       ret.setRe_datcon(rr1.getRe_datcon());
       ret.setRe_horcon(rr1.getRe_horcon());
       ret.setRe_seqcon(rr1.getRe_seqcon());
       ret.setRe_mate(rr1.getRe_mate());       
       ret.setRe_codpro(rr1.getRe_codpro());
       ret.setRe_lote(rr1.getRe_lote());
       ret.setRe_qtdcon(rr1.getRe_qtdcon());
       ret.setRe_status(rr1.getRe_status());
       ret.setRe_indbip(rr1.getRe_indbip());
       ret.setRe_obsbip(rr1.getRe_obsbip());
       ret.setRe_etiqueta(rr1.getRe_etiqueta());
       ret.setRe_bloqueado(rr1.isRe_bloqueado());
       ret.setContinua(rr1.isContinua());
       ret.setEti_valida(rr1.isEti_valida()); 
       }      
       
       
      listar(ret,"validar etiqueta");
      
       
       
       if(ret.isContinua() == true && ret.isEti_valida() == true){
              // System.out.println("VERIFICA TIPO DE ETIQUETA");
           if(ret.getRe_mate().equals("PR")){
                System.out.println("VERIFICA TIPO DE ETIQUETA - PR");
                TetrefN rr2 = ValidarProduto(ret);

                ret.setRe_codemp(rr2.getRe_codemp());
                ret.setRe_datinv(rr2.getRe_datinv());
                ret.setRe_coddep(rr2.getRe_coddep());
                ret.setRe_codder(rr2.getRe_codder());
                ret.setRe_numcon(rr2.getRe_numcon());
                ret.setRe_usucon(rr2.getRe_usucon());
                ret.setRe_datcon(rr2.getRe_datcon());
                ret.setRe_horcon(rr2.getRe_horcon());
                ret.setRe_seqcon(rr2.getRe_seqcon());
                ret.setRe_mate(rr2.getRe_mate());       
                ret.setRe_codpro(rr2.getRe_codpro());
                ret.setRe_lote(rr2.getRe_lote());
                ret.setRe_qtdcon(rr2.getRe_qtdcon());
                ret.setRe_status(rr2.getRe_status());
                ret.setRe_indbip(rr2.getRe_indbip());
                ret.setRe_obsbip(rr2.getRe_obsbip());
                ret.setRe_etiqueta(rr2.getRe_etiqueta());
                ret.setRe_bloqueado(rr2.isRe_bloqueado());
                ret.setContinua(rr2.isContinua());
                ret.setEti_valida(rr2.isEti_valida());
           } else {
               System.out.println("VERIFICA TIPO DE ETIQUETA - MP");
                TetrefN rr2 = ValidarMateria(ret);

                ret.setRe_codemp(rr2.getRe_codemp());
                ret.setRe_datinv(rr2.getRe_datinv());
                ret.setRe_coddep(rr2.getRe_coddep());
                ret.setRe_codder(rr2.getRe_codder());
                ret.setRe_numcon(rr2.getRe_numcon());
                ret.setRe_usucon(rr2.getRe_usucon());
                ret.setRe_datcon(rr2.getRe_datcon());
                ret.setRe_horcon(rr2.getRe_horcon());
                ret.setRe_seqcon(rr2.getRe_seqcon());
                ret.setRe_mate(rr2.getRe_mate());       
                ret.setRe_codpro(rr2.getRe_codpro());
                ret.setRe_lote(rr2.getRe_lote());
                ret.setRe_qtdcon(rr2.getRe_qtdcon());
                ret.setRe_status(rr2.getRe_status());
                ret.setRe_indbip(rr2.getRe_indbip());
                ret.setRe_obsbip(rr2.getRe_obsbip());
                ret.setRe_etiqueta(rr2.getRe_etiqueta());
                ret.setRe_bloqueado(rr2.isRe_bloqueado());
                ret.setContinua(rr2.isContinua());
                ret.setEti_valida(rr2.isEti_valida());                
           }
       }

       listar(ret,"verifica tipo de etiqueta");
       
       
       
       
       if(ret.isContinua() == true && ret.isEti_valida() == true){
            System.out.println("VERIFICA SE PRODUTO E DEPOSITO ATIVOS");
           TetrefN rr3 = Produtodepositoativo(ret);
           
           
           
                
       ret.setRe_codemp(rr3.getRe_codemp());
       ret.setRe_datinv(rr3.getRe_datinv());
       ret.setRe_coddep(rr3.getRe_coddep());
       ret.setRe_codder(rr3.getRe_codder());
       ret.setRe_numcon(rr3.getRe_numcon());
       ret.setRe_usucon(rr3.getRe_usucon());
       ret.setRe_datcon(rr3.getRe_datcon());
       ret.setRe_horcon(rr3.getRe_horcon());
       ret.setRe_seqcon(rr3.getRe_seqcon());
       ret.setRe_mate(rr3.getRe_mate());       
       ret.setRe_codpro(rr3.getRe_codpro());
       ret.setRe_lote(rr3.getRe_lote());
       ret.setRe_qtdcon(rr3.getRe_qtdcon());
       ret.setRe_status(rr3.getRe_status());
       ret.setRe_indbip(rr3.getRe_indbip());
       ret.setRe_obsbip(rr3.getRe_obsbip());
       ret.setRe_etiqueta(rr3.getRe_etiqueta());
       ret.setRe_bloqueado(rr3.isRe_bloqueado());
       ret.setContinua(rr3.isContinua());
       ret.setEti_valida(rr3.isEti_valida());
       }
      
       
       listar(ret,"verifica se produto e deposito ativos");
       
       
       
       
      if(ret.isContinua() == true && ret.isEti_valida() == true){
             System.out.println("VERIFICA SE PRODUTO ESTA ATIVO " );
            TetrefN rr4 = Produtoativo(ret);
           //bContinua = rr4.isContinua();

     
       ret.setRe_codemp(rr4.getRe_codemp());
       ret.setRe_datinv(rr4.getRe_datinv());
       ret.setRe_coddep(rr4.getRe_coddep());
       ret.setRe_codder(rr4.getRe_codder());
       ret.setRe_numcon(rr4.getRe_numcon());
       ret.setRe_usucon(rr4.getRe_usucon());
       ret.setRe_datcon(rr4.getRe_datcon());
       ret.setRe_horcon(rr4.getRe_horcon());
       ret.setRe_seqcon(rr4.getRe_seqcon());
       ret.setRe_mate(rr4.getRe_mate());       
       ret.setRe_codpro(rr4.getRe_codpro());
       ret.setRe_lote(rr4.getRe_lote());
       ret.setRe_qtdcon(rr4.getRe_qtdcon());
       ret.setRe_status(rr4.getRe_status());
       ret.setRe_indbip(rr4.getRe_indbip());
       ret.setRe_obsbip(rr4.getRe_obsbip());
       ret.setRe_etiqueta(rr4.getRe_etiqueta());
       ret.setRe_bloqueado(rr4.isRe_bloqueado());
       ret.setContinua(rr4.isContinua());
       ret.setEti_valida(rr4.isEti_valida());

      }
       
      
      listar(ret,"verifica se produto esta ativo");
      
      
      
      
       if(ret.isContinua() == true && ret.isEti_valida() == true){
           System.out.println("VERIFICA SE DEPOSITO ESTA ATIVO");
                 TetrefN rr5 = Depositoativo(ret);

                      
       ret.setRe_codemp(rr5.getRe_codemp());
       ret.setRe_datinv(rr5.getRe_datinv());
       ret.setRe_coddep(rr5.getRe_coddep());
       ret.setRe_codder(rr5.getRe_codder());
       ret.setRe_numcon(rr5.getRe_numcon());
       ret.setRe_usucon(rr5.getRe_usucon());
       ret.setRe_datcon(rr5.getRe_datcon());
       ret.setRe_horcon(rr5.getRe_horcon());
       ret.setRe_seqcon(rr0.getRe_seqcon());
       ret.setRe_mate(rr5.getRe_mate());       
       ret.setRe_codpro(rr5.getRe_codpro());
       ret.setRe_lote(rr5.getRe_lote());
       ret.setRe_qtdcon(rr5.getRe_qtdcon());
       ret.setRe_status(rr5.getRe_status());
       ret.setRe_indbip(rr5.getRe_indbip());
       ret.setRe_obsbip(rr5.getRe_obsbip());
       ret.setRe_etiqueta(rr5.getRe_etiqueta());
       ret.setRe_bloqueado(rr5.isRe_bloqueado());
       ret.setContinua(rr5.isContinua());
       ret.setEti_valida(rr5.isEti_valida());
                  }
        
       
      listar(ret,"verifica se depostio esta ativo"); 
      
       
      if(ret.isRe_bloqueado() == true){
//               System.out.println("deposito bloqueado para inventario");
       } else {
                 System.out.println("VERIFICA SE PODE GRAVAR OS DADOS");       
           TetrefN rr6 = Buscalancto(ret);
           
                
       ret.setRe_codemp(rr6.getRe_codemp());
       ret.setRe_datinv(rr6.getRe_datinv());
       ret.setRe_coddep(rr6.getRe_coddep());
       ret.setRe_codder(rr6.getRe_codder());
       ret.setRe_numcon(rr6.getRe_numcon());
       ret.setRe_usucon(rr6.getRe_usucon());
       ret.setRe_datcon(rr6.getRe_datcon());
       ret.setRe_horcon(rr6.getRe_horcon());
       ret.setRe_seqcon(rr6.getRe_seqcon());
       ret.setRe_mate(rr6.getRe_mate());       
       ret.setRe_codpro(rr6.getRe_codpro());
       ret.setRe_lote(rr6.getRe_lote());
       ret.setRe_qtdcon(rr6.getRe_qtdcon());
       ret.setRe_status(rr6.getRe_status());
       ret.setRe_indbip(rr6.getRe_indbip());
       ret.setRe_obsbip(rr6.getRe_obsbip());
       ret.setRe_etiqueta(rr6.getRe_etiqueta());
       ret.setRe_bloqueado(rr6.isRe_bloqueado());
       ret.setContinua(rr6.isContinua());
       ret.setEti_valida(rr6.isEti_valida());
      }
      
      
      listar(ret,"verifica se pode gravar");
      
      if(ret.isRe_bloqueado() == false && ret.isContinua() == true ){  
              
            if(ret.isEti_valida() == false){
                ret.setRe_codpro("vazio");
                ret.setRe_lote("sem");
                ret.setRe_qtdcon("0");
                ret.setRe_indbip("N");
                ret.setRe_codder(".");
                ret.setRe_obsbip("Etiqueta Invalida");
            }
          
            
            
      
        if(ret.getRe_codpro()== null){
             
             System.out.println(" null testando campo codigo do produto vazio");
             ret.setRe_codpro("vazio");
                ret.setRe_qtdcon("0");
         }
         
         
         if(ret.getRe_codpro().isEmpty()){
             
             System.out.println("empty  testando campo codigo do produto vazio");
             ret.setRe_codpro("vazio");
                ret.setRe_qtdcon("0");
         }
          
            listar(ret,"apos verificar campo vazio");
       
       
            TetrefN rr7 = inserirDados(ret);
            ret.setRe_obsbip(rr7.getRe_obsbip());       


            ret.setRe_codemp(rr7.getRe_codemp());
            ret.setRe_datinv(rr7.getRe_datinv());
            ret.setRe_coddep(rr7.getRe_coddep());
            ret.setRe_codder(rr7.getRe_codder());
            ret.setRe_numcon(rr7.getRe_numcon());
            ret.setRe_usucon(rr7.getRe_usucon());
            ret.setRe_datcon(rr7.getRe_datcon());
            ret.setRe_horcon(rr7.getRe_horcon());
            ret.setRe_seqcon(rr7.getRe_seqcon());
            ret.setRe_mate(rr7.getRe_mate());       
            ret.setRe_codpro(rr7.getRe_codpro());
            ret.setRe_lote(rr7.getRe_lote());
            ret.setRe_qtdcon(rr7.getRe_qtdcon());
            ret.setRe_status(rr7.getRe_status());
            ret.setRe_indbip(rr7.getRe_indbip());
            ret.setRe_obsbip(rr7.getRe_obsbip());
            ret.setRe_etiqueta(rr7.getRe_etiqueta());
            ret.setRe_bloqueado(rr7.isRe_bloqueado());
            ret.setContinua(rr7.isContinua());
            ret.setEti_valida(rr7.isEti_valida());
      }          
      
      
      
//      
//      System.out.println("dados de retorno");
//      System.out.println("retorna " + ret.getRe_etiqueta() );
//      System.out.println("cod produto " + ret.getRe_codpro());
//      System.out.println("lote " + ret.getRe_lote());
//      System.out.println("qtdade " + ret.getRe_qtdcon());
//      System.out.println("status " + ret.getRe_status());
//      System.out.println("obsev "+ ret.getRe_obsbip());
//      
            
      
      Tetref items = new Tetref(ret.getRe_etiqueta(),
                     ret.getRe_codpro(),
                     ret.getRe_lote(),
                     ret.getRe_qtdcon(),
                     ret.getRe_status(),
                     ret.getRe_obsbip());   
                 listaDados.add(items); 
            return listaDados;       
   }
   
   
   public static TetrefN Tbloqueio(TetrefN dados1) throws ClassNotFoundException, SQLException{
       return EtiquetaDB.selectBloqueio(dados1);
   }
   
   public static TetrefN Validar(TetrefN dados1){
       return EtiquetaDB.validarCarac(dados1);
   }
   
   public static TetrefN ValidarProduto(TetrefN dados1) throws ClassNotFoundException{
       return EtiquetaDB.validaPR(dados1);
   }
   
   public static TetrefN ValidarMateria(TetrefN dados1) throws ClassNotFoundException{
       return EtiquetaDB.validaMP(dados1);
   }
   
   public static TetrefN Produtodepositoativo(TetrefN dados1) throws ClassNotFoundException{
       return EtiquetaDB.ProdutoDepositoAtivo(dados1);
   }
   
  public static TetrefN Produtoativo(TetrefN dados1) throws ClassNotFoundException{
       return EtiquetaDB.ProdutoAtivo(dados1);
   }
   
  public static TetrefN Depositoativo(TetrefN dados1) throws ClassNotFoundException{
       return EtiquetaDB.DepositoAtivo(dados1); 
   }
   
   public static TetrefN Buscalancto(TetrefN dados1) throws ClassNotFoundException{
       return EtiquetaDB.BuscaLancto(dados1); 
   }
   
   public static TetrefN inserirDados(TetrefN dados1) throws ClassNotFoundException, SQLException{
       return EtiquetaDB.save(dados1);  
   }
   
   public static void listar(TetrefN ret1,String texto){
        System.out.println("");
        System.out.println("");
        System.out.println("");
       System.out.println(texto);
       System.out.println("codemp " + ret1.getRe_codemp());
       System.out.println("datinv " + ret1.getRe_datinv());
       System.out.println("coddep " + ret1.getRe_coddep());
       System.out.println("coddep " + ret1.getRe_codder());
       System.out.println("numcon " + ret1.getRe_numcon());
       System.out.println("usucon " + ret1.getRe_usucon());
       System.out.println("datcon " + ret1.getRe_datcon());
       System.out.println("horcon " + ret1.getRe_horcon());
       System.out.println("seqcon " + ret1.getRe_seqcon());
       System.out.println("horcon " + ret1.getRe_mate());
       System.out.println("horcon " + ret1.getRe_codpro());
       System.out.println("horcon " + ret1.getRe_lote());
       System.out.println("horcon " + ret1.getRe_qtdcon());
       System.out.println("horcon " + ret1.getRe_status());
       System.out.println("indbip " + ret1.getRe_indbip());
       System.out.println("obsbip " + ret1.getRe_obsbip());
       System.out.println("codpro " + ret1.getRe_etiqueta());
        System.out.println("indbip " + ret1.getRe_indbip());
       System.out.println(ret1.isContinua());
       System.out.println(ret1.isEti_valida());
       
   }
   
   
   
   
   
   
   
   /*
   public static TetrefN Tbloqueio(String qdata,String qdeposito) throws ClassNotFoundException{
       return EtiquetaDB.selectBloqueio(qdata, qdeposito);
   }
   
   public static TetrefN Validar(String etiqueta){
       return EtiquetaDB.validarCarac(etiqueta);
   }
   
   public static TetrefN ValidarProduto(String etiqueta) throws ClassNotFoundException{
       return EtiquetaDB.validaPR(etiqueta);
   }
   
   public static TetrefN ValidarMateria(String etiqueta) throws ClassNotFoundException{
       return EtiquetaDB.validaMP(etiqueta);
   }
   
   public static TetrefN Produtodepositoativo(String data,String deposito,String prod) throws ClassNotFoundException{
       return EtiquetaDB.ProdutoDepositoAtivo(data,deposito,prod);
   }
   
  public static TetrefN Produtoativo(String prod) throws ClassNotFoundException{
       return EtiquetaDB.ProdutoAtivo(prod);
   }
   
  public static TetrefN Depositoativo(String depo,String prod) throws ClassNotFoundException{
       return EtiquetaDB.DepositoAtivo(depo, prod); 
   }
   
   public static TetrefN Buscalancto(String data,String deposito,String produto,String etiqueta,int contagem) throws ClassNotFoundException{
       return EtiquetaDB.BuscaLancto(data, deposito, produto, etiqueta, contagem); 
   }
   
   public static TetrefN inserirDados(TetrefN dados1) throws ClassNotFoundException, SQLException{
       return EtiquetaDB.save(dados1);  
   }
   */
    
}