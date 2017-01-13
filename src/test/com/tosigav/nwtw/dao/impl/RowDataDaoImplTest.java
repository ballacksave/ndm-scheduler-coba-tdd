package com.tosigav.nwtw.dao.impl;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * @author ronny susetyo  <ronny at susetyo.com>
 * @since 10 Nov 2016
 */
public class RowDataDaoImplTest {
    private RowDataDaoImpl rowDataDaoImpl;

    @Before
    public void before() {
        rowDataDaoImpl = new RowDataDaoImpl();
        rowDataDaoImpl.setRec("01");
        rowDataDaoImpl.setChannel("0114");
        rowDataDaoImpl.setWellTablename("ndm182");
        rowDataDaoImpl.setChunkSize(100);
    }

    @Test
    public void should_valid_sqlFindByRangeTs_useMaxTs_when_lastTsNotNull() {
        //when
        String expected = rowDataDaoImpl.getSqlRangeTs("2016-01-02 00:00:01.0", 100);

        //then
        assertThat(expected, is("SELECT `0114` FROM ts_ndm182_01 WHERE NOT ISNULL(`0114`) AND `0114` != '-999.25' AND ts BETWEEN DATE_SUB('2016-01-02 00:00:01.0', INTERVAL 100 SECOND) AND '2016-01-02 00:00:01.0';"));
    }

    @Test
    public void should_valid_sqlFindByRangeTs_useMaxTs_when_lastTsIsNull() {
        //when
        String expected = rowDataDaoImpl.getSqlRangeTs(null, 100);

        //then
        assertThat(expected, is("SELECT NULL;"));
    }

    @Test
    public void should_valid_sqlFindByRangeTw_useMaxTw_when_lastTsNotNull() {
        //when
        String expected = rowDataDaoImpl.getSqlRangeTw("2016-01-02 00:00:01.0", 100);

        //then
        assertThat(expected, is("SELECT `0114` FROM tw_ndm182_01 WHERE NOT ISNULL(`0114`) AND `0114` != '-999.25' AND tw BETWEEN DATE_SUB('2016-01-02 00:00:01.0', INTERVAL 100 SECOND) AND '2016-01-02 00:00:01.0';"));
    }

    @Test
    public void should_valid_sqlFindByRangeTw_useMaxTw_when_lastTsIsNull() {
        //when
        String expected = rowDataDaoImpl.getSqlRangeTw(null, 100);

        //then
        assertThat(expected, is("SELECT NULL;"));
    }

    @Test
    public void should_valid_sqlFindByRangeTd_useMaxTd_when_lastTsNotNull() {
        //when
        String expected = rowDataDaoImpl.getSqlRangeTd(8000D, 100000);

        //then
        assertThat(expected, is("SELECT `0114` FROM td_ndm182_01 WHERE NOT ISNULL(`0114`) AND `0114` != '-999.25' AND md BETWEEN 8000.0 - 100000 AND 8000.0;"));
    }

    @Test
    public void should_valid_sqlFindByRangeTd_useMaxTd_when_lastTsIsNull() {
        //when
        String expected = rowDataDaoImpl.getSqlRangeTd(null, 100000);

        //then
        assertThat(expected, is("SELECT NULL;"));
    }

    @Test
    public void should_valid_sqlFindByRangeTv_useMaxTv_when_lastTsNotNull() {
        //when
        String expected = rowDataDaoImpl.getSqlRangeTv(8000D, 100000);

        //then
        assertThat(expected, is("SELECT `0114` FROM tv_ndm182_01 WHERE NOT ISNULL(`0114`) AND `0114` != '-999.25' AND tv BETWEEN 8000.0 - 100000 AND 8000.0;"));
    }

    @Test
    public void should_valid_sqlFindByRangeTv_useMaxTv_when_lastTsIsNull() {
        //when
        String expected = rowDataDaoImpl.getSqlRangeTv(null, 100000);

        //then
        assertThat(expected, is("SELECT NULL;"));
    }

    @Test
    public void should_valid_sqlFindByRangeTc_useMaxTc_when_lastTsNotNull() {
        //when
        String expected = rowDataDaoImpl.getSqlRangeTc(1002, 200);

        //then
        assertThat(expected, is("SELECT `0114` FROM tc_ndm182_01 WHERE NOT ISNULL(`0114`) AND `0114` != '-999.25' AND tc BETWEEN IF(1002-200 < 1, 0, 1002-200) AND 1002;"));
    }

    @Test
    public void should_valid_sqlFindByRangeTc_useMaxTc_when_lastTsIsNull() {
        //when
        String expected = rowDataDaoImpl.getSqlRangeTc(null, 200);

        //then
        assertThat(expected, is("SELECT NULL;"));
    }

    @Test
    public void should_valid_sqlfindDataByChunkSizeAndLastUpdateTs_when_lastTsNotNull() {
        //given
        rowDataDaoImpl.setChunkSize(50);
        //when
        String expected = rowDataDaoImpl.getSqlChunkSizeAndLastUpdateTs("2016-01-02 00:00:01.0");

        //then
        assertThat(expected, is("SELECT `ts` FROM ts_ndm182_01 WHERE ts > '2016-01-02 00:00:01.0' LIMIT 50;"));
    }

    @Test
    public void should_valid_sqlfindDataByChunkSizeAndLastUpdateTs_when_lastTsIsNull() {
        //given
        rowDataDaoImpl.setChunkSize(50);
        //when
        String expected = rowDataDaoImpl.getSqlChunkSizeAndLastUpdateTs(null);

        //then
        assertThat(expected, is("SELECT `ts` FROM ts_ndm182_01 WHERE `ts` <> '0000-00-00 00:00:00' ORDER BY ts LIMIT 1;"));
    }

    @Test
    public void should_valid_sqlfindDataByChunkSizeAndLastUpdateTw_when_lastTwNotNull() {
        //given
        rowDataDaoImpl.setChunkSize(50);
        //when
        String expected = rowDataDaoImpl.getSqlChunkSizeAndLastUpdateTw("2016-01-02 00:00:01.0");

        //then
        assertThat(expected, is("SELECT `tw` FROM tw_ndm182_01 WHERE tw > '2016-01-02 00:00:01.0' LIMIT 50;"));
    }

    @Test
    public void should_valid_sqlfindDataByChunkSizeAndLastUpdateTw_when_lastTwIsNull() {
        //given
        rowDataDaoImpl.setChunkSize(50);
        //when
        String expected = rowDataDaoImpl.getSqlChunkSizeAndLastUpdateTw(null);

        //then
        assertThat(expected, is("SELECT `tw` FROM tw_ndm182_01 WHERE `tw` <> '0000-00-00 00:00:00' ORDER BY tw LIMIT 1;"));
    }

    @Test
    public void should_valid_sqlfindDataByChunkSizeAndLastUpdateTd_when_lastTdNotNull() {
        //given
        rowDataDaoImpl.setChunkSize(50);
        //when
        String expected = rowDataDaoImpl.getSqlChunkSizeAndLastUpdateTd(8000D);

        //then
        assertThat(expected, is("SELECT `md` FROM td_ndm182_01 WHERE md > 8000.0 LIMIT 50;"));
    }

    @Test
    public void should_valid_sqlfindDataByChunkSizeAndLastUpdateTd_when_lastTdIsNull() {
        //given
        rowDataDaoImpl.setChunkSize(50);
        //when
        String expected = rowDataDaoImpl.getSqlChunkSizeAndLastUpdateTd(null);

        //then
        assertThat(expected, is("SELECT `md` FROM td_ndm182_01 ORDER BY md LIMIT 1;"));
    }

    @Test
    public void should_valid_sqlfindDataByChunkSizeAndLastUpdateTv_when_lastTvNotNull() {
        //given
        rowDataDaoImpl.setChunkSize(50);
        //when
        String expected = rowDataDaoImpl.getSqlChunkSizeAndLastUpdateTv(8000D);

        //then
        assertThat(expected, is("SELECT `tv` FROM tv_ndm182_01 WHERE tv > 8000.0 LIMIT 50;"));
    }

    @Test
    public void should_valid_sqlfindDataByChunkSizeAndLastUpdateTv_when_lastTvIsNull() {
        //given
        rowDataDaoImpl.setChunkSize(50);
        //when
        String expected = rowDataDaoImpl.getSqlChunkSizeAndLastUpdateTv(null);

        //then
        assertThat(expected, is("SELECT `tv` FROM tv_ndm182_01 ORDER BY tv LIMIT 1;"));
    }

    @Test
    public void should_valid_sqlfindDataByChunkSizeAndLastUpdateTc_when_lastTcNotNull() {
        //given
        rowDataDaoImpl.setChunkSize(50);
        //when
        String expected = rowDataDaoImpl.getSqlChunkSizeAndLastUpdateTc(8000);

        //then
        assertThat(expected, is("SELECT `tc` FROM tc_ndm182_01 WHERE tc > 8000 LIMIT 50;"));
    }

