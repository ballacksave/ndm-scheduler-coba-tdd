package com.tosigav.nwtw.service.impl;

import com.tosigav.nwtw.component.CalcTrendAlgorithm;
import com.tosigav.nwtw.dao.CalcTrendDao;
import com.tosigav.nwtw.dao.RowDataDao;
import com.tosigav.nwtw.domain.CalcTrend;
import com.tosigav.nwtw.domain.CalcTrendPK;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;

import static com.tosigav.nwtw.component.CalcTrendAlgorithm.POSITIF;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

/**
 * @author ronny susetyo  <ronny at susetyo.com>
 * @since 09 Nov 2016
 */
@RunWith(MockitoJUnitRunner.class)
public class CalcTrendServiceImplTest {
    private CalcTrendServiceImpl calcTrendServiceImpl = new CalcTrendServiceImpl();

    @Mock
    private CalcTrendDao calcTrendDao;

    @Mock
    private RowDataDao rowDataDao;

    @Mock
    private CalcTrendAlgorithm calcTrendAlgorithm;

    @Before
    public void before() {
        calcTrendServiceImpl.setCalcTrendDao(calcTrendDao);
        calcTrendServiceImpl.setRowDataDao(rowDataDao);
        calcTrendServiceImpl.setCalcTrendAlgorithm(calcTrendAlgorithm);
    }

    @Test
    public void should_validSizeCalcTrending_when_findByIsIdleAndIsEnable() {
        //given
        given(calcTrendDao.findByEnableAndIsIdle(1, 1)).willReturn(getDummyCalcTrends());

        //when
        calcTrendServiceImpl.findByEnabledAndIsIdle();

        //then
        assertThat(calcTrendServiceImpl.getCalcTrends().size(), is(2));
    }

    @Test
    public void should_setIsIdle_to_0_when_start() {
        //given
        calcTrendServiceImpl.setCalcTrends(getDummyCalcTrends());

        //when
        calcTrendServiceImpl.calculate();

        //then
        verify(calcTrendDao, times(14)).save(any(CalcTrend.class));
    }

    @Test
    public void should_justUpdateTdAndTv_when_cobaTdd() {
        //given
        calcTrendServiceImpl.setCalcTrends(getJustOneDummyCalcTrends());

        //when
        calcTrendServiceImpl.calculateCobaTdd();

        //then
        verify(rowDataDao, never()).findDataByChunkSizeAndLastUpdateTs(null);
        verify(rowDataDao, never()).findDataByChunkSizeAndLastUpdateTw(null);
        verify(rowDataDao).findDataByChunkSizeAndLastUpdateTd(null);
        verify(rowDataDao).findDataByChunkSizeAndLastUpdateTv(null);
        verify(rowDataDao, never()).findDataByChunkSizeAndLastUpdateTc(null);

    }

    @Test
    public void should_findDataByChunkSizeAndLastUpdate_when_enableCalcOnTable_isEnabled() {
        //given
        calcTrendServiceImpl.setCalcTrends(getJustOneDummyCalcTrends());

        //when
        calcTrendServiceImpl.calculate();

        //then
        verify(rowDataDao).findDataByChunkSizeAndLastUpdateTs(null);
        verify(rowDataDao).findDataByChunkSizeAndLastUpdateTw(null);
        verify(rowDataDao).findDataByChunkSizeAndLastUpdateTd(null);
        verify(rowDataDao).findDataByChunkSizeAndLastUpdateTv(null);
        verify(rowDataDao).findDataByChunkSizeAndLastUpdateTc(null);
    }

