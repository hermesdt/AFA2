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

import java.util.*;

/**
 *
 * @author Alvaro Duran Tovar
 */
public class PluginFilter {

    private Vector<String> os;
    private Vector<String> plugin_names;
    private Vector<String> categories;
    private Vector<String> tags;
    private Vector<String> authors;
    private String filter;


    public PluginFilter(String filter){
        this.filter = filter;
        os = new Vector<String>();
        tags = new Vector<String>();
        plugin_names = new Vector<String>();
        categories = new Vector<String>();
        authors = new Vector<String>();

        parseFilter();
    }

    public Vector<String> getAuthors() {
        return authors;
    }

    public Vector<String> getCategories() {
        return categories;
    }

    public Vector<String> getOs() {
        return os;
    }

    public Vector<String> getPluginNames() {
        return plugin_names;
    }

    public Vector<String> getTags() {
        return tags;
    }

    private void parseFilter(){
        String blocks[] = filter.split(" ");
        for(String block : blocks){
            parseBlock(block);
        }
    }

    private void parseBlock(String block) {
        String parts[] = block.split(":");
        String filterName = parts[0];

        String values[] = parts[1].split(",");

        if(filterName.equals("plugin_names"))
            plugin_names.addAll(Arrays.asList(values));

        if(filterName.equals("os"))
            os.addAll(Arrays.asList(values));

        if(filterName.equals("authors"))
            authors.addAll(Arrays.asList(values));

        if(filterName.equals("categories"))
            categories.addAll(Arrays.asList(values));

        if(filterName.equals("tags"))
            tags.addAll(Arrays.asList(values));
    }
}
