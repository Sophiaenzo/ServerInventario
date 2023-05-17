package util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.Properties;

/**
 *
 * @author wilson
 */
public class Funcoes {    
    public static Properties prop = new Properties();
    public void SaveProp(String title, String value) {
        try
        {
            prop.setProperty(title, value);
            prop.store(new FileOutputStream("src/config.properties"),null);
     
        }catch(IOException e){
            System.out.println("erro " + e.getMessage());
        }        
    }
   
    /*
    public static String GetProp(String title)
    {
        String value = "";
        try
        {
           prop.load(new FileInputStream("src/properties/config.properties"));
           value = prop.getProperty(title);          
           
        }catch(IOException e)
        {
            
        }
        return value;
    }
    */
    /**
     * class para preencher a esquerda com u carcater especifico
     * @param valueToPad - tamanho da string
     * @param filler     - caracter com o qual que sseja preenchido
     * @param size       - tamanho das repeticoes
     * @return 
     */
    public static String lpad(String valueToPad, String filler, int size) {
        while (valueToPad.length() < size) {
            valueToPad = filler + valueToPad;
        }
        return valueToPad;
    }
    /**
     * class para preencher a direita com u carcater especifico
     * @param valueToPad - tamanho da string
     * @param filler     - caracter com o qual que sseja preenchido
     * @param size       - tamanho das repeticoes
     * @return 
     */
     public static String rpad(String valueToPad, String filler, int size) {
        while (valueToPad.length() < size) {
            valueToPad = valueToPad+filler;
        }
        return valueToPad;
    }
     /**
      * class para formatar com 0 um campo numerico
      * @param numero tamanho da string
      * @return 
      */
    public static String formatarFloat(float numero){
        String retorno = "";
        DecimalFormat formatter = new DecimalFormat("000,000,000.00000");
        try{
            retorno = formatter.format(numero);
        }catch(Exception ex){
            System.err.println("Erro ao formatar numero: " + ex);
        }
        return retorno;
    }
    /**
     * class para formatar um string com valor
     * @param numero
     * @return 
     */
    public static String formatarFloat0(float numero){
        String retorno = "";
        DecimalFormat formatter = new DecimalFormat("##0.00");
        try{
            retorno = formatter.format(numero);
        }catch(Exception ex){
            System.err.println("Erro ao formatar numero: " + ex);
        }
        return retorno;
    }
    /**
     * class para formatar hma data retornando dia mes e ano
     * @param data - campo data a ser formatada
     * @return
     * @throws ParseException 
     */
    public static Date mydata(String data) throws ParseException{
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");        
        return sdf.parse(data);  
    }
    /**
     * class para retornar o dia do mes em numero
     * @param data - campo data 
     * @return 
     */
    
    public static int diames(String data){
        LocalDate currentDate = LocalDate.parse(data);
        int dia = currentDate.getDayOfMonth();
        System.out.println(dia); 
        return dia;
    }
   /**
    * class que retorna parte da data 
    * @param date - campo data que deve ser retornado a parte desejada
    * @param tipo - informar o tipo de retorno - D -> dia, M-> Mes ou A->ano
    * @return Dia , Mes ou Ano
    */   
   public static String parteData(String date, String tipo){
        String retorno;
        String dateParts[] = date.split("/");        
        String day = dateParts[0];
        String month = dateParts[1];
        String year = dateParts[2];
 
        if (tipo == "D")
            {
                retorno = day;
            } else if (tipo == "M") {
                retorno = month;
            } else {
                retorno = year;
            }
        return retorno;
      }   
    
   /**
    * class que retorna data atual ou hora atual
    * @param tipo - H retorna hora atual / D retorna data atual
    * @return 
    */
   public static String DataHoraAtual(String tipo){
        String data = "dd/MM/yyyy";
        String hora = "hh:mm:ss";
        String data1, hora1;
        java.util.Date agora = new java.util.Date();
        SimpleDateFormat formata = new SimpleDateFormat(data);
        data1 = formata.format(agora);							
        formata = new SimpleDateFormat(hora);
        hora1 = formata.format(agora);
        if(tipo.equals("H")){
            return hora1;
        }else{
          return data1;  
        }
   }
   
    public static void gravaBloqueio(String tipo){
       Properties prop = new Properties();
       try{
       prop.setProperty("bloqueio",tipo);
       prop.store(new FileOutputStream("src/properties/dadosBloqueio.properties"),null);
       }catch(IOException e){
           System.out.println("erro - > " + e.getMessage());
       }      
      
   }
    
   public static String getBloqueio() throws FileNotFoundException, IOException{
       String retorno = null;
       Properties prop = new Properties();
       FileInputStream file = new FileInputStream("src/properties/dadosBloqueio.properties");
       prop.load(file);
       retorno  = prop.getProperty("bloqueio"); 
       return retorno;
       
   }
    
    
}
