package manola;

import manola.*;
import java.util.Date;
/*
 * Pouzivatel.java
 *
 * Created on 30 July 2008, 21:11
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

/**
 *
 * @author simo
 */
public class Pouzivatel {
    public String meno = null;
    public String priezvisko = null;
    public String adresa = null;
    public String cislo_OP = null;    //toto je rodne cislo
    public String pohlavie = null;
    public String stav = null;
    public String rod_cis = null;
    public String doplnk_info = null;   //toto je vlastne cislo obcianskeho 
    /** Creates a new instance of Pouzivatel */
    public Pouzivatel() {
    }
    
    public Pouzivatel(String nove_meno, String nove_priezvisko, String nova_adresa, String nove_cislo_OP, String nove_doplnk_info, String nove_pohlavie, String novy_stav) {
        meno = nove_meno;
        priezvisko = nove_priezvisko;
        adresa = nova_adresa;
        cislo_OP = nove_cislo_OP;
        pohlavie = nove_pohlavie;
        stav = novy_stav;
        doplnk_info = nove_doplnk_info;
        
        System.out.println("Novy pouzivatel vytvoreny");
    }
}
