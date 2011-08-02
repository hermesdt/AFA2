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
 * IgnoreOptionsPanel.java
 *
 * Created on 15-mar-2011, 21:02:45
 */

package afa.view;

/**
 *
 * @author Alvaro Duran Tovar
 */
public class IgnoreOptionsPanel extends javax.swing.JPanel {

    /** Creates new form IgnoreOptionsPanel */
    public IgnoreOptionsPanel() {
        initComponents();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        step4 = new javax.swing.JPanel();
        jLabel23 = new javax.swing.JLabel();
        jSeparator5 = new javax.swing.JSeparator();
        checkBoxRecursiveSearch = new javax.swing.JCheckBox();
        checkBoxHashMD5 = new javax.swing.JCheckBox();
        checkBoxHashSHA1 = new javax.swing.JCheckBox();
        checkBoxFileContent = new javax.swing.JCheckBox();
        checkBoxFindFileTechnique = new javax.swing.JCheckBox();

        setLayout(new java.awt.BorderLayout());

        jLabel23.setText("Configuración del análisis");

        checkBoxRecursiveSearch.setText("Ignorar búsquedas recursivas");

        checkBoxHashMD5.setText("Ignorar búsquedas de hashes MD5");

        checkBoxHashSHA1.setText("Ignorar búsquedas de hashes SHA1");

        checkBoxFileContent.setText("Ignorar búsquedas de contenido de fichero");

        checkBoxFindFileTechnique.setText("Ignorar búsquedas tipo: find_file");

        javax.swing.GroupLayout step4Layout = new javax.swing.GroupLayout(step4);
        step4.setLayout(step4Layout);
        step4Layout.setHorizontalGroup(
            step4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(step4Layout.createSequentialGroup()
                .addGroup(step4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(step4Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel23))
                    .addGroup(step4Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jSeparator5, javax.swing.GroupLayout.DEFAULT_SIZE, 376, Short.MAX_VALUE))
                    .addGroup(step4Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(checkBoxRecursiveSearch))
                    .addGroup(step4Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(checkBoxHashMD5))
                    .addGroup(step4Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(checkBoxHashSHA1))
                    .addGroup(step4Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(checkBoxFileContent))
                    .addGroup(step4Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(checkBoxFindFileTechnique)))
                .addContainerGap())
        );
        step4Layout.setVerticalGroup(
            step4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(step4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel23)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(checkBoxRecursiveSearch)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(checkBoxHashMD5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(checkBoxHashSHA1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(checkBoxFileContent)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(checkBoxFindFileTechnique)
                .addContainerGap(120, Short.MAX_VALUE))
        );

        add(step4, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    boolean isCheckBoxFileContentSelected() {
        return checkBoxFileContent.isSelected();
    }

    boolean isCheckBoxHashMD5Selected() {
        return checkBoxHashMD5.isSelected();
    }

    boolean isCheckBoxHashSHA1Selected() {
        return checkBoxHashSHA1.isSelected();
    }

    boolean isCheckBoxFindFileTechniqueSelected() {
        return checkBoxFindFileTechnique.isSelected();
    }

    boolean isCheckBoxRecursiveSearchSelected() {
        return checkBoxRecursiveSearch.isSelected();
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox checkBoxFileContent;
    private javax.swing.JCheckBox checkBoxFindFileTechnique;
    private javax.swing.JCheckBox checkBoxHashMD5;
    private javax.swing.JCheckBox checkBoxHashSHA1;
    private javax.swing.JCheckBox checkBoxRecursiveSearch;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JPanel step4;
    // End of variables declaration//GEN-END:variables

}