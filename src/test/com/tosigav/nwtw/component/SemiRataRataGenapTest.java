package com.tosigav.nwtw.component;

import org.junit.Test;

import java.util.ArrayList;

import static com.tosigav.nwtw.component.CalcTrendAlgorithm.*;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * @author ronny susetyo  <ronny at susetyo.com>
 * @since 10 Nov 2016
 */
public class SemiRataRataGenapTest {
    @Test
    public void should_validTrend_when_checkByListDouble() {
        assertThat(tren(), is(DATAR));
        assertThat(tren(1), is(DATAR));
        assertThat(tren(1, 2), is(POSITIF));
        assertThat(tren(2, 1), is(NEGATIF));
        assertThat(tren(2, 1, 2, 1, 2, 2), is(DATAR));
        assertThat(tren(2, 3, 4, 5, 5, 1), is(POSITIF));
        assertThat(tren(2, 3, 2, 2, 5, 6), is(POSITIF));
        assertThat(tren(3, 3, 4, 3, 3, 2), is(NEGATIF));
        assertThat(tren(4, 5, 3, 2, 3, 4), is(NEGATIF));
    }

    private int tren(int... data) {
        ArrayList<Double> doubles = new ArrayList<Double>();
        for (int d : data) {
            doubles.add(new Double(d));
        }
        return new SemiRataRataGenap().getTrend(doubles);
    }

}