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

import java.io.File;
import java.util.*;

/**
 * Clase que representa un Plugin
 *@author Alvaro Duran Tovar
 */
public class Plugin {

    private Metadata metadata;
    private Vector<Technique> techniques = new Vector<Technique>();
    private String xmlarchive;
    private File location;
    private boolean isLocal = true;
    private String repositoryLocation = "";
    private boolean remoteNewer = false;

    public boolean isRemoteNewer() {
        return remoteNewer;
    }

    public void setRemoteNewer(boolean remoteNewer) {
        this.remoteNewer = remoteNewer;
    }

    public Vector<Tag> getTags(){
        return metadata.getTags();
    }

    public Vector<String> getTagsAsVectorStrings(){
        Vector<String> results = new Vector<String>();

        for(Tag t : getTags())
            results.add(t.getName());

        return results;
    }

    public void setTags(Vector<Tag> vec){
        metadata.setTags(vec);
    }

    public boolean isIsLocal() {
        return isLocal;
    }

    public void setIsLocal(boolean isLocal) {
        this.isLocal = isLocal;
    }

    public String getCategory(){
        return metadata.getCategory();
    }

    public void setCategory(String cat){
        metadata.setCategory(cat);
    }

    public String getDescription(){
        return metadata.getDescription();
    }

    public void setDescription(String desc){
        metadata.setDescription(desc);
    }

    public String getRepositoryLocation() {
        return repositoryLocation;
    }

    public String getLastUpdatedMilis(){
        return metadata.getLastUpdated();
    }

    public String getLastUpdated(){
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(Long.parseLong(getLastUpdatedMilis()));
        int min_i = c.get(c.MINUTE);
        int sec_i = c.get(c.SECOND);
        String min = (min_i < 10)? "0"+min_i : ""+min_i;
        String sec = (sec_i < 10)? "0"+sec_i : ""+sec_i;

        String date = ""+
                c.get(c.DAY_OF_MONTH)+"/"+
                c.get(c.MONTH)+"/"+
                c.get(c.YEAR)+" ("+
                c.get(c.HOUR_OF_DAY)+":"+
                min+":"+
                sec+")";

        return date;
    }

    public void setLastUpdated(String time){
        metadata.setLastUpdated(time);
    }

    public void setRepositoryLocation(String repositoryLocation) {
        this.repositoryLocation = repositoryLocation;
    }

    

    public File getLocation() {
        return location;
    }

    public void setLocation(File location) {
        this.location = location;
    }

    public Plugin(Metadata metadata){
        this.metadata = metadata;
    }

    public void printMetadata(){
        metadata.print();
    }

    /**
     * Constructor de la clase Plugin
     * @param name Nombre del plugin
     */
    public Plugin(String name) {
        metadata = new Metadata();
        setName(name);
    }

    public Plugin(){
        
    }

    /**
     * Metodo que devuelve al conjunto de tecnicas
     * @return Vector de tecnicas
     */
    public Vector<Technique> getTechniques() {
        return techniques;

    }

    public void setTechniques(Vector<Technique> vec){
        this.techniques = vec;
    }

    /**
     * Metodo que permite anadir una tecnica al conjunto de la tecnicas del plugin
     * @param tec Tecnica a anadir
     */
    public void addTechnique(Technique tec) {
        techniques.add(tec);
    }

    public void addTechnique(Vector<Technique> vec) {
        techniques.addAll(vec);
    }

    /**
     * MEtodo que devuelve una tecnica concreta del conjunto de las tecnicas
     * @param index indice de la tecnica solicitada
     * @return Tecnica devuelta
     */
    public Technique getTechinque(int index) {
        return techniques.get(index);
    }

    /**
     * Metodo que permite borrar un tecnica
     * @param index Metodo que se quiere borrar dentro del vector de tecnicas
     */
    public void removeTechnique(int index) {
        techniques.remove(index);
    }

    /**
     * Metodo que establece el sistema operativo
     * @param _so Metodo que introduce un sistema operativo para el plugin
     */
    public String getOS() {
        return metadata.getOs();
    }


    public String getAuthor(){
        return metadata.getAuthor();
    }

    public String getDate(){
        return metadata.getDate();
    }

    /**
     * Metodo que devuelve el sistema operativo del plugin
     * @return Sistema operativo del plugin
     */
    public void setOS(String os) {
        metadata.setOs(os);
    }

    /**
     * Metodo que establece la version del plugin
     * @param _version version del plugin
     */
    public void setVersion(String version) {
        metadata.setVersion(version);
    }

    /**
     * Metodo que devuelve la version del software buscado en el plugin
     * @return Version del software en el plugin
     */
    public String getVersion() {
        return metadata.getVersion();
    }

    /**
     * Metodo que establece el nombre del plugin
     * @param _name Nombre del plugin
     */
    public void setName(String name) {
        if(metadata == null)
            metadata = new Metadata();
        metadata.setName(name);
    }

    /**
     * Metodo que devuelve el nombre del plugin
     * @return Nombre del plugin
     */
    public String getName() {
        return metadata.getName();
    }

    /**
     *
     * @return Category + name
     */
    public String getFullName(){
        return metadata.getCategory()+File.separator+metadata.getName();
    }


    /**
     * Metodo que devuelve true si se ha encontrado el resultado del plugin
     * @return Boolean que indica si se ha encontrado el software buscado por el plugin
     */
    public boolean success(Device dev) {
        for(Technique t : techniques){
            if(t.success(dev)){
                return true;
            }
        }
        return false;
    }

    /**
     * 
     */
    public void setXMLArchive(String archivoxml) {
        this.xmlarchive = archivoxml;
    }

    public String getXMLArchive() {
        return xmlarchive;
    }
}