    @Test
    public void should_neverFindDataByChunkSizeAndLastUpdate_when_enableCalcOnTable_eq_zero() {
        //given
        ArrayList<CalcTrend> dummyCalcTrends = getJustOneDummyCalcTrends();
        dummyCalcTrends.get(0).setEnableCalcTs(0);
        dummyCalcTrends.get(0).setEnableCalcTw(0);
        dummyCalcTrends.get(0).setEnableCalcTd(0);
        dummyCalcTrends.get(0).setEnableCalcTv(0);
        dummyCalcTrends.get(0).setEnableCalcTc(0);
        calcTrendServiceImpl.setCalcTrends(dummyCalcTrends);

        //when
        calcTrendServiceImpl.calculate();

        //then
        verify(rowDataDao, never()).findDataByChunkSizeAndLastUpdateTs(null);
        verify(rowDataDao, never()).findDataByChunkSizeAndLastUpdateTw(null);
        verify(rowDataDao, never()).findDataByChunkSizeAndLastUpdateTd(null);
        verify(rowDataDao, never()).findDataByChunkSizeAndLastUpdateTv(null);
        verify(rowDataDao, never()).findDataByChunkSizeAndLastUpdateTc(null);
    }



    @Test
    public void should_onlyFindByRange_when_range_greaterThan_0() {
        //given
        ArrayList<CalcTrend> dummyCalcTrends = getJustOneDummyCalcTrends();
        dummyCalcTrends.get(0).setRangeSecond1(0);
        dummyCalcTrends.get(0).setRangeSecond2(0);
        dummyCalcTrends.get(0).setRangeSecond3(0);
        dummyCalcTrends.get(0).setRangeDepth1(0);
        dummyCalcTrends.get(0).setRangeDepth2(0);
        dummyCalcTrends.get(0).setRangeDepth3(0);
        dummyCalcTrends.get(0).setRangeCounter1(0);
        dummyCalcTrends.get(0).setRangeCounter2(0);
        dummyCalcTrends.get(0).setRangeCounter3(0);

        given(rowDataDao.findDataByChunkSizeAndLastUpdateTs(anyString())).willReturn(Arrays.asList("2016-01-02 00:00:01.0", "2016-01-02 00:00:02.0"));
        given(rowDataDao.findDataByChunkSizeAndLastUpdateTw(anyString())).willReturn(Arrays.asList("2016-01-02 00:00:01.0", "2016-01-02 00:00:02.0"));
        given(rowDataDao.findDataByChunkSizeAndLastUpdateTd(anyDouble())).willReturn(Arrays.asList(1D, 2D));
        given(rowDataDao.findDataByChunkSizeAndLastUpdateTv(anyDouble())).willReturn(Arrays.asList(1D, 2D));
        given(rowDataDao.findDataByChunkSizeAndLastUpdateTc(anyInt())).willReturn(Arrays.asList(1, 2));

        calcTrendServiceImpl.setCalcTrends(dummyCalcTrends);

        //when
        calcTrendServiceImpl.calculate();

        //then
        verify(rowDataDao, never()).findByRangeTs(null, 0);
        verify(rowDataDao, never()).findByRangeTs(null, 0);
        verify(rowDataDao, never()).findByRangeTs(null, 0);

        verify(rowDataDao, never()).findByRangeTw(null, 0);
        verify(rowDataDao, never()).findByRangeTw(null, 0);
        verify(rowDataDao, never()).findByRangeTw(null, 0);

        verify(rowDataDao, never()).findByRangeTd(null, 0);
        verify(rowDataDao, never()).findByRangeTd(null, 0);
        verify(rowDataDao, never()).findByRangeTd(null, 0);

        verify(rowDataDao, never()).findByRangeTv(null, 0);
        verify(rowDataDao, never()).findByRangeTv(null, 0);
        verify(rowDataDao, never()).findByRangeTv(null, 0);

        verify(rowDataDao, never()).findByRangeTc(null, 0);
        verify(rowDataDao, never()).findByRangeTc(null, 0);
        verify(rowDataDao, never()).findByRangeTc(null, 0);
    }

