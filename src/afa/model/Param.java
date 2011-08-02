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

import java.util.Vector;

/**
 * Clase que representa un parametro
 */
public class Param {

    private String name;
    private String value;
    private Vector<Param> params = new Vector<Param>();

    /**
     * Constructor de la clase Parametro
     * @param name Nombre del parametro
     * @param value Valor de parametro
     */
    public Param(String name, String value) {

        this.name = name;
        this.value = value;

    }

    public void addParam(Param p){
        params.add(p);
    }


    public void addParam(Vector<Param> vec){
        params.addAll(vec);
    }

    public Vector<Param> getParams(){
        return params;
    }

    public int paramsCount(){
        return params.size();
    }

    public boolean containsParams(){
        return paramsCount() == 0;
    }

    public Param getParam(Param p){
        for(Param param : params){
            if(param.getName().equals(p.getName()))
                return param;
        }
        return new Param("", "");
    }

    public Param getParam(String name){
        for(Param param : params){
            if(param.getName().equals(name))
                return param;
        }
        return new Param("", "");
    }

    /**
     * Metodo que devuelve el nombre del parametro
     * @return Nombre del parametro
     */
    public String getName() {
        return (name != null)? name : "";
    }

    /**
     * Devuelve el valor del parametro
     * @return Valor del parametro
     */
    public String getValue() {
        return (value != null)? value : "";
    }

    /**
     * Metodo que establece el nombre del parametro
     * @param name Nombre del parametro
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Metodo que introduce el valor del parametro
     * @param value Valor del parametro
     */
    public void setValue(String value) {
        this.value = value;
    }
}
