/*
 * ZmluvaDetail.java
 *
 * Created on 31 July 2008, 09:46
 */

package manola;

import java.util.Calendar;

/**
 *
 * @author  simo
 */
public class ZmluvaDetail extends javax.swing.JFrame {
    
    Zmluva zmluva;
    Pouzivatel zakaznik ;
    
    public String ID_zmluvy = null;
    /** Creates new form ZmluvaDetail */
    public ZmluvaDetail() {
        initComponents();
    }
    
    
    public ZmluvaDetail(String ID) {
        initComponents();
        
        ID_zmluvy = ID;
        
        Databaza db = new Databaza();
        
        zmluva = db.vratZmluvuPodlaID(ID);
        zakaznik = db.vratPouzivatelaPodlaCislaOP(zmluva.zakaznik_OP.toString());
        
       lbl_meno.setText(zakaznik.meno+" "+zakaznik.priezvisko);
       lbl_adresa.setText(zakaznik.adresa);
       lbl_doplnk_info.setText(zakaznik.doplnk_info);
       
       lbl_cislo_OP.setText(zmluva.zakaznik_OP);
       
       
       lbl_predmet.setText(zmluva.predmet);
       lbl_suma.setText(""+zmluva.hodnota);
       lbl_poznamka.setText(zmluva.poznamka);
       lbl_datum.setText(zmluva.datum.getDate()+"."+(zmluva.datum.getMonth()+1)+"."+zmluva.datum.getYear());
       lbl_id_zmluvy.setText(zmluva.ID);
       
       jtf_suma_oskupenia.setText(zmluva.odkupna_suma+"");
      
       lbl_posledna_zmena.setText(zmluva.datum_zmeny.getDate()+"."+(zmluva.datum_zmeny.getMonth()+1)+"."+zmluva.datum_zmeny.getYear());
       
       Calendar cal = Calendar.getInstance();
       cal.set(zmluva.datum.getYear(), zmluva.datum.getMonth(), zmluva.datum.getDate());
       
       jcb_stav_zmluvy.setSelectedItem((Object)zmluva.stav);
       
       lbl_od1.setText("od: "+cal.get(Calendar.DAY_OF_MONTH)+"."+(cal.get(Calendar.MONTH)+1)+"."+cal.get(Calendar.YEAR));
       cal.add(Calendar.DAY_OF_MONTH,7);
       lbl_do1.setText("od: "+cal.get(Calendar.DAY_OF_MONTH)+"."+(cal.get(Calendar.MONTH)+1)+"."+cal.get(Calendar.YEAR));
       
       cal.add(Calendar.DAY_OF_MONTH,1);
       lbl_od2.setText("od: "+cal.get(Calendar.DAY_OF_MONTH)+"."+(cal.get(Calendar.MONTH)+1)+"."+cal.get(Calendar.YEAR));
       cal.add(Calendar.DAY_OF_MONTH,6);
       lbl_do2.setText("od: "+cal.get(Calendar.DAY_OF_MONTH)+"."+(cal.get(Calendar.MONTH)+1)+"."+cal.get(Calendar.YEAR));
      
       cal.add(Calendar.DAY_OF_MONTH,1);
       lbl_od3.setText("od: "+cal.get(Calendar.DAY_OF_MONTH)+"."+(cal.get(Calendar.MONTH)+1)+"."+cal.get(Calendar.YEAR));
       cal.add(Calendar.DAY_OF_MONTH,6);
       lbl_do3.setText("od: "+cal.get(Calendar.DAY_OF_MONTH)+"."+(cal.get(Calendar.MONTH)+1)+"."+cal.get(Calendar.YEAR));

       cal.add(Calendar.DAY_OF_MONTH,1);
       lbl_od4.setText("od: "+cal.get(Calendar.DAY_OF_MONTH)+"."+(cal.get(Calendar.MONTH)+1)+"."+cal.get(Calendar.YEAR));
       cal.add(Calendar.DAY_OF_MONTH,6);
       lbl_do4.setText("od: "+cal.get(Calendar.DAY_OF_MONTH)+"."+(cal.get(Calendar.MONTH)+1)+"."+cal.get(Calendar.YEAR));
       
       cal.add(Calendar.DAY_OF_MONTH,1);
       lbl_od5.setText("od: "+cal.get(Calendar.DAY_OF_MONTH)+"."+(cal.get(Calendar.MONTH)+1)+"."+cal.get(Calendar.YEAR));
       cal.add(Calendar.DAY_OF_MONTH,6);
       lbl_do5.setText("od: "+cal.get(Calendar.DAY_OF_MONTH)+"."+(cal.get(Calendar.MONTH)+1)+"."+cal.get(Calendar.YEAR));

       
       Nastavenia nastavenia = db.vratNastavenia();
       lbl_urok1.setText("urok "+zmluva.urok1+"%");
       lbl_urok2.setText("urok "+zmluva.urok2+"%");
       lbl_urok3.setText("urok "+zmluva.urok3+"%");
       lbl_urok4.setText("urok "+zmluva.urok4+"%");
       lbl_urok5.setText("urok "+zmluva.urok5+"%");
       
       float spolu =0;
       float poplatok;
       
       if(zmluva.datum.getYear() == 2008) poplatok = 20f;
       else poplatok = 0.66f;
       
       spolu = (float)zmluva.hodnota + poplatok + ((float)zmluva.hodnota/100)*(float)zmluva.urok1;
       //koli prechodu na euro
       switch(zmluva.datum.getYear()){
           case 2008:
               lbl_spolu1.setText((float)Math.round(spolu*100)/100f+" SK"+" ("+(float)Math.round((spolu/30.126f)*100)/100f+" €)");
               break;
           case 2009:
               lbl_spolu1.setText((float)Math.round(spolu*100)/100f+" €"+" ("+(float)Math.round((spolu*30.126f)*100)/100f+" SK)");
               break;
           default:
               lbl_spolu1.setText((float)Math.round(spolu*100)/100f+" €");
               break;
       }
       
       spolu = (float)zmluva.hodnota + poplatok + ((float)zmluva.hodnota/100)*(float)zmluva.urok2;
       //koli prechodu na euro
       switch(zmluva.datum.getYear()){
           case 2008:
               lbl_spolu2.setText((float)Math.round(spolu*100)/100f+" SK"+" ("+(float)Math.round((spolu/30.126f)*100)/100f+" €)");
               break;
           case 2009:
               lbl_spolu2.setText((float)Math.round(spolu*100)/100f+" €"+" ("+(float)Math.round((spolu*30.126f)*100)/100f+" SK)");
               break;
           default:
               lbl_spolu2.setText((float)Math.round(spolu*100)/100f+" €");
               break;
       }       
       
       spolu = (float)zmluva.hodnota + poplatok + ((float)zmluva.hodnota/100)*(float)zmluva.urok3;
       //koli prechodu na â‚¬o
       switch(zmluva.datum.getYear()){
           case 2008:
               lbl_spolu3.setText(Math.round((float)spolu*100)/100f+" SK"+" ("+(float)Math.round((spolu/30.126f)*100)/100f+" €)");
               break;
           case 2009:
               lbl_spolu3.setText(Math.round((float)spolu*100)/100f+" €"+" ("+(float)Math.round((spolu*30.126f)*100)/100f+" SK)");
               break;
           default:
               lbl_spolu3.setText(Math.round((float)spolu*100)/100f+" €");
               break;
       }

       spolu = (float)zmluva.hodnota + poplatok + ((float)zmluva.hodnota/100)*(float)zmluva.urok4;
       //koli prechodu na euro
       switch(zmluva.datum.getYear()){
           case 2008:
               lbl_spolu4.setText((float)Math.round(spolu*100)/100f+" SK"+" ("+(float)Math.round((spolu/30.126f)*100)/100f+" €)");
               break;
           case 2009:
               lbl_spolu4.setText((float)Math.round(spolu*100)/100f+" €"+" ("+(float)Math.round((spolu*30.126f)*100)/100f+" SK)");
               break;
           default:
               lbl_spolu4.setText((float)Math.round(spolu*100)/100f+" €");               
               break;
       }
       
       spolu = (float)zmluva.hodnota + poplatok + ((float)zmluva.hodnota/100)*(float)zmluva.urok5;
       //koli prechodu na euro
       switch(zmluva.datum.getYear()){
           case 2008:
               lbl_spolu5.setText((float)Math.round(spolu*100)/100f+" SK"+" ("+(float)Math.round((spolu/30.126f)*100)/100f+" €)");
               break;
           case 2009:
               lbl_spolu5.setText((float)Math.round(spolu*100)/100f+" €"+" ("+(float)Math.round((spolu*30.126f)*100)/100f+" SK)");
               break;
           default:
               lbl_spolu5.setText((float)Math.round(spolu*100)/100f+" €");               
               break;
       }       
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        lbl_od1 = new javax.swing.JLabel();
        lbl_od2 = new javax.swing.JLabel();
        lbl_od3 = new javax.swing.JLabel();
        lbl_od4 = new javax.swing.JLabel();
        lbl_od5 = new javax.swing.JLabel();
        lbl_do1 = new javax.swing.JLabel();
        lbl_do2 = new javax.swing.JLabel();
        lbl_do3 = new javax.swing.JLabel();
        lbl_do4 = new javax.swing.JLabel();
        lbl_do5 = new javax.swing.JLabel();
        lbl_urok1 = new javax.swing.JLabel();
        lbl_urok2 = new javax.swing.JLabel();
        lbl_urok3 = new javax.swing.JLabel();
        lbl_urok4 = new javax.swing.JLabel();
        lbl_urok5 = new javax.swing.JLabel();
        lbl_spolu1 = new javax.swing.JLabel();
        lbl_spolu2 = new javax.swing.JLabel();
        lbl_spolu3 = new javax.swing.JLabel();
        lbl_spolu4 = new javax.swing.JLabel();
        lbl_spolu5 = new javax.swing.JLabel();
        lbl_meno = new javax.swing.JLabel();
        lbl_cislo_OP = new javax.swing.JLabel();
        lbl_doplnk_info = new javax.swing.JLabel();
        lbl_adresa = new javax.swing.JLabel();
        lbl_predmet = new javax.swing.JLabel();
        lbl_poznamka = new javax.swing.JLabel();
        lbl_suma = new javax.swing.JLabel();
        lbl_datum = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jcb_stav_zmluvy = new javax.swing.JComboBox();
        jbt_uloz_zmenu_stavu = new javax.swing.JButton();
        jtf_suma_oskupenia = new javax.swing.JTextField();
        lbl_odkupna_suma = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        lbl_posledna_zmena = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        lbl_id_zmluvy = new javax.swing.JLabel();
        btn_tlac = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Detail zmluvy");

        jLabel1.setText("Meno:");

        jLabel2.setText("Rodne cislo:");

        jLabel3.setText("Cislo OP:");

        jLabel4.setText("Adresa");

        jLabel5.setText("Predmet zmluvy:");

        jLabel6.setText("Poznamka:");

        jLabel7.setText("Suma:");

        jLabel8.setText("Datum:");

        lbl_od1.setText("jLabel9");

        lbl_od2.setText("jLabel10");

        lbl_od3.setText("jLab");

        lbl_od4.setText("jLabel12");

        lbl_od5.setText("jLabel9");

        lbl_do1.setText("jLabel9");

        lbl_do2.setText("jLabel10");

        lbl_do3.setText("jLabel11");

        lbl_do4.setText("jLabel12");

        lbl_do5.setText("jLabel13");

        lbl_urok1.setText("jLabel9");

        lbl_urok2.setText("jLabel10");

        lbl_urok3.setText("jLabel11");

        lbl_urok4.setText("jLabel12");

        lbl_urok5.setText("jLabel13");

        lbl_spolu1.setText("jLabel9");

        lbl_spolu2.setText("jLabel10");

        lbl_spolu3.setText("jLabel11");

        lbl_spolu4.setText("jLabel12");

        lbl_spolu5.setText("jLabel13");

        lbl_meno.setText("jLabel9");

        lbl_cislo_OP.setText("jLabel10");

        lbl_doplnk_info.setText("jLabel11");

        lbl_adresa.setText("jLabel12");

        lbl_predmet.setText("jLabel13");

        lbl_poznamka.setText("jLabel14");

        lbl_suma.setText("jLabel15");

        lbl_datum.setText("jLabel16");

        jLabel9.setText("Stav:");

        jcb_stav_zmluvy.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Zalozena", "Prepadnuta", "Vyplatena", "Odkupena" }));
        jcb_stav_zmluvy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcb_stav_zmluvyActionPerformed(evt);
            }
        });

        jbt_uloz_zmenu_stavu.setText("Uloz zmeny");
        jbt_uloz_zmenu_stavu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbt_uloz_zmenu_stavuActionPerformed(evt);
            }
        });

        jtf_suma_oskupenia.setEnabled(false);

        lbl_odkupna_suma.setText("Suma:");

        jLabel10.setText("Posledna zmena stavu:");

        lbl_posledna_zmena.setText("jLabel11");

        jLabel11.setText("ID zmluvy:");

        lbl_id_zmluvy.setText("jLabel12");

        btn_tlac.setText("Tlac");
        btn_tlac.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_tlacActionPerformed(evt);
            }
        });

        jButton1.setText("Zavri okno");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel11)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lbl_od2)
                                    .addComponent(lbl_od1)
                                    .addComponent(lbl_od3)
                                    .addComponent(lbl_od4)
                                    .addComponent(lbl_od5))
                                .addGap(72, 72, 72)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lbl_do4)
                                    .addComponent(lbl_do5)
                                    .addComponent(lbl_do3)
                                    .addComponent(lbl_do1)
                                    .addComponent(lbl_do2))
                                .addGap(61, 61, 61)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lbl_urok5)
                                    .addComponent(lbl_urok4)
                                    .addComponent(lbl_urok3)
                                    .addComponent(lbl_urok1)
                                    .addComponent(lbl_urok2)))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel8)
                                    .addComponent(jLabel9)
                                    .addComponent(jLabel10))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lbl_posledna_zmena)
                                    .addComponent(jcb_stav_zmluvy, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lbl_poznamka)
                                    .addComponent(lbl_predmet)
                                    .addComponent(lbl_datum)
                                    .addComponent(lbl_suma)
                                    .addComponent(lbl_id_zmluvy))))
                        .addGap(39, 39, 39)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(67, 67, 67)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lbl_spolu5)
                                    .addComponent(lbl_spolu3)
                                    .addComponent(lbl_spolu2)
                                    .addComponent(lbl_spolu4)
                                    .addComponent(lbl_spolu1)))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jbt_uloz_zmenu_stavu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(lbl_odkupna_suma)
                                        .addGap(5, 5, 5)
                                        .addComponent(jtf_suma_oskupenia, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(btn_tlac, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4))
                        .addGap(79, 79, 79)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbl_adresa)
                            .addComponent(lbl_doplnk_info)
                            .addComponent(lbl_cislo_OP)
                            .addComponent(lbl_meno))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(lbl_meno))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(lbl_cislo_OP)
                    .addComponent(btn_tlac))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(lbl_doplnk_info))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(lbl_adresa)
                    .addComponent(jButton1))
                .addGap(17, 17, 17)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(lbl_id_zmluvy))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(lbl_predmet))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(lbl_poznamka))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(lbl_suma))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(lbl_datum))
                .addGap(17, 17, 17)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(jtf_suma_oskupenia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_odkupna_suma)
                    .addComponent(jcb_stav_zmluvy, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(lbl_posledna_zmena)
                    .addComponent(jbt_uloz_zmenu_stavu))
                .addGap(33, 33, 33)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lbl_od1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbl_od2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbl_od3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbl_od4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbl_od5))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                            .addGap(1, 1, 1)
                            .addComponent(lbl_do1)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(lbl_do2)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(lbl_do3, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(lbl_do4)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(lbl_do5))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(lbl_spolu1)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(lbl_spolu2)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(lbl_spolu3)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(lbl_spolu4)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(lbl_spolu5))
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(lbl_urok1)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(lbl_urok2)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(lbl_urok3)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(lbl_urok4)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(lbl_urok5)))
                            .addGap(1, 1, 1))))
                .addContainerGap(57, Short.MAX_VALUE))
        );

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-690)/2, (screenSize.height-500)/2, 690, 500);
    }// </editor-fold>//GEN-END:initComponents

    private void btn_tlacActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_tlacActionPerformed
        Tlac tlac = new Tlac(zakaznik, zmluva);
        
    }//GEN-LAST:event_btn_tlacActionPerformed

    private void jcb_stav_zmluvyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcb_stav_zmluvyActionPerformed
        if (jcb_stav_zmluvy.getSelectedIndex() > 1) jtf_suma_oskupenia.setEnabled(true);
        else jtf_suma_oskupenia.setEnabled(false);
    }//GEN-LAST:event_jcb_stav_zmluvyActionPerformed

    private void jbt_uloz_zmenu_stavuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbt_uloz_zmenu_stavuActionPerformed
        Databaza db = new Databaza();
        db.zmenStavZmluvy(ID_zmluvy, jcb_stav_zmluvy.getSelectedItem().toString(), new Float(jtf_suma_oskupenia.getText()).floatValue());
    }//GEN-LAST:event_jbt_uloz_zmenu_stavuActionPerformed

