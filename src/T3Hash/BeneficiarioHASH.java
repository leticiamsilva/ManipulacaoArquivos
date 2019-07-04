package bolsafamilia;

import java.io.DataOutput;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;

/**
 *
 * @author Lelê
 */
public class BeneficiarioHASH {

    String nis;
    String posicao;

    public void leBeneficiario(String pa_benefiiciarioLinha) 
    {
        String[] va_dadosBeneficiario = pa_benefiiciarioLinha.split(";");
     
        this.nis = va_dadosBeneficiario[5];        
    }
    
    public void escreveHash(DataOutput dout) throws IOException
    {       
        // Definie a forma como caracteres especias estão codificados.
        Charset enc = Charset.forName("ISO-8859-1");
        dout.write(this.nis.getBytes(enc));
        dout.write(this.posicao.getBytes(enc));
        dout.write(" \n".getBytes(enc));
    }

}
