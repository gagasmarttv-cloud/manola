/*
 * Databaza.java
 *
 * Created on 30 July 2008, 21:06
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package manola;

import com.db4o.ObjectSet;
import java.util.Calendar;
import java.util.Date;
import manola.*;
import com.db4o.Db4o;
import com.db4o.ObjectContainer;

/**
 * Hlavna trieda pre pracu s databazou
 * @author simo
 */
public class Databaza {
    
    /** Creates a new instance of Databaza */
    public Databaza() {
        System.out.println("Vsetko OK");
    }
    
    /**
     * prida noveho pouzivatela do databazy
     * @param novy_pouzivatel pouzivatel na pridanie
     */
    public void pridajPouzivatela(Pouzivatel novy_pouzivatel){
        ObjectContainer db_pouzivatel = Db4o.openFile("pouzivatelia.yap");
        try{
            db_pouzivatel.set(novy_pouzivatel);
            System.out.println("novy pouzivatel pridany");
        }
        finally{
            db_pouzivatel.close();
        }
    }
    
    
    /**
     * prida novu zmluvu do databazy, zisti ID, datum aj uroky
     * @param nova_zmluva nova zmluva na prodanie
     */
    public void pridajZmluvu(Zmluva nova_zmluva){
        
        Calendar cal = Calendar.getInstance();
        
        int id = vratIdZmluvyaInkrementuj();
        
        String id_a_nuly = "";
        
        for (int i = new Integer(id).toString().length(); i < 4; i++) {
            id_a_nuly = id_a_nuly+"0";
        }
        
        id_a_nuly = id_a_nuly+id;
        nova_zmluva.ID = "M"+cal.get(Calendar.YEAR)+"*"+id_a_nuly;
        
        Nastavenia nastavenia = vratNastavenia();
        
        nova_zmluva.urok1 = nastavenia.urok1;
        nova_zmluva.urok2 = nastavenia.urok2;
        nova_zmluva.urok3 = nastavenia.urok3;
        nova_zmluva.urok4 = nastavenia.urok4;
        nova_zmluva.urok5 = nastavenia.urok5;
        
        
        int rok = cal.get(Calendar.YEAR);
        int mesiac = cal.get(Calendar.MONTH);
        int den = cal.get(Calendar.DAY_OF_MONTH);
        
        nova_zmluva.datum = new Date(rok, mesiac, den);
        nova_zmluva.datum_zmeny = new Date(rok, mesiac, den);
        
        ObjectContainer db_zmluvy = Db4o.openFile("zmluvy.yap");
        try{
            db_zmluvy.set(nova_zmluva);
            System.out.println("novy pouzivatel pridany");
        }
        finally{
            db_zmluvy.close();
        }
        
        System.out.println("Zmluva pridana: ");
    }
    
    /**
     * Vrati zoznam vsetkych pouzivatelov
     * @return pole s pouzivatelmi
     */
    public Object[] vratPouzivatelov(){
        
        Object[] vratit;
        ObjectContainer db_pouzivatel = Db4o.openFile("pouzivatelia.yap");
        
        try{
            ObjectSet najdene = db_pouzivatel.get(Pouzivatel.class);
            vratit = najdene.toArray();
        }
        finally{
            db_pouzivatel.close();
        }
        
        return vratit;
      }
 
     /**
     * Vrati zoznam vsetkych zmluv
     * @return pole so zmluvami
     */
    public Object[] vratZmluvy(){
        
        Object[] vratit;
        ObjectContainer db_zmluva = Db4o.openFile("zmluvy.yap");
        
        try{
            ObjectSet najdene = db_zmluva.get(Zmluva.class);
            vratit = najdene.toArray();
        }
        finally{
            db_zmluva.close();
        }
        
        return vratit;
      }
    
