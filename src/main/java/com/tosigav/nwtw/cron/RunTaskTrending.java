/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tosigav.nwtw.cron;

import com.tosigav.nwtw.service.CalcTrendService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;


public class RunTaskTrending {
    private static final Logger logger = LoggerFactory.getLogger(RunTaskTrending.class);

    @Autowired
    CalcTrendService calcTrendService;

    public void doTaskTrending() {
        logger.debug("{}", "start calculate trending");
        try {
            calcTrendService.findByEnabledAndIsIdle();
            calcTrendService.calculate();
        } catch (Exception e) {
            logger.error("error on calculate trending", e);
        }
        logger.debug("{}", "end calculate trending\n");
    }

    public void setCalcTrendService(CalcTrendService calcTrendService) {
        this.calcTrendService = calcTrendService;
    }
}
