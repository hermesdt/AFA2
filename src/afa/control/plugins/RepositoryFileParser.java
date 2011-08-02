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

package afa.control.plugins;

import afa.model.Plugin;
import java.util.Vector;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import afa.control.Constants;
import java.io.File;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author Alvaro Duran Tovar
 */
class RepositoryFileParser {
    
    static Vector<Plugin> getPlugins() throws Exception{
        Vector<Plugin> vector = new Vector<Plugin>();
        SAXBuilder builder = new SAXBuilder();
        Element root = builder.build(
                new File(Constants.getRepositoryFile())).getRootElement();

        Element plugins_elem = root;
        List<Element> plugins = plugins_elem.getChildren();
        Iterator<Element> iterator = plugins.iterator();

        while(iterator.hasNext()){
            Element plugin_elem = iterator.next();
            Plugin p = new Plugin();

            Iterator<Element> meta = plugin_elem.getChildren().iterator();
            while(meta.hasNext()){
                Element data = meta.next();

                if(data.getName().trim().equals("name"))
                    p.setName(data.getText());

                if(data.getName().trim().equals("location")){
                    p.setRepositoryLocation(data.getText());
                    p.setIsLocal(false);
                }

                if(data.getName().trim().equals("last_updated"))
                    p.setLastUpdated(data.getText());

                if(data.getName().trim().equals("category"))
                    p.setCategory(data.getText());
            }
            vector.add(p);
        }

        return vector;
    }

}
