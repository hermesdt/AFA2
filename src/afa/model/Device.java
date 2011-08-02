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


package afa.model;

import afa.control.Tools;
import java.io.File;

/**
 *
 * @author Alvaro Duran Tovar
 */
public class Device {

    //identifier == uuid (unix) or serialnumber (windows)
    private File deviceName, mountPoint;
            private String identifier;
    private boolean selected;

    public Device(File deviceName, File mountPoint, String identifier, boolean selected){
        this.deviceName = deviceName;
        this.mountPoint = mountPoint;
        this.selected = selected;
        this.identifier = identifier;
    }

    public Device() {
    }

    public File getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(File deviceName){
        this.deviceName = deviceName;
        //identifier = Tools.getSerialVolume(deviceName);
    }

    public String getIdentifier(){
        return identifier;
    }

    public void setIdentifier(String identifier){
        this.identifier = identifier;
    }

    public File getMountPoint() {
        return mountPoint;
    }

    public void setMountPoint(File mountPoint) {
        this.mountPoint = mountPoint;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
