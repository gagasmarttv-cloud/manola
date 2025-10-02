/*
 * Zmluva.java
 *
 * Created on 30 July 2008, 23:28
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package manola;

import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author simo
 */
public class Zmluva {
    /**
     * V skutocnosti rodne cislo zakaznika, prepaja zmluvu a zakaznika
     */
    public String zakaznik_OP;
    /**
     * Predmet zmluvy
     */
    public String predmet;
    public String poznamka;
    /**
     * ID zmluvy
     */
    public String ID;
    public float  hodnota;
    public String stav;
    /**
     * Datum zalozenia zmluvy
     */
    public Date datum;  //datum zalozenia zmluvy
    public float urok1;
    public float urok2;
    public float urok3;
    public float urok4;
    public float urok5;
    /**
     * Suma za ktoru bola zmluva odkupena alebo vyplatena
     */
    public float odkupna_suma;
    /**
     * Datum poslednej zmenystavu zmluvy
     */
    public Date datum_zmeny;    //datum poslednej zmeny stavu zmluvy
    /** Creates a new instance of Zmluva */
    public Zmluva() {
    }
    
    /**
     * 
     * 
     * @TODO dorobit nacitavanie urokov z databazy
     * @TODO dorobit automaticke pridanie datumu
     * @param novy_zakaznik_OP 
     * @param novy_predmet 
     * @param nova_poznamka 
     * @param nova_hodnota 
     */ 
    public Zmluva(String novy_zakaznik_OP, String novy_predmet, String nova_poznamka, float nova_hodnota){
        zakaznik_OP = novy_zakaznik_OP;
        predmet = novy_predmet;
        poznamka = nova_poznamka;
        hodnota = nova_hodnota;
        stav = "Zalozena";
        odkupna_suma = nova_hodnota;
    }
    
    /**
     * Zmeni stav zmluvy
     * @param novy_stav Stav do ktoreho sa ma zmluva dostat
     * @param suma Suma za ktoru bola zmluva odkupena alebo vyplatena
     */
    public void zmenStav(String novy_stav, float suma){
        this.stav = novy_stav;
        this.odkupna_suma = suma;
        
        Calendar cal = Calendar.getInstance();
        
        this.datum_zmeny = new Date(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
        
    }
    
}
