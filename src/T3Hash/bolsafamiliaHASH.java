package bolsafamilia;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author Lelê
 */
public class Bolsafamilia {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        String arquivoBolsaMes1 = "C:\\Users\\Lelê\\Documents\\bs\\201901_BolsaFamiliaT.csv";
        File f1 = new File(arquivoBolsaMes1);

        String arquivoBolsaMes2 = "C:\\Users\\Lelê\\Documents\\bs\\201801_BolsaFamiliaT.csv";
        File f2 = new File(arquivoBolsaMes2);

        ArrayList<Beneficiario> mes1, mes2;

        mes1 = salvarHASH(f1);
        mes2 = salvarHASH(f2);

        ArrayList<Beneficiario> beneficiariosAmbosMeses = BeneficiariosReceberamMes1Mes2(mes1, mes2);
        
        System.out.println("As pessoas que receberam em ambos os meses foram: "); 
        for (Beneficiario b : beneficiariosAmbosMeses)
        {
            System.out.println(b.nome);
        }
        
        System.out.println("O maior valor de parcela recebida no mês 1 de 2019 foi: " + Beneficiario.maiorValor(mes1));
        System.out.println("O maior valor de parcela recebida no mês 2 de 2019 foi: " + Beneficiario.maiorValor(mes2));
        
        System.out.println("A diferenca gasta entre o mes de janeiro e de fevereiro em 2019 foi: " + (Beneficiario.somaParcelas(mes1) - Beneficiario.somaParcelas(mes2)));
    }



     //EM UM CENÁRIO REAL, O IDEAL SERIA SEPARAR EM VÁRIOS ARQUIVOS. Esse código já existe no diretório corrente
    private static void salvarHASH(File arquivo) throws IOException //utiliza o getFilePointer
    {
        ArrayList<Beneficiario> listaBeneficiarios = new ArrayList<Beneficiario>();
        
        Beneficiario b;
        RandomAccessFile rf = new RandomAccessFile(arquivo, "r");
        rf.seek(0);
        
        File f_escrita = new File("C:\\Users\\Lelê\\Documents\\bs\\201801_BolsaFamiliaHASH.csv");
        RandomAccessFile rf_escrita = new RandomAccessFile(f_escrita, "rw");
        
        int posicao = 0;

        String linha = rf.readLine();
        String nis_arquivo, posicao_arquivo;
        
        if(linha == null)
            return new ArrayList<Beneficiario>();
        
        BeneficiarioHASH bh = new BeneficiarioHASH();
        
        linha = rf.readLine();        

        while (linha != null) 
        {             
            posicao++;
            
            b = new Beneficiario();
            b.leBeneficiario(linha);
            
            bh.nis = b.nis;
            bh.posicao = Integer.toString(posicao);
            
            bh.escreveHash(rf_escrita);            
            
            linha = rf.readLine();           
        }
        rf.close();
        rf_escrita.close();
       
    }
 }