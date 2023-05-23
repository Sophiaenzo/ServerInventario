package controller;

import static formularios.FormPrincipal.AddLinhaTable;
import static formularios.FormPrincipal.DelLinhaTable;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.time.LocalTime;
import java.util.ArrayList;
import model.R999usuDao;
import model.Te220conDao;
import model.Te220consDao;
import model.Te220csvDao;
import model.Te220invDao;
import model.Te220invcDao;
import model.Te220iteDao;
import model.Te220txtDao;
import model.TetrefDao;
import modelDominio.R999usu;
import modelDominio.Te220con;
import modelDominio.Te220cons;
import modelDominio.Te220csv;
import modelDominio.Te220inv;
import modelDominio.Te220invc;
import modelDominio.Te220ite;
import modelDominio.Te220sal;
import modelDominio.Te220txt;
import modelDominio.Tetref;
import util.Funcoes;
import util.Operacoes;
import util.PropertiesUtil;

/**
 * Classe responsavel por controlar as conexoes dos clientes
 * efetuando as requisicoes solicitadas
 * @author wilson.simoes
 */
public class TrataClienteController extends Thread {
   
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private Socket s;
    private int idUnico;
    
         String data    ;
         String deposito;
         String codigo  ;
         String tipo    ;
         String codusu  ;
         String depo    ;
         int    cont    ;
         boolean bloq   ;
         
    
    
    
    Funcoes funcoes = new Funcoes();
    
       /**
        * classe responsavel pelo controle da entrada e saida de informacoes
        * @param in dados de entrada
        * @param out dados de saida
        * @param s  socket
        * @param idUnico ID de controle das conexoes
        */
       public TrataClienteController(ObjectInputStream in, ObjectOutputStream out, Socket s, int idUnico) {
        this.in = in;
        this.out = out;
        this.s = s;
        this.idUnico = idUnico;
    }

