package figo.companion;

/**
 * Created by figo on 1/11/17.
 */

public class ItemModel{
    private Integer n;
    private String  name;

    ItemModel(Integer n, String name){
        this.n = n;
        this.name = name;
    }

    public Integer getN() {
        return n;
    }

    public String getName() {
        return name;
    }

    public void setN(Integer n) {
        this.n = n;
    }

    public void setName(String name) {
        this.name = name;
    }
}

