package manola;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterJob;
import java.util.Calendar;

/**
 * Tlač zmluvy (Printable). Predpokladá existenciu tried Pouzivatel a Zmluva.
 */
public class Tlac implements Printable {

    public Pouzivatel zakaznik;
    public Zmluva zmluva;

    /** Vytvorenie a okamžité spustenie tlače */
    public Tlac(Pouzivatel novy_pouzivatel, Zmluva nova_zmluva) {
        zakaznik = novy_pouzivatel;
        zmluva   = nova_zmluva;

        PrinterJob printJob = PrinterJob.getPrinterJob();
        printJob.setPrintable(this);

        try {
            printJob.print();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int print(Graphics g, PageFormat pageFormat, int page) {
        if (page != 0) return NO_SUCH_PAGE;

        // Nastavenie vykresľovania a fontov s podporou diakritiky
        Graphics2D g2d = (Graphics2D) g;
        g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                             RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        Font normalFont = new Font("DejaVu Sans", Font.PLAIN, 10);
        Font boldFont   = new Font("DejaVu Sans", Font.BOLD, 12);

        // Dátumy
        Calendar cal = Calendar.getInstance();
        Calendar calZmluva = Calendar.getInstance();
        // Bezpečné nastavenie času zo zmluvy (zmluva.datum je očakávaný java.util.Date)
        if (zmluva.datum != null) {
            calZmluva.setTime(zmluva.datum);
        }

        g2d.setFont(boldFont);
        g2d.drawString("Záložná zmluva číslo: " + zmluva.ID, 120, 10);

        g2d.setFont(normalFont);
        g2d.drawString("O poskytnutí pôžičky v záložnom práve", 150, 22);

        g2d.drawString("Uzatvorená zmluva v zmysle §657 a nasl., §121 ods. 3 a §544 až §545 Občianskeho zákonníka", 0, 40);
        g2d.drawString("medzi MANOLA s.r.o. a zastúpeným pracovníkom záložne p. Peter Gašparík (Veriteľ).", 0, 52);

        g2d.drawString("'Dlžník' pán/pani: ", 0, 70);
        g2d.drawString("Rodné číslo: ", 0, 82);
        g2d.drawString("Číslo OP: ", 0, 94);
        g2d.drawString("Bytom: ", 0, 106);

        g2d.drawString(zakaznik.meno + " " + zakaznik.priezvisko, 200, 70);
        g2d.drawString(zakaznik.cislo_OP, 200, 82);
        g2d.drawString(zakaznik.doplnk_info, 200, 94);
        g2d.drawString(zakaznik.adresa, 200, 106);

        int rokZmluvy = calZmluva.get(Calendar.YEAR);
        if (rokZmluvy == 2008) {
            g2d.drawString(
                "Veriteľ poskytne dlžníkovi pôžičku v sume: " + zmluva.hodnota + " SK (" +
                Math.round(((float) zmluva.hodnota / 30.126f) * 100f) / 100f + " €) v peniazoch.", 0, 130);
        } else if (rokZmluvy == 2009) {
            g2d.drawString(
                "Veriteľ poskytne dlžníkovi pôžičku v sume: " + zmluva.hodnota + " € (" +
                Math.round(((float) zmluva.hodnota * 30.126f) * 100f) / 100f + " SK) v peniazoch.", 0, 130);
        } else {
            g2d.drawString("Veriteľ poskytne dlžníkovi pôžičku v sume: " + zmluva.hodnota + " € v peniazoch.", 0, 130);
        }

        if (rokZmluvy == 2008) {
            g2d.drawString(
                "Poskytnutá suma: " + zmluva.hodnota + " SK (" +
                Math.round(((float) zmluva.hodnota / 30.126f) * 100f) / 100f + " €) v peniazoch.", 0, 570);
        } else if (rokZmluvy == 2009) {
            g2d.drawString(
                "Poskytnutá suma: " + zmluva.hodnota + " € (" +
                Math.round(((float) zmluva.hodnota * 30.126f) * 100f) / 100f + " SK) v peniazoch.", 0, 570);
        } else {
            g2d.drawString("Poskytnutá suma: " + zmluva.hodnota + " € v peniazoch.", 0, 570);
        }

        g2d.drawString("Za poskytnutú pôžičku odovzdá dlžník veriteľovi do zálohy:", 0, 150);
        g2d.drawString(String.valueOf(zmluva.predmet), 0, 162);

        int rok = cal.get(Calendar.YEAR);
        if (rok == 2008) {
            g2d.drawString("ktorú bude veriteľ starostlivo opatrovať, za čo mu prislúcha suma vo výške 20 SK (0,66 €).", 0, 180);
        } else if (rok == 2009) {
            g2d.drawString("ktorú bude veriteľ starostlivo opatrovať, za čo mu prislúcha suma vo výške 0,66 € (20 SK).", 0, 180);
        } else {
            g2d.drawString("ktorú bude veriteľ starostlivo opatrovať, za čo mu prislúcha suma vo výške 0,66 €.", 0, 180);
        }

        g2d.drawString("Dlžník sa zaväzuje veriteľovi pôžičku vrátiť spolu s úrokom v týchto splátkach a termínoch:", 0, 200);

        // 1. splátka
        g2d.drawString("od: " + d(calZmluvy, 0), 0, 212);
        g2d.drawString("od: " + d(calZmluvy, 0), 0, 600);
        calZmluva.add(Calendar.DAY_OF_MONTH, 7);
        g2d.drawString("do: " + d(calZmluva, 0), 75, 212);
        g2d.drawString("do: " + d(calZmluva, 0), 75, 600);
        g2d.drawString("úrok: " + Math.round(zmluva.urok1) + "%", 150, 212);
        g2d.drawString("úrok: " + Math.round(zmluva.urok1) + "%", 150, 600);

        // 2. splátka
        calZmluva.add(Calendar.DAY_OF_MONTH, 1);
        g2d.drawString("od: " + d(calZmluva, 0), 0, 224);
        g2d.drawString("od: " + d(calZmluva, 0), 0, 612);
        calZmluva.add(Calendar.DAY_OF_MONTH, 6);
        g2d.drawString("do: " + d(calZmluva, 0), 75, 224);
        g2d.drawString("do: " + d(calZmluva, 0), 75, 612);
        g2d.drawString("úrok: " + Math.round(zmluva.urok2) + "%", 150, 224);
        g2d.drawString("úrok: " + Math.round(zmluva.urok2) + "%", 150, 612);

        // 3. splátka
        calZmluva.add(Calendar.DAY_OF_MONTH, 1);
        g2d.drawString("od: " + d(calZmluva, 0), 0, 236);
        g2d.drawString("od: " + d(calZmluva, 0), 0, 624);
        calZmluva.add(Calendar.DAY_OF_MONTH, 6);
        g2d.drawString("do: " + d(calZmluva, 0), 75, 236);
        g2d.drawString("do: " + d(calZmluva, 0), 75, 624);
        g2d.drawString("úrok: " + Math.round(zmluva.urok3) + "%", 150, 236);
        g2d.drawString("úrok: " + Math.round(zmluva.urok3) + "%", 150, 624);

        // 4. splátka
        calZmluva.add(Calendar.DAY_OF_MONTH, 1);
        g2d.drawString("od: " + d(calZmluva, 0), 0, 248);
        g2d.drawString("od: " + d(calZmluva, 0), 0, 636);
        calZmluva.add(Calendar.DAY_OF_MONTH, 6);
        g2d.drawString("do: " + d(calZmluva, 0), 75, 248);
        g2d.drawString("do: " + d(calZmluva, 0), 75, 636);
        g2d.drawString("úrok: " + Math.round(zmluva.urok4) + "%", 150, 248);
        g2d.drawString("úrok: " + Math.round(zmluva.urok4) + "%", 150, 636);

        // 5. splátka
        calZmluva.add(Calendar.DAY_OF_MONTH, 1);
        g2d.drawString("od: " + d(calZmluva, 0), 0, 260);
        g2d.drawString("od: " + d(calZmluva, 0), 0, 648);
        calZmluva.add(Calendar.DAY_OF_MONTH, 6);
        g2d.drawString("do: " + d(calZmluva, 0), 75, 260);
        g2d.drawString("do: " + d(calZmluva, 0), 75, 648);
        g2d.drawString("úrok: " + Math.round(zmluva.urok5) + "%", 150, 260);
        g2d.drawString("úrok: " + Math.round(zmluva.urok5) + "%", 150, 648);

        // Poplatky a sumy
        float poplatok = (rokZmluvy < 2009) ? 20f : 0.66f;

        float spolu1 = (float) zmluva.hodnota + poplatok + ((float) zmluva.hodnota / 100f) * (float) zmluva.urok1;
        drawSuma(g2d, spolu1, 225, 212, rokZmluvy);
        drawSuma(g2d, spolu1, 225, 600, rokZmluvy);

        float spolu2 = (float) zmluva.hodnota + poplatok + ((float) zmluva.hodnota / 100f) * (float) zmluva.urok2;
        drawSuma(g2d, spolu2, 225, 224, rokZmluvy);
        drawSuma(g2d, spolu2, 225, 612, rokZmluvy);

        float spolu3 = (float) zmluva.hodnota + poplatok + ((float) zmluva.hodnota / 100f) * (float) zmluva.urok3;
        drawSuma(g2d, spolu3, 225, 236, rokZmluvy);
        drawSuma(g2d, spolu3, 225, 624, rokZmluvy);

        float spolu4 = (float) zmluva.hodnota + poplatok + ((float) zmluva.hodnota / 100f) * (float) zmluva.urok4;
        drawSuma(g2d, spolu4, 225, 248, rokZmluvy);
        drawSuma(g2d, spolu4, 225, 636, rokZmluvy);

        float spolu5 = (float) zmluva.hodnota + poplatok + ((float) zmluva.hodnota / 100f) * (float) zmluva.urok5;
        drawSuma(g2d, spolu5, 225, 260, rokZmluvy);
        drawSuma(g2d, spolu5, 225, 648, rokZmluvy);

        g2d.drawString("Dlžník prehlasuje, že vec, ktorú poskytol do zálohy, je jeho výlučným vlastníctvom a je oprávnený ju založiť.", 0, 290);

        if (rok == 2008) {
            g2d.drawString("Za stratu ústrižku a jeho vyhľadanie v evidencii účtujeme 10 SK (0,33 €).", 0, 310);
        } else if (rok == 2009) {
            g2d.drawString("Za stratu ústrižku a jeho vyhľadanie v evidencii účtujeme 0,33 € (10 SK).", 0, 310);
        } else {
            g2d.drawString("Za stratu ústrižku a jeho vyhľadanie v evidencii účtujeme 0,33 €.", 0, 310);
        }

        g2d.drawString("Úplné znenie podmienok sa nachádza v priestoroch záložne.", 0, 330);
        g2d.drawString("O tom, či sa určitá vec môže stať predmetom záložnej zmluvy, rozhoduje poverený pracovník záložne.", 0, 350);
        g2d.drawString("Predmetom záložnej zmluvy nemôžu byť poškodené a nekompletné veci ani veci, ktoré nie sú funkčné.", 0, 362);

        if (rokZmluvy == 2008) {
            g2d.drawString("Dlžník potvrdzuje prevzatie pôžičky: " + zmluva.hodnota + " SK (" +
                           Math.round((zmluva.hodnota / 30.126f) * 100f) / 100f + " €) v peniazoch.", 0, 380);
        } else if (rokZmluvy == 2009) {
            g2d.drawString("Dlžník potvrdzuje prevzatie pôžičky: " + zmluva.hodnota + " € (" +
                           Math.round((zmluva.hodnota * 30.126f) * 100f) / 100f + " SK) v peniazoch.", 0, 380);
        } else {
            g2d.drawString("Dlžník potvrdzuje prevzatie pôžičky: " + zmluva.hodnota + " € v peniazoch.", 0, 380);
        }

        g2d.drawString("V Martine: " + d(calZmluva, -999 /*print actual date*/ ), 0, 430);

        g2d.drawString("..............................         ..............................", 200, 430);
        g2d.drawString("          Veriteľ                                     Dlžník        ", 200, 440);

        g2d.drawString("Potvrdzujem prevzatie veci bez nedostatkov dňa .................................................................", 0, 460);
        g2d.drawString(".....................................................................................................................................................", 0, 472);
        g2d.drawString("Podpis", 250, 484);

        g2d.drawString("__________________________________________________________________________________________________________________________________________________", 0, 520);

        g2d.drawString("Záložná zmluva číslo: " + zmluva.ID, 0, 550);
        g2d.drawString(
            "Založená dňa: " + fmt(calZmluva.get(Calendar.DAY_OF_MONTH)) + "." +
            fmt(calZmluva.get(Calendar.MONTH) + 1) + "." + calZmluva.get(Calendar.YEAR), 250, 550);

        // Posledný deň výberu = aktuálna hodnota calZmluva po predchádzajúcich add() operáciách
        g2d.drawString(
            "Posledný deň výberu: " + fmt(calZmluva.get(Calendar.DAY_OF_MONTH)) + "." +
            fmt(calZmluva.get(Calendar.MONTH) + 1) + "." + calZmluva.get(Calendar.YEAR), 250, 570);

        return PAGE_EXISTS;
    }

    // Pomocné formátovanie dátumu „dd.mm.yyyy“
    private static String d(Calendar c, int _unused) {
        return fmt(c.get(Calendar.DAY_OF_MONTH)) + "." +
               fmt(c.get(Calendar.MONTH) + 1) + "." +
               c.get(Calendar.YEAR);
    }

    private static String fmt(int n) {
        return (n < 10 ? "0" : "") + n;
    }

    // Vykreslenie sumy podľa roka s konverziami SKK/EUR pre 2008/2009
    private static void drawSuma(Graphics2D g2d, float suma, int x, int y, int rok) {
        float zaokr = (float) Math.round(suma * 100f) / 100f;
        if (rok == 2008) {
            float eur = (float) Math.round((suma / 30.126f) * 100f) / 100f;
            g2d.drawString(zaokr + " SK (" + eur + " €)", x, y);
        } else if (rok == 2009) {
            float sk = (float) Math.round((suma * 30.126f) * 100f) / 100f;
            g2d.drawString(zaokr + " € (" + sk + " SK)", x, y);
        } else {
            g2d.drawString(zaokr + " €", x, y);
        }
    }
}
