package util;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 *
 * @author wilson
 */
public class ExportarTXT {
    public static void ExportaTXT(String pcaminho, String texto){
        try(
              FileWriter criaArq = new FileWriter(pcaminho,true)  ;
              BufferedWriter buffer = new BufferedWriter(criaArq); 
              PrintWriter  escritorArq = new PrintWriter(buffer);
                
                ){
            escritorArq.append(texto);
        }catch(IOException e){
            e.printStackTrace();
        }
        
    }
    
}
