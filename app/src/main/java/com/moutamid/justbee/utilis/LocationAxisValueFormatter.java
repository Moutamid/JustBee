package com.moutamid.justbee.utilis;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.util.List;

public class LocationAxisValueFormatter extends ValueFormatter implements IAxisValueFormatter {
    private final List<String> locations;

    public LocationAxisValueFormatter(List<String> locations) {
        this.locations = locations;
    }

    @Override
    public String getAxisLabel(float value, AxisBase axis) {
        return super.getAxisLabel(value, axis);
    }

    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        int index = (int) value;
        if (index >= 0 && index < locations.size()) {
            return locations.get(index);
        }
        return "";
    }
}