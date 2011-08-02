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

import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Clase que contiene los parametros de configuracion para la ejecucion de la herramienta
 * @author Armando Machuca Llorente
 */
public class Constants {

    private static String config_dir = "config";
    private static String repository_file = config_dir+File.separator+"repository.xml";
    private static String fichero_parametros = config_dir + File.separator + "Param.cfg";
    private static String databaseFile  = getText("database_file");
    //Directorio donde ubican los plugins
    private static String directorio_plugins = getText("directorio_plugins");

    private static String repository = getText("url_repositorio");

    //Maximo tama�o de fichero a escanear. Con archivos gigantes el sistema se queda sin memoria
    private static int max_tam_arch_escanear = Integer.parseInt(getText("max_tam_arch_escanear"));
    //Directorio donde se ubican los informes
    private static String directorio_informes = getText("directorio_informes");
    //Archivo de idioma activo
    private static String archivo_idioma = getText("archivo_idioma");
    private static String ImagesDir = getText("images");
    //Archivo de logs
    private static String log = getText("log");
    //Archivo de ayuda
    private static String ayuda = getText("ayuda");

    public final static Color ERROR_FEEDBACK_COLOR = Color.decode("0xB22222");
    public final static Color SUCCESS_FEEDBACK_COLOR = Color.decode("0x006400");
    public final static Color INFO_FEEDBACK_COLOR = Color.decode("0x27408b");
    public final static Color VERBOSE_FEEDBACK_COLOR = Color.LIGHT_GRAY;
    public final static boolean ENABLE_RECURSIVE_SEARCH_FILE = true;

    public static String getDatabaseFile() {
        return databaseFile;
    }

    public static void setDatabaseFile(String databaseFile) {
        Constants.databaseFile = databaseFile;
    }
    /**
     * Metodo que permite obtener el parametro directorio_plugins
     * @return directorio donde se encuentran los plugins
     */
    public static String getDirectorioPlugins() {
        return directorio_plugins;
    }

    public static String getConfigDir() {
        return config_dir;
    }

    public static String getRepository() {
        return repository;
    }

    public static String getRepositoryFile() {
        return repository_file;
    }

    /**
     * Metodo que permite obtener el parametro directorio_plugins
     * @return directorio donde se encuentran los plugins
     */
    public static String getImagesDir() {
        return ImagesDir;
    }

    /**
     * 
     * @return 
     */
    public static int getMax_tam_arch_escanear() {
        return max_tam_arch_escanear;
    }

    /**
     * Metodo que permite obtener el parametro directorio_informes
     * @return Directorio donde se almacenan los informes
     */
    public static String getDirectorio_informes() {
        return directorio_informes;
    }

    /**
     * Metodo que permite establecer el archivo de idioma a utilizar
     * @param   archivo_idioma Archivo de idioma a utilizar
     */
    public static void setArchivoIdioma(String archivo_idioma) {
        Constants.archivo_idioma = archivo_idioma;
    }

    /**
     * Metodo que devuelve el archivo de idioma utilizado
     * @return Archivo de idioma en uso
     */
    public static String getArchivoIdioma() {
        return archivo_idioma;
    }

    /**
     * Metodo que permite establecer el archivo de ayuda
     * @param   archivo_idioma Archivo de log
     */
    public static void setLogFile(String _log) {
        Constants.log = _log;
    }

    /**
     * Metodo que devuelve el archivo de ayuda
     * @return Archivo de ayuda
     */
    public static String getLogFile() {
        return log;
    }

    /**
     * Metodo que devuelve el archivo de log
     * @return Archivo de log
     */
    public static String getHelpFile() {
        return ayuda;
    }

    /**
     * Metodo que permite establecer el archivo de log
     * @param   archivo_idioma Archivo de log
     */
    public static void setHelpFile(String _ayuda) {
        Constants.ayuda = _ayuda;
    }

    /**
     * Metodo que devuelve la traduccion de una cadena al idioma activo
     * @param texto texto originar
     * @return Traduccion en el idioma seleccionado
     */
    public static String getText(String texto) {

        String parametro = "";
        try {
            FileReader fr = new FileReader(fichero_parametros);
            BufferedReader br = new BufferedReader(fr);
            String str;
            while ((str = br.readLine()) != null) {
                if (str.indexOf(texto) != -1) {
                    parametro = str.substring(str.indexOf("-") + 1);
                    break;
                }
            }
            br.close();
        } catch (IOException e) {
            System.err.println(e);
        }
        parametro = parametro.trim();
        return parametro;
    }

    public static String getRepoBase() {
        return getText("url_repo_base");
    }
}
