package intercalacep;

import java.util.Comparator;

/**
 *
 * @author Lelê
 */
public class ComparaCEP implements Comparator<Endereco>
{
    @Override
    public int compare(Endereco e1, Endereco e2)
    {
        return e1.getCep().compareTo(e2.getCep());    
    }
}
      
