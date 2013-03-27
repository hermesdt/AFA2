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

import afa.model.*;
import java.io.File;
import java.util.Vector;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

/**
 * 
 * @author Alvaro Duran Tovar
 */
class PluginLoader {

    private static Vector<Tag> extractTags(Element tags){
        Vector<Tag> vec = new Vector<Tag>();
        Element elements[] = (Element[]) tags.getChildren().toArray(new Element[0]);
        for(Element tag : elements){
            if(tag.getName().equals("tag"))
                vec.add(new Tag(tag.getText().trim()));
        }

        return vec;
    }

    protected static String pluginName(String xml) throws Exception{
        return PluginLoader.loadPlugin(xml).getName();
    }


    private static Metadata extractMetadata(Element metadata){
        String name = null, version = null, os = null, author = null,
                date = null, category = null, description = null, lastUpdate = null;
        Vector<Tag> tags = null;

        Element elements[] = (Element[]) metadata.getChildren().toArray(new Element[0]);
        for(Element data : elements){
            if(data.getName().equals("name")) name = data.getText().trim();
            if(data.getName().equals("last_updated")) lastUpdate = data.getText().trim();
            if(data.getName().equals("version")) version = data.getText().trim();
            if(data.getName().equals("os")) os = data.getText().trim();
            if(data.getName().equals("author")) author = data.getText().trim();
            if(data.getName().equals("date")) date = data.getText().trim();
            if(data.getName().equals("date")) category = data.getText().trim();
            if(data.getName().equals("description")) description = data.getText().trim();
            if(data.getName().equals("category")) category = data.getText().trim();
            if(data.getName().equals("tags")){
                tags = extractTags(data);
            }
        }
        Metadata meta = new Metadata(name, os, version, author, date, category, tags, description);
        meta.setLastUpdated(lastUpdate);
        return meta;
    }

    private static Vector<Technique> extractTechniques(Element techniques){
        Vector<Technique> vec = new Vector<Technique>();
        Element techs[] = (Element[]) techniques.getChildren().toArray(new Element[0]);

        for(Element tech : techs){
            Technique t = new Technique(extractParams(tech));
            t.setType(tech.getName());
            vec.add(t);
        }

        return vec;
    }

    private static Vector<Param> extractParams(Element root){
        Vector<Param> params = new Vector<Param>();
        Element elems[] = (Element[]) root.getChildren().toArray(new Element[0]);
        for(Element param : elems){
            Param p = new Param(param.getName(), param.getValue().trim());
            if(param.getChildren().size() > 0){
                p.addParam(extractParams(param));
            }
            params.add(p);
        }

        return params;
    }

    /**
     * Metodo para cargar un plugin desde un documento xml
     * @param fichero Fichero XML con el plugin en el formato adecuado
     * @return Objeto Plugin con los elementos y tecnicas definidos en los ficheros
     */
    static Plugin loadPlugin(String fichero) throws Exception {
        Plugin p = null;

        SAXBuilder sax = new SAXBuilder();
        Element root = sax.build(new File(fichero)).getRootElement();
        
        //metadata
        Element metadata = root.getChild("metadata");
        p = new Plugin(extractMetadata(metadata));

        //techniques
        Element techniques = root.getChild("techniques");
        p.setTechniques(extractTechniques(techniques));

        //set plugin location
        p.setLocation(new File(fichero));
        
        //post analisis
        //TODO

        return p;
    }
}









