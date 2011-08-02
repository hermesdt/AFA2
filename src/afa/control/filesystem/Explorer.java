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

import afa.control.Constants;
import afa.control.Tools;
import afa.control.analisis.Scheduler;
import afa.model.*;
import java.io.File;
import java.io.IOException;
import java.util.Vector;

/**
 * Clase encargada de buscar en el disco duro.
 * Buscara una direccion concreta o bien una busqueda recursiva.
 * Dado un vector de objetos file patterns comprueba si cada fichero pasa
 * o no los patrones.
 * 
 * @author Alvaro Duran Tovar
 */
public class Explorer {

    private static boolean stoped = false;
    private static boolean console_mode = false;

    /**
     * Check if one file exists. Used by techniques that are not recursive.
     * @param path
     * @return
     */
    public static boolean checkLocation(String path, Technique t) throws Exception {
        boolean match = checkMatch(new File(path), new FilePattern(t));
        return match;
    }

    /**
     * Alias for the other checkLocationsRecursively
     * @param dev
     * @param path
     * @param patterns
     * @throws Exception
     */
    public static void checkLocationsRecursively(Device dev, String path, Vector<FilePattern> patterns) throws Exception {
        checkLocationsRecursively(dev, new File(path), patterns);
    }

    /**
     *
     * @param dev
     * @param path
     * @param patterns
     * @throws Exception
     */
    public static void checkLocationsRecursively(Device dev, File path,
            Vector<FilePattern> patterns) throws Exception {
        String relativePath = path.getAbsolutePath();
        if (!dev.getMountPoint().getAbsolutePath().equals("/")) {
            relativePath = relativePath.replace(dev.getMountPoint().getAbsolutePath(), "");
        }

        if (!console_mode) {
            Scheduler.notifyMessage("[" + dev.getDeviceName().getAbsolutePath() + "] " + relativePath,
                    Constants.VERBOSE_FEEDBACK_COLOR);
        } else {
            System.out.println("[" + dev.getDeviceName().getAbsolutePath() + "]" + " " + relativePath);
        }

        if (stoped) {
            return;
        }

        //explore files of the path
        File files[] = path.listFiles();
        if (files != null) //check permisions
        {
            for (File file : files) {
                if (stoped) return;

                //if symbolic link return
                if (isLink(file)) continue;

                //check if its a directory or not.
                //if its then make a recursive call
                if (file.isDirectory())
                    checkLocationsRecursively(dev, file, patterns);
                else
                    checkMatchs(dev, file, patterns);
                
            }
        }

    }

    /**
     * Check if each pattern matchs against the selected file.
     *
     * @param patterns
     * @param f
     */
    private static void checkMatchs(Device dev, File file, Vector<FilePattern> patterns) throws Exception {
        for (FilePattern fp : patterns) {

            //file pattern match with current file
            if (checkMatch(file, fp)) {
                fp.addMatch(dev, file.getAbsolutePath());
                //notify
                Result aux = fp.getLastMatch(dev);
                if (!console_mode) {
                    Scheduler.notifyResult(aux);
                } else {
                    System.out.println("result: " + aux);
                }
            }
        }
    }

    /**
     * Check if the filepattern match with the selected file.
     * The method will apply diferent filters and if one of them return negative
     * then method stop.
     *
     * @param fp
     * @param matcher
     * @return if the file matchs with the file pattern
     */
    private static boolean checkMatch(File matcher, FilePattern fp) throws IOException, Exception {

        if (fp.getInitialPath() != null && fp.getName() != null) {
            /* start fix paths */
            String auxPath = fp.getInitialPath();
            if (!auxPath.startsWith(File.separator)) {
                auxPath = File.separator + auxPath;
            }
            if (!auxPath.endsWith(File.separator)) {
                auxPath += File.separator;
            }
            /* end fix paths*/


            if (matcher.getAbsolutePath().contains(auxPath)
                    && matcher.getAbsolutePath().contains(matcher.getName())) {

                //begining of the matchs section
                //the variable flag will tell if the filters are passed or not.
                boolean flag = true;

                /* check file name */
                //check if the file being analized ends with the name of the file pattern
                //maybe it should check only its the exact name.
                flag = flag & matcher.getName().endsWith(fp.getName());
                //in case of negative match return.
                if (!flag) return flag;

                /* check md5 */
                //if the file pattern have a MD5 pattern
                if (fp.getHashType().equals("MD5")) {
                    //check if the md5 values match
                    flag = flag & (Tools.getMD5(matcher.getAbsolutePath()).equals(fp.getHashValue()));
                    //in case of negative match return.
                    if (!flag) return flag;
                }

                /* check file content */
                //if the file pattern have a pattern to check content
                if (fp.haveContent()) {
                    //check if the file contains the value of the filepattern
                    flag = flag & FindContent.find(matcher, fp.getContent());
                    //in case of negative match return.
                    if (!flag) return flag;
                }

                /* check mimetype */
                //if file pattern have mime type
                if(fp.haveMimeType()){
                    flag = flag & MimeType.getMimeType(matcher.getAbsolutePath()).contains(fp.getMimeType());
                    if(!flag) return flag;
                }

                return flag;
            }
        }
        //shouldn't be here.
        return false;
    }

    /**
     * Check if a File is a symbolic link.
     * Im not sure if it works...
     */
    private static boolean isLink(File file) {
        try {
            if (!file.exists()) {
                return true;
            } else {
                String cnnpath = file.getCanonicalPath();
                String abspath = file.getAbsolutePath();
                return !abspath.equals(cnnpath);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return true;
        }
    }

    /**
     * Finaliza la ejecucion del explorador. Utilizado para terminar la ejecucion de busquedas
     * recursivas manualmente.
     */
    public static void stop() {
        stoped = true;
    }

    /**
     * Inicializa el explorador de forma que asigna al atributo "stoped" el valor false.
     */
    public static void init() {
        stoped = false;
    }
}
