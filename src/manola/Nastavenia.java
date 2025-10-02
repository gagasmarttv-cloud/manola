/*
 * Nastavenia.java
 *
 * Created on 31 July 2008, 01:53
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package manola;

import java.util.Calendar;

/**
 *
 * @author simo
 */
public class Nastavenia {
    int ID_zmluvy = 1;
    int urok1 = 4;
    int urok2 = 10;
    int urok3 = 15;
    int urok4 = 20;
    int urok5 = 25;
    int rok = 0;
    /** Creates a new instance of Nastavenia */
    public Nastavenia() {
        Calendar cal = Calendar.getInstance();
        rok = cal.get(Calendar.YEAR);
    }
    
    
    /**
     * Zmeni nastavenia programu
     * @param u1 urok za 1. tyzden
     * @param u2 urok za 2. tyzden
     * @param u3 urok za 3. tyzden
     * @param u4 urok za 4. tyzden
     * @param u5 urok za 5. tyzden
     * @param nove_id Nove id nasledujucej zmluvy
     */
    public void zmenNastavenia(int u1, int u2, int u3, int u4, int u5, int nove_id){
        urok1 = u1;
        urok2 = u2;
        urok3 = u3;
        urok4 = u4;
        urok5 = u5;
        
        if(ID_zmluvy < nove_id)    ID_zmluvy = nove_id;
    }
    /**
     * Zvysi cislo nasledujucej zmluvy o 1
     */
    public void incID(){
        ID_zmluvy++;
      //  System.out.println("ID zvuysene na "+ID_zmluvy);
    }
    
    public void naJedna(){
        ID_zmluvy = 1;
    }
    
    public void zmenNaRok(int new_rok){
        rok = new_rok;
    }
}
