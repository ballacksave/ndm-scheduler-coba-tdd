package com.tosigav.nwtw.component;

import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author ronny susetyo  <ronny at susetyo.com>
 * @since 10 Nov 2016
 */
@Component
public class SemiRataRataGenap implements CalcTrendAlgorithm {

    @Override
    public int getTrend(List<Double> data) {
        int trend;

        int count = data.size();
        boolean isGanjil = count % 2 > 0;
        int midle = count / 2;

        Double k1 = getK1(data, isGanjil, midle);
        Double k2 = getK2(data, isGanjil, midle, count);

        if (k2.equals(k1)) {
            trend = DATAR;
        } else if ((k2 - k1) > 0) {
            trend = POSITIF;
        } else {
            trend = NEGATIF;
        }

        return trend;
    }

    private Double getK2(List<Double> data, boolean isGanjil, int midle, int count) {
        Double sum2 = new Double(0);
        int startIndex = isGanjil ? midle + 1 : midle;
        for (int i = startIndex; i < count; i++) {
            sum2 += data.get(i);
        }
        return sum2 / (midle);
    }

    private Double getK1(List<Double> data, boolean isGanjil, int midle) {
        Double sum1 = new Double(0);
        int startIndex = isGanjil ? 1 : 0;
        int endIndex = isGanjil ? midle + 1 : midle;
        for (int i = startIndex; i < endIndex; i++) {
            sum1 += data.get(i);
        }
        return sum1 / (midle);
    }
}
