package io_systems;

import java.io.*;
import java.net.URI;

public class LocalFileIOSystem implements IOSystem{
    private URI location;

    public void setLocation(URI location) {
        this.location = location;
    }

    public void write(byte[] out) {
        try {
            InputStream stream = new ByteArrayInputStream(out);
            File file = new File(location);
            if (!file.exists()) file.createNewFile();
            FileOutputStream outputStream = new FileOutputStream(file);
            outputStream.write(stream.read());
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public InputStream read() {
        try {
            File inputFile = new File(location);
            return new FileInputStream(inputFile);
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
