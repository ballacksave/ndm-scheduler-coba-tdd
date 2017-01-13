package com.tosigav.nwtw.component;

import java.util.List;

/**
 * @author ronny susetyo  <ronny at susetyo.com>
 * @since 10 Nov 2016
 */
public interface CalcTrendAlgorithm {
    public static int POSITIF = 1;
    public static int NEGATIF = -1;
    public static int DATAR = 0;

    int getTrend(List<Double> data);
}
