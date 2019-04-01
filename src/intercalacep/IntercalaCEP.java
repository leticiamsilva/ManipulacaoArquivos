/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package intercalacep;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 *
 * @author Lelê
 */
public class IntercalaCEP {

    public static void main(String[] args) 
    {
        int qtd_arquivos = 8;
        String arquivo_origem = "C:\\Users\\Lelê\\Documents\\cep.dat";
        String caminho_saida = "C:\\Users\\Lelê\\Documents\\arquivo";        
        
        separaArquivos(arquivo_origem, caminho_saida, qtd_arquivos);
        
        //juntaArquivos(caminho_saida, qtd_arquivos);        
    }

    private static void separaArquivos(String arquivo_origem, String arquivo_saida, int qtd_arquivos) 
    {
        
        try
        {
            Endereco e = new Endereco();
            
            BufferedWriter bw;
            File arquivo_escrita;
            RandomAccessFile f = new RandomAccessFile (arquivo_origem, "r");  
            f.seek(0);
            
            long registros_por_arquivo = f.length() / qtd_arquivos;
            long registros_a_salvar = registros_por_arquivo;
            int aux_gravar_registros=0;
            
            
            for(int i=1; i <= qtd_arquivos; i++)
            {
                arquivo_escrita = new File(arquivo_saida + i + ".dat"); // está aqui para o nome do arquivo ser igual ao i;
                                
                while(i>2)
                //while(aux_gravar_registros <= registros_a_salvar)                
                {
                    e.leEndereco(f);                                        
                    bw = new BufferedWriter(new FileWriter(arquivo_escrita, true));
                    bw.write(e.getEnderoCompleto());
                    bw.close(); 
                    
                    aux_gravar_registros += 300;                    
                }
                
                ordenarArquivo(arquivo_escrita);
                
                if(i == (qtd_arquivos - 1)) //se for o último arquivo, talvez seu tamanho seja maior ou menor que o registros_por_arquivo 
                    registros_a_salvar = f.length();
                else
                    registros_a_salvar += registros_por_arquivo;
            }
                          
        }
        catch(Exception ex)
        {
            System.out.println("Nao foi possivel ler o arquivo");
        }
    }

    private static void ordenarArquivo(File arquivo_escrita) throws IOException  //BubbleSort
    {        
        RandomAccessFile f = new RandomAccessFile (arquivo_escrita, "r");  
        Endereco e1 = new Endereco();
        Endereco e2 = new Endereco();
        
        long i = arquivo_escrita.length();
        
        while(i>0) 
        {
            for (int j = 1; j < i; j++) 
            {       
                f.seek(i-300);  
                e1.leEndereco(f); //pega o cepp a comparar                              
                String cep1 = e1.getCep();
                
                f.seek(i);  
                e2.leEndereco(f);                              
                String cep2 = e2.getCep();
                
                if(cep1.compareTo(cep2) >= 0)
                {
                   String aux = cep2;
                   e2.leEndereco(f);
                }
                /*if (v[j - 1] > v[j]) {
                    int aux = v[j];
                    v[j] = v[j - 1];
                    v[j - 1] = aux;
                }*/
            }
            
            i -= 300; //tamanho dos registros
        }
    }        

    private static void juntaArquivos(String caminho_saida, int qtd_arquivos) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