    /**
     * Vrati zoznam zmluv prisluchajuci zakaznikovy s danym identifikatorom
     * @param cislo_OP Identifikator zakaznika
     * @return zmluvy patriace zakaznikovi
     */
    public Object[] vratZmluvy(String cislo_OP){
        
        Object[] vratit;
        ObjectContainer db_zmluva = Db4o.openFile("zmluvy.yap");
        
        Zmluva proto = new Zmluva();
        
        proto.ID = null;
        proto.datum = null;
        proto.hodnota = 0;
        proto.poznamka = null;
        proto.predmet = null;
        proto.stav = null;
        proto.urok1 = 0;
        proto.urok2 = 0;
        proto.urok3 = 0;
        proto.urok4 = 0;
        proto.urok5 = 0;
        proto.zakaznik_OP = cislo_OP;
        
        try{
            ObjectSet najdene = db_zmluva.get(proto);
            vratit = najdene.toArray();
        }
        finally{
            db_zmluva.close();
        }
        
        return vratit;
      }
    
    /**
     * najde pouzivatela na zaklade jeho cisla OP
     * @param cislo_OP Cislo OP na zaklade ktoreho je pouzivatel vyhladany
     * @return Pouzivatel s danym cislo <B>OP</B>
     */
    public Pouzivatel vratPouzivatelaPodlaCislaOP(String cislo_OP){
        Pouzivatel vratit;
        
        ObjectContainer db_pouzivatel = Db4o.openFile("pouzivatelia.yap");
        
        try{
            Pouzivatel proto = new Pouzivatel(null, null, null , cislo_OP, null, null, null);
            ObjectSet najdene = db_pouzivatel.get(proto);
            
            vratit = (Pouzivatel)najdene.next();
        }
        finally{
            db_pouzivatel.close();
        }
        return vratit;
    }
    
    
    /**
     * Vrati zmluvu na zaklade ID
     * @param ID Identifikacne cislo zmluvy
     * @return Najden azmluva
     */
    public Zmluva vratZmluvuPodlaID(String ID){
        Zmluva vratit;
        ObjectContainer db_zmluva = Db4o.openFile("zmluvy.yap");
        
        try{
            Zmluva proto = new Zmluva();
            proto.ID = ID;
            proto.datum = null;
            proto.hodnota = 0;
            proto.poznamka = null;
            proto.predmet = null;
            proto.stav = null;
            proto.urok1= 0;
            proto.urok2= 0;
            proto.urok3= 0;
            proto.urok4= 0;
            proto.urok5= 0;
            proto.zakaznik_OP=null;
            
            ObjectSet najdene = db_zmluva.get(proto);
            
            vratit = (Zmluva)najdene.next();
        }
        finally{
            db_zmluva.close();
        }
        return vratit;
    }
    
    
     /**
     * Vrati id zmluvy ktora sa ma zalozit
     * @return Cislo zmluvy v danom roku
     */
    public int vratIdZmluvy(){
        ObjectContainer db_nastavenia = Db4o.openFile("nastavenia.yap");
        int vratit;
        try{
            Nastavenia nastavenia = new Nastavenia();
            nastavenia.ID_zmluvy = 0;
            ObjectSet vysledok =  db_nastavenia.get(Nastavenia.class);
            
            Nastavenia najdene;
            if(vysledok.size() == 0){
                db_nastavenia.set(nastavenia);
                najdene = nastavenia;
            }
            else{
                najdene = (Nastavenia)vysledok.next();
            }
            
            //System.out.println("Pocet: "+vysledok.size());
            
            
            
            //skontroluje sa ci nieje novy rok a netreba zmenit id zmluvy na 1
            Calendar cal = Calendar.getInstance();
            if(cal.get(Calendar.YEAR) != najdene.rok){
                vratit = 1;
            }
            else{
                vratit = najdene.ID_zmluvy;
            }
           /** System.out.println("ID "+najdene.ID_zmluvy);
            System.out.println("1 "+najdene.urok1);
            System.out.println("2 "+najdene.urok2);
            System.out.println("3 "+najdene.urok3);
            System.out.println("4 "+najdene.urok4);*/
            
            
            //System.out.println("ID increment: "+najdene.ID_zmluvy);
            
        }
        finally{
            db_nastavenia.close();
        }
        
        return vratit;
    }
   
    
    /**
     * Vrati cislo zmluvy, a nasledne inkrementuje cislo zmluvy v databaze pre dalsie pouzitie
     * @return cislo novej zmluvy
     */
        public int vratIdZmluvyaInkrementuj(){
        ObjectContainer db_nastavenia = Db4o.openFile("nastavenia.yap");
        int vratit;
        try{
            Nastavenia nastavenia = new Nastavenia();
            nastavenia.ID_zmluvy = 0;
            ObjectSet vysledok =  db_nastavenia.get(Nastavenia.class);
            
            Nastavenia najdene;
            if(vysledok.size() == 0){
                db_nastavenia.set(nastavenia);
                najdene = nastavenia;
            }
            else{
                najdene = (Nastavenia)vysledok.next();
            }
            
            System.out.println("Pocet: "+vysledok.size());
            
            //skontroluje sa ci nieje novy rok a ci netreba zmenit id zmluvy na 1
            Calendar cal = Calendar.getInstance();
            if(cal.get(cal.YEAR) != najdene.rok){
                najdene.naJedna();
                najdene.zmenNaRok(cal.get(Calendar.YEAR));
                vratit = 1;
                najdene.incID();
            }
            else{
                vratit = najdene.ID_zmluvy;
                najdene.incID();
            }
            
            System.out.println("ID "+najdene.ID_zmluvy);
            System.out.println("1 "+najdene.urok1);
            System.out.println("2 "+najdene.urok2);
            System.out.println("3 "+najdene.urok3);
            System.out.println("4 "+najdene.urok4);
            System.out.println("ROK "+najdene.rok);
            
            
            
            //System.out.println("ID increment: "+najdene.ID_zmluvy);
            
            db_nastavenia.set(najdene);
        }
        finally{
            db_nastavenia.close();
        }
        
        return vratit;
    }

