/*
 *  Copyright (C) 2011 alvaro
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

import afa.model.Category;
import afa.model.Plugin;
import java.util.Vector;

/**
 * Clase encargada de administrar todas las operaciones con los plugins.
 * 
 * @author alvaro
 */
public class PluginsManager {

    /**
     * Crea un nuevo objeto de tipo Plugin cargando los datos contenidos en el
     * fichero especificado.
     * 
     * @param fichero
     * @return
     * @throws Exception
     */
    public static Plugin loadPlugin(String fichero) throws Exception {
        return PluginLoader.loadPlugin(fichero);
    }

    /**
     * Borra el plugin especificado por el parametro pluginFile
     * 
     * @param pluginFile
     * @throws Exception
     */
    public static void delete(String pluginFile) throws Exception{
        DeletePlugin.delete(pluginFile);
    }

    /**
     * Instala o actualiza el plugin especificado. El parametro relativePath
     * indica la localizacion del plugin en el repositorio de plugins.
     * El objeto p para saber el nombre del plugin y su categoria.
     * El parametro action indica si se esta actualizando o instalando.
     *
     * @param relativePath Ruta relativa al plugin en el repositorio.
     * @param p Objeto que contiene unos pocos metadatos (name, category).
     * @param action Puede tener el valor "install" o "update".
     * @throws Exception
     */
    public static void installPlugin(String relativePath, Plugin p, String action) throws Exception{
        InstallPlugin.installPlugin(relativePath, p, action);
    }

    /**
     * Devuelve el listado de plugins que hay en el repositorio. El parametro
     * update_repository indica si se va a descargar el listado de plugins.
     *
     * @param update_repository Si true se descargar el listado de plugins. Si
     * no entonces trabaja con la ultima copia del repositorio.
     * @return
     * @throws Exception
     */
    public static Vector<Plugin> getRemotePlugins(boolean update_repository) throws Exception{
        return RepositoryPlugins.getRemotePlugins(update_repository);
    }

    /**
     * Devuelve un vector con todos los plugins instalados organizados por
     * categorias. El parametro loadAllContent indica si los plugins deberan
     * cargar la informacion del fichero xml o tan solo deberan contener
     * el nombre del plugin y su categoria.
     * 
     * @param loadAllContent
     * @return
     * @throws Exception
     */
    public static Vector<Category> getLocalPlugins(boolean loadAllContent) throws Exception {
        return LocalPlugins.getLocalPlugins(loadAllContent);
    }

    /**
     * Devuelve un vector con todos los plugins instalados organizados por
     * categorias. El parametro filter indica un filtro que deben pasar
     * los plugins seleccionados. El filtro tiene el formato:
     *
     * Ex 1: plugin_names:name1,name2,name3
     * Ex 2: tags:tag1,tag2,tag3
     * Ex 3: plugin_names:name1 tags:tag1,tag2
     *
     * El filtro esta separado en tipo y sus valores. Asi un tipo puede permitir
     * varios valores, o se pueden seleccionar varios tipos de forma que el
     * filtro sera mas concreto. Los valores se separan por ",", excepto el
     * ultimo. Los grupos de cada tipo deben separarse por espacios.
     *
     * Ex 4: plugin_names:name1 os:linux,macos authors:alvaro
     *
     * @param filter
     * @return
     * @throws Exception
     */
    public static Vector<Category> getLocalPluginsByFilter(String filter) throws Exception {
        return LocalPlugins.getLocalPluginsByFilter(filter);
    }

    /**
     * Realiza la misma accion que el metodo anterior, pero ademas fuerza a
     * ignorar los plugins especificados por "excluded". Utilizado desde los
     * jtree de la interfaz de seleccion de plugins.
     * 
     * @param filter
     * @param excluded
     * @return
     * @throws Exception
     */
    public static Vector<Category> getLocalPluginsByFilter(String filter, Vector<Category> excluded) throws Exception {
        return LocalPlugins.getLocalPluginsByFilter(filter, excluded);
    }
}