    @Test
    public void should_valid_sqlfindDataByChunkSizeAndLastUpdateTc_when_lastTcIsNull() {
        //given
        rowDataDaoImpl.setChunkSize(50);
        //when
        String expected = rowDataDaoImpl.getSqlChunkSizeAndLastUpdateTc(null);

        //then
        assertThat(expected, is("SELECT `tc` FROM tc_ndm182_01 ORDER BY tc LIMIT 1;"));
    }

    @Test
    public void should_valid_sqlUpdateTrendTs_when_lastTcNotNull() {
        //given
        //when
        String expected = rowDataDaoImpl.getSqlUpdateTrendTs("2016-01-21 07:57:00.0", 1, -1, 0);

        //then
        assertThat(expected, is("UPDATE ts_ndm182_01 SET `0114_1`='1', `0114_2`='-1', `0114_3`='0' WHERE ts = '2016-01-21 07:57:00.0';"));
    }

    @Test
    public void should_valid_sqlUpdateTrendTs_when_lastTcIsNull() {
        //given
        //when
        String expected = rowDataDaoImpl.getSqlUpdateTrendTs(null, null, null, null);

        //then
        assertThat(expected, is("UPDATE ts_ndm182_01 SET `0114_1`=NULL, `0114_2`=NULL, `0114_3`=NULL WHERE ts = NULL;"));
    }

    @Test
    public void should_valid_sqlUpdateTrendTw_when_lastTcNotNull() {
        //given
        //when
        String expected = rowDataDaoImpl.getSqlUpdateTrendTw("2016-01-21 07:57:00.0", 1, -1, 0);

        //then
        assertThat(expected, is("UPDATE tw_ndm182_01 SET `0114_1`='1', `0114_2`='-1', `0114_3`='0' WHERE tw = '2016-01-21 07:57:00.0';"));
    }

    @Test
    public void should_valid_sqlUpdateTrendTw_when_lastTcIsNull() {
        //given
        //when
        String expected = rowDataDaoImpl.getSqlUpdateTrendTw(null, null, null, null);

        //then
        assertThat(expected, is("UPDATE tw_ndm182_01 SET `0114_1`=NULL, `0114_2`=NULL, `0114_3`=NULL WHERE tw = NULL;"));
    }

    @Test
    public void should_valid_sqlUpdateTrendTd_when_lastTcNotNull() {
        //given
        //when
        String expected = rowDataDaoImpl.getSqlUpdateTrendTd(8000D, 1, -1, 0);

        //then
        assertThat(expected, is("UPDATE td_ndm182_01 SET `0114_1`='1', `0114_2`='-1', `0114_3`='0' WHERE md = '8000.0';"));
    }

    @Test
    public void should_valid_sqlUpdateTrendTd_when_lastTcIsNull() {
        //given
        //when
        String expected = rowDataDaoImpl.getSqlUpdateTrendTd(null, null, null, null);

        //then
        assertThat(expected, is("UPDATE td_ndm182_01 SET `0114_1`=NULL, `0114_2`=NULL, `0114_3`=NULL WHERE md = NULL;"));
    }

    @Test
    public void should_valid_sqlUpdateTrendTv_when_lastTcNotNull() {
        //given
        //when
        String expected = rowDataDaoImpl.getSqlUpdateTrendTv(8000D, 1, -1, 0);

        //then
        assertThat(expected, is("UPDATE tv_ndm182_01 SET `0114_1`='1', `0114_2`='-1', `0114_3`='0' WHERE tv = '8000.0';"));
    }

    @Test
    public void should_valid_sqlUpdateTrendTv_when_lastTcIsNull() {
        //given
        //when
        String expected = rowDataDaoImpl.getSqlUpdateTrendTv(null, null, null, null);

        //then
        assertThat(expected, is("UPDATE tv_ndm182_01 SET `0114_1`=NULL, `0114_2`=NULL, `0114_3`=NULL WHERE tv = NULL;"));
    }

    @Test
    public void should_valid_sqlUpdateTrendTc_when_lastTcNotNull() {
        //given
        //when
        String expected = rowDataDaoImpl.getSqlUpdateTrendTc(8000, 1, -1, 0);

        //then
        assertThat(expected, is("UPDATE tc_ndm182_01 SET `0114_1`='1', `0114_2`='-1', `0114_3`='0' WHERE tc = '8000';"));
    }

    @Test
    public void should_valid_sqlUpdateTrendTc_when_lastTcIsNull() {
        //given
        //when
        String expected = rowDataDaoImpl.getSqlUpdateTrendTc(null, null, null, null);

        //then
        assertThat(expected, is("UPDATE tc_ndm182_01 SET `0114_1`=NULL, `0114_2`=NULL, `0114_3`=NULL WHERE tc = NULL;"));
    }
}