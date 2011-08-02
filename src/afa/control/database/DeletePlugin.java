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

package afa.control.database;

import afa.model.Plugin;
import java.sql.SQLException;

/**
 *
 * @author Alvaro Duran Tovar
 */
public class DeletePlugin extends DBOperator {

    private DeletePlugin(){
    }

    public static void delete(Plugin p) throws Exception{
        DeletePlugin delete = new DeletePlugin();

        delete.openDatabase();

        //delete plugin from database
        delete.deletePlugin(p);

        delete.closeDatabase();
    }

    private void deletePlugin(Plugin p) throws SQLException {
        //delete from plugins table (name and category should be uniques)
        stat.executeUpdate("delete from plugins where "
                + "name = '"+p.getName()+"' and category = '"+p.getCategory()+"';");

    }
}
