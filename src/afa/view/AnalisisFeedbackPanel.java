/*
 *  Copyright (C) 2011 Alvaro Duran Tovar
 *
 *  This file is part of AFA.
 *
 *  AFA is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */


/*
 * AnalisisPanel.java
 *
 * Created on 15-mar-2011, 21:08:22
 */

package afa.view;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.JScrollBar;
import javax.swing.JTextPane;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

/**
 *
 * @author Alvaro Duran Tovar
 */
public class AnalisisFeedbackPanel extends javax.swing.JPanel {

    private boolean autoScrollDown = true;

    /** Creates new form AnalisisPanel */
    public AnalisisFeedbackPanel() {
        initComponents();


        final JScrollBar bar = jScrollPane3.getVerticalScrollBar();
        bar.addMouseListener(new MouseAdapter() {
            private boolean autoScrollDown;

            @Override
            public void mousePressed(MouseEvent e) {
                autoScrollDown = false;
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                int aux = (bar.getMaximum() * 2 / 25); //8%
                if (bar.getValue() > bar.getMaximum() - aux) {
                    autoScrollDown = true;
                }
            }
        });

    }

    JLabel getTecnicasTerminadas(){
        return tecnicasTerminadas;
    }

    JTextPane getFeedBackPane(){
        return feedback;
    }


    JProgressBar getProgresoTecnicas() {
        return progresoTecnicas;
    }

    JLabel getTotalTecnicas() {
        return totalTecnicas;
    }

    void setAutoScrollDown(boolean value){
        autoScrollDown = value;
    }

    boolean getAutoScrollDown(){
        return autoScrollDown;
    }


    void printAnalisisMessage(String s, Color color) {
        // Atributos para la frase
        final Color c = color;
        final String text = s;


        SimpleAttributeSet attrs = new SimpleAttributeSet();
        StyleConstants.setForeground(attrs, c);



        try {
            feedback.getStyledDocument().insertString(feedback.getStyledDocument().getLength(), text + "\n", attrs);


        } catch (Exception e) {
            e.printStackTrace();


        }

        java.awt.EventQueue.invokeLater(new Thread() {

            @Override
            public void run() {
                if (getAutoScrollDown()) {
                    JScrollBar bar = jScrollPane3.getVerticalScrollBar();
                    bar.setValue(bar.getMaximum());


                } //
            }
        });


    }



    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        step5 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        progresoTecnicas = new javax.swing.JProgressBar();
        jLabel3 = new javax.swing.JLabel();
        tecnicasTerminadas = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        totalTecnicas = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        feedback = new javax.swing.JTextPane();

        setLayout(new java.awt.BorderLayout());

        jLabel2.setText("Estado del análisis");

        progresoTecnicas.setString("1/3");

        jLabel3.setText("Tecnicas terminadas:");

        tecnicasTerminadas.setText("0");

        jLabel27.setText("/");

        totalTecnicas.setText("0");

        feedback.setEditable(false);
        jScrollPane3.setViewportView(feedback);

        javax.swing.GroupLayout step5Layout = new javax.swing.GroupLayout(step5);
        step5.setLayout(step5Layout);
        step5Layout.setHorizontalGroup(
            step5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(step5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(step5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(step5Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tecnicasTerminadas)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel27)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(totalTecnicas))
                    .addComponent(progresoTecnicas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 587, Short.MAX_VALUE)
                    .addComponent(jSeparator2, javax.swing.GroupLayout.DEFAULT_SIZE, 587, Short.MAX_VALUE)
                    .addComponent(jLabel2))
                .addContainerGap())
        );
        step5Layout.setVerticalGroup(
            step5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(step5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(step5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(tecnicasTerminadas)
                    .addComponent(jLabel27)
                    .addComponent(totalTecnicas))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(progresoTecnicas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 275, Short.MAX_VALUE)
                .addContainerGap())
        );

        add(step5, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextPane feedback;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JProgressBar progresoTecnicas;
    private javax.swing.JPanel step5;
    private javax.swing.JLabel tecnicasTerminadas;
    private javax.swing.JLabel totalTecnicas;
    // End of variables declaration//GEN-END:variables


}
