package util;

import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author wilson
 */
public class ExportarCSV {
    
     
    public void Exportaccc(String usu_codemp, String usu_datinv, String usu_coddep, String usu_codpro, String usu_qdtcon,String usu_usucon,String usu_datcon, String url) {        
         Criacsv(url);
        try {
            List<String> existentes = LinhaExistentes(url);
            String linha = usu_codemp + ";" + usu_datinv+ ";" + usu_coddep+ ";" + usu_codpro+ ";" +usu_qdtcon+ ";" + usu_usucon + ";" + usu_datcon+ ";" ;
            existentes.add(linha);

            // adicionar no csv
            String todasLinhas = UnicaLinha(existentes);
            FileWriter arquivo = new FileWriter(url);
            arquivo.write(todasLinhas);
            arquivo.close();

        } catch (Exception error) {
            System.out.println("Erro ao gerar o arquivo " + url);
        }
    }

    public String UnicaLinha(List<String> existentes) {

        String unicaLinha = "";
        for (String linha : existentes) {
            unicaLinha += linha + " \n";
        }
        return unicaLinha;
    }
    
    
    public void Criacsv(String url){
    try {
            File arquivo = new File(url);
            arquivo.createNewFile();
        } catch (Exception erro) {
            System.out.println("Erro ao criar arquivo.");
        }
    }
    
    public List<String> LinhaExistentes(String url) {

        List<String> result = new ArrayList<String>();

        try {
            Path path = Paths.get(url);
            result = Files.readAllLines(path);
        } catch (Exception erro) {
            System.out.println("Erro ao ler o arquivo.");
        }

        return result;
    }
    
    
    
}
