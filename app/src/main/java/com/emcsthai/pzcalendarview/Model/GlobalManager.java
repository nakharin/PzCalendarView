package com.emcsthai.pzcalendarview.Model;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by nakarin on 2/28/2017 AD.
 */

public class GlobalManager {

    private static GlobalManager instance = null;

    private GlobalManager() {
    }

    public static GlobalManager getInstance() {
        if (instance == null) {
            instance = new GlobalManager();
        }
        return instance;
    }

    public ArrayList<Calendar> calendarArrayList = new ArrayList<>();
}
