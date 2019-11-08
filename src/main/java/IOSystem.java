import java.io.InputStream;
import java.io.OutputStream;

public interface IOSystem {
    void write(OutputStream out);
    InputStream read();
}
