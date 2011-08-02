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
public class Result {

    private Device dev;
    private String value;
    private boolean success;


    public Result(Device dev, String value, boolean success){
        this.dev = dev;
        this.value = value;
        this.success = success;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }


    public void setData(Device d, String s, boolean success){
        dev = d;
        value = s;
        this.success = success;
    }

    public void setDevice(Device dev){
        this.dev = dev;
    }

    public void setValue(String value){
        this.value = value;
    }
    
    public Device getDevice(){
        return dev;
    }

    public String getValue(){
        return value;
    }
}
