/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package telecardio;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.util.Objects;

/**
 *
 * @author tiavr
 */
public class ApplicationGeneraliste extends javax.swing.JFrame {
    private String generaliste;
    private SimulateurEcg ecg = new SimulateurEcg();
    /**
     * Creates new form ApplicationGeneraliste
     */
    public ApplicationGeneraliste(ServeurTelecardiolSansSW serveur) {
        initComponents();
        DefaultTableModel model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        jTable1.setModel(model);
        model.addColumn("Numéro Dossier");
        model.addColumn("Etat");
        JFrame frame =new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(jPanel1);
        frame.pack();
        frame.setVisible(true);
        jTabbedPane2.setEnabledAt(1, false);
        jTabbedPane2.setEnabledAt(2, false);
        jTabbedPane2.setEnabledAt(3, false);
        nomGeneraliste.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e){
                nomGeneraliste.setText("");
            }
        });

        cycleECG.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e){
                cycleECG.setText("");
            }
        });

        //Button Panel 1
        deposerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(nomGeneraliste.getText().equals("")){
                    JOptionPane.showMessageDialog(null, "Veuillez entre un nom");

                }
                else {
                    generaliste = nomGeneraliste.getText();
                    jTabbedPane2.setSelectedIndex(1);
                    jTabbedPane2.setEnabledAt(1, true);
                }
            }
        });

        dossiersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(nomGeneraliste.getText().equals("")){
                    JOptionPane.showMessageDialog(null, "Veuillez entre un nom");

                }
                else{
                    generaliste = nomGeneraliste.getText();
                    if(serveur.rechercheDossierNomGeneraliste(generaliste).isEmpty()){
                        JOptionPane.showMessageDialog(null, "Aucun dossier traité par ce généraliste");
                    }
                    else{
                        model.setRowCount(0);
                        for(DossierExpertise dossier : serveur.rechercheDossierNomGeneraliste(generaliste)){
                            model.addRow(new Object[]{dossier.getNumero(), serveur.getEtat(dossier.getNumero()) });

                        }
                        jTabbedPane2.setEnabledAt(2,true);
                        jTabbedPane2.setEnabledAt(3,false);
                        jTabbedPane2.setEnabledAt(1,false);
                        jTabbedPane2.setSelectedIndex(2);
                    }


                }
            }
        });

        retourButtonTable.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jTabbedPane2.setSelectedIndex(0);
                jTabbedPane2.setEnabledAt(2,false);
                jTabbedPane2.setEnabledAt(3,false);
            }
        });


        ecgButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(isNumeric(cycleECG.getText())){
                    SimulateurEcg ecg = new SimulateurEcg();
                    jPanelEcg1.setEcg(ecg.genererEcg(Integer.parseInt(cycleECG.getText())));
                }
                else{
                    JOptionPane.showMessageDialog(null, "Veuillez saisir un cycle ecg valide");
                }
            }
        });
        //Listeners pannel 2
        transmettreDossierButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(isNumeric(nomPatient.getText()) || !isNumeric(cycleECG.getText()) || jTextArea1.getText().equals("")){
                    JOptionPane.showMessageDialog(null, "Les données fournies sont incorrectes");
                }
                else{
                    DossierExpertise dossier = new DossierExpertise(nomPatient.getText(), generaliste, jTextArea1.getText(), SimulateurEcg.genererEcg(Integer.parseInt(cycleECG.getText())));
                    int numDossier = serveur.deposerDossier(dossier.getPatient(), dossier.getGeneraliste(), dossier.getSignes(), dossier.getEcg());
                    dossier.setNumero(numDossier);
                    numeroDossier.setText(Integer.toString(dossier.getNumero()));
                    JOptionPane.showMessageDialog(null, "Le dossier numéro " + dossier.getNumero() + " a bien été transmis");
                    jTabbedPane2.setEnabledAt(0,false);
                }

            }
        });

        retourButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jTabbedPane2.setSelectedIndex(0);
                numeroDossier.setText("");
                nomPatient.setText("");
                jTextArea1.setText("");
                cycleECG.setText("");
                jPanelEcg1.setEcg(SimulateurEcg.genererEcg(0));
                jTabbedPane2.setEnabledAt(1,false);
            }
        });

        jTable1.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {     // to detect doble click events
                    JTable target = (JTable)e.getSource();
                    int row = target.getSelectedRow(); // select a row
                    int column = target.getSelectedColumn(); // select a column
                    jTabbedPane2.setSelectedIndex(3);
                    jTabbedPane2.setEnabledAt(3, true);
                    numeroDossierAvis.setText(String.valueOf(jTable1.getValueAt(row, column)));
                    nomPatientAvis.setText(serveur.getPatient((Integer) jTable1.getValueAt(row, column)));
                    if(serveur.getSpecialiste((Integer) jTable1.getValueAt(row, column)) == null){
                        nomSpecialiste.setText("Pas de spécialiste attribué");
                    }
                    else{
                        nomSpecialiste.setText(serveur.getSpecialiste(((Integer) jTable1.getValueAt(row, column))));
                    }

                    avisSpecialiste.setText(serveur.lireAvis(((Integer) jTable1.getValueAt(row, column))));
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }

        });


    }

    public ApplicationGeneraliste() {

    }

    public static boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel3 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        nomGeneraliste = new javax.swing.JTextField();
        dossiersButton = new javax.swing.JButton();
        deposerButton = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        numeroDossier = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        nomPatient = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jLabel8 = new javax.swing.JLabel();
        cycleECG = new javax.swing.JTextField();
        ecgButton = new javax.swing.JButton();
        transmettreDossierButton = new javax.swing.JButton();
        jPanelEcg1 = new telecardio.JPanelEcg();
        retourButton = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        retourButtonTable = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        numeroDossierAvis = new javax.swing.JTextField();
        nomPatientAvis = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        nomSpecialiste = new javax.swing.JTextField();
        avisSpecialiste = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        setSize(new java.awt.Dimension(300, 480));

        jPanel1.setBackground(new java.awt.Color(188, 223, 191));

        jPanel2.setBackground(new java.awt.Color(226, 248, 210));

        jTabbedPane2.setBackground(new java.awt.Color(188, 223, 191));
        jTabbedPane2.setTabPlacement(javax.swing.JTabbedPane.LEFT);
        jTabbedPane2.setToolTipText("");
        jTabbedPane2.setPreferredSize(new java.awt.Dimension(300, 480));

        jPanel3.setBackground(new java.awt.Color(188, 223, 191));
        jPanel3.setForeground(new java.awt.Color(255, 255, 255));
        jPanel3.setPreferredSize(new java.awt.Dimension(300, 480));

        jLabel3.setFont(new java.awt.Font("DejaVu Sans Mono", 0, 16)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Généraliste :");

        nomGeneraliste.setBackground(new java.awt.Color(255, 255, 255));
        nomGeneraliste.setForeground(new java.awt.Color(0, 0, 0));
        nomGeneraliste.setText("Veuillez saisir votre nom");
        nomGeneraliste.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nomGeneralisteActionPerformed(evt);
            }
        });

        dossiersButton.setText("Consulter Dossiers");
        dossiersButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dossiersButtonActionPerformed(evt);
            }
        });

        deposerButton.setText("Deposer Demande");
        deposerButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deposerButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(81, 81, 81)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(nomGeneraliste, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 139, Short.MAX_VALUE)
                    .addComponent(deposerButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(dossiersButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(71, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(64, 64, 64)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(nomGeneraliste, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 244, Short.MAX_VALUE)
                .addComponent(deposerButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(dossiersButton)
                .addGap(60, 60, 60))
        );

        jTabbedPane2.addTab("Accueil", jPanel3);

        jPanel4.setBackground(new java.awt.Color(188, 223, 191));

        jLabel4.setFont(new java.awt.Font("DejaVu Sans Mono", 0, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 0, 0));
        jLabel4.setText("Dossier n°:");

        numeroDossier.setEditable(false);
        numeroDossier.setBackground(new java.awt.Color(255, 255, 255));
        numeroDossier.setForeground(new java.awt.Color(0, 0, 0));
        numeroDossier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                numeroDossierActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("DejaVu Sans Mono", 0, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 0, 0));
        jLabel5.setText("Patient:");

        nomPatient.setBackground(new java.awt.Color(255, 255, 255));
        nomPatient.setForeground(new java.awt.Color(0, 0, 0));

        jLabel7.setFont(new java.awt.Font("DejaVu Sans Mono", 0, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 0, 0));
        jLabel7.setText("Signes cliniques:");

        jTextArea1.setBackground(new java.awt.Color(255, 255, 255));
        jTextArea1.setColumns(20);
        jTextArea1.setForeground(new java.awt.Color(0, 0, 0));
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        jLabel8.setFont(new java.awt.Font("DejaVu Sans Mono", 0, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 0, 0));
        jLabel8.setText("ECG :");

        cycleECG.setBackground(new java.awt.Color(255, 255, 255));
        cycleECG.setForeground(new java.awt.Color(0, 0, 0));
        cycleECG.setText("Nombre de cycles ?");

        ecgButton.setText("ECG");

        transmettreDossierButton.setText("Transmettre");

        javax.swing.GroupLayout jPanelEcg1Layout = new javax.swing.GroupLayout(jPanelEcg1);
        jPanelEcg1.setLayout(jPanelEcg1Layout);
        jPanelEcg1Layout.setHorizontalGroup(
            jPanelEcg1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 200, Short.MAX_VALUE)
        );
        jPanelEcg1Layout.setVerticalGroup(
            jPanelEcg1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        retourButton.setText("Retour");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addGap(18, 18, 18)
                                .addComponent(cycleECG))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel5))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(nomPatient)
                                    .addComponent(numeroDossier, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(42, 42, 42))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addComponent(ecgButton, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanelEcg1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(21, 21, 21))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addComponent(retourButton)
                        .addGap(18, 18, 18)
                        .addComponent(transmettreDossierButton)
                        .addGap(62, 62, 62))))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(numeroDossier, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26)
                        .addComponent(nomPatient, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(37, 37, 37)
                        .addComponent(jLabel5)))
                .addGap(29, 29, 29)
                .addComponent(jLabel7)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(cycleECG, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(88, 88, 88)
                        .addComponent(ecgButton)
                        .addContainerGap(101, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 49, Short.MAX_VALUE)
                        .addComponent(jPanelEcg1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(40, 40, 40)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(transmettreDossierButton)
                            .addComponent(retourButton)))))
        );

        jTabbedPane2.addTab("Dépot", jPanel4);
        jPanel4.getAccessibleContext().setAccessibleDescription("");

        jPanel5.setBackground(new java.awt.Color(188, 223, 191));

        jScrollPane2.setBackground(new java.awt.Color(188, 223, 191));

        jTable1.setBackground(new java.awt.Color(188, 223, 191));
        jTable1.setColumnSelectionAllowed(true);
        jScrollPane2.setViewportView(jTable1);
        jTable1.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);

        retourButtonTable.setText("Retour");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(112, 112, 112)
                .addComponent(retourButtonTable)
                .addContainerGap(113, Short.MAX_VALUE))
            .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel7Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 279, Short.MAX_VALUE)))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap(439, Short.MAX_VALUE)
                .addComponent(retourButtonTable)
                .addContainerGap())
            .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel7Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 415, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(48, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jTabbedPane2.addTab("Consulter", jPanel5);

        jPanel6.setBackground(new java.awt.Color(188, 223, 191));

        jLabel6.setFont(new java.awt.Font("DejaVu Sans Mono", 0, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 0, 0));
        jLabel6.setText("Dossier n°:");

        numeroDossierAvis.setEditable(false);
        numeroDossierAvis.setBackground(new java.awt.Color(255, 255, 255));
        numeroDossierAvis.setForeground(new java.awt.Color(0, 0, 0));
        numeroDossierAvis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                numeroDossierAvisActionPerformed(evt);
            }
        });

        nomPatientAvis.setEditable(false);
        nomPatientAvis.setBackground(new java.awt.Color(255, 255, 255));
        nomPatientAvis.setForeground(new java.awt.Color(0, 0, 0));

        jLabel9.setFont(new java.awt.Font("DejaVu Sans Mono", 0, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 0, 0));
        jLabel9.setText("Patient:");

        jLabel10.setFont(new java.awt.Font("DejaVu Sans Mono", 0, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(0, 0, 0));
        jLabel10.setText("Avis spécialiste:");

        jLabel11.setFont(new java.awt.Font("DejaVu Sans Mono", 0, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(0, 0, 0));
        jLabel11.setText("Spécialiste:");

        nomSpecialiste.setEditable(false);
        nomSpecialiste.setBackground(new java.awt.Color(255, 255, 255));
        nomSpecialiste.setForeground(new java.awt.Color(0, 0, 0));

        avisSpecialiste.setEditable(false);
        avisSpecialiste.setBackground(new java.awt.Color(255, 255, 255));
        avisSpecialiste.setForeground(new java.awt.Color(0, 0, 0));

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(avisSpecialiste)
                    .addComponent(jLabel10)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(jLabel9)
                            .addComponent(jLabel11))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(nomPatientAvis)
                            .addComponent(numeroDossierAvis, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(nomSpecialiste, javax.swing.GroupLayout.Alignment.LEADING))))
                .addContainerGap(21, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(numeroDossierAvis, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(nomPatientAvis, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addGap(54, 54, 54)))
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(nomSpecialiste, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jLabel10)
                .addGap(18, 18, 18)
                .addComponent(avisSpecialiste, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(146, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("Avis", jPanel6);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 321, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jTabbedPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jTabbedPane2.getAccessibleContext().setAccessibleName("");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void nomGeneralisteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nomGeneralisteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_nomGeneralisteActionPerformed

    private void dossiersButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dossiersButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_dossiersButtonActionPerformed

    private void deposerButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deposerButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_deposerButtonActionPerformed

    private void numeroDossierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_numeroDossierActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_numeroDossierActionPerformed

    private void numeroDossierAvisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_numeroDossierAvisActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_numeroDossierAvisActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ApplicationGeneraliste.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ApplicationGeneraliste.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ApplicationGeneraliste.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ApplicationGeneraliste.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ApplicationGeneraliste();
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField avisSpecialiste;
    private javax.swing.JTextField cycleECG;
    private javax.swing.JButton deposerButton;
    private javax.swing.JButton dossiersButton;
    private javax.swing.JButton ecgButton;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private telecardio.JPanelEcg jPanelEcg1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField nomGeneraliste;
    private javax.swing.JTextField nomPatient;
    private javax.swing.JTextField nomPatientAvis;
    private javax.swing.JTextField nomSpecialiste;
    private javax.swing.JTextField numeroDossier;
    private javax.swing.JTextField numeroDossierAvis;
    private javax.swing.JButton retourButton;
    private javax.swing.JButton retourButtonTable;
    private javax.swing.JButton transmettreDossierButton;
    // End of variables declaration//GEN-END:variables
}
