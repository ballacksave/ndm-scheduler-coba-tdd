/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tosigav.nwtw.cron;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 *
 * @author Ronny Susetyo

 * Description :
 */
public class RunJobTrending extends QuartzJobBean implements StatefulJob {
    private RunTaskTrending runTaskTrending;

    public void setRunTaskTrending(RunTaskTrending runTaskTrending) {
        this.runTaskTrending = runTaskTrending;
    }

    @Override
    protected void executeInternal(JobExecutionContext jec) throws JobExecutionException {
        runTaskTrending.doTaskTrending();
    }
    
}
