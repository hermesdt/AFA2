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

import afa.control.Constants;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Properties;
import org.sqlite.SQLiteConfig;

/**
 * Abstract class. The purpouse of this class is to define the open and close operations.
 * @author Alvaro Duran Tovar
 */
public abstract class DBOperator {

    protected static Connection conn;
    protected static Statement stat;

    protected void openDatabase() throws Exception {
        Class.forName("org.sqlite.JDBC");

        //necesario para que admita borrado en cascada
        Properties props = null;
        SQLiteConfig conf = new SQLiteConfig();
        conf.enforceForeignKeys(true);
        props = conf.toProperties();

        conn = DriverManager.getConnection("jdbc:sqlite:" + Constants.getDatabaseFile(), props);
        stat = conn.createStatement();


    }

    protected void closeDatabase() throws Exception {
        conn.close();
    }

//    

}
