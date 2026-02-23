package console.screens;

import java.io.BufferedReader;

public interface IScreen<T> {
    final BufferedReader br = null;

    public void show();

    public T askInput();
}
