package by.training.certificationCenter.controller.filter;

import by.training.certificationCenter.bean.Role;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class SecurityConfig {
    private static final Map<Role, List<String>> mapConfig = new ConcurrentHashMap<>();
    static {
        init();
    }
    private static void init() {
        List<String> clientUrlPatterns = new ArrayList<>();
        clientUrlPatterns.add("/applications");
        clientUrlPatterns.add("/apply");
        mapConfig.put(Role.CLIENT, clientUrlPatterns);
        List<String> expertUrlPatterns = new ArrayList<>();
        expertUrlPatterns.add("/applications");
        mapConfig.put(Role.EXPERT, expertUrlPatterns);
    }

    public static Set<Role> getAllAppRoles() {
        return mapConfig.keySet();
    }

    public static List<String> getUrlPatternsForRole(Role role) {
        return mapConfig.get(role);
    }
}
