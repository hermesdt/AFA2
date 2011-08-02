/*
 *  Copyright (C) 2011 
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

package afa.control;

import java.io.*;

/**
 * Clase de Idioma que traduce una frase entrante al idioma actual
 * @author Armando Machuca Llorente
 */
public  class Language{
	
    /**
     * Fichero donde se encuentra el idioma activo
     */
    
    
	static File fichero_idioma=new File(Constants.getArchivoIdioma());
   
	
    /**
     * Metodo que devuelve la traduccion al idioma seleccionado
     * @param texto Texto que se quiere traducir
     * @return Traduccion del texto
     */
	public static String getText(String texto){
		
		String traduccion="";
		 try {
	            FileReader fr = new FileReader(fichero_idioma);
	            BufferedReader br = new BufferedReader(fr);
	            String str;
	            while ((str = br.readLine()) != null) {
	                if (str.indexOf(texto) != -1) {
	                    traduccion=str.substring(str.indexOf("-") + 1);
	                    break;
	                }
	            }
	            br.close();
	        }
	        catch (IOException e) {
	            System.err.println(e);
	        }   
		return traduccion;
	}		
}