package com.findmybus.spotter.Spotter;

import java.io.Serializable;

/**
 * Created by sushantkumar on 18/4/16.
 */
public class SpotterBean implements Serializable{
    private String route;
    private String upStop;
    private String downStop;

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public String getUpStop() {
        return upStop;
    }

    public void setUpStop(String upStop) {
        this.upStop = upStop;
    }

    public String getDownStop() {
        return downStop;
    }

    public void setDownStop(String downStop) {
        this.downStop = downStop;
    }
}
