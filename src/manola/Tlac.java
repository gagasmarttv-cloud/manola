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
            g2d.drawString("Z�lo�n� zmluva ��slo: "+zmluva.ID  ,120,10);
            g2d.setFont(normalFont);
            g2d.drawString("O poskytnut� p��i�ky v z�lo�nom pr�ve "  ,150,22);
            
            g2d.drawString("Uzatvoren� zmluva v zmysle �657 a nasl. �121 ods 3 a �544 a� �545 ob�ianskeho z�konn�ka"  ,0,40);
            g2d.drawString("medzi MANOLA.s.r.o. a zastupen�m pracovnikom z�lo�ne p. �ubom�r Lamo� (Verite�)."  ,0,52);
            
            g2d.drawString("'Dl�n�k' p�n-pani: ", 0, 70);
            g2d.drawString("Rodn� ��slo: ",0,82);
            g2d.drawString("��slo OP:",0,94);
            g2d.drawString("Bytom: ", 0, 106);
           
            g2d.drawString(zakaznik.meno+" "+zakaznik.priezvisko, 200, 70);
            g2d.drawString(zakaznik.cislo_OP, 200, 82);
            g2d.drawString(zakaznik.doplnk_info, 200, 94);
            g2d.drawString(zakaznik.adresa, 200, 106);
           
             switch(cal_zmluva.get(Calendar.YEAR)){
                case 2008:
                    g2d.drawString("Verite� poskytne dl�n�kovi p��i�ku v sume: "+zmluva.hodnota+" SK ("+Math.round(((float)zmluva.hodnota/30.126)*100f)/100f+" �) v peniazoch.", 0, 130);
                    break;   
                case 2009:
                    g2d.drawString("Verite� poskytne dl�n�kovi p��i�ku v sume: "+zmluva.hodnota+" � ("+Math.round(((float)zmluva.hodnota*30.126)*100f)/100f+" SK) v peniazoch.", 0, 130);
                    break;
                default:
                    g2d.drawString("Verite� poskytne dl�n�kovi p��i�ku v sume: "+zmluva.hodnota+" � v peniazoch.", 0, 130);
                    break;
            }
            
            
             switch(cal_zmluva.get(Calendar.YEAR)){
                case 2008:
                    g2d.drawString("Poskytnut� suma: "+zmluva.hodnota+" SK ("+Math.round(((float)zmluva.hodnota/30.126)*100f)/100f+" �) v peniazoch.", 0, 570);
                    break;   
                case 2009:
                    g2d.drawString("Poskytnut� suma: "+zmluva.hodnota+" � ("+Math.round(((float)zmluva.hodnota*30.126)*100f)/100f+" SK) v peniazoch.", 0, 570);
                    break;
                default:
                    g2d.drawString("Poskytnut� suma: "+zmluva.hodnota+" � v peniazoch.", 0, 570);
                    break;
            }
             
            g2d.drawString("Za poskytnut� p��i�ku odovzd� dl�n�k verite�ovi do z�lohy: ", 0, 150);
            g2d.drawString(""+zmluva.predmet, 0, 162);
            
            
            switch(cal.get(Calendar.YEAR)){
                case 2008:
                    g2d.drawString("ktor� bude verite� starostlivo opatrova�, za �o mu prisl�cha suma vo v��ke 20 SK (0.66 �).", 0, 180);
                    break;   
                case 2009:
                    g2d.drawString("ktor� bude verite� starostlivo opatrova�, za �o mu prisl�cha suma vo v��ke 0.66 � (20 SK).", 0, 180);
                    break;
                default:
                    g2d.drawString("ktor� bude verite� starostlivo opatrova�, za �o mu prisl�cha suma vo v��ke 0.66 �.", 0, 180);
                    break;
            }
            
            g2d.drawString("Dl�n�k sa zav�zuje verite�ovi p��i�ku vr�ti� spolu s �rokom v t�chto spl�tkach a term�noch nasledovne: ",0, 200);
            
            
            
            g2d.drawString("od: "+cal_zmluva.get(Calendar.DAY_OF_MONTH)+"."+(cal_zmluva.get(Calendar.MONTH)+1)+"."+cal_zmluva.get(Calendar.YEAR), 0, 212);
            g2d.drawString("od: "+cal_zmluva.get(Calendar.DAY_OF_MONTH)+"."+(cal_zmluva.get(Calendar.MONTH)+1)+"."+cal_zmluva.get(Calendar.YEAR), 0, 600);
            cal_zmluva.add(Calendar.DAY_OF_MONTH,7);
            g2d.drawString("do: "+cal_zmluva.get(Calendar.DAY_OF_MONTH)+"."+(cal_zmluva.get(Calendar.MONTH)+1)+"."+cal_zmluva.get(Calendar.YEAR), 75, 212);
            g2d.drawString("do: "+cal_zmluva.get(Calendar.DAY_OF_MONTH)+"."+(cal_zmluva.get(Calendar.MONTH)+1)+"."+cal_zmluva.get(Calendar.YEAR), 75, 600);
            
            g2d.drawString("�rok: "+Math.round(zmluva.urok1)+"%", 150, 212);
            g2d.drawString("�rok: "+Math.round(zmluva.urok1)+"%", 150, 600);
            
            cal_zmluva.add(Calendar.DAY_OF_MONTH,1);
            g2d.drawString("od: "+cal_zmluva.get(Calendar.DAY_OF_MONTH)+"."+(cal_zmluva.get(Calendar.MONTH)+1)+"."+cal_zmluva.get(Calendar.YEAR), 0, 224);
            g2d.drawString("od: "+cal_zmluva.get(Calendar.DAY_OF_MONTH)+"."+(cal_zmluva.get(Calendar.MONTH)+1)+"."+cal_zmluva.get(Calendar.YEAR), 0, 612);
            cal_zmluva.add(Calendar.DAY_OF_MONTH,6);
            g2d.drawString("do: "+cal_zmluva.get(Calendar.DAY_OF_MONTH)+"."+(cal_zmluva.get(Calendar.MONTH)+1)+"."+cal.get(Calendar.YEAR), 75, 224);
            g2d.drawString("do: "+cal_zmluva.get(Calendar.DAY_OF_MONTH)+"."+(cal_zmluva.get(Calendar.MONTH)+1)+"."+cal.get(Calendar.YEAR), 75, 612);
            
            g2d.drawString("�rok: "+Math.round(zmluva.urok2)+"%", 150, 224);
            g2d.drawString("�rok: "+Math.round(zmluva.urok2)+"%", 150, 612);
            
            cal_zmluva.add(Calendar.DAY_OF_MONTH,1);
            g2d.drawString("od: "+cal_zmluva.get(Calendar.DAY_OF_MONTH)+"."+(cal_zmluva.get(Calendar.MONTH)+1)+"."+cal_zmluva.get(Calendar.YEAR), 0, 236);
            g2d.drawString("od: "+cal_zmluva.get(Calendar.DAY_OF_MONTH)+"."+(cal_zmluva.get(Calendar.MONTH)+1)+"."+cal_zmluva.get(Calendar.YEAR), 0, 624);
            cal_zmluva.add(Calendar.DAY_OF_MONTH,6);
            g2d.drawString("do: "+cal_zmluva.get(Calendar.DAY_OF_MONTH)+"."+(cal_zmluva.get(Calendar.MONTH)+1)+"."+cal_zmluva.get(Calendar.YEAR), 75, 236);
            g2d.drawString("do: "+cal_zmluva.get(Calendar.DAY_OF_MONTH)+"."+(cal_zmluva.get(Calendar.MONTH)+1)+"."+cal_zmluva.get(Calendar.YEAR), 75, 624);
            
            g2d.drawString("�rok: "+Math.round(zmluva.urok3)+"%", 150, 236);
            g2d.drawString("�rok: "+Math.round(zmluva.urok3)+"%", 150, 624);
            
            cal_zmluva.add(Calendar.DAY_OF_MONTH,1);
            g2d.drawString("od: "+cal_zmluva.get(Calendar.DAY_OF_MONTH)+"."+(cal_zmluva.get(Calendar.MONTH)+1)+"."+cal_zmluva.get(Calendar.YEAR), 0, 248);
            g2d.drawString("od: "+cal_zmluva.get(Calendar.DAY_OF_MONTH)+"."+(cal_zmluva.get(Calendar.MONTH)+1)+"."+cal_zmluva.get(Calendar.YEAR), 0, 636);
            cal_zmluva.add(Calendar.DAY_OF_MONTH,6);
            g2d.drawString("do: "+cal_zmluva.get(Calendar.DAY_OF_MONTH)+"."+(cal_zmluva.get(Calendar.MONTH)+1)+"."+cal_zmluva.get(Calendar.YEAR), 75, 248);
            g2d.drawString("do: "+cal_zmluva.get(Calendar.DAY_OF_MONTH)+"."+(cal_zmluva.get(Calendar.MONTH)+1)+"."+cal_zmluva.get(Calendar.YEAR), 75, 636);
            
            g2d.drawString("�rok: "+Math.round(zmluva.urok4)+"%", 150, 248);
            g2d.drawString("�rok: "+Math.round(zmluva.urok4)+"%", 150, 636);
            
            cal_zmluva.add(Calendar.DAY_OF_MONTH,1);
            g2d.drawString("od: "+cal_zmluva.get(Calendar.DAY_OF_MONTH)+"."+(cal_zmluva.get(Calendar.MONTH)+1)+"."+cal_zmluva.get(Calendar.YEAR), 0, 260);
            g2d.drawString("od: "+cal_zmluva.get(Calendar.DAY_OF_MONTH)+"."+(cal_zmluva.get(Calendar.MONTH)+1)+"."+cal_zmluva.get(Calendar.YEAR), 0, 648);
            cal_zmluva.add(Calendar.DAY_OF_MONTH,6);
            g2d.drawString("do: "+cal_zmluva.get(Calendar.DAY_OF_MONTH)+"."+(cal_zmluva.get(Calendar.MONTH)+1)+"."+cal_zmluva.get(Calendar.YEAR), 75, 260);
            g2d.drawString("do: "+cal_zmluva.get(Calendar.DAY_OF_MONTH)+"."+(cal_zmluva.get(Calendar.MONTH)+1)+"."+cal_zmluva.get(Calendar.YEAR), 75, 648);
            
            g2d.drawString("�rok: "+Math.round(zmluva.urok5)+"%", 150, 260);
            g2d.drawString("�rok: "+Math.round(zmluva.urok5)+"%", 150, 648);
            
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
                       g2d.drawString((float)Math.round(spolu*100)/100f+" SK"+" ("+(float)Math.round((spolu/30.126f)*100)/100f+" �)", 225,212);
                       g2d.drawString((float)Math.round(spolu*100)/100f+" SK"+" ("+(float)Math.round((spolu/30.126f)*100)/100f+" �)", 225,600);
                       break;
                   case 2009:
                       g2d.drawString((float)Math.round(spolu*100)/100f+" �"+" ("+(float)Math.round((spolu*30.126f)*100)/100f+" SK)", 225,212);
                       g2d.drawString((float)Math.round(spolu*100)/100f+" �"+" ("+(float)Math.round((spolu*30.126f)*100)/100f+" SK)", 225,600);
                       break;
                   default:
                       g2d.drawString((float)Math.round(spolu*100)/100f+" �", 225,212);
                       g2d.drawString((float)Math.round(spolu*100)/100f+" �", 225,600);
                       break;
                       
               }
               
               spolu = (float)zmluva.hodnota + poplatok + ((float)zmluva.hodnota/100)*(float)zmluva.urok2;
               //koli prechodu na euro
               switch(zmluva.datum.getYear()){
                   case 2008:
                       g2d.drawString((float)Math.round(spolu*100)/100f+" SK"+" ("+(float)Math.round((spolu/30.126f)*100)/100f+" �)", 225,224);
                       g2d.drawString((float)Math.round(spolu*100)/100f+" SK"+" ("+(float)Math.round((spolu/30.126f)*100)/100f+" �)", 225,612);
                       break;
                   case 2009:
                       g2d.drawString((float)Math.round(spolu*100)/100f+" �"+" ("+(float)Math.round((spolu*30.126f)*100)/100f+" SK)", 225,224);
                       g2d.drawString((float)Math.round(spolu*100)/100f+" �"+" ("+(float)Math.round((spolu*30.126f)*100)/100f+" SK)", 225,612);
                       break;
                   default:
                       g2d.drawString((float)Math.round(spolu*100)/100f+" �", 225,224);
                       g2d.drawString((float)Math.round(spolu*100)/100f+" �", 225,612);
                       break;
               }
               
               spolu = (float)zmluva.hodnota + poplatok + ((float)zmluva.hodnota/100)*(float)zmluva.urok3;
               //koli prechodu na euro
               switch(zmluva.datum.getYear()){
                   case 2008:
                       g2d.drawString((float)Math.round(spolu*100)/100f+" SK"+" ("+(float)Math.round((spolu/30.126f)*100)/100f+" �)", 225,236);
                       g2d.drawString((float)Math.round(spolu*100)/100f+" SK"+" ("+(float)Math.round((spolu/30.126f)*100)/100f+" �)", 225,624);
                       break;
                   case 2009:
                       g2d.drawString((float)Math.round(spolu*100)/100f+" �"+" ("+(float)Math.round((spolu*30.126f)*100)/100f+" SK)", 225,236);
                       g2d.drawString((float)Math.round(spolu*100)/100f+" �"+" ("+(float)Math.round((spolu*30.126f)*100)/100f+" SK)", 225,624);
                       break;
                   default:
                       g2d.drawString((float)Math.round(spolu*100)/100f+" �", 225,236);
                       g2d.drawString((float)Math.round(spolu*100)/100f+" �", 225,624);
                       break;
               }
               
               spolu = (float)zmluva.hodnota + poplatok + ((float)zmluva.hodnota/100)*(float)zmluva.urok4;
               //koli prechodu na euro
               switch(zmluva.datum.getYear()){
                   case 2008:
                       g2d.drawString((float)Math.round(spolu*100)/100f+" SK"+" ("+(float)Math.round((spolu/30.126f)*100)/100f+" �)", 225,248);
                       g2d.drawString((float)Math.round(spolu*100)/100f+" SK"+" ("+(float)Math.round((spolu/30.126f)*100)/100f+" �)", 225,636);
                       break;
                   case 2009:
                       g2d.drawString((float)Math.round(spolu*100)/100f+" �"+" ("+(float)Math.round((spolu*30.126f)*100)/100f+" SK)", 225,248);
                       g2d.drawString((float)Math.round(spolu*100)/100f+" �"+" ("+(float)Math.round((spolu*30.126f)*100)/100f+" SK)", 225,636);
                       break;
                   default:
                       g2d.drawString((float)Math.round(spolu*100)/100f+" �", 225,248);
                       g2d.drawString((float)Math.round(spolu*100)/100f+" �", 225,636);
                       break;
               }
               
               
               spolu = (float)zmluva.hodnota + poplatok + ((float)zmluva.hodnota/100)*(float)zmluva.urok5;
               //koli prechodu na euro
               switch(zmluva.datum.getYear()){
                   case 2008:
                       g2d.drawString((float)Math.round(spolu*100)/100f+" SK"+" ("+(float)Math.round((spolu/30.126f)*100)/100f+" �)", 225,260);
                       g2d.drawString((float)Math.round(spolu*100)/100f+" SK"+" ("+(float)Math.round((spolu/30.126f)*100)/100f+" �)", 225,648);
                       break;
                   case 2009:
                       g2d.drawString((float)Math.round(spolu*100)/100f+" �"+" ("+(float)Math.round((spolu*30.126f)*100)/100f+" SK)", 225,260);
                       g2d.drawString((float)Math.round(spolu*100)/100f+" �"+" ("+(float)Math.round((spolu*30.126f)*100)/100f+" SK)", 225,648);
                       break;
                   default:
                       g2d.drawString((float)Math.round(spolu*100)/100f+" �", 225,260);
                       g2d.drawString((float)Math.round(spolu*100)/100f+" �", 225,648);
                       break;
               }
               
               
               g2d.drawString("Dl��k prehlasuje, �e vec, ktor� poskytol do z�lohy je jeho v�lu�n�m vlastn�ctvom a je opr�vnen� ju zalo�i�.", 0,290);
               
               switch(cal.get(Calendar.YEAR)){
                   case 2008:
                        g2d.drawString("Za stratu �stri�ku a jeko vyh�adanie v evidencii ��tujeme 10 SK (0.33 �).", 0,310);
                        break;
                   case 2009:
                        g2d.drawString("Za stratu �stri�ku a jeko vyh�adanie v evidencii ��tujeme 0.33 � (10 SK).", 0,310);
                        break;
                   default:
                        g2d.drawString("Za stratu �stri�ku a jeko vyh�adanie v evidencii ��tujeme 0.33 �.", 0,310);
                        break;
               }
               
             g2d.drawString("�pln� znenie podmienok sa nach�dza v priestoroch z�lo�ne.", 0,330);  
             
             g2d.drawString("O tom �i sa ur�it� vec m��e sta� predmetom z�lo�nej zmluvy rozhoduje poveren� pracovn�k z�lo�ne.", 0,350);
             g2d.drawString("Predmetom z�lo�nej zmluvy nem��u by� po�koden� a nekompletn� veci ako aj veci, ktor� nie s� funk�n�.", 0,362);
              
            switch(cal.get(Calendar.YEAR)){
                case 2008:
                    g2d.drawString("Dl�n�k potvrdzuej prevzatie p��i�ky: "+zmluva.hodnota+" SK ("+(float)Math.round((zmluva.hodnota/30.126)*100f)/100+" �) v peniazoch.", 0, 390);
                    break;   
                case 2009:
                    g2d.drawString("Dl�n�k potvrdzuej prevzatie p��i�ky: "+zmluva.hodnota+" � ("+(float)Math.round((zmluva.hodnota*30.126)*100f)/100+" SK) v peniazoch.", 0, 380);
                    break;
                default:
                    g2d.drawString("Dl�n�k potvrdzuej prevzatie p��i�ky: "+zmluva.hodnota+" � v peniazoch.", 0, 380);
                    break;
            }
              
            g2d.drawString("V Martine : "+zmluva.datum.getDate()+"."+(zmluva.datum.getMonth()+1)+"."+zmluva.datum.getYear(), 0, 430);
            
            g2d.drawString("..............................         .............................." , 200, 430);
            g2d.drawString("          Verite�                                     Dl�n�k        " , 200, 440);
            
            g2d.drawString("Potvrdzujem prevzatie veci bez nedostatkov d�a ......................................................................." , 0, 460);
            g2d.drawString("....................................................................................................................................................." , 0, 472);
            g2d.drawString("Podpis" , 250, 484);
            
            g2d.drawString("__________________________________________________________________________________________________________________________________________________" , 0, 520);
            
            g2d.drawString("Z�lo�n� zmluva ��slo: "+zmluva.ID  ,0,550);
            
            g2d.drawString("Zalo�en� d�a: "+zmluva.datum.getDate()+"."+(zmluva.datum.getMonth()+1)+"."+zmluva.datum.getYear()  ,250,550);
            g2d.drawString("Posledn� de� v�beru: "+cal_zmluva.get(Calendar.DAY_OF_MONTH)+"."+(cal_zmluva.get(Calendar.MONTH)+1)+"."+cal_zmluva.get(Calendar.YEAR)  ,250,570);
            return(PAGE_EXISTS);
        }
        else{
            return(NO_SUCH_PAGE);
        }
    }
    
    
}
