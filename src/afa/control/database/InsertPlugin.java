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
import afa.model.Tag;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Alvaro Duran Tovar
 */
public class InsertPlugin extends DBOperator {

    private InsertPlugin(){
    }

    /**
     * Insert plugin's data on plugins table.
     * @param p
     * @throws Exception
     */
    private void pluginsTable(Plugin p) throws Exception{
        stat.executeUpdate("insert into plugins values ("
                + "null, "
                + "'"+p.getName()+"', "
                + "'"+p.getAuthor()+"', "
                + "'"+p.getCategory()+"', "
                + "'"+p.getOS()+"'"
                + ");");
    }

    /**
     * Insert tags on tags table.
     * 
     * @param p
     * @throws Exception
     */
    private void tagsTable(Plugin p) throws Exception {
        if(p.getTags() == null) return;

        for(Tag t : p.getTags()){
            try{
                stat.executeUpdate("insert into tags values ("
                        + "null,"
                        + "'"+t.getName()+"'"
                        + ");");
            }catch(Exception e){}
        }
    }

    /**
     * Add to the table PluginTags the relation of the plugin with each tag
     * related with it.
     * @param p
     * @throws Exception
     */
    private void pluginTagsTable(Plugin p) throws Exception {
        if(p.getTags() == null) return;

        //id of the plugin on database
        ResultSet rs = stat.executeQuery("select id "
                + "from plugins where name = '"+p.getName()+"' and category = '"
                + p.getCategory()+"';");

        rs.next();
        //id del plugin
        int plugin_id = rs.getInt("id");

        ArrayList<Integer> tag_ids = new ArrayList<Integer>();

        //una vez que tengo el plugin id recorro la tabla de tags para recoger los ids de los tags
        for(Tag t : p.getTags()){
            //obtener id del tag
            rs = stat.executeQuery("select id "
                + "from tags where name = '"+t.getName()+"';");
            rs.next();
            tag_ids.add(rs.getInt("id"));
        }

        //insertar en la tabla plugin_tags los ids obtenidos
        for(int i = 0;i<tag_ids.size();i++){
            int tag_id = tag_ids.get(i);

            //insert sql
            stat.executeUpdate("insert into plugin_tags values ("
                    + ""+plugin_id+", "+tag_id+");");
        }
    }

    public static void insertPlugin(Plugin p) throws Exception{
        InsertPlugin insert = new InsertPlugin();

        //open
        insert.openDatabase();

        //add values to plugins table
        insert.pluginsTable(p);

        //add values to tags table
        insert.tagsTable(p);

        //add values to plugin_tags table
        insert.pluginTagsTable(p);

        //close
        insert.closeDatabase();
    }

}