private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
    this.dispose();
}//GEN-LAST:event_jButton1ActionPerformed
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ZmluvaDetail().setVisible(true);
            }
        });
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_tlac;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JButton jbt_uloz_zmenu_stavu;
    private javax.swing.JComboBox jcb_stav_zmluvy;
    private javax.swing.JTextField jtf_suma_oskupenia;
    private javax.swing.JLabel lbl_adresa;
    private javax.swing.JLabel lbl_cislo_OP;
    private javax.swing.JLabel lbl_datum;
    private javax.swing.JLabel lbl_do1;
    private javax.swing.JLabel lbl_do2;
    private javax.swing.JLabel lbl_do3;
    private javax.swing.JLabel lbl_do4;
    private javax.swing.JLabel lbl_do5;
    private javax.swing.JLabel lbl_doplnk_info;
    private javax.swing.JLabel lbl_id_zmluvy;
    private javax.swing.JLabel lbl_meno;
    private javax.swing.JLabel lbl_od1;
    private javax.swing.JLabel lbl_od2;
    private javax.swing.JLabel lbl_od3;
    private javax.swing.JLabel lbl_od4;
    private javax.swing.JLabel lbl_od5;
    private javax.swing.JLabel lbl_odkupna_suma;
    private javax.swing.JLabel lbl_posledna_zmena;
    private javax.swing.JLabel lbl_poznamka;
    private javax.swing.JLabel lbl_predmet;
    private javax.swing.JLabel lbl_spolu1;
    private javax.swing.JLabel lbl_spolu2;
    private javax.swing.JLabel lbl_spolu3;
    private javax.swing.JLabel lbl_spolu4;
    private javax.swing.JLabel lbl_spolu5;
    private javax.swing.JLabel lbl_suma;
    private javax.swing.JLabel lbl_urok1;
    private javax.swing.JLabel lbl_urok2;
    private javax.swing.JLabel lbl_urok3;
    private javax.swing.JLabel lbl_urok4;
    private javax.swing.JLabel lbl_urok5;
    // End of variables declaration//GEN-END:variables
    
}
