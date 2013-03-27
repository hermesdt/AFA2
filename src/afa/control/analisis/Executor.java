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
import afa.control.filesystem.Explorer;
import afa.model.Device;
import afa.model.FilePattern;
import afa.model.Technique;
import java.io.File;
import java.util.Vector;

/**
 *
 * @author Alvaro Duran Tovar
 */
class Executor {

    private static boolean stoped = false;

    private static void executeFindFile(Device dev, Technique t) throws Exception {
        String pre, name, mount;
        //fix mount string
        //mount NO termina con File.separator
        mount = dev.getMountPoint().getAbsolutePath();
        mount = (mount.endsWith(File.separator)) ? mount.substring(1) : mount;

        //fix pre string
        //pre SI empiza por File.separator
        //pre SI termina por File.separator
        pre = t.getParam("location").getValue();
        pre = (pre.startsWith(File.separator)) ? pre : pre + File.separator;
        pre = (pre.endsWith(File.separator)) ? pre : pre + File.separator;

        //fix name string
        //name NO empieza por File.separator
        name = t.getParam("name").getValue();
        name = (name.startsWith("/") || name.startsWith("\\")) ? name.substring(1) : name;

        String path = mount + pre + name;

        boolean result = Explorer.checkLocation(path, t);
        //add result
        if (!dev.getMountPoint().getAbsolutePath().equals("/")) {
            path = path.replace(dev.getMountPoint().getAbsolutePath(), "");
        }

        t.addResult(dev, path, result);

        //
        Scheduler.decressRemainingTechs();

        //notify result
        Scheduler.notifyResult(t.getResults(dev));
    }

    private static Vector<String> extractPaths(Vector<Technique> vec) {
        Vector<String> paths = new Vector<String>();

        for (Technique t : vec) {
            String location = t.getParam("location").getValue();
            //fix location
            //NO acaba por File.separator
            if (location.endsWith(File.separator)) {
                location = location.substring(1);
            }

            //comprabar si ya hay algun path que contenga este
            boolean flag = false;
            for (String path : paths) {
                if (location.startsWith(path)) {
                    flag = true;
                    break;
                }
            }

            if (!flag) {
                paths.add(location);
            }
        }

        return paths;
    }

    /*
     * Metodo que se encarga de buscar ficheros recursivamente.
     */

    private static void executeFindFileRecurse(Device dev, Vector<Technique> vec) throws Exception {
        if(!Constants.ENABLE_RECURSIVE_SEARCH_FILE){
            for (int i = 0; i < vec.size(); i++) {
                Scheduler.decressRemainingTechs();
                Scheduler.notifyMessage("Recursive search disabled.", Constants.INFO_FEEDBACK_COLOR);
            }
            return;
        }

        //start recurse message
        Scheduler.notifyMessage("Starting recursive analisis on [" + dev.getDeviceName() + "] " + " ...",
                Constants.INFO_FEEDBACK_COLOR);

        Vector<FilePattern> fp = new Vector<FilePattern>();
        for (Technique t : vec) {
            fp.add(new FilePattern(t));
        }

        Vector<String> paths = extractPaths(vec);
        for (String path : paths) {
            if (stoped) {
                break;
            }
            //add path to dev.mountpoint
            //prefix NO acaba por File.separator
            String prefix = dev.getMountPoint().getAbsolutePath();
            if (prefix.endsWith(File.separator)) {
                prefix = prefix.substring(1);
            }

            //path SI empieza por File.separator
            if (!path.startsWith(File.separator)) {
                path = File.separator + path;
            }

            Explorer.checkLocationsRecursively(dev, prefix + path, fp);
        }

        Scheduler.notifyMessage("... finished recursive analisis on ["+dev.getDeviceName()+"] "+".",
                        Constants.INFO_FEEDBACK_COLOR);

        for (int i = 0; i < vec.size(); i++) {
            Scheduler.decressRemainingTechs();
            //Scheduler.notifyMessage("FindFile recursive are not being execute", Constants.INFO_FEEDBACK_COLOR);
        }
    }

    /*
     * Metodo encargado de ejecutar la tecnica. En funcion del tipo llamara a un metodo u otro.
     */
    static void executeTechnique(Device dev, Technique t) throws Exception {
        if (t.getType().equals("find_file")) {
            executeFindFile(dev, t);
        }
    }

    /*
     * Decision de disenyo. Dado que decidi que para agilizar la ejecucion de las tecnicas recursivas
     * era mejor ejecutarlas todos a la vez, es decir, de todas se extrae los nombres de archivos que
     * se buscaran, a continuacion el programa buscara recursivamente en todo el dispositivo, comprobando
     * en cada fichero si se ha encontrado algun nombre buscado.
     *
     * Por lo anterior antes de proceder a ejecutar las tecnicas se analizaran y separaran las que
     * requieran una busqueda recursiva, por lo tanto:
     * 1) Siempre se ejecutaran antes (por ahora) las tecnicas de buscar un fichero concreto
     * 2) Se creara un vector que contendra las busquedas recursivas, que se ira rellenando
     * segun se acceda a cada objeto del vector pasado por parametro.
     * 3) Una vez leido todo el vector "vec" se ejecutaran las busquedas recursivas que se han separado.
     */
    static void executeTechniques(Device dev, Vector<Technique> vec) throws Exception {
        if (vec.isEmpty()) {
            return;
        }

        Vector<Technique> recursiveFindFile = new Vector<Technique>();

        for (Technique t : vec) {
            if (t.getType().equals("find_file")) {
                if (t.getParam("recursively").getValue().equals("true")) {
                    recursiveFindFile.add(t);
                    continue;
                }
            }

            executeTechnique(dev, t);
        }

        if (!recursiveFindFile.isEmpty()/* && Constants.ENABLE_RECURSIVE_SEARCH_FILE*/) {
            executeFindFileRecurse(dev, recursiveFindFile);
        }
    }

    public static void stop() {
        Executor.stoped = true;
        Explorer.stop();
    }

    public static void init() {
        Executor.stoped = false;
        Explorer.init();
    }
}
