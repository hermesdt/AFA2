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

package afa.model;

import afa.control.plugins.PluginsManager;
import afa.view.Window;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.table.*;

/**
 *
 * @author Alvaro Duran Tovar
 */

public class MyButtonCell extends AbstractCellEditor
        implements TableCellRenderer, TableCellEditor {

    JTable table;
    JButton renderButton;
    JButton editButton;
    String text;
    private String s = File.separator;
    private String language_xml;
    private Window window;
    private String cancel, install, clean, remove;


    public MyButtonCell(JTable table, Window window, int column) {
        super();
        this.table = table;
        this.window = window;
        final JTable t = this.table;
        renderButton = new JButton();
        renderButton.setHorizontalTextPosition(renderButton.CENTER);

        editButton = new JButton();
        editButton.setFocusPainted(true);
        //editButton.addActionListener(this);
        editButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int row = t.rowAtPoint(t.getMousePosition());
                    t.getSelectionModel().setSelectionInterval(row, row);
                    ActionPerformed(e);
                    
                } catch (Exception ex) {
                    Logger.getLogger(MyButtonCell.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        });

        TableColumnModel columnModel = table.getColumnModel();
        columnModel.getColumn(column).setCellRenderer(this);
        columnModel.getColumn(column).setCellEditor(this);
    }


    public Component getTableCellRendererComponent(
            JTable table, Object value, boolean isSelected, boolean hasFocus,
            int row, int column) {
        if (hasFocus) {
            renderButton.setForeground(table.getForeground());
            renderButton.setBackground(UIManager.getColor(
                    "Button.background"));
        } else if (isSelected) {
            renderButton.setForeground(table.getSelectionForeground());
            renderButton.setBackground(table.getSelectionBackground());
        } else {
            renderButton.setForeground(table.getForeground());
            renderButton.setBackground(UIManager.getColor(
                    "Button.background"));
        }
        //   value = "Borrar";
        renderButton.setText((value == null) ? "" : value.toString());
        return renderButton;
    }

    public Component getTableCellEditorComponent(
            JTable table, Object value, boolean isSelected, int row,
            int column) {
        //   value = "Borrar";
        text = (value == null) ? "" : value.toString();
        //System.out.println("MyButtonCell text value: "+text);
        editButton.setText(text);
        return editButton;
    }

    public Object getCellEditorValue() {
        return text;
    }

    public void removeAction() {
        //borrar plugin
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        String plug = (String) model.getValueAt(table.getSelectedRow(),
                0);

    }

    public void installAction(java.awt.event.ActionEvent evt) throws Exception {

        DefaultTableModel model = (DefaultTableModel) table.getModel();
        

        int row = table.getSelectedRow();
        //System.out.println("[MyButtonCell.installAction] row: "+row);
        //instalar plugin
        //mal!! si se borra de la interfaz estamos accediendo a un indice incorrecto!!
        Plugin p = PluginsManager.getRemotePlugins(false).get(row);
        String action = (String) model.getValueAt(row, 3);
        action = action.toLowerCase();
        PluginsManager.installPlugin(p.getRepositoryLocation(), p, action);

        //post install
        window.deleteAllRows();
        window.loadRemotePluginList(PluginsManager.getRemotePlugins(false));
    }


    public void ActionPerformed(java.awt.event.ActionEvent evt) throws Exception {
        //System.out.println("[MyButtonCell.ActionPerformed]: intalar plugin");
        
        installAction(evt);
    }

}

