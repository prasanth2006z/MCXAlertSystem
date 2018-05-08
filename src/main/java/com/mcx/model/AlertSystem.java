package com.mcx.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by prasanth.p on 06/05/18.
 */
public class AlertSystem {
    private Map<Integer, User> userMap;

    private Map<String, String> loginMap;

    public AlertSystem() {
        userMap = new HashMap<>();
        loginMap = new HashMap<>();
    }

    public Map<Integer, User> getUserMap() {
        return userMap;
    }

    public void setUserMap(Map<Integer, User> userMap) {
        this.userMap = userMap;
    }

    public Map<String, String> getLoginMap() {
        return loginMap;
    }

    public void setLoginMap(Map<String, String> loginMap) {
        this.loginMap = loginMap;
    }
}
