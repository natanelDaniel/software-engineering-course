package Ex2;

import java.util.Comparator;
public class telephoneNodeComp implements Comparator<TelephoneNode>{
    @Override
    public int compare(TelephoneNode o1, TelephoneNode o2) {
        return o1.getName().compareTo(o2.getName());
    }
}