    /**
     * Vrati nastavenie
     * @return nastavenia
     */
    public Nastavenia vratNastavenia(){
        ObjectContainer db_nastavenia = Db4o.openFile("nastavenia.yap");
        Nastavenia najdene;
        
        try{
            Nastavenia nastavenia = new Nastavenia();
            nastavenia.ID_zmluvy = 0;
            ObjectSet vysledok =  db_nastavenia.get(Nastavenia.class);
            
            
            if(vysledok.size() == 0){
                db_nastavenia.set(nastavenia);
                najdene = nastavenia;
            }
            else{
                najdene = (Nastavenia)vysledok.next();
            }
            
           /* System.out.println("Pocet: "+vysledok.size());
            
            System.out.println("ID "+najdene.ID_zmluvy);
            System.out.println("1 "+najdene.urok1);
            System.out.println("2 "+najdene.urok2);
            System.out.println("3 "+najdene.urok3);
            System.out.println("4 "+najdene.urok4);
            */
        }
        finally{
            db_nastavenia.close();
        }
        
        return najdene;
    }        
    
    /**
     * Zmeni nastavenia programu
     * @param u1 Urok za 1. tyzden
     * @param u2 Urok za 2. tyzden
     * @param u3 Urok za 3. tyzden
     * @param u4 Urok za 4. tyzden
     * @param u5 Urok za 5. tyzden
     * @param nove_id Nove id pre pouzivane zmluvy
     */
    public void zmenNastavenia(int u1, int u2, int u3, int u4, int u5, int nove_id){
      
        ObjectContainer db_nastavenia = Db4o.openFile("nastavenia.yap");
        
        Nastavenia najdene;
        
        try{
            ObjectSet vysledok = db_nastavenia.get(Nastavenia.class);
            
            System.out.println("Pocet nastaveni: "+vysledok.size());
            
            najdene = (Nastavenia)vysledok.next();
            
            najdene.zmenNastavenia(u1, u2, u3, u4, u5, nove_id);
            
            db_nastavenia.set(najdene);
        }
        finally{
            db_nastavenia.close();
        }
    }
    
    public void zmenStavZmluvy(String ID_zmluvy, String novy_stav, float suma){
        
        Zmluva menena = vratZmluvuPodlaID(ID_zmluvy);
        
        ObjectContainer db_zmluvy = Db4o.openFile("zmluvy.yap");
            
        try{
            ObjectSet vysledok = db_zmluvy.get(menena);
            Zmluva zmenena = (Zmluva)vysledok.next();
            
            zmenena.zmenStav(novy_stav, suma);
            db_zmluvy.set(zmenena);
        }
        finally{
            db_zmluvy.close();
        }
    }
      
}
