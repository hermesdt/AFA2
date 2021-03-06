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
 * DeviceSelection.java
 *
 * Created on 15-mar-2011, 20:27:31
 */
package afa.view;

import afa.control.Tools;
import afa.model.Device;
import java.awt.Component;
import java.io.File;
import java.util.Vector;
import javax.swing.JCheckBox;

/**
 *
 * @author Alvaro Duran Tovar
 */
public class DeviceSelectionPanel extends javax.swing.JPanel {

    /** Creates new form DeviceSelection */
    public DeviceSelectionPanel() {
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

        step2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane8 = new javax.swing.JScrollPane();
        jPanel2 = new javax.swing.JPanel();
        jSeparator3 = new javax.swing.JSeparator();
        jButton1 = new javax.swing.JButton();

        setLayout(new java.awt.BorderLayout());

        jLabel1.setText("Elija las particiones a analizar");

        jPanel2.setLayout(new javax.swing.BoxLayout(jPanel2, javax.swing.BoxLayout.Y_AXIS));
        jScrollPane8.setViewportView(jPanel2);

        jButton1.setText("Seleccionar todo");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout step2Layout = new javax.swing.GroupLayout(step2);
        step2.setLayout(step2Layout);
        step2Layout.setHorizontalGroup(
            step2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, step2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(step2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane8, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 627, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 627, Short.MAX_VALUE)
                    .addComponent(jButton1))
                .addContainerGap())
        );
        step2Layout.setVerticalGroup(
            step2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(step2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane8, javax.swing.GroupLayout.DEFAULT_SIZE, 293, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
                .addContainerGap())
        );

        add(step2, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        Component components[] = jPanel2.getComponents();
        for (Component c : components) {
            if (c instanceof JCheckBox) {
                ((JCheckBox) c).setSelected(true);
            }
        }
}//GEN-LAST:event_jButton1ActionPerformed

    boolean checkPanel() throws Exception{
        Object ob[] = jPanel2.getComponents();

        //check Recursivity
        //get selected items
        Vector<Device> selected = this.getPartitionsVector();

        for (Device dev : selected) {
            String mountPoint = dev.getMountPoint().getAbsolutePath();


            for (Device aux : selected) {
                String mountPoint2 = aux.getMountPoint().getAbsolutePath();


                if (!mountPoint2.equals(mountPoint) && mountPoint2.contains(mountPoint)) {
                    int result = javax.swing.JOptionPane.showConfirmDialog(null,
                            "El path \"" + mountPoint2 + "\" esta incluido en\n"
                            + "\"" + mountPoint + "\", ¿deseas proseguir con el analisis?",
                            "Recursivity",
                            javax.swing.JOptionPane.OK_CANCEL_OPTION);


                    if (result == javax.swing.JOptionPane.OK_OPTION) {
                        return true;
                    } else {
                        return false;
                    }
                }
            }
        }


        if (selected.size() > 0) {
            return true;
        }

        javax.swing.JOptionPane.showMessageDialog(null, "Debes seleccionar al menos una particion",
                "No partition selected",
                javax.swing.JOptionPane.ERROR_MESSAGE);
        return false;
    }

    void loadDevices() throws Exception {

        jPanel2.removeAll();

        String[] unidades = Tools.obtenerUnidades();

        for (String s : unidades) {
            JCheckBox but = new JCheckBox(s + " -> " + Tools.getMountPointByDevice(s));
            jPanel2.add(but);
        }

        int aux = jScrollPane8.getWidth();
        jScrollPane8.getVerticalScrollBar().setUnitIncrement(aux / 5);
    }

    private Vector<String> getSelectedPartitionsString() {
        Object[] ob = jPanel2.getComponents();

        Vector<String> selected = new Vector<String>();
        for (Object obj : ob) {
            if (obj instanceof JCheckBox && ((JCheckBox) obj).isSelected()) {
                selected.add(((JCheckBox) obj).getText().split(" -> ")[1]);
            }
        }

        return selected;
    }

    Vector<Device> getPartitionsVector() throws Exception {


        Object[] ob = jPanel2.getComponents();

        Vector<Device> selected = new Vector<Device>();
        for (Object obj : ob) {
            if (obj instanceof JCheckBox) {
                JCheckBox check = (JCheckBox) obj;
                if (!check.isSelected()) {
                    continue;
                }
                Device dev = new Device();

                dev.setSelected(check.isSelected());
                dev.setDeviceName(new File(check.getText().split(" -> ")[0]));
                dev.setMountPoint(new File(check.getText().split(" -> ")[1]));
                //dev.setIdentifier(Tools.getSerialVolume(dev.getDeviceName().getAbsolutePath()));
                selected.add(dev);
            }
        }

        return selected;
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JPanel step2;
    // End of variables declaration//GEN-END:variables
}
