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

import java.util.Vector;

/**
 *
 * @author Alvaro Duran Tovar
 */
public class Category {

    private Vector<Plugin> plugins = new Vector<Plugin>();

    public Vector<Plugin> getPlugins() {
        return plugins;
    }

    public void addPlugin(Plugin p) {
        plugins.add(p);
    }

    public Plugin getPlugin(int index) {
        return plugins.elementAt(index);
    }

    public boolean removePlugin(Plugin p) {
        return plugins.remove(p);
    }

    public void removePlugin(int index) {
        plugins.removeElementAt(index);
    }

    public boolean containsPluginName(String name) {
        for (Plugin p : plugins) {
            if (p.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    public void removePluginByName(String name) {
        Vector<Plugin> aux = new Vector<Plugin>();
        for (Plugin p : plugins) {
            if (!p.getName().equals(name)) {
                aux.add(p);
            }
        }

        plugins = aux;
    }
    private String name;

    public Category(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPlugins(Vector<Plugin> v) {
        this.plugins = v;
    }
}
