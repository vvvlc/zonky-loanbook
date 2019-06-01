package com.github.robozonky.loanbook.charts;

import java.util.ArrayList;
import java.util.List;

import io.vavr.Tuple;
import io.vavr.Tuple2;

public class XYChart extends Chart {

    private final String labelForX;
    private final String labelForY;
    private final List<Tuple2<String, Number>> data = new ArrayList<>(0);


    public XYChart(final ChartType type, final String title, final String labelForX, final String labelForY) {
        super(type, title);
        this.labelForX = labelForX;
        this.labelForY = labelForY;
    }

    public String getLabelForX() {
        return labelForX;
    }

    public String getLabelForY() {
        return labelForY;
    }

    public List<Tuple2<String, Number>> getData() {
        return data;
    }

    public void add(final String x, final Number y) {
        data.add(Tuple.of(x, y));
    }

}