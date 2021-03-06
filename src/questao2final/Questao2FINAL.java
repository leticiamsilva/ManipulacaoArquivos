﻿package questao2final;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author Lelê
 */
public class Questao2FINAL {

    public static void main(String[] args) throws IOException {
        String arquivoEscrita = "C:\\Users\\Lelê\\Documents\\bs\\ordenado.csv";
        String arquivoBolsaMes1 = "C:\\Users\\Lelê\\Documents\\bs\\201901_BolsaFamiliaT.csv";
        File f1 = new File(arquivoBolsaMes1);

        ArrayList<Beneficiario> mes;

        mes = ordenarArquivo(f1);
        
        BufferedWriter br = new BufferedWriter(new FileWriter(arquivoEscrita));
        StringBuilder sb = new StringBuilder();
        
        for(Beneficiario b: mes)
        {
            for(String s : b.escreveBeneficiarioLinha())
            {
                sb.append(s);
                sb.append(";");
            }
            sb.append("\n");  
        }
        br.write(sb.toString());
        br.close();

         System.out.println(mes.get(9).escreveBeneficiarioLinha()[3]);
    }

    private static ArrayList<Beneficiario> ordenarArquivo(File arquivo) throws IOException //utiliza o getFilePointer
    {
        ArrayList<Beneficiario> listaBeneficiarios = new ArrayList<Beneficiario>();

        Beneficiario b;
        RandomAccessFile rf = new RandomAccessFile(arquivo, "r");
        rf.seek(0);

        String linha = rf.readLine();

        if (linha == null) {
            return new ArrayList<Beneficiario>();
        }

        linha = rf.readLine();

        while (linha != null) {

            b = new Beneficiario();
            b.leBeneficiario(linha);
            listaBeneficiarios.add(b);

            linha = rf.readLine();

        }
        rf.close();

        Collections.sort(listaBeneficiarios, new comparaBeneficiario());

        return listaBeneficiarios;
    }

}
