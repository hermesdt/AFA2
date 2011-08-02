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

import afa.control.*;
import afa.model.Category;
import afa.model.Plugin;
import afa.model.PluginFilter;
import java.io.File;
import java.util.Vector;

/**
 *
 * @author Alvaro Duran Tovar
 */
class LocalPlugins {

    /**
     * Metodo que se encarga de filtrar el vector "vec" y devolver un nuevo vector
     * de forma que solo contenga los elementos que hallan superado el filtro
     * definido por el objeto pf.
     * Es un metodo de uso interno de esta clase que sera invocado desde
     * getPluginsByFilter.
     * 
     * @param vec Vector con los plugins que seran filtrados.
     * @param pf Objeto que representa el filtro que deben superar los plugins.
     * @return
     */
    private static Vector<Category> filterPlugins(Vector<Category> vec, PluginFilter pf) {
        Vector<Category> result = new Vector<Category>();

        for (Category c : vec) {
            //categoria que contendra los plugins seleccionados
            Category new_c = new Category(c.getName());

            Vector<Plugin> plugins = c.getPlugins();
            for (Plugin p : plugins) {

                //comprobar los elementos de plugin filter para ver que plugins los cumplen
                boolean willAdd = true;
                //authors
                if(pf.getAuthors().size() > 0)
                    if(!pf.getAuthors().contains(p.getAuthor()))
                        continue;

                //tags
                if(pf.getTags().size() > 0)
                    if(!p.getTagsAsVectorStrings().containsAll(pf.getTags()))
                        continue;

                //categories
                if(pf.getCategories().size() > 0)
                    if(!pf.getCategories().contains(p.getCategory()))
                        continue;

                //plugin_names
                if(pf.getPluginNames().size() > 0)
                    if(!pf.getPluginNames().contains(p.getName()))
                        continue;

                //os
                if(pf.getOs().size() > 0)
                    if(!pf.getOs().contains(p.getOS()))
                        continue;

                new_c.addPlugin(p);
            }

            result.add(new_c);
            
        }

        return result;
    }

    /**
     * Metodo similar a getLocalPluginsByFilter, pero ademas elimina del resultado
     * los plugins especificados mediante el parametro excluded.
     * Utilizado para que en la seleccion de plugins no aparezcan repetidos.
     * @param filter
     * @param excluded
     * @return
     * @throws Exception
     */
    static Vector<Category> getLocalPluginsByFilter(String filter, Vector<Category> excluded) throws Exception {
        Vector<Category> vec = getLocalPluginsByFilter(filter);
        Vector<Plugin> aux = Tools.categoryVecToPluginVec(excluded);

        for (Category c : vec)
            for (Plugin p : aux)
                c.removePluginByName(p.getName());
            
        return vec;
    }

    /**
     * Devuelve los plugins instalados que pasen el filtro especificado por el
     * parametro filter.
     * 
     * @param filter
     * @return
     * @throws Exception
     */
    static Vector<Category> getLocalPluginsByFilter(String filter) throws Exception {

        Vector<Category> vec = LocalPlugins.getLocalPlugins(true);

        //si el filtro es nulo o no contiene nada devolver todos los plugins.
        if (filter == null || filter.equals("") || filter.length() == 0) {
            return vec;
        }

        //crear un nuevo pluginfilter en base al parametro filter
        PluginFilter pf = new PluginFilter(filter);

        return filterPlugins(vec, pf);
    }

    /**
     * Devuelve todos los plugins instalados. El parametro loadAllContent
     * permite especificar si es necesario o no cargar el contenido de los plugins
     * de esta forma se optimiza el funcionamiento. ¿necesario?
     * 
     * @param loadAllContent
     * @return
     * @throws Exception
     */
    static Vector<Category> getLocalPlugins(boolean loadAllContent) throws Exception {
        Vector<Category> categories = new Vector<Category>();

        File f = new File(Constants.getDirectorioPlugins());
        File[] files = f.listFiles();

        for (File file : files) {
            if(file.isHidden())
                continue;

            if (!file.isDirectory()) //ignore files
            {
                continue;
            }
            Category c = new Category(file.getName());

            //explore xml childs
            File[] plugins = file.listFiles();
            for (File plugin : plugins) {
                if(file.isHidden())
                continue;
                if (!plugin.getName().endsWith(".xml")) //ignore no xml files
                {
                    continue;
                }

                Plugin p = null;
                if (loadAllContent) {
                    p = PluginLoader.loadPlugin(plugin.getAbsolutePath());
                } else {
                    p = new Plugin(PluginLoader.pluginName(plugin.getAbsolutePath()));
                }

                c.addPlugin(p);
            }
            categories.add(c);
        }
        return categories;
    }
}
