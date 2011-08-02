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

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

/**
 *
 * @author Alvaro Duran Tovar
 */
public class GetValues extends DBOperator {

    private GetValues(){
    }
    /*select plugins.name, tags.name from plugins,
     tags, plugin_tags where plugins.id = plugin_id and
     tags.id = plugin_tags.tag_id;
     *
     */

    public static String[] getValues(String viewName) throws Exception {
        GetValues get = new GetValues();

        get.openDatabase();

        String values[] = get.getValuesOfView(viewName);

        get.closeDatabase();

        return values;
    }

    private String[] getValuesOfView(String viewName) throws SQLException{
        Vector<String> vec = new Vector<String>();

        String field = "";

        if(viewName.equals("os")) field = "os";
        if(viewName.equals("authors")) field = "author";
        if(viewName.equals("categories")) field = "category";
        if(viewName.equals("plugin_names")) field = "name";
        if(viewName.equals("tags")) field = "name";

        ResultSet rs = stat.executeQuery("SELECT "+field+" FROM "+viewName+"");
        while(rs.next()){
            vec.add(rs.getString(field));
        }

        return (String[]) vec.toArray(new String[]{});
    }
}
