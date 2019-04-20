
package intercalacep;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.io.BufferedWriter;
import java.util.logging.Level;
import java.io.RandomAccessFile;
import java.util.logging.Logger;
import java.io.FileNotFoundException;

/**
 *
 * @author Lelê
 */
public class IntercalaCEP {

    public static void main(String[] args) throws IOException 
    {
        int qtd_arquivos = 8;
        String arquivo_origem = "C:\\Users\\Lelê\\Documents\\cep.dat";
        String caminho_saida = "C:\\Users\\Lelê\\Documents\\arquivo";        
        
        separarArquivos(arquivo_origem, caminho_saida, qtd_arquivos);
        
        //ordenarArquivo(new File(caminho_saida + 1 + ".dat"));
    }

    private static void separarArquivos(String arquivo_origem, String arquivo_saida, int qtd_arquivos) //utiliza o tamanho dos registros
    {        
        try
        {
            Endereco e = new Endereco();
            
            BufferedWriter bw;
            File arquivo_escrita;
            RandomAccessFile f = new RandomAccessFile (arquivo_origem, "r");
            RandomAccessFile f_escrita;
            f.seek(0);
            
            long registros_por_arquivo = f.length() / qtd_arquivos;
            long registros_a_salvar = registros_por_arquivo;
           
             String stop;
            for(int i=1; i <= qtd_arquivos; i++)
            {
                arquivo_escrita = new File(arquivo_saida + i + ".dat"); // essa linha está aqui para o nome do arquivo ser igual ao i;
                f_escrita = new RandomAccessFile (arquivo_escrita, "rw"); 
                
                //while(i>2)
                while(f.getFilePointer() < registros_a_salvar)                
                {
                    e.leEndereco(f);                    
                    e.escreveEndereco(f_escrita);      
                 //209792100
                }
                f_escrita.close();
                
                ordenarArquivo(arquivo_escrita);                
                
                if(i == (qtd_arquivos - 1)) //se for o último arquivo, talvez seu tamanho seja maior ou menor que o registros_por_arquivo 
                    registros_a_salvar = f.length();
                else
                    registros_a_salvar += registros_por_arquivo;
                
                System.out.println(i);
            }
                          
        }
        catch(Exception ex)
        {
            System.out.println("Nao foi possivel ler o arquivo");
        }
    }

    private static void ordenarArquivo(File arquivo_escrita) throws IOException  //utiliza o getFilePointer
    {        
        RandomAccessFile f = new RandomAccessFile (arquivo_escrita, "r");  
        Endereco e;
        
        ArrayList<Endereco> listaEnderecos = new ArrayList<Endereco>();
        
        long tamanho_arquivo = arquivo_escrita.length();
        
        while(f.getFilePointer() < tamanho_arquivo) 
        {         
           e = new Endereco();
           e.leEndereco(f);
           listaEnderecos.add(e);                
        }
        f.close();
        
        Collections.sort(listaEnderecos, new ComparaCEP());        
      
        arquivo_escrita.delete();
        RandomAccessFile f_escrita = new RandomAccessFile (arquivo_escrita, "rw");  
        for(Endereco end : listaEnderecos)
        {
           end.escreveEndereco(f_escrita);
        }
        f_escrita.close();
        
    }        

    private static void juntarArquivos(String caminho_arquivos, int qtd_arquivos) throws FileNotFoundException, IOException 
    {
        Endereco e1 = new Endereco();
        Endereco e2 = new Endereco();
        
        int i=1;              
        
        while(i<(qtd_arquivos-1))
        {            
            RandomAccessFile f1 = new RandomAccessFile (caminho_arquivos+i+".dat", "r");
            RandomAccessFile f2 = new RandomAccessFile (caminho_arquivos+(i+1)+".dat", "r");
            f1.seek(0);
            f2.seek(0);
            
            e1.leEndereco(f1);
            e2.leEndereco(f2);
            
            RandomAccessFile arquivo_saida = new RandomAccessFile(caminho_arquivos + (qtd_arquivos+1) + ".dat", "rw");
                       
            int j1=0, j2=0; //zerar sempre para a contagem do tamaho dos arquivos
            
            while(j1<=f1.length() && j2<=f2.length())
            {                 
                if(e1.getCep().compareTo(e2.getCep()) < 0) //e1 menor que e2
                {
                    //bw.write(e1.getEnderoCompleto());
                    e1.escreveEndereco(arquivo_saida);
                    e1.leEndereco(f1);
                    j1 += 300;
                }
                else
                {
                   //bw.write(e2.getEnderoCompleto());
                   e2.escreveEndereco(arquivo_saida);
                   e2.leEndereco(f2);
                   j2 += 300;
                }
                
            }
            
            if(j1<f1.length())
            {
                while(j1<=f1.length())
                {                    
                    e1.escreveEndereco(arquivo_saida);
                    e1.leEndereco(f1);
                    j1 += 300;
                }
                
            }
            
            if(j2<f2.length())
            {
               while(j2<=f2.length())
               {
                    e2.escreveEndereco(arquivo_saida);
                    e2.leEndereco(f2);
                    j2 += 300; 
               } 
            }            
            
            qtd_arquivos++;
            i += 2;
        }
    }    
}
