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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;

/**
 *
 * @author Alvaro Duran Tovar
 */
class IndexWriter {

    private String file;
    private long offset = 0;
    private long parent_offset = 0;
    private long last_offset = 0;
    private long empty = 0;
    private RandomAccessFile raf;

    IndexWriter(String file) {
        this.file = file;
    }

    public void open() throws FileNotFoundException, UnsupportedEncodingException, IOException {
        raf = new RandomAccessFile(file, "rw");
        raf.setLength(0); //trancate the file
    }

    public void close() throws IOException, Exception {
        raf.close();
    }

    public static void main(String arg[]) throws FileNotFoundException, Exception {

        IndexWriter w = new IndexWriter("/home/Alvaro Duran Tovar/prueba_index.txt");
        w.open();
        //w.addRootDir(null);

        w.addChildren(new File[]{new File("/etc")});
        File files[] = new File[]{new File("/etc/rc.1"), new File("/etc/rc.2"), new File("/etc/rc.3"),
            new File("/etc/rc.4"), new File("/etc/rc.5"), new File("/etc/rc.6"), new File("/etc/rc.d")};

        w.addChildren(files);

        w.close();
    }

    private void addChild(File file) throws Exception {

        last_offset = offset;
        offset = (int) raf.getFilePointer();

        raf.writeByte((file.isDirectory()) ? 1 : 0);//is directory
        raf.writeShort(file.getName().length());//name length
        raf.writeChars(file.getName());//name
        raf.writeInt((int) parent_offset);//parent
        raf.writeInt(0);//next brother
        raf.writeInt((int) last_offset);//previous
        
        if(file.isDirectory())
            raf.writeInt(0);//first child

        empty = raf.getFilePointer();
    }

    /**
     *
     * @param file - Not used by now
     */
    /*public void addRootDir(File file) throws IOException{
        last_offset = offset;
        offset = (int) raf.getFilePointer();

        //raf.writeByte(1);//is directory
        //raf.writeShort(0);//name length
        //raf.writeChars(file.getName());//name
        //raf.writeInt(0);//parent
        //raf.writeInt(0);//next brother
        //raf.writeInt(0);//previous brother
        raf.writeInt((int) raf.getFilePointer()+4);//first child

        parent_offset = 0;
        empty = raf.getFilePointer();
    }*/

    /**
     *
     * @param offset - Offset of the registry to be updated
     */
    private void updateNextBrother(long offset, long new_offset) throws Exception{
        long current_offset = raf.getFilePointer();
        raf.seek(offset);

        raf.readUnsignedByte();//is directory
        int aux = raf.readUnsignedShort();//name length
        while(aux > 0){
            raf.readChar();
            aux--;
        }//read name
        raf.readInt();//read parent
        raf.writeInt((int) new_offset);//update brother

        raf.seek(current_offset);
    }

    /**
     *
     * @param offset - Offset of the registry to be updated
     */
    private void updateFirstChild(long offset, long new_offset) throws Exception{
        if(offset == 0 && new_offset == 0) return;
        long current_offset = raf.getFilePointer();
        raf.seek(offset);

        int isDir = raf.readUnsignedByte();//is directory
        if(isDir == 0)//[NOTE] should throw exception?????
            return;
        int aux = raf.readUnsignedShort();//name length
        while(aux > 0){
            raf.readChar();
            aux--;
        }//read name
        raf.readInt();//read parent
        raf.readInt();//read next brother
        raf.readInt();//read previous brother
        raf.writeInt((int) new_offset);//update first child

        raf.seek(current_offset);
    }

    public void addChildren(File[] files) throws Exception {
        updateFirstChild(last_offset, raf.getFilePointer());
        boolean flag = false;
        long aux = 0, last_aux = 0;
        for(File f : files){
            last_aux = aux;
            aux = raf.getFilePointer();
            addChild(f);
            if(flag){
                updateNextBrother(last_aux, offset);
            }else
                flag = true;
        }
    }

    public void setOffset(int offset) throws IOException {
        this.offset = offset;
        raf.seek(offset);
    }

    public long getOffset() {
        return offset;
    }
}
