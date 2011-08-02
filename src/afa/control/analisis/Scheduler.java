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

import afa.model.Device;
import afa.model.Result;
import afa.model.Technique;
import java.awt.Color;
import java.util.Vector;

/**
 * Clase encarga de administrar la ejecucion den analisis. Pasa mensajes de informacion.
 * Se encarga de iniciar y parar la ejecucion.
 * 
 * @author Alvaro Duran Tovar
 */
public class Scheduler {

    private static Vector<Technique> techniques = new Vector<Technique>();
    private static Vector<Device> partitions = new Vector<Device>();
    private static Thread thread = null;
    private static int remainingTechs = 0;

    /**
     * Notifica varios resultados. Invocado directamente desde Executor.executeFindFile
     * Dicho metodo se encarga de ejecutar todas las busquedas de ficheros concretos.
     * @param results
     */
    public static void notifyResult(Vector<Result> results) {
        Analisis.notifyResult(results);
    }

    public static void notifyMessage(String message, Color color) {
        //aqui filtrar si se quiere o no hacer mensajes verbose
        Analisis.notifyMessage(message, color);
    }

    /**
     * Notifica un resultado. Usado para ir mostrando informacion del feedback.
     * @param result
     */
    public static void notifyResult(Result result) {
        Vector<Result> res = new Vector<Result>();
        res.add(result);

        Scheduler.notifyResult(res);
    }

    /**
     * Para todos los hilos.
     */
    static void stopAll() {
        Executor.stop();
        ((TechThread) thread).stopThread();
    }

    /**
     * Devuelve el valor del total de tecnicas, tanto las ya ejecutadas como
     * las que faltan por ejecutar.
     * @return
     */
    static int getTotalTechs() {
        return (techniques.size()) * partitions.size();
    }

    /**
     * Resta uno a la cantidad de tecnicas que restan por ejecutar y actualiza
     * la vista correspondiente.
     */
    static synchronized void decressRemainingTechs() {
        remainingTechs--;
        Analisis.remainingTechsUpdated();
    }

    /**
     * Devuelve el valor de las tecnicas que faltan por ejecutar.
     * @return tecnicas que faltan por ejecutar.
     */
    static int getRemainingTechs() {
        return remainingTechs;
    }

    /**
     * Elimina el contenidod el vector de tecnicas.
     */
    private static void cleanAll() {
        techniques.removeAllElements();
    }

    /**
     * Agrega una nueva tecnica al vector de tecnicas a ejecutar.
     * @param t
     */
    static void addTech(Technique t) {
        techniques.add(t);
        remainingTechs++;
    }

    static void setPartitions(Vector<Device> vec) {
        partitions = vec;
    }

    /**
     * Inicia el analisis.
     * Calcula cual es el maximo de tecnicas. El maximo se calcula como el numero de tecnicas
     * multiplicado por el numero de particiones distintas a analizar.
     *
     */
    static void start() {
        remainingTechs = remainingTechs * partitions.size();
        thread = new TechThread(techniques);

        thread.start();
    }

    /**
     * Inicializa la clase para un nuevo analisis.
     * Limia el vector de tecnicas y el de hilos.
     * Inicializa la clase Executor y actualiza el valor de reaminingTechs a 0.
     */
    static void init() {
        cleanAll();
        Executor.init();
        remainingTechs = 0;
    }

    /**
     * Clase utilizada para ejecutar los plugins dentro de hilos.
     * Ademas se ha implementado el metodo stopThread para parar los hilos de forma controlada.
     */

    private static class TechThread extends Thread {

        private Vector<Technique> tecnicas;
        private boolean stoped = false;

        TechThread(Vector<Technique> vec) {
            setTecnicas(vec);
        }

        private void setTecnicas(Vector<Technique> vec) {
            tecnicas = vec;
        }

        @Override
        public void run() {
            for(Device dev : partitions){
                try {
                    //if(!stoped)
                    Executor.executeTechniques(dev, tecnicas);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }

        public void stopThread() {
            stoped = true;
        }
    }
}
