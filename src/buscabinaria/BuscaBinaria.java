package buscabinaria;

import java.io.RandomAccessFile;
import javax.sound.midi.SysexMessage;

/**
 *
 * @author Lelê
 */
public class BuscaBinária {

    public static void main(String args[]) 
    {    
        int iteracoes = 1;
        long inicio, meio, fim;
        
        String cep_requirido = "22060001";
        
        Endereco e = new Endereco();
        
        try
        {
            RandomAccessFile f = new RandomAccessFile ("C:\\Users\\Lelê\\Documents\\cep_ordenado.dat", "r");

            inicio = 0;
            fim = (f.length()/e.tamanhoRegistro) - 1; 
            
            while(inicio<=fim)
            {
                meio = (inicio+fim)/2;
                
                f.seek(meio * e.tamanhoRegistro);
                e.leEndereco(f);
                
                if(cep_requirido.equals(e.getCep()))
                {
                    System.out.println("Achou o " + e.getCep() + "! Iteracoes: " + iteracoes);
                    break;
                }
                else
                {
                    if(e.getCep().compareTo(cep_requirido) > 0) //o que cep que quero é menor que o meio
                        fim = meio - 1;   
                    else
                        inicio = meio+1;
                }
                    
                    
                iteracoes++;
            }
            
            

        }         
        catch(Exception ex)
        {
            System.out.println("Arquivo não encontrado ou não possível de manipular");
        }
    }
    
}