    @Test
    public void should_calcTrend2Times_when_resultLastUpdateIs2RowData() {
        //given
        calcTrendServiceImpl.setCalcTrends(getJustOneDummyCalcTrends());
        given(rowDataDao.findDataByChunkSizeAndLastUpdateTs(anyString())).willReturn(Arrays.asList("2016-01-02 00:00:01.0", "2016-01-02 00:00:02.0"));
        given(rowDataDao.findDataByChunkSizeAndLastUpdateTw(anyString())).willReturn(Arrays.asList("2016-01-02 00:00:01.0", "2016-01-02 00:00:02.0"));
        given(rowDataDao.findDataByChunkSizeAndLastUpdateTd(anyDouble())).willReturn(Arrays.asList(1D, 2D));
        given(rowDataDao.findDataByChunkSizeAndLastUpdateTv(anyDouble())).willReturn(Arrays.asList(1D, 2D));
        given(rowDataDao.findDataByChunkSizeAndLastUpdateTc(anyInt())).willReturn(Arrays.asList(1, 2));

        //when
        calcTrendServiceImpl.calculate();

        //then
        verify(rowDataDao).findByRangeTs("2016-01-02 00:00:01.0", 300);
        verify(rowDataDao).findByRangeTs("2016-01-02 00:00:01.0", 900);
        verify(rowDataDao).findByRangeTs("2016-01-02 00:00:01.0", 1800);
        verify(rowDataDao).findByRangeTs("2016-01-02 00:00:02.0", 300);
        verify(rowDataDao).findByRangeTs("2016-01-02 00:00:02.0", 900);
        verify(rowDataDao).findByRangeTs("2016-01-02 00:00:02.0", 1800);

        verify(rowDataDao).findByRangeTw("2016-01-02 00:00:01.0", 300);
        verify(rowDataDao).findByRangeTw("2016-01-02 00:00:01.0", 900);
        verify(rowDataDao).findByRangeTw("2016-01-02 00:00:01.0", 1800);
        verify(rowDataDao).findByRangeTw("2016-01-02 00:00:02.0", 300);
        verify(rowDataDao).findByRangeTw("2016-01-02 00:00:02.0", 900);
        verify(rowDataDao).findByRangeTw("2016-01-02 00:00:02.0", 1800);

        verify(rowDataDao).findByRangeTd(1D, 10);
        verify(rowDataDao).findByRangeTd(1D, 25);
        verify(rowDataDao).findByRangeTd(1D, 50);
        verify(rowDataDao).findByRangeTd(2D, 10);
        verify(rowDataDao).findByRangeTd(2D, 25);
        verify(rowDataDao).findByRangeTd(2D, 50);

        verify(rowDataDao).findByRangeTv(1D, 10);
        verify(rowDataDao).findByRangeTv(1D, 25);
        verify(rowDataDao).findByRangeTv(1D, 50);
        verify(rowDataDao).findByRangeTv(2D, 10);
        verify(rowDataDao).findByRangeTv(2D, 25);
        verify(rowDataDao).findByRangeTv(2D, 50);

        verify(rowDataDao).findByRangeTc(1, 10);
        verify(rowDataDao).findByRangeTc(1, 50);
        verify(rowDataDao).findByRangeTc(1, 100);
        verify(rowDataDao).findByRangeTc(2, 10);
        verify(rowDataDao).findByRangeTc(2, 50);
        verify(rowDataDao).findByRangeTc(2, 100);
    }

