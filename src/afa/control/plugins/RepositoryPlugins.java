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
import afa.control.net.DownloadFile;
import afa.model.Plugin;
import java.util.Vector;

/**
 *
 * @author Alvaro Duran Tovar
 */
class RepositoryPlugins {

    static Vector<Plugin> getRemotePlugins(boolean update_repository) throws Exception{
        //si se indica que hay que actualizar el fichero de repo
        if(update_repository)
            downloadRepositoryIndex();

        //vector con plugins remotos
        Vector<Plugin> remotes = RepositoryFileParser.getPlugins();
        //vector con plugins locales
        Vector<Plugin> vec = Tools.categoryVecToPluginVec(PluginsManager.getLocalPlugins(true));
        //vector que sera devuelto con los plugins a instalar
        Vector<Plugin> result = new Vector<Plugin>();

        //comparar remotos contra locales y agregar los que no esten ya instaldos,
        //o los que puedan ser actualizados
        for(Plugin remote : remotes){
            boolean flag = false;

            for(Plugin local : vec){
                //si tienen mismo nombre
                if(local.getName().equals(remote.getName()))
                //si tienen misma categoria
                if(local.getCategory().equals(remote.getCategory()))
                //si la version local es mayor o igual que la remota
                if(Long.valueOf(local.getLastUpdatedMilis()) >=
                        Long.valueOf(remote.getLastUpdatedMilis()))
                //flag = true, por lo que no sera agregado al resultado
                    flag = true;
                else
                    //la version remote es mas nueva
                    remote.setRemoteNewer(true);
            }

            if(flag == false){
                result.add(remote);
            }
        }

        return result;
    }

    /*
     * Descarga todo el respositorio siempre.
     * Deberia comprobar si es neceario, si merece la pena, descargarlo.
     * Algun parametro para comprobar versiones de repositorio?
     */
    private static void downloadRepositoryIndex() throws Exception{
        //download repository file
        DownloadFile.getTextFile(Constants.getRepository(), Constants.getRepositoryFile());
    }

}
