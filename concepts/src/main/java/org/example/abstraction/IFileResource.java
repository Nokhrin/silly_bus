package org.example.abstraction;

import java.io.IOException;

public interface IFileResource extends AutoCloseable {
    void close() throws IOException;
}