    @Test
    public void should_calcTrending_when_data_count_greaterThan_one() {
        //given
        calcTrendServiceImpl.setCalcTrends(getJustOneDummyCalcTrends());
        given(rowDataDao.findDataByChunkSizeAndLastUpdateTs(anyString())).willReturn(Arrays.asList("2016-01-02 00:00:01.0", "2016-01-02 00:00:02.0"));
        given(rowDataDao.findDataByChunkSizeAndLastUpdateTw(anyString())).willReturn(Arrays.asList("2016-01-02 00:00:01.0", "2016-01-02 00:00:02.0"));
        given(rowDataDao.findDataByChunkSizeAndLastUpdateTd(anyDouble())).willReturn(Arrays.asList(1D, 2D));
        given(rowDataDao.findDataByChunkSizeAndLastUpdateTv(anyDouble())).willReturn(Arrays.asList(1D, 2D));
        given(rowDataDao.findDataByChunkSizeAndLastUpdateTc(anyInt())).willReturn(Arrays.asList(1, 2));

        given(rowDataDao.findByRangeTs(anyString(), anyInt())).willReturn(Arrays.asList(1D, 2D));
        given(rowDataDao.findByRangeTw(anyString(), anyInt())).willReturn(Arrays.asList(1D, 2D));
        given(rowDataDao.findByRangeTd(anyDouble(), anyInt())).willReturn(Arrays.asList(1D, 2D));
        given(rowDataDao.findByRangeTv(anyDouble(), anyInt())).willReturn(Arrays.asList(1D, 2D));
        given(rowDataDao.findByRangeTc(anyInt(), anyInt())).willReturn(Arrays.asList(1D, 2D));

        //when
        calcTrendServiceImpl.calculate();

        //then
        verify(calcTrendAlgorithm, times(30)).getTrend(anyListOf(Double.class));
    }

    @Test
    public void should_updateRowDataTrending_when_doneCalcTrend_1_2_3() {
        //given
        calcTrendServiceImpl.setCalcTrends(getJustOneDummyCalcTrends());
        given(rowDataDao.findDataByChunkSizeAndLastUpdateTs(anyString())).willReturn(Arrays.asList("2016-01-02 00:00:01.0", "2016-01-02 00:00:02.0"));
        given(rowDataDao.findDataByChunkSizeAndLastUpdateTw(anyString())).willReturn(Arrays.asList("2016-01-02 00:00:01.0", "2016-01-02 00:00:02.0"));
        given(rowDataDao.findDataByChunkSizeAndLastUpdateTd(anyDouble())).willReturn(Arrays.asList(1D, 2D));
        given(rowDataDao.findDataByChunkSizeAndLastUpdateTv(anyDouble())).willReturn(Arrays.asList(1D, 2D));
        given(rowDataDao.findDataByChunkSizeAndLastUpdateTc(anyInt())).willReturn(Arrays.asList(1, 2));

        given(rowDataDao.findByRangeTs(anyString(), anyInt())).willReturn(Arrays.asList(1D, 2D));
        given(rowDataDao.findByRangeTw(anyString(), anyInt())).willReturn(Arrays.asList(1D, 2D));
        given(rowDataDao.findByRangeTd(anyDouble(), anyInt())).willReturn(Arrays.asList(1D, 2D));
        given(rowDataDao.findByRangeTv(anyDouble(), anyInt())).willReturn(Arrays.asList(1D, 2D));
        given(rowDataDao.findByRangeTc(anyInt(), anyInt())).willReturn(Arrays.asList(1D, 2D));

        given(calcTrendAlgorithm.getTrend(anyList())).willReturn(POSITIF);

        //when
        calcTrendServiceImpl.calculate();

        //then
        verify(rowDataDao).updateTrendTs("2016-01-02 00:00:01.0", POSITIF, POSITIF, POSITIF);
        verify(rowDataDao).updateTrendTs("2016-01-02 00:00:02.0", POSITIF, POSITIF, POSITIF);

        verify(rowDataDao).updateTrendTw("2016-01-02 00:00:01.0", POSITIF, POSITIF, POSITIF);
        verify(rowDataDao).updateTrendTw("2016-01-02 00:00:02.0", POSITIF, POSITIF, POSITIF);

        verify(rowDataDao).updateTrendTd(1D, POSITIF, POSITIF, POSITIF);
        verify(rowDataDao).updateTrendTd(2D, POSITIF, POSITIF, POSITIF);

        verify(rowDataDao).updateTrendTv(1D, POSITIF, POSITIF, POSITIF);
        verify(rowDataDao).updateTrendTv(2D, POSITIF, POSITIF, POSITIF);

        verify(rowDataDao).updateTrendTc(1, POSITIF, POSITIF, POSITIF);
        verify(rowDataDao).updateTrendTc(2, POSITIF, POSITIF, POSITIF);
    }

