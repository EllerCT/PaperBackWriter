import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;

public interface IOSystem {
    void setLocation(URI location);
    void write(OutputStream out);
    InputStream read();
}
