package fr.demo.metier.converter.utilisateurdetail;

import fr.demo.metier.converter.Transformer;
import org.springframework.stereotype.Component;

import java.util.BitSet;

@Component("utilisateurdetail.habilitationsBitSetTransformer")
public class HabilitationsBitSetTransformer implements Transformer<String, BitSet> {

  @Override
  public BitSet transform(String habilitations) {

    if (habilitations == null) {
      return null;
    }
    int size = habilitations.length();
    BitSet result = new BitSet(size);
    for (int idx = 0; idx < size; idx++) {
      result.set(idx, habilitations.charAt(idx) == '1');
    }
    return result;
  }

}
