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


package afa.model.filesystem;

/**
 *
 * @author Alvaro Duran Tovar
 */
public abstract class DBFile {

    public abstract boolean isDirectory();
    public abstract boolean isFile();
    public abstract String getName();
    public abstract DBFile getParent();
    public abstract String getAbsolutePath();
    public abstract DBFile[] getChildren();
    //get next brother
    public abstract boolean getNext();
    //get previous brother
    public abstract boolean getPrevious();
    public abstract boolean hasNext();
    public abstract boolean hasPrevious();
}
