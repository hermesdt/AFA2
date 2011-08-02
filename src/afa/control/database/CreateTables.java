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

import java.sql.*;

/**
 *
 * @author Alvaro Duran Tovar
 */
public class CreateTables extends DBOperator {

    private CreateTables(){
    }

    private void createTrigger() throws SQLException{
        stat.execute("create trigger if not exists plugin_tags_delete_trigger"
                + " after delete on plugin_tags "
                + "begin "
                + "delete from tags where id not in (select tag_id from plugin_tags);"
                + " end;");
    }

    public static void createInitialTables() throws Exception{
        CreateTables create = new CreateTables();

        //open database
        create.openDatabase();

        //create plugins table
        create.createPluginsTable();

        //create tags table
        create.createTagsTable();

        //create plugin tags table
        create.createPluginTagsTable();

        //create table for nombre plugin
        create.createView("plugins", "name", "plugin_names");

        //create table for os
        create.createView("plugins", "os", "os");

        //create table for category
        create.createView("plugins", "category", "categories");

        //create table for author
        create.createView("plugins", "author", "authors");

        //create plugin tags delete trigger
        create.createTrigger();

        //close database;
        create.closeDatabase();
    }

    private void createPluginsTable() throws Exception {
        stat.executeUpdate("create table if not exists plugins ("
                + "id integer primary key autoincrement,"
                + "name string,"
                + "author string,"
                + "category string,"
                + "os string"
                + ");");
    }
    
    /**
     * Todas las tablas seran iguales, asi que uso algo generico.
     * @param tableName
     * @throws SQLException
     */
    private void createView(String tableName, String field, String viewName) throws SQLException{

        stat.executeUpdate("create view if not exists "+viewName
                + " as "
                +" select distinct "+field+" from "+tableName
                + " ;");
    }
    
    private void createTagsTable() throws Exception {
        stat.executeUpdate("create table if not exists tags ("
                +"id integer primary key autoincrement, "
                + "name string unique on conflict abort"
                + ");");
    }

    private void createPluginTagsTable() throws Exception {
        stat.executeUpdate("create table if not exists plugin_tags ("
                +"plugin_id references plugins(id) on delete cascade, "
                + "tag_id references tags(id), "
                + "constraint plugin_tags_pk primary key (plugin_id, tag_id)"
                + ");");

    }
}
