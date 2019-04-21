package br.com.lucaophp.cardenetatopografica;

/**
 * Created by lucao on 18/09/2017.
 */

import android.annotation.TargetApi;
import android.os.Build;

import java.io.*;
import java.net.*;

public class Client {
    private static String lerArquivo(String path){
        String conteudo="";
        try {
            InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(path));
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String linha = "";
            while((linha = bufferedReader.readLine())!=null){
                conteudo+=linha+"\n";

            }
            inputStreamReader.close();
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return conteudo;
    }

    public static void send(String path, String host,TransferenciaActivity activity) throws IOException {
        try (Socket clientSocket = new Socket(host, 9999)) {
            DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
            BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String conteudo = Client.lerArquivo(path);
            File f = new File(path);
            String nameArq = f.getName();
            outToServer.writeBytes(String.format("%s\n", nameArq));
            outToServer.writeBytes(String.format("%s\n", conteudo));

        } catch (IOException ioe) {
            ioe.printStackTrace();

        }
    }
}