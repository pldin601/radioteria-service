package com.radioteria.util.io;

import java.io.IOException;

public interface IOConsumer<T> {
    void accept(T t) throws IOException;
}
