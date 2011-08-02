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

package afa.control.filesystem;

import java.io.BufferedReader;
import java.io.File;
import java.io.*;

/**
 *
 * @author Alvaro Duran Tovar
 */
class FindContent {

    public static boolean find(String file, String value) throws Exception{
        return find(new File(file), value);
    }

    public static boolean find(File file, String value) throws Exception{

        //si value no tiene valor no se busca patron
        if(value.length() == 0 || value.equals("") || value == null){
            return false;
        }

        //RandomAccessFile buf = new RandomAccessFile(file, "r");

       BufferedInputStream buf = new BufferedInputStream(new FileInputStream(file));


        byte[] bytes = value.getBytes();
        int data = 0;
        boolean exit_while = false;
        boolean for_breaked = false;
        long position = 0;
        long counter = 0;

        while((data = buf.read()) != -1 && !exit_while){
            for_breaked = false;
            
            if(data == bytes[0]){
                //guardar posicion del puntero
                //position = buf.getFilePointer();
                buf.mark(bytes.length+1);

                for(int i = 1;i<bytes.length; i++){
                    data = buf.read();
                    //si EOF salir del while
                    if(data == -1){
                        exit_while = true;
                        break;
                    }
                    //si data no es igual al byte buscado
                    //fin for, seguir buscando
                    if(data != bytes[i]){
                        for_breaked = true;
                        break;
                    }
                }

                //si el for no fue interrumpido hemos encontrado el valor
                if(!for_breaked){buf.close(); return true;}
                //si no fue encontrado restaurar la posicion del puntero lector
                else buf.reset();
            }

        }
        
        buf.close();
        return false;
    }

}
