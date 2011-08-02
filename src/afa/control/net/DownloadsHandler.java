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

import javax.swing.JTextArea;

/**
 *
 * @author Alvaro Duran Tovar
 */
public class DownloadsHandler {

    public static final int BINARY = 0x1;
    public static final int TEXT = 0x2;
    private static int currentThreads = 0;

    private static synchronized void incrementThreads() {
        currentThreads++;
    }

    private static synchronized void decrementThreads() {
        currentThreads--;
    }

    public static synchronized int getCurrentDownloads() {
        return currentThreads;
    }

    public static void downloadFile(String src, String dest, int type, JTextArea text) throws Exception {
        Thread t = null;
        final String f_src = src;
        final String f_dest = dest;
        final JTextArea f_text = text;
        final int f_type = type;

        if (type == BINARY || type == TEXT) {
            t = new Thread() {

                @Override
                public void run() {
                    try {
                        incrementThreads();
                        if (f_text != null) {
                            f_text.putClientProperty("DOWNLOAD_FINISHED", System.currentTimeMillis());
                        }

                        if (f_type == BINARY) {
                            DownloadFile.getBinaryFile(f_src, f_dest);
                        }
                        if (f_type == TEXT) {
                            DownloadFile.getTextFile(f_src, f_dest);
                        }
                        decrementThreads();
                        if (f_text != null) {
                            f_text.append("[OK] Descarga de " + f_src + " terminada.\n");
                            f_text.putClientProperty("DOWNLOAD_FINISHED", System.currentTimeMillis());
                        }

                    } catch (Exception ex) {
                        decrementThreads();
                        if (f_text != null) {
                            f_text.append("[ERROR] " + ex.getMessage() + "\n");
                            f_text.putClientProperty("DOWNLOAD_FINISHED", System.currentTimeMillis());
                        }
                    }
                }
            };
        } else {
            throw new Exception("[DownloadsHandler] Incorrect download file type");
        }
        t.start();
    }
}