    @Test
    public void should_updateCalcTrending_when_afterUpdateRowDataTrending() {
        //given
        calcTrendServiceImpl.setCalcTrends(getJustOneDummyCalcTrends());
        given(rowDataDao.findDataByChunkSizeAndLastUpdateTs(anyString())).willReturn(Arrays.asList("2016-01-02 00:00:01.0", "2016-01-02 00:00:02.0"));
        given(rowDataDao.findDataByChunkSizeAndLastUpdateTw(anyString())).willReturn(Arrays.asList("2016-01-02 00:00:01.0", "2016-01-02 00:00:02.0"));
        given(rowDataDao.findDataByChunkSizeAndLastUpdateTd(anyDouble())).willReturn(Arrays.asList(1D, 2D));
        given(rowDataDao.findDataByChunkSizeAndLastUpdateTv(anyDouble())).willReturn(Arrays.asList(1D, 2D));
        given(rowDataDao.findDataByChunkSizeAndLastUpdateTc(anyInt())).willReturn(Arrays.asList(1, 2));

        given(rowDataDao.findByRangeTs(anyString(), anyInt())).willReturn(Arrays.asList(1D, 2D));
        given(rowDataDao.findByRangeTw(anyString(), anyInt())).willReturn(Arrays.asList(1D, 2D));
        given(rowDataDao.findByRangeTd(anyDouble(), anyInt())).willReturn(Arrays.asList(1D, 2D));
        given(rowDataDao.findByRangeTv(anyDouble(), anyInt())).willReturn(Arrays.asList(1D, 2D));
        given(rowDataDao.findByRangeTc(anyInt(), anyInt())).willReturn(Arrays.asList(1D, 2D));

        given(calcTrendAlgorithm.getTrend(anyList())).willReturn(POSITIF);

        //when
        calcTrendServiceImpl.calculate();

        //then
        InOrder inOrder = inOrder(rowDataDao, calcTrendDao);

        inOrder.verify(rowDataDao).updateTrendTs("2016-01-02 00:00:01.0", POSITIF, POSITIF, POSITIF);
        inOrder.verify(calcTrendDao).save(any(CalcTrend.class));
        inOrder.verify(rowDataDao).updateTrendTs("2016-01-02 00:00:02.0", POSITIF, POSITIF, POSITIF);
        inOrder.verify(calcTrendDao, times(2)).save(any(CalcTrend.class));

        inOrder.verify(rowDataDao).updateTrendTw("2016-01-02 00:00:01.0", POSITIF, POSITIF, POSITIF);
        inOrder.verify(calcTrendDao).save(any(CalcTrend.class));
        inOrder.verify(rowDataDao).updateTrendTw("2016-01-02 00:00:02.0", POSITIF, POSITIF, POSITIF);
        inOrder.verify(calcTrendDao, times(2)).save(any(CalcTrend.class));

        inOrder.verify(rowDataDao).updateTrendTd(1D, POSITIF, POSITIF, POSITIF);
        inOrder.verify(calcTrendDao).save(any(CalcTrend.class));
        inOrder.verify(rowDataDao).updateTrendTd(2D, POSITIF, POSITIF, POSITIF);
        inOrder.verify(calcTrendDao, times(2)).save(any(CalcTrend.class));

        inOrder.verify(rowDataDao).updateTrendTv(1D, POSITIF, POSITIF, POSITIF);
        inOrder.verify(calcTrendDao).save(any(CalcTrend.class));
        inOrder.verify(rowDataDao).updateTrendTv(2D, POSITIF, POSITIF, POSITIF);
        inOrder.verify(calcTrendDao, times(2)).save(any(CalcTrend.class));

        inOrder.verify(rowDataDao).updateTrendTc(1, POSITIF, POSITIF, POSITIF);
        inOrder.verify(calcTrendDao).save(any(CalcTrend.class));
        inOrder.verify(rowDataDao).updateTrendTc(2, POSITIF, POSITIF, POSITIF);
        inOrder.verify(calcTrendDao, times(3)).save(any(CalcTrend.class));

    }

