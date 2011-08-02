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

import afa.control.Constants;
import afa.control.Tools;
import afa.control.database.InsertPlugin;
import afa.control.net.DownloadFile;
import afa.model.Plugin;
import java.io.File;

/**
 *
 * @author alvaro
 */
class InstallPlugin {


    static void installPlugin(String relativePath, Plugin p, String action) throws Exception{

        String repo = Constants.getRepoBase();
        //comprobar si termina por "/"
        repo = (repo.endsWith("/")) ? repo : repo + "/";

        //si el path relativo empieza por "/" quitarlo, da problemas con rails si la direccion
        //contiene doble "/"
        if(relativePath.startsWith("/"))
            relativePath = relativePath.substring(1);

        //direccion destino temporal donde sera descargado
        String temp_dest = "temp"+File.separator+p.getName();
        //descarga el plugin
        DownloadFile.getTextFile(repo+relativePath, temp_dest);

        //parseo y carga del plugin para poder saber donde instalarlo
        Plugin aux = PluginLoader.loadPlugin(temp_dest);

        //crear category si necesario
        Tools.createCategory(aux.getCategory());

        //copiar plugin a su destino
        String plug_dest = Constants.getDirectorioPlugins()+File.separator+
                aux.getFullName()+".xml";

        //si estamos actualizando primero se borra para que la bbdd sea consistente
        if(action.equals("update")){
            DeletePlugin.delete(plug_dest);
        }

        //copy
        Tools.copyfile(temp_dest, plug_dest);

        //meter datos en la base de datos
        /*try{*/InsertPlugin.insertPlugin(aux);/*}catch(Exception e){}*/
    }
}
