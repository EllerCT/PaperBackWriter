package io_pipes;

import java.util.Map;

public interface IOPipe {
    Map load();

    void save(Map map);
}
