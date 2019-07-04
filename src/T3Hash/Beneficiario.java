package bolsafamilia;

import java.util.ArrayList;

/**
 *
 * @author Lelê
 */
public class Beneficiario {

    String mesReferencia;
    String mesCompetencia;
    String uf;
    String nis;
    String nome;
    double parcela;

    public void leBeneficiario(String pa_benefiiciarioLinha) {
        String[] va_dadosBeneficiario = pa_benefiiciarioLinha.split(";");
        this.mesReferencia = va_dadosBeneficiario[0];
        this.mesCompetencia = va_dadosBeneficiario[1];
        this.uf = va_dadosBeneficiario[2];
        this.nis = va_dadosBeneficiario[5];
        this.nome = va_dadosBeneficiario[6];
        double parseLida = 0;
        try 
        {
            parseLida = Double.parseDouble(va_dadosBeneficiario[7].split(",")[0].replace("\"", ""));
        } catch (Exception ex) {
            System.out.println(va_dadosBeneficiario[7].split(",")[0].replace("\"", ""));
            System.out.println("--- valor: " + va_dadosBeneficiario[7]);

        }
        this.parcela = parseLida;
    }

    public static double maiorValor(ArrayList<Beneficiario> b) {
        double max = 0;

        for (Beneficiario b1 : b) {
            if (b1.parcela > max) {
                max = b1.parcela;
            }
        }
        return max;
    }

    public static double somaParcelas(ArrayList<Beneficiario> b) {
        double valorTotal = 0;

        for (Beneficiario b1 : b) {
            valorTotal += b1.parcela;
        }

        return valorTotal;
    }
}
