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
package afa.control.analisis;

import afa.control.Constants;
import afa.model.*;
import afa.view.Window;
import java.awt.Color;
import java.util.Vector;

/**
 * Clase que representa el estado y configuracion del analisis.
 * A traves de esta clase se dice que tipo de datos se ignoraran o no.
 * Hace de interfaz con el exterior, el scheduler no puede ser invocado directamente.
 * Ademas el scheduler no llama al exterior directamente, esta clase esta hecha para hacer
 * de "proxy".
 * 
 * @author Alvaro Duran Tovar
 */
public class Analisis {

    private static Vector<Category> cat;
    private static Vector<Device> partitions;
    private static Window window;
    private static boolean ignoreRecursiveTechniques = false;
    private static boolean ignoreFileContentTechniques = false;
    private static boolean ignoreMD5HashTechniques = false;
    private static boolean ignoreSHA1HashTechniques = false;
    private static boolean ignoreFindFileTechniques = false;

    public static void setIgnoreFileContentTechniques(boolean ignoreFileContentTechniques) {
        Analisis.ignoreFileContentTechniques = ignoreFileContentTechniques;
    }

    public static void setIgnoreFindFileTechniques(boolean ignoreFindFileTechniques) {
        Analisis.ignoreFindFileTechniques = ignoreFindFileTechniques;
    }

    public static void setIgnoreMD5HashTechniques(boolean ignoreMD5HashTechniques) {
        Analisis.ignoreMD5HashTechniques = ignoreMD5HashTechniques;
    }

    public static void setIgnoreRecursiveTechniques(boolean ignoreRecursiveTechniques) {
        Analisis.ignoreRecursiveTechniques = ignoreRecursiveTechniques;
    }

    public static void setIgnoreSHA1HashTechniques(boolean ignoreSHA1HashTechniques) {
        Analisis.ignoreSHA1HashTechniques = ignoreSHA1HashTechniques;
    }

    public static void clean() {
        cat = null;
    }

    /**
     * Para la ejecuciono de planificador. Al ser un metodo plublico puede ser invocado desde fuera.
     * 
     */
    public static void stop() {
        Scheduler.stopAll();
    }

    public static Vector<Device> getDevices() {
        return partitions;
    }

    /**
     * Devuelve todos los plugins que han sido.
     * @return
     */
    static Vector<Category> getPlugins() {
        return cat;
    }

    /**
     * Primero limpia el contenido del vector de plugins anterior.
     * Despues asigna al vector de plugins el objeto pasado por parametro.
     * 
     * @param v Plugins a ejecutar.
     */
    public static void setPlugins(Vector<Category> v) {
        clean();
        cat = v;
    }

    public static void setPartitions(Vector<Device> v) {
        partitions = v;
    }

    private static boolean ignoreFindFileTechniques(Technique t){
        return t.getType().equals("find_file") && ignoreFindFileTechniques;
    }

    private static boolean ignoreFileContentTechniques(Technique t){
        return !t.getParam("content").getValue().equals("") && ignoreFileContentTechniques;
    }

    private static boolean ignoreRecursiveTechniques(Technique t){
        return (t.getParam("recursively").getValue().equals("true")) &&
                ignoreRecursiveTechniques;
    }

    private static boolean ignoreMD5HashTechniques(Technique t){
        return t.getParam("hash").getParam("type").getValue().equals("MD5") &&
                ignoreMD5HashTechniques;
    }

    private static boolean ignoreSHA1HashTechniques(Technique t){
        return t.getParam("hash").getParam("type").getValue().equals("SHA1") &&
                ignoreMD5HashTechniques;
    }

    /**
     * Metodo encargado de configurar el sheduler e iniciar la ejecucion.
     * Solo a traves de este metodo puede hacerse.
     * Aqui tambien se comprueba que plugins van a ser ignorados.
     * Se necesita un objeto Window para poder devolver algun feedback.
     * 
     * @param win
     * @throws Exception
     */
    public static void execute(Window win) throws Exception {
        window = win;
        if (cat == null || partitions == null) {
            throw new Exception("[Analisis.execute] Vector tiene valor null.");
        }

        //limpiar planificador
        Scheduler.init();

        //extraer plugins
        for (Category c : cat) {
            for (Plugin p : c.getPlugins()) {
                for (Technique t : p.getTechniques()) {
                    boolean ignore = false;
                    ignore = ignore | ignoreFindFileTechniques(t);
                    ignore = ignore | ignoreFileContentTechniques(t);
                    ignore = ignore | ignoreRecursiveTechniques(t);
                    ignore = ignore | ignoreMD5HashTechniques(t);
                    ignore = ignore | ignoreSHA1HashTechniques(t);
                        
                    if(!ignore)
                        Scheduler.addTech(t);
                }
            }
        }

        //particiones
        Scheduler.setPartitions(partitions);

        //asignar a la vista el total de tecnicas a ejecutar
        window.setTotalTechs(Scheduler.getTotalTechs());

        //ejecutar listas de plugins
        Scheduler.start();
    }

    private static void generateReport() {
        //ReportPDF.createDocument("/home/Alvaro Duran Tovar/reportePDF.pdf");
        System.out.println("[Analisis.generateRepor] reporte generado");
    }

    /**
     * Metodo invocado desde el scheduler cuando finaliza un tecnica.
     * La labor de este metodo es actualizar la vista principal y si ya no quedan
     * mas tecnicas a ejecutar generar el reporte.
     * 
     */
    static void remainingTechsUpdated() {
        window.updateRemainingTechs(Scheduler.getTotalTechs() - Scheduler.getRemainingTechs());
        if (Scheduler.getRemainingTechs() == 0) {
            
            generateReport();
        }
    }
    
    /**
     * Metodo usado desde el scheduler para actualizar la ventana principal.
     * @param results
     */
    static void notifyResult(Vector<Result> results) {
        for (Result result : results) {
            if(result.isSuccess()){
                Analisis.notifyMessage("[" + result.getDevice().getDeviceName() + "] "
                    + result.getValue() + " - " + Boolean.toString(result.isSuccess()),
                    Constants.SUCCESS_FEEDBACK_COLOR);
            }else{
                Analisis.notifyMessage("[" + result.getDevice().getDeviceName() + "] "
                    + result.getValue() + " - " + Boolean.toString(result.isSuccess()),
                    Constants.ERROR_FEEDBACK_COLOR);
            }
            
        }
    }

    /**
     * Metodo utilizado para enviar mensajes de feedback a la ventana principal.
     * 
     * @param message
     * @param color
     */
    static synchronized void notifyMessage(String message, Color color){
        window.printAnalisisMessage(message, color);
    }
}
