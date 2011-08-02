/* This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.
You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package afa.model;

import java.util.*;

//import org.jdom.Element;
/**
 * Clase abstracta que representa a una tecnica
 */
public class Technique {

    /**
     * Vector conjunto de los parametros de la tecnica
     */
    public Vector<Param> params = new Vector<Param>();

    private Vector<Result> results = new Vector<Result>();
    
    /**
     * Constructor de la clase Tecnica
     */
    public Technique() {
    }

    /**
     * Constructor de la clase Tecnica
     * @param parametros Vector de parametros
     * @param plugin Plugin de la clase parametro
     */
    public Technique(Vector<Param> parametros) {
        this.params = parametros;
    }


    public void addParam(String name, String value){
        params.add(new Param(name, value));
    }

    //Devuelve un valor por el nombre del parametro
    /**
     * Metodo que devuelve el valor de un parametro concreto
     * @param nombre Nombre del parametro del que se quiere obtener el valor
     * @return Valor del parametro concreto seleccionado
     */
    public Param getParam(String nombre) {
        
        for(Param e : params){
            if (e.getName().compareTo(nombre) == 0) {
                return e;
            }
        }
        return new Param("", "");
    }

    public void addResult(Device d, String s, boolean success){
        results.add(new Result(d, s, success));
        
    }

    public Vector<Result> getResults() {
        return results;
    }

    public Vector<Result> getResults(Device dev){
        Vector<Result> aux = new Vector<Result>();
        for(Result r : results){
            if(r.getDevice().equals(dev))
                aux.add(r);
        }
        
        return aux;
    }

    public Vector<Device> getDevices(){
        Vector<Device> dev = new Vector<Device>();
        for(Result r : results){
            if(!dev.contains(r.getDevice()))
                dev.add(r.getDevice());
        }

        return dev;
    }
    
    public boolean success(Device dev) {
        Vector<Result> aux = getResults(dev);
        for(Result r : aux){
            if(r.isSuccess()) return true;
        }
        
        return false;
    }

    public Result getLastResult(Device dev) {
        return getResults(dev).lastElement();
    }
}
