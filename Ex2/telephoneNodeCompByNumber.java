package Ex2;

import java.util.Comparator;
public class telephoneNodeCompByNumber implements Comparator<TelephoneNode>{
    @Override
    public int compare(TelephoneNode o1, TelephoneNode o2) {
        return o1.getNumber().compareTo(o2.getNumber());
    }
}
