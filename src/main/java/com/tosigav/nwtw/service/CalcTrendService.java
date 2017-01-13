package com.tosigav.nwtw.service;

/**
 * @author ronny susetyo  <ronny at susetyo.com>
 * @since 09 Nov 2016
 */
public interface CalcTrendService {
    void findByEnabledAndIsIdle();

    void calculate();

    void calculateCobaTdd();
}
