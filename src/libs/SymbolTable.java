package libs;

import java.util.HashMap;
import java.util.Observer;

public class SymbolTable {
    private HashMap<String, Object> map;
    private static SymbolTable instance;

    private SymbolTable() {
        map = new HashMap<>();
    }

    public static SymbolTable getInstance() {
        if (instance == null) {
            instance = new SymbolTable();
        }
        return instance;
    }

    public HashMap<String, Object> getTable() {
        return map;
    }

    public void put(String s, Object o) {
        map.put(s, o);
    }

    public Object get(String s) {
        return map.get(s);
    }

    public void remove(String s) {
        map.remove(s);
    }
}
