/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bolsafamilia;

import java.util.Comparator;

/**
 *
 * @author Lelê
 */
public class comparaBeneficiario implements Comparator<Beneficiario>
{
    
    @Override
    public int compare(Beneficiario b1, Beneficiario b2) 
    {
        return b1.nis.compareTo(b2.nis);  
    }
    
}
