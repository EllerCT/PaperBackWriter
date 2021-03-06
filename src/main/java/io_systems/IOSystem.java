package io_systems;

import java.io.InputStream;
import java.net.URI;

public interface IOSystem {
    void setLocation(URI location);

    void write(byte[] out);

    InputStream read();
}
