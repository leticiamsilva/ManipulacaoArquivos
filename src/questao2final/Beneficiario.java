/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package questao2final;

import java.io.DataOutput;
import java.io.IOException;
import java.nio.charset.Charset;

public class Beneficiario {

    String mesReferencia;
    String mesCompetencia;
    String uf;
    String codMunicipio;
    String nomeMunicipio;
    String nis;
    String nome;
    String parcela;

    public void leBeneficiario(String pa_benefiiciarioLinha) {
        String[] va_dadosBeneficiario = pa_benefiiciarioLinha.split(";");
        this.mesReferencia = va_dadosBeneficiario[0];
        this.mesCompetencia = va_dadosBeneficiario[1];
        this.uf = va_dadosBeneficiario[2];
        this.codMunicipio = va_dadosBeneficiario[3];
        this.nomeMunicipio = va_dadosBeneficiario[4];
        this.nis = va_dadosBeneficiario[5];
        this.nome = va_dadosBeneficiario[6];
        this.parcela = va_dadosBeneficiario[7];
    }
    
     public void escreveBeneficiario(DataOutput dout) throws IOException
    {       
        // Definie a forma como caracteres especias estão codificados.
        
        Charset enc = Charset.forName("ISO-8859-1");
        dout.write(this.mesReferencia.getBytes(enc));
        dout.write(this.mesCompetencia.getBytes(enc));
        dout.write(this.uf.getBytes(enc));
        dout.write(this.codMunicipio.getBytes(enc));
        dout.write(this.nomeMunicipio.getBytes(enc));
        dout.write(this.nis.getBytes(enc));
        dout.write(this.nome.getBytes(enc));
        dout.write(this.parcela.getBytes(enc));
        dout.write(" \n".getBytes(enc));
    }

    String[] escreveBeneficiarioLinha() {
      
        String[] retorno= {mesReferencia, mesCompetencia, uf, codMunicipio, nomeMunicipio, nis, nome, parcela};
        return retorno;    
    }

}
