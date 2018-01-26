package br.com.android.androidbasico.servico;

import android.support.annotation.Nullable;

import java.io.IOException;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by JHUNIIN on 14/01/2018.
 */

public class WebClient {

    public String post(String json){
        String endereco = "https://www.caelum.com.br/mobile";;
        return realizaRequisicao(json, endereco);
    }

    public void insere(String json) {
        String endereco = "http://192.168.0.11:8080/api/aluno";
        realizaRequisicao(json, endereco);
    }

    @Nullable
    private String realizaRequisicao(String json, String endereco) {
        URL url;
        HttpURLConnection connection;
        try {
            url = new URL(endereco);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("Content-type", "application/json");
            connection.setRequestProperty("Accept", "application/json");

            connection.setDoOutput(true);
            PrintStream output = new PrintStream(connection.getOutputStream());
            output.print(json);

            connection.connect();
            Scanner scanner = new Scanner(connection.getInputStream());
            String resposta = scanner.next();
            return resposta;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }


}
