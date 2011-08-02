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
import java.io.File;

/**
 *
 * @author Alvaro Duran Tovar
 */
class DeletePlugin {
    static void delete(String pluginFile) throws Exception{
        //load plugin before deletion
        Plugin p = PluginLoader.loadPlugin(pluginFile);


        File f = new File(pluginFile);
        f.delete();

        //delete from database
        afa.control.database.DeletePlugin.delete(p);
    }
}
