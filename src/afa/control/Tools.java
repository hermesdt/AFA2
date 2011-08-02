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

/**
 *
 * @author: Alvaro Duran Tovar
 */
package afa.control;

import afa.model.Category;
import afa.model.Plugin;
import com.twmacinta.util.MD5;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;
import java.util.Vector;

public class Tools {

    public static String getSerialVolume(String unidad) throws Exception {
        if (System.getProperty("os.name").toLowerCase().contains("windows")) {
            return getSerialVolumeWindows(unidad);
        } else if (System.getProperty("os.name").toLowerCase().contains("linux")) {
            return getSerialVolumeLinux(unidad);
        } else {
            throw new Exception("[Tools.getSerialVolume] Not impleneted yet [MAC option].");
        }
    }

    /**
     * Devuelve el numero de serie del disco pasado por parametro.
     * @param unidad Unidad de disco de la que se quiere averiguar el numero de serie
     * @return Numero de serie del disco
     */
    private static String getSerialVolumeWindows(String unidad) {
        String Serial = "";
        try {
            String linea;

            Process p = Runtime.getRuntime().exec("vol " + unidad.subSequence(0, unidad.length() - 1));
            BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
            while ((linea = input.readLine()) != null) {
                linea = linea.toLowerCase();
                if (linea.contains("serial") || linea.contains("serie")) {
                    Serial = linea;
                }
            }
            input.close();
        } catch (Exception err) {
            err.printStackTrace();
        }
        return Serial;
    }

    /**
     * Devuelve el numero de serie del disco pasado por parametro en Linux
     * @param unidad Unidad de disco de la que se quiere averiguar el numero de serie
     * @return Numero de serie del disco
     */
    private static String getSerialVolumeLinux(String device) throws IOException, InterruptedException, Exception {
        device = new File(device).getCanonicalPath();

        File list[] = new File("/dev/disk/by-uuid/").listFiles();

        for (File f : list) {
            if (f.getCanonicalPath().equals(device)) {
                return f.getName();
            }
        }

        return null;
    }

    public static String getMountPointByDevice(String device) throws Exception {
        if (System.getProperty("os.name").toLowerCase().contains("linux")) {
            return getMountPointByDeviceLinux(device);
        }else if (System.getProperty("os.name").toLowerCase().contains("mac")) {
            return getMountPointByDeviceMac(device);
        } else {
            throw new Exception("[Tools.obtenerUnidades] Not impleneted yet [MAC option].");
        }

    }
    
    public static String getMountPointByDeviceLinux(String device) throws Exception {
        
        Vector<String> devices = new Vector<String>();
        Scanner scan = new Scanner(new File("/etc/mtab"));
        while (scan.hasNextLine()) {
            String l = scan.nextLine();
            if (l.trim().startsWith(device)) {
                return l.split(" ")[1];
            }
        }

        return null;
    }
    
    
    public static String getMountPointByDeviceMac(String device) throws Exception {
        
        Vector<String> devices = new Vector<String>();
        BufferedReader br = new BufferedReader(new InputStreamReader(Runtime.getRuntime().exec("mount").getInputStream()));
        
        String l = null;
        while((l = br.readLine()) != null){
            if (l.trim().startsWith(device)) {
                return l.split(" ")[2];
            }
        }
        
        return null;
    }

    /**
     * Metodo que permite obtener el conjuntod e unidades que tiene montados en linux
     * @return Vector con el conjunto de unidades montadas en el sistema operativo linux
     */
    private static String[] obtenerUnidadesLinux() throws FileNotFoundException {

        Vector<String> devices = new Vector<String>();
        Scanner scan = new Scanner(new File("/etc/mtab"));
        while (scan.hasNextLine()) {
            String l = scan.nextLine();
            if (l.trim().startsWith("/")) {
                devices.add(l.split(" ")[0]);
            }
        }

        return devices.toArray(new String[]{});
    }
    
    /**
     * Metodo que permite obtener el conjuntod e unidades que tiene montados en linux
     * @return Vector con el conjunto de unidades montadas en el sistema operativo linux
     */
    private static String[] obtenerUnidadesMac() throws Exception {

        Vector<String> devices = new Vector<String>();
        BufferedReader br = new BufferedReader(new InputStreamReader(Runtime.getRuntime().exec("mount").getInputStream()));
        
        String l = null;
        while((l = br.readLine()) != null){
            if (l.trim().startsWith("/")) {
                devices.add(l.split(" ")[0]);
            }
        }
        
        return devices.toArray(new String[]{});
    }

    public static String[] obtenerUnidades() throws Exception {
        if (System.getProperty("os.name").toLowerCase().contains("windows")) {
            return obtenerUnidadesWindows();
        } else if (System.getProperty("os.name").toLowerCase().contains("linux")) {
            return obtenerUnidadesLinux();
        }else if (System.getProperty("os.name").toLowerCase().contains("mac")) {
            return obtenerUnidadesMac();
        } else {
            throw new Exception("[Tools.obtenerUnidades] Not impleneted yet [MAC option].");
        }
    }
    
    

    /*
     * Using fast-md5 lib
     */
    public static String getMD5(String filename) throws IOException {

        String hash = MD5.asHex(MD5.getHash(new File(filename)));

        return hash;
    }

    public static void main(String arg[]) throws IOException {
        if (arg.length != 1) {
            return;
        }

        System.out.println(getMD5(arg[0]));
    }

    /**
     * Calcula el SHA1 del fichero indicado.
     *
     */
    public static String getSHA1(String f) throws NoSuchAlgorithmException, Exception {


        MessageDigest md = MessageDigest.getInstance("SHA1");
        Scanner scan = new Scanner(new File(f));
        StringBuffer buff = new StringBuffer();
        while (scan.hasNextLine()) {
            buff.append(scan.nextLine() + "\n");
        }

        md.update(buff.toString().getBytes());
        byte hash[] = md.digest();

        String s = "";

        //convert to string
        for (byte aux : hash) {
            int b = aux & 0xff;
            if (Integer.toHexString(b).length() == 1) {
                s += "0";
            }
            s += Integer.toHexString(b);
        }

        return s;
    }

    /**
     * Metodo que permite obtener el conjuntod e unidades que tiene montados en linux
     * @return Vector con el conjunto de unidades montadas en el sistema operativo linux
     */
    private static String[] obtenerUnidadesWindows() {
        String[] unidades;
        File[] raices;
        raices = File.listRoots();
        unidades = new String[raices.length];
        for (int i = 0; i < raices.length; i++) {
            unidades[i] = raices[i].toString();
        }
        return unidades;
    }

    public static void createCategory(String cat) {
        File f = new File(Constants.getDirectorioPlugins() + File.separator + cat);
        f.mkdirs();
    }

    public static void copyfile(String srFile, String dtFile) {
        try {
            File f1 = new File(srFile);
            File f2 = new File(dtFile);
            InputStream in = new FileInputStream(f1);

            //For Overwrite the file.
            OutputStream out = new FileOutputStream(f2);

            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            in.close();
            out.close();
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage() + " in the specified directory.");
            //System.exit(0);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }


    public static Vector<Plugin> categoryVecToPluginVec(Vector<Category> v) {
        Vector<Plugin> vec = new Vector<Plugin>();
        for (Category c : v) {
            vec.addAll(c.getPlugins());
        }

        return vec;
    }

}
