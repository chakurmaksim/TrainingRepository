package by.training.certificationCenter.controller.filter;

import by.training.certificationCenter.bean.Role;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class SecurityConfig {
    /**
     * Map contains user roles that have access rights to certain resources
     * and url patterns of these resources.
     */
    private static final Map<Role, List<String>>
            mapConfig = new ConcurrentHashMap<>();

    static {
        init();
    }

    private static void init() {
        List<String> clientUrlPatterns = new ArrayList<>();
        clientUrlPatterns.add("/applications");
        clientUrlPatterns.add("/applyFor");
        clientUrlPatterns.add(("/deleteApplication"));
        clientUrlPatterns.add(("/editApplication"));
        clientUrlPatterns.add(("/showApplication"));
        mapConfig.put(Role.CLIENT, clientUrlPatterns);
        List<String> expertUrlPatterns = new ArrayList<>();
        expertUrlPatterns.add("/applications");
        expertUrlPatterns.add("/showApplication");
        expertUrlPatterns.add("/registerApplication");
        mapConfig.put(Role.EXPERT, expertUrlPatterns);
    }

    /**
     * Method returns user roles from the map.
     *
     * @return Roles that the map contains
     */
    public static Set<Role> getAllAppRoles() {
        return mapConfig.keySet();
    }

    /**
     * Method returns url patterns from the map.
     *
     * @param role user Role
     * @return url patterns of the resources the user has access to
     */
    public static List<String> getUrlPatternsForRole(Role role) {
        return mapConfig.get(role);
    }
}
