package yq.test.handler.hooks;

import java.util.HashMap;
import java.util.Map;

public class InfuseVar {
    private static Map<VarScope, Map<String, Object>> varStore ;

    static {
        varStore = new HashMap<>();
        varStore.put(VarScope.GLOBAL, new HashMap<>());
        varStore.put(VarScope.FILE, new HashMap<>());
        varStore.put(VarScope.SUITE, new HashMap<>());
    }

    public static Map<String, Object> get(VarScope scope){
        return varStore.get(scope);
    }

    public static void set(VarScope scope, String key, Object value) {
        varStore.get(scope).put(key, value);
    }
}
