package controller;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;
import model.TetrefDaoN; // ou crie um DAO específico para o duplo check

public class TrataClienteFlutter extends Thread {
    private Socket socket;
    private int idUnico;

    public TrataClienteFlutter(Socket socket, int idUnico) {
        this.socket = socket;
        this.idUnico = idUnico;
    }

    @Override
    public void run() {
        System.out.println("Cliente Flutter (Duplo Check) conectado: " + idUnico);
        try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"))) {
            String linha;
            while ((linha = in.readLine()) != null) {
                if (linha.trim().isEmpty()) continue;

                System.out.println("Recebido do Flutter: " + linha);
                //Java: Clean Java Language Server Workspace
                // Parse do JSON enviado pelo Flutter
                JsonObject json = new com.google.gson.JsonParser().parse(linha).getAsJsonObject();


                if (json.has("tipo") && "DUPLA_CHECAGEM".equals(json.get("tipo").getAsString())) {
                    String usuario = json.get("usuario").getAsString();
                    String primeiroCodigo = json.get("primeiro_codigo").getAsString();
                    String segundoCodigo = json.get("segundo_codigo").getAsString();
                    String codigoBase = json.get("codigo_base").getAsString();

                    // Chame seu DAO para gravar no banco
                    TetrefDaoN dao = new TetrefDaoN();
                    // dao.salvarDuplaChecagem(usuario, primeiroCodigo, segundoCodigo, codigoBase);
                    
                    System.out.println("Duplo Check registrado com sucesso para a base: " + codigoBase);
                }
            }
        } catch (Exception e) {
            System.out.println("Cliente Flutter " + idUnico + " desconectou: " + e.getMessage());
        } finally {
            try {
                socket.close();
            } catch (Exception e) {}
        }
    }
}