/*
 * Tlac.java
 *
 * Created on 07 August 2008, 18:26
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package manola;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.HeadlessException;
import java.awt.print.PageFormat;
import java.awt.print.Pageable;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.util.Calendar;
import javax.print.attribute.PrintRequestAttributeSet;

/**
 *
 * @author simo
 */
public class Tlac implements Printable{
    
    public Pouzivatel zakaznik;
    public Zmluva zmluva;
    
    /** Creates a new instance of Tlac */
    public Tlac(Pouzivatel novy_pouzivatel, Zmluva nova_zmluva) {
        
        zakaznik = novy_pouzivatel;
        zmluva = nova_zmluva;
        
        PrinterJob printJob = PrinterJob.getPrinterJob();
        
        printJob.setPrintable(this);
        
        System.out.println("_____");
        
           try {
              printJob.print();
           } catch (Exception PrintException) {
              PrintException.printStackTrace();
           }

    }
    
    
    public int print (Graphics g, PageFormat pageFormat, int page){
        
        if(page == 0){
            
            Nahlad nahlad = new Nahlad();
            
            Calendar cal = Calendar.getInstance();
            
            Calendar cal_zmluva = Calendar.getInstance();
            cal_zmluva.set(zmluva.datum.getYear(), zmluva.datum.getMonth(), zmluva.datum.getDate());
            
            Graphics2D g2d = (Graphics2D)g;
            
            g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());
            
            Font normalFont = new Font ("serif", Font.PLAIN, 10);
            Font boldFont = new Font ("serif", Font.BOLD, 12);

            g2d.setFont(boldFont);
            g2d.drawString("Z·loûn· zmluva ËÌslo: "+zmluva.ID  ,120,10);
            g2d.setFont(normalFont);
            g2d.drawString("O poskytnutÌ pÙûiËky v z·loûnom pr·ve "  ,150,22);
            
            g2d.drawString("Uzatvoren· zmluva v zmysle ß657 a nasl. ß121 ods 3 a ß544 aû ß545 obËianskeho z·konnÌka"  ,0,40);
            g2d.drawString("medzi MANOLA.s.r.o. a zastupen˝m pracovnikom z·loûne p. ºubomÌr Lamoö (Veriteæ)."  ,0,52);
            
            g2d.drawString("'DlûnÌk' p·n-pani: ", 0, 70);
            g2d.drawString("RodnÈ ËÌslo: ",0,82);
            g2d.drawString("»Ìslo OP:",0,94);
            g2d.drawString("Bytom: ", 0, 106);
           
            g2d.drawString(zakaznik.meno+" "+zakaznik.priezvisko, 200, 70);
            g2d.drawString(zakaznik.cislo_OP, 200, 82);
            g2d.drawString(zakaznik.doplnk_info, 200, 94);
            g2d.drawString(zakaznik.adresa, 200, 106);
           
             switch(cal_zmluva.get(Calendar.YEAR)){
                case 2008:
                    g2d.drawString("Veriteæ poskytne dlûnÌkovi pÙûiËku v sume: "+zmluva.hodnota+" SK ("+Math.round(((float)zmluva.hodnota/30.126)*100f)/100f+" Ä) v peniazoch.", 0, 130);
                    break;   
                case 2009:
                    g2d.drawString("Veriteæ poskytne dlûnÌkovi pÙûiËku v sume: "+zmluva.hodnota+" Ä ("+Math.round(((float)zmluva.hodnota*30.126)*100f)/100f+" SK) v peniazoch.", 0, 130);
                    break;
                default:
                    g2d.drawString("Veriteæ poskytne dlûnÌkovi pÙûiËku v sume: "+zmluva.hodnota+" Ä v peniazoch.", 0, 130);
                    break;
            }
            
            
             switch(cal_zmluva.get(Calendar.YEAR)){
                case 2008:
                    g2d.drawString("Poskytnut· suma: "+zmluva.hodnota+" SK ("+Math.round(((float)zmluva.hodnota/30.126)*100f)/100f+" Ä) v peniazoch.", 0, 570);
                    break;   
                case 2009:
                    g2d.drawString("Poskytnut· suma: "+zmluva.hodnota+" Ä ("+Math.round(((float)zmluva.hodnota*30.126)*100f)/100f+" SK) v peniazoch.", 0, 570);
                    break;
                default:
                    g2d.drawString("Poskytnut· suma: "+zmluva.hodnota+" Ä v peniazoch.", 0, 570);
                    break;
            }
             
            g2d.drawString("Za poskytnut˙ pÙûiËku odovzd· dlûnÌk veriteæovi do z·lohy: ", 0, 150);
            g2d.drawString(""+zmluva.predmet, 0, 162);
            
            
            switch(cal.get(Calendar.YEAR)){
                case 2008:
                    g2d.drawString("ktor˙ bude veriteæ starostlivo opatrovaù, za Ëo mu prisl˙cha suma vo v˝öke 20 SK (0.66 Ä).", 0, 180);
                    break;   
                case 2009:
                    g2d.drawString("ktor˙ bude veriteæ starostlivo opatrovaù, za Ëo mu prisl˙cha suma vo v˝öke 0.66 Ä (20 SK).", 0, 180);
                    break;
                default:
                    g2d.drawString("ktor˙ bude veriteæ starostlivo opatrovaù, za Ëo mu prisl˙cha suma vo v˝öke 0.66 Ä.", 0, 180);
                    break;
            }
            
            g2d.drawString("DlûnÌk sa zav‰zuje veriteæovi pÙûiËku vr·tiù spolu s ˙rokom v t˝chto spl·tkach a term˝noch nasledovne: ",0, 200);
            
            
            
            g2d.drawString("od: "+cal_zmluva.get(Calendar.DAY_OF_MONTH)+"."+(cal_zmluva.get(Calendar.MONTH)+1)+"."+cal_zmluva.get(Calendar.YEAR), 0, 212);
            g2d.drawString("od: "+cal_zmluva.get(Calendar.DAY_OF_MONTH)+"."+(cal_zmluva.get(Calendar.MONTH)+1)+"."+cal_zmluva.get(Calendar.YEAR), 0, 600);
            cal_zmluva.add(Calendar.DAY_OF_MONTH,7);
            g2d.drawString("do: "+cal_zmluva.get(Calendar.DAY_OF_MONTH)+"."+(cal_zmluva.get(Calendar.MONTH)+1)+"."+cal_zmluva.get(Calendar.YEAR), 75, 212);
            g2d.drawString("do: "+cal_zmluva.get(Calendar.DAY_OF_MONTH)+"."+(cal_zmluva.get(Calendar.MONTH)+1)+"."+cal_zmluva.get(Calendar.YEAR), 75, 600);
            
            g2d.drawString("˙rok: "+Math.round(zmluva.urok1)+"%", 150, 212);
            g2d.drawString("˙rok: "+Math.round(zmluva.urok1)+"%", 150, 600);
            
            cal_zmluva.add(Calendar.DAY_OF_MONTH,1);
            g2d.drawString("od: "+cal_zmluva.get(Calendar.DAY_OF_MONTH)+"."+(cal_zmluva.get(Calendar.MONTH)+1)+"."+cal_zmluva.get(Calendar.YEAR), 0, 224);
            g2d.drawString("od: "+cal_zmluva.get(Calendar.DAY_OF_MONTH)+"."+(cal_zmluva.get(Calendar.MONTH)+1)+"."+cal_zmluva.get(Calendar.YEAR), 0, 612);
            cal_zmluva.add(Calendar.DAY_OF_MONTH,6);
            g2d.drawString("do: "+cal_zmluva.get(Calendar.DAY_OF_MONTH)+"."+(cal_zmluva.get(Calendar.MONTH)+1)+"."+cal.get(Calendar.YEAR), 75, 224);
            g2d.drawString("do: "+cal_zmluva.get(Calendar.DAY_OF_MONTH)+"."+(cal_zmluva.get(Calendar.MONTH)+1)+"."+cal.get(Calendar.YEAR), 75, 612);
            
            g2d.drawString("˙rok: "+Math.round(zmluva.urok2)+"%", 150, 224);
            g2d.drawString("˙rok: "+Math.round(zmluva.urok2)+"%", 150, 612);
            
            cal_zmluva.add(Calendar.DAY_OF_MONTH,1);
            g2d.drawString("od: "+cal_zmluva.get(Calendar.DAY_OF_MONTH)+"."+(cal_zmluva.get(Calendar.MONTH)+1)+"."+cal_zmluva.get(Calendar.YEAR), 0, 236);
            g2d.drawString("od: "+cal_zmluva.get(Calendar.DAY_OF_MONTH)+"."+(cal_zmluva.get(Calendar.MONTH)+1)+"."+cal_zmluva.get(Calendar.YEAR), 0, 624);
            cal_zmluva.add(Calendar.DAY_OF_MONTH,6);
            g2d.drawString("do: "+cal_zmluva.get(Calendar.DAY_OF_MONTH)+"."+(cal_zmluva.get(Calendar.MONTH)+1)+"."+cal_zmluva.get(Calendar.YEAR), 75, 236);
            g2d.drawString("do: "+cal_zmluva.get(Calendar.DAY_OF_MONTH)+"."+(cal_zmluva.get(Calendar.MONTH)+1)+"."+cal_zmluva.get(Calendar.YEAR), 75, 624);
            
            g2d.drawString("˙rok: "+Math.round(zmluva.urok3)+"%", 150, 236);
            g2d.drawString("˙rok: "+Math.round(zmluva.urok3)+"%", 150, 624);
            
            cal_zmluva.add(Calendar.DAY_OF_MONTH,1);
            g2d.drawString("od: "+cal_zmluva.get(Calendar.DAY_OF_MONTH)+"."+(cal_zmluva.get(Calendar.MONTH)+1)+"."+cal_zmluva.get(Calendar.YEAR), 0, 248);
            g2d.drawString("od: "+cal_zmluva.get(Calendar.DAY_OF_MONTH)+"."+(cal_zmluva.get(Calendar.MONTH)+1)+"."+cal_zmluva.get(Calendar.YEAR), 0, 636);
            cal_zmluva.add(Calendar.DAY_OF_MONTH,6);
            g2d.drawString("do: "+cal_zmluva.get(Calendar.DAY_OF_MONTH)+"."+(cal_zmluva.get(Calendar.MONTH)+1)+"."+cal_zmluva.get(Calendar.YEAR), 75, 248);
            g2d.drawString("do: "+cal_zmluva.get(Calendar.DAY_OF_MONTH)+"."+(cal_zmluva.get(Calendar.MONTH)+1)+"."+cal_zmluva.get(Calendar.YEAR), 75, 636);
            
            g2d.drawString("˙rok: "+Math.round(zmluva.urok4)+"%", 150, 248);
            g2d.drawString("˙rok: "+Math.round(zmluva.urok4)+"%", 150, 636);
            
            cal_zmluva.add(Calendar.DAY_OF_MONTH,1);
            g2d.drawString("od: "+cal_zmluva.get(Calendar.DAY_OF_MONTH)+"."+(cal_zmluva.get(Calendar.MONTH)+1)+"."+cal_zmluva.get(Calendar.YEAR), 0, 260);
            g2d.drawString("od: "+cal_zmluva.get(Calendar.DAY_OF_MONTH)+"."+(cal_zmluva.get(Calendar.MONTH)+1)+"."+cal_zmluva.get(Calendar.YEAR), 0, 648);
            cal_zmluva.add(Calendar.DAY_OF_MONTH,6);
            g2d.drawString("do: "+cal_zmluva.get(Calendar.DAY_OF_MONTH)+"."+(cal_zmluva.get(Calendar.MONTH)+1)+"."+cal_zmluva.get(Calendar.YEAR), 75, 260);
            g2d.drawString("do: "+cal_zmluva.get(Calendar.DAY_OF_MONTH)+"."+(cal_zmluva.get(Calendar.MONTH)+1)+"."+cal_zmluva.get(Calendar.YEAR), 75, 648);
            
            g2d.drawString("˙rok: "+Math.round(zmluva.urok5)+"%", 150, 260);
            g2d.drawString("˙rok: "+Math.round(zmluva.urok5)+"%", 150, 648);
            
               float spolu =0;
               float poplatok = 0.0f;
               
               
               if(zmluva.datum.getYear() < 2009){
                   poplatok =  20f;
               }
               else{
                   poplatok = 0.66f;
               }
               
               spolu = (float)zmluva.hodnota + poplatok + ((float)zmluva.hodnota/100)*(float)zmluva.urok1;
               //koli prechodu na euro
               switch(zmluva.datum.getYear()){
                   case 2008:
                       g2d.drawString((float)Math.round(spolu*100)/100f+" SK"+" ("+(float)Math.round((spolu/30.126f)*100)/100f+" Ä)", 225,212);
                       g2d.drawString((float)Math.round(spolu*100)/100f+" SK"+" ("+(float)Math.round((spolu/30.126f)*100)/100f+" Ä)", 225,600);
                       break;
                   case 2009:
                       g2d.drawString((float)Math.round(spolu*100)/100f+" Ä"+" ("+(float)Math.round((spolu*30.126f)*100)/100f+" SK)", 225,212);
                       g2d.drawString((float)Math.round(spolu*100)/100f+" Ä"+" ("+(float)Math.round((spolu*30.126f)*100)/100f+" SK)", 225,600);
                       break;
                   default:
                       g2d.drawString((float)Math.round(spolu*100)/100f+" Ä", 225,212);
                       g2d.drawString((float)Math.round(spolu*100)/100f+" Ä", 225,600);
                       break;
                       
               }
               
               spolu = (float)zmluva.hodnota + poplatok + ((float)zmluva.hodnota/100)*(float)zmluva.urok2;
               //koli prechodu na euro
               switch(zmluva.datum.getYear()){
                   case 2008:
                       g2d.drawString((float)Math.round(spolu*100)/100f+" SK"+" ("+(float)Math.round((spolu/30.126f)*100)/100f+" Ä)", 225,224);
                       g2d.drawString((float)Math.round(spolu*100)/100f+" SK"+" ("+(float)Math.round((spolu/30.126f)*100)/100f+" Ä)", 225,612);
                       break;
                   case 2009:
                       g2d.drawString((float)Math.round(spolu*100)/100f+" Ä"+" ("+(float)Math.round((spolu*30.126f)*100)/100f+" SK)", 225,224);
                       g2d.drawString((float)Math.round(spolu*100)/100f+" Ä"+" ("+(float)Math.round((spolu*30.126f)*100)/100f+" SK)", 225,612);
                       break;
                   default:
                       g2d.drawString((float)Math.round(spolu*100)/100f+" Ä", 225,224);
                       g2d.drawString((float)Math.round(spolu*100)/100f+" Ä", 225,612);
                       break;
               }
               
               spolu = (float)zmluva.hodnota + poplatok + ((float)zmluva.hodnota/100)*(float)zmluva.urok3;
               //koli prechodu na euro
               switch(zmluva.datum.getYear()){
                   case 2008:
                       g2d.drawString((float)Math.round(spolu*100)/100f+" SK"+" ("+(float)Math.round((spolu/30.126f)*100)/100f+" Ä)", 225,236);
                       g2d.drawString((float)Math.round(spolu*100)/100f+" SK"+" ("+(float)Math.round((spolu/30.126f)*100)/100f+" Ä)", 225,624);
                       break;
                   case 2009:
                       g2d.drawString((float)Math.round(spolu*100)/100f+" Ä"+" ("+(float)Math.round((spolu*30.126f)*100)/100f+" SK)", 225,236);
                       g2d.drawString((float)Math.round(spolu*100)/100f+" Ä"+" ("+(float)Math.round((spolu*30.126f)*100)/100f+" SK)", 225,624);
                       break;
                   default:
                       g2d.drawString((float)Math.round(spolu*100)/100f+" Ä", 225,236);
                       g2d.drawString((float)Math.round(spolu*100)/100f+" Ä", 225,624);
                       break;
               }
               
               spolu = (float)zmluva.hodnota + poplatok + ((float)zmluva.hodnota/100)*(float)zmluva.urok4;
               //koli prechodu na euro
               switch(zmluva.datum.getYear()){
                   case 2008:
                       g2d.drawString((float)Math.round(spolu*100)/100f+" SK"+" ("+(float)Math.round((spolu/30.126f)*100)/100f+" Ä)", 225,248);
                       g2d.drawString((float)Math.round(spolu*100)/100f+" SK"+" ("+(float)Math.round((spolu/30.126f)*100)/100f+" Ä)", 225,636);
                       break;
                   case 2009:
                       g2d.drawString((float)Math.round(spolu*100)/100f+" Ä"+" ("+(float)Math.round((spolu*30.126f)*100)/100f+" SK)", 225,248);
                       g2d.drawString((float)Math.round(spolu*100)/100f+" Ä"+" ("+(float)Math.round((spolu*30.126f)*100)/100f+" SK)", 225,636);
                       break;
                   default:
                       g2d.drawString((float)Math.round(spolu*100)/100f+" Ä", 225,248);
                       g2d.drawString((float)Math.round(spolu*100)/100f+" Ä", 225,636);
                       break;
               }
               
               
               spolu = (float)zmluva.hodnota + poplatok + ((float)zmluva.hodnota/100)*(float)zmluva.urok5;
               //koli prechodu na euro
               switch(zmluva.datum.getYear()){
                   case 2008:
                       g2d.drawString((float)Math.round(spolu*100)/100f+" SK"+" ("+(float)Math.round((spolu/30.126f)*100)/100f+" Ä)", 225,260);
                       g2d.drawString((float)Math.round(spolu*100)/100f+" SK"+" ("+(float)Math.round((spolu/30.126f)*100)/100f+" Ä)", 225,648);
                       break;
                   case 2009:
                       g2d.drawString((float)Math.round(spolu*100)/100f+" Ä"+" ("+(float)Math.round((spolu*30.126f)*100)/100f+" SK)", 225,260);
                       g2d.drawString((float)Math.round(spolu*100)/100f+" Ä"+" ("+(float)Math.round((spolu*30.126f)*100)/100f+" SK)", 225,648);
                       break;
                   default:
                       g2d.drawString((float)Math.round(spolu*100)/100f+" Ä", 225,260);
                       g2d.drawString((float)Math.round(spolu*100)/100f+" Ä", 225,648);
                       break;
               }
               
               
               g2d.drawString("DlûÌk prehlasuje, ûe vec, ktor˙ poskytol do z·lohy je jeho v˝luËn˝m vlastnÌctvom a je opr·vnen˝ ju zaloûiù.", 0,290);
               
               switch(cal.get(Calendar.YEAR)){
                   case 2008:
                        g2d.drawString("Za stratu ˙striûku a jeko vyhæadanie v evidencii ˙Ëtujeme 10 SK (0.33 Ä).", 0,310);
                        break;
                   case 2009:
                        g2d.drawString("Za stratu ˙striûku a jeko vyhæadanie v evidencii ˙Ëtujeme 0.33 Ä (10 SK).", 0,310);
                        break;
                   default:
                        g2d.drawString("Za stratu ˙striûku a jeko vyhæadanie v evidencii ˙Ëtujeme 0.33 Ä.", 0,310);
                        break;
               }
               
             g2d.drawString("⁄plnÈ znenie podmienok sa nach·dza v priestoroch z·loûne.", 0,330);  
             
             g2d.drawString("O tom Ëi sa urËit· vec mÙûe staù predmetom z·loûnej zmluvy rozhoduje poveren˝ pracovnÌk z·loûne.", 0,350);
             g2d.drawString("Predmetom z·loûnej zmluvy nemÙûu byù poökodenÈ a nekompletnÈ veci ako aj veci, ktorÈ nie s˙ funkËnÈ.", 0,362);
              
            switch(cal.get(Calendar.YEAR)){
                case 2008:
                    g2d.drawString("DlûnÌk potvrdzuej prevzatie pÙûiËky: "+zmluva.hodnota+" SK ("+(float)Math.round((zmluva.hodnota/30.126)*100f)/100+" Ä) v peniazoch.", 0, 390);
                    break;   
                case 2009:
                    g2d.drawString("DlûnÌk potvrdzuej prevzatie pÙûiËky: "+zmluva.hodnota+" Ä ("+(float)Math.round((zmluva.hodnota*30.126)*100f)/100+" SK) v peniazoch.", 0, 380);
                    break;
                default:
                    g2d.drawString("DlûnÌk potvrdzuej prevzatie pÙûiËky: "+zmluva.hodnota+" Ä v peniazoch.", 0, 380);
                    break;
            }
              
            g2d.drawString("V Martine : "+zmluva.datum.getDate()+"."+(zmluva.datum.getMonth()+1)+"."+zmluva.datum.getYear(), 0, 430);
            
            g2d.drawString("..............................         .............................." , 200, 430);
            g2d.drawString("          Veriteæ                                     DlûnÌk        " , 200, 440);
            
            g2d.drawString("Potvrdzujem prevzatie veci bez nedostatkov dÚa ......................................................................." , 0, 460);
            g2d.drawString("....................................................................................................................................................." , 0, 472);
            g2d.drawString("Podpis" , 250, 484);
            
            g2d.drawString("__________________________________________________________________________________________________________________________________________________" , 0, 520);
            
            g2d.drawString("Z·loûn· zmluva ËÌslo: "+zmluva.ID  ,0,550);
            
            g2d.drawString("Zaloûen· dÚa: "+zmluva.datum.getDate()+"."+(zmluva.datum.getMonth()+1)+"."+zmluva.datum.getYear()  ,250,550);
            g2d.drawString("Posledn˝ deÚ v˝beru: "+cal_zmluva.get(Calendar.DAY_OF_MONTH)+"."+(cal_zmluva.get(Calendar.MONTH)+1)+"."+cal_zmluva.get(Calendar.YEAR)  ,250,570);
            return(PAGE_EXISTS);
        }
        else{
            return(NO_SUCH_PAGE);
        }
    }
    
    
}
