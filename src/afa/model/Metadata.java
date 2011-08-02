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
 *
 * @author Alvaro Duran Tovar
 */
public class Metadata {

    private String name, os, version, author, description, date, category, lastUpdated;
    private Vector<Tag> tags;

    public Metadata(String name, String os, String version, String author,
            String date, String category, Vector<Tag> tags, String description){
        this.name = name;
        this.os = os;
        this.version = version;
        this.author = author;
        this.date = date;
        this.category = category;
        this.description = description;

        this.tags = tags;
    }

    public Metadata(){

    }

    public String getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(String lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    
    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Vector<Tag> getTags() {
        return tags;
    }

    public void setTags(Vector<Tag> tags) {
        this.tags = tags;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    

    public void print(){
        System.out.println("Name: "+getName());
        System.out.println("OS: "+getOs());
        System.out.println("Version: "+getVersion());
        System.out.println("Author: "+getAuthor());
        System.out.println("Date: "+getDate());
        System.out.println("Category: "+getCategory());
        //labels
        for(Tag tag : tags){
            System.out.println("Tag: "+tag.getName());
        }
        System.out.println("Description: "+getDescription());
    }
}
