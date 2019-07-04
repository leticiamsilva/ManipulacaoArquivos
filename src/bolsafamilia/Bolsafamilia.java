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
        String arquivoBolsaMes1 = "C:\\Users\\Lelê\\Documents\\bs\\201901_BolsaFamilia.csv";
        File f1 = new File(arquivoBolsaMes1);

        String arquivoBolsaMes2 = "C:\\Users\\Lelê\\Documents\\bs\\201902_BolsaFamilia.csv";
        File f2 = new File(arquivoBolsaMes2);

        ArrayList<Beneficiario> mes1, mes2;

        mes1 = ordenarArquivo(f1);
        mes2 = ordenarArquivo(f2);

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

    private static ArrayList<Beneficiario> ordenarArquivo(File arquivo) throws IOException //utiliza o getFilePointer
    {
        ArrayList<Beneficiario> listaBeneficiarios = new ArrayList<Beneficiario>();
        
        Beneficiario b;
        RandomAccessFile rf = new RandomAccessFile(arquivo, "r");
        rf.seek(0);

        String linha = rf.readLine();
        
        if(linha == null)
            return new ArrayList<Beneficiario>();
        
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

    
    private static ArrayList<Beneficiario>  BeneficiariosReceberamMes1Mes2(ArrayList<Beneficiario> b1, ArrayList<Beneficiario> b2)
    {
        ArrayList<Beneficiario> recebeuMes1Mes2 = new ArrayList<>();
     
       int i=0, j=0;
        
       while(i < b1.size() && j < b2.size())
       {
           if(b1.get(i).nis.compareTo(b2.get(j).nis) == 0) //se igual adiciona e passa pros próximos
           {
               recebeuMes1Mes2.add(b1.get(i));
               i++;
               j++;
           }           
              
           else if(b1.get(i).nis.compareTo(b2.get(j).nis) < 0 ) //-1 se b1 menor que b2 => le o menor, no caso b1
            i++;   
           
           else
               j++;           
       }        
        
        return recebeuMes1Mes2;
    }
}