    private ArrayList<CalcTrend> getDummyCalcTrends() {
        ArrayList<CalcTrend> calcTrends = new ArrayList<CalcTrend>();
        calcTrends.add(getDummyCalcTrend1());
        calcTrends.add(getDummyCalcTrend2());
        return calcTrends;
    }

    private ArrayList<CalcTrend> getJustOneDummyCalcTrends() {
        ArrayList<CalcTrend> calcTrends = new ArrayList<CalcTrend>();
        calcTrends.add(getDummyCalcTrend1());
        return calcTrends;
    }

    private CalcTrend getDummyCalcTrend1() {
        CalcTrend calcTrend = new CalcTrend();
        calcTrend.setTCalcTrendPK(new CalcTrendPK("01", 1, "0114"));
        calcTrend.setEnable(1);
        calcTrend.setRangeSecond1(300);
        calcTrend.setRangeSecond2(900);
        calcTrend.setRangeSecond3(1800);
        calcTrend.setRangeDepth1(10);
        calcTrend.setRangeDepth2(25);
        calcTrend.setRangeDepth3(50);
        calcTrend.setRangeCounter1(10);
        calcTrend.setRangeCounter2(50);
        calcTrend.setRangeCounter3(100);
        calcTrend.setEnableCalcTs(1);
        calcTrend.setEnableCalcTw(1);
        calcTrend.setEnableCalcTd(1);
        calcTrend.setEnableCalcTv(1);
        calcTrend.setEnableCalcTc(1);
        calcTrend.setMsg(null);
        calcTrend.setLastUpdateTs(null);
        calcTrend.setLastUpdateTw(null);
        calcTrend.setLastUpdateTd(null);
        calcTrend.setLastUpdateTv(null);
        calcTrend.setLastUpdateTc(null);
        calcTrend.setLastVisitTs(null);
        calcTrend.setLastVisitTw(null);
        calcTrend.setLastVisitTd(null);
        calcTrend.setLastVisitTv(null);
        calcTrend.setLastVisitTc(null);
        calcTrend.setWellTableName("ndm182");
        calcTrend.setIsIdle(1);
        calcTrend.setChunkSize(100);
        return calcTrend;
    }

    private CalcTrend getDummyCalcTrend2() {
        CalcTrend calcTrend = new CalcTrend();
        calcTrend.setTCalcTrendPK(new CalcTrendPK("01", 1, "0126"));
        calcTrend.setEnable(1);
        calcTrend.setRangeSecond1(300);
        calcTrend.setRangeSecond2(900);
        calcTrend.setRangeSecond3(1800);
        calcTrend.setRangeDepth1(10);
        calcTrend.setRangeDepth2(25);
        calcTrend.setRangeDepth3(50);
        calcTrend.setRangeCounter1(10);
        calcTrend.setRangeCounter2(50);
        calcTrend.setRangeCounter3(100);
        calcTrend.setEnableCalcTs(1);
        calcTrend.setEnableCalcTw(1);
        calcTrend.setEnableCalcTd(1);
        calcTrend.setEnableCalcTv(1);
        calcTrend.setEnableCalcTc(1);
        calcTrend.setMsg(null);
        calcTrend.setLastUpdateTs(null);
        calcTrend.setLastUpdateTw(null);
        calcTrend.setLastUpdateTd(null);
        calcTrend.setLastUpdateTv(null);
        calcTrend.setLastUpdateTc(null);
        calcTrend.setLastVisitTs(null);
        calcTrend.setLastVisitTw(null);
        calcTrend.setLastVisitTd(null);
        calcTrend.setLastVisitTv(null);
        calcTrend.setLastVisitTc(null);
        calcTrend.setWellTableName("ndm182");
        calcTrend.setIsIdle(1);
        calcTrend.setChunkSize(100);
        return calcTrend;
    }

}