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

package afa.control.net;

import java.io.*;
import java.net.*;

/**
 *
 * @author Alvaro Duran Tovar
 */
public class DownloadFile {

    /*
     * Crear directorios padre si es necesario
     */
    private static void checkDestination(String dest){
        File f = new File(dest);

        File parent = f.getParentFile();
        if(parent != null && !parent.exists()){
            parent.mkdirs();
        }
    }

    /**
     * Descarga un fichero de texto desde la direccion origen hasta la direccion
     * destino.
     * 
     * @param s_url
     * @param dest
     * @throws Exception
     */
    public static void getTextFile(String s_url, String dest) throws Exception{
        checkDestination(dest);
        URL url = new URL(s_url);
        URLConnection conn = url.openConnection();

        PrintWriter pw = new PrintWriter(new FileWriter(new File(dest)));
        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String line = null;
        while((line = br.readLine()) != null){
            pw.println(line);
        }
        br.close();
        pw.close();
    }

    /**
     * Descarga un fichero binario desde la direccion origen hastda la direccion
     * destino.
     * 
     * @param file
     * @param dest
     * @throws MalformedURLException
     * @throws IOException
     */
    public static void getBinaryFile(String file, String dest) throws MalformedURLException, IOException{
        checkDestination(dest);
        URL url = new URL(file);
        URLConnection conn = url.openConnection();

        BufferedInputStream bis = new BufferedInputStream(conn.getInputStream());
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(new File(dest)));

        int data = -1;
        while((data = bis.read()) != -1){
            bos.write(data);
        }
        bos.close();
        bis.close();
    }
}
