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


package afa.model;

/**
 *
 * @author Alvaro Duran Tovar
 */
public class FilePattern {

    private Technique t;

    public FilePattern(Technique t) {
        this.t = t;
    }

    public String getHashType() {
        try{
            return t.getParam("hash").getParam("type").getValue();
        }catch(Exception e){
            System.out.println("");
            return null;
        }
    }

    public String getContent(){
        return t.getParam("content").getValue();
    }

    //comprueba si existe el parametro "content" en la tecnica.
    public boolean haveContent(){
        String aux = t.getParam("content").getValue();
        return (aux == null || aux.length() == 0 || aux.equals(""))? false : true;
    }

    public boolean haveMimeType(){
        String aux = t.getParam("mimetype").getValue();
        return (aux == null || aux.length() == 0 || aux.equals(""))? false : true;
    }

    public String getHashValue() {
        return t.getParam("hash").getParam("value").getValue();
    }

    public String getInitialPath() {
        return t.getParam("location").getValue();
    }

    public String getName() {
        return t.getParam("name").getValue();
    }

    public String getMimeType(){
        return t.getParam("mimetype").getValue();
    }

    public void addMatch(Device dev, String match) {
        t.addResult(dev, match, true);
    }

    public Result getLastMatch(Device dev){
        return t.getLastResult(dev);
    }
}
