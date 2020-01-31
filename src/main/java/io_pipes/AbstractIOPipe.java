package io_pipes;

import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.util.Map;

//TODO: Generify IOPipe load/saves.
public abstract class AbstractIOPipe implements IOPipe {
    @Override
    public Map load() {

        return null;
    }

    @Override
    public void save(Map map) {

    }

    protected abstract Object translateRecord(CSVRecord record, Object into);

    protected void makeNewFile() throws IOException {

    }
}