    @Override
    public void run() {
     LocalTime localTime = LocalTime.now();
       
       String comando;
       System.out.println("Esperando comandos do cliente " + idUnico + s.getInetAddress());                      
       AddLinha(Integer.toString(idUnico), s.getInetAddress().toString(), s.getInetAddress().getHostName());
       
       try {
           Operacoes m     = (Operacoes) in.readObject();
           comando         = m.getOperacao();
           data     = (String) m.getParam("data");
           deposito = (String) m.getParam("deposito");
           codigo   = (String) m.getParam("codigo");
           tipo     = (String) m.getParam("tipo"); 
           codusu   = (String) m.getParam("codusu"); 
       
           
           while(!comando.equalsIgnoreCase("fim")){
            System.out.println("Cliente "+ idUnico+" enviou o comando " + comando );
            
            if (comando.equalsIgnoreCase("enchecombo")){
                //preencher combo com os departamentos
                Te220invDao dadosDao = new Te220invDao();                   
                ArrayList<Te220inv> listaDeposito = dadosDao.getListaDados(data);  
                out.writeObject(listaDeposito);
            }else if(comando.equalsIgnoreCase("leituraInventario")){
                 //preencher combo com os departamentos
                Te220conDao dadosDao = new Te220conDao();                   
                ArrayList<Te220con> listaLeitura = dadosDao.getListaDados(data);  
                out.writeObject(listaLeitura);
            }else if(comando.equalsIgnoreCase("verificausuario")){
                R999usuDao dadosDao = new R999usuDao();                   
                ArrayList<R999usu> listaUsuario = dadosDao.getListaUsu(data);  
                out.writeObject(listaUsuario);
            }else if(comando.equalsIgnoreCase("verificaSaldo")){
                 //verifica saldo estoque
                Te220invDao dadosDao = new Te220invDao();                   
                ArrayList<Te220sal> listaSaldo = dadosDao.getListaSaldo(tipo, data);
                out.writeObject(listaSaldo);
            }else if(comando.equalsIgnoreCase("exportartxt")){                 
                Te220txtDao dadosDao = new Te220txtDao();                   
                ArrayList<Te220txt> listaLeitura = dadosDao.getListaDados(data,tipo);  
                out.writeObject(listaLeitura);                 
            }else if(comando.equalsIgnoreCase("exportarcsv")){
                  Te220csvDao dadosDao = new Te220csvDao();                   
                  ArrayList<Te220csv> listaLeitura = dadosDao.getListaDados(data,tipo);  
                 out.writeObject(listaLeitura);              
            }else if(comando.equalsIgnoreCase("leretiqueta")){
                  TetrefDao dadosDao = new TetrefDao();                   
                  ArrayList<Tetref> listaLeitura = dadosDao.getListaDados(data,deposito,codigo,tipo,codusu);
                 out.writeObject(listaLeitura);   
            }else if(comando.equalsIgnoreCase("aaabloqueio")){
                 /*verificar foi substituda pela funcao bloqueio*/
                  funcoes.SaveProp("inventario.bloqueado",data);                   
            }else if(comando.equalsIgnoreCase("verificacontagem")){
                //verifica saldo na contagem
                Te220iteDao dadosDao = new Te220iteDao();                   
                ArrayList<Te220ite> listaSaldo = dadosDao.getListaSaldo(data,tipo);
                out.writeObject(listaSaldo);                 
            }else if(comando.equalsIgnoreCase("versaoatual")){
                  String versao = PropertiesUtil.getProperty("app.versao");
                                   
                  out.writeObject(versao);                  
            }else if(comando.equalsIgnoreCase("versaopath")){
                  String versao = PropertiesUtil.getProperty("app.versaopath");
                             
                  out.writeObject(versao);
             }else if(comando.equalsIgnoreCase("depositoscontagem")){ 
               
                  Te220invcDao dadosDao = new Te220invcDao();                   
                  ArrayList<Te220invc> getLanca = dadosDao.getLanca(data);  
                  out.writeObject(getLanca);
                                                           
             }else if(comando.equalsIgnoreCase("proximacontagem")){
                            
                 //preencher ultima contagem deinventario por deposito
                 Te220consDao dadosDao = new Te220consDao();   
                 ArrayList<Te220cons> getConta = dadosDao.getContagem(data, deposito);
                 out.writeObject(getConta); 
                 
                 
            }else if(comando.equalsIgnoreCase("bloqueio")){                
                   tipo     = (String) m.getParam("tipo");         
                   data     = (String) m.getParam("data");
                   deposito = (String) m.getParam("deposito");
                   cont     = (int) m.getParam("cont"); 
                   bloq     = (boolean) m.getParam("bloq");               
                 Te220invcDao dadosDao = new Te220invcDao();                   
                 ArrayList<Te220invc> listaBloqueio = dadosDao.getBloqueio(data,deposito,cont,bloq);                           
            }else{
                   //comando invalido
                   out.writeObject("nok");
            }
            m =(Operacoes) in.readObject();
            comando  = m.getOperacao();
            data     = (String) m.getParam("data");
            deposito = (String) m.getParam("deposito");
            codigo   = (String) m.getParam("codigo");
            tipo     = (String) m.getParam("tipo");       
            codusu   = (String) m.getParam("codusu");       
           }           
       } catch(Exception e) {
           System.out.println("Cliente " + idUnico + " desconectou");
       }
       
       
       try {
            DelLinha(Integer.toString(idUnico));
             in.close();
             out.close();       
       }catch(Exception e){
            System.out.println("Cliente " + idUnico + " desconectou");
       }
    } 
    /**
     * clas que inclui uma linha na tela de listagem dos lancamentos
     * nao afeta a tabela de dados oficial
     * @param idCliente
     * @param Address
     * @param local 
     */
    public void AddLinha(String idCliente, String Address , String local){
       AddLinhaTable(new Object[]{
           idCliente,
         Address,
         local,       
         });    
    }   
   /**
    * class que deleta uma linha da tela de listagem dos lancamentos
    * nao afeta a tabela de dados oficial
    * @param idcampo 
    */
   public void DelLinha(String idcampo){
        DelLinhaTable(idcampo);   
       }
   }
    