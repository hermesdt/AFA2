/*
 *  Copyright (C) 2011 alvaro
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

import eu.medsea.mimeutil.MimeUtil;
import eu.medsea.mimeutil.detector.MimeDetector;
import java.util.Collection;


/**
 *
 * @author alvaro
 */
public class MimeType {
    public static String getMimeType(String file){
        MimeUtil.registerMimeDetector("eu.medsea.mimeutil.detector.MagicMimeMimeDetector");
        
        /*MimeDetector md = new eu.medsea.mimeutil.detector.OpendesktopMimeDetector("/usr/share/mime/mime.cache");
        System.out.println(md.getMimeTypes(file));*/
        eu.medsea.mimeutil.MimeType m = MimeUtil.getMostSpecificMimeType(MimeUtil.getMimeTypes(file));

        MimeUtil.unregisterMimeDetector("eu.medsea.mimeutil.detector.MagicMimeMimeDetector");
        return m.toString();
    }
}
