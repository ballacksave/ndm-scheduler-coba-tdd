package com.tosigav.nwtw.cron;

import com.tosigav.nwtw.service.CalcTrendService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

/**
 * @author ronny susetyo  <ronny at susetyo.com>
 * @since 09 Nov 2016
 */
@RunWith(MockitoJUnitRunner.class)
public class RunTaskTrendingTest {
    private RunTaskTrending runTaskTrending = new RunTaskTrending();

    @Mock
    private CalcTrendService calcTrendService;

    @Before
    public void before() {
        runTaskTrending.setCalcTrendService(calcTrendService);
    }

    @Test
    public void should_normal_when_checkTrending() {
        //given

        //when
        runTaskTrending.doTaskTrending();

        //then
        verify(calcTrendService).findByEnabledAndIsIdle();
        verify(calcTrendService).calculate();
    }

    @Test
    public void should_notCalcTrending_when_findCalcException() {
        //given
        doThrow(Exception.class).when(calcTrendService).findByEnabledAndIsIdle();

        //when
        runTaskTrending.doTaskTrending();

        //then
        verify(calcTrendService).findByEnabledAndIsIdle();
        verify(calcTrendService, never()).calculate();
    }

    @Test
    public void should_notCalcTrending_when_calcTrendingException() {
        //given
        doThrow(Exception.class).when(calcTrendService).calculate();

        //when
        runTaskTrending.doTaskTrending();

        //then
        verify(calcTrendService).findByEnabledAndIsIdle();
        verify(calcTrendService).calculate();
    }

}