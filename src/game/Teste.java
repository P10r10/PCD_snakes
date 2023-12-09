package game;

import java.io.Serializable;

public class Teste implements Serializable {
    private int n = 10;
    private String nome = "Alex";

    @Override
    public String toString() {
        return nome;
    }
}
