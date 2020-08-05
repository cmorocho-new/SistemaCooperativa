package ec.edu.ups.bean;

import java.io.Serializable;

public class RowCounter implements Serializable {

    private transient int row = 0;

    public int getRow() {
        return ++row;
    }

}