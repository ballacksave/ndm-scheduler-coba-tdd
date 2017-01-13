package com.tosigav.nwtw.service.impl;

import com.tosigav.nwtw.component.CalcTrendAlgorithm;
import com.tosigav.nwtw.dao.CalcTrendDao;
import com.tosigav.nwtw.dao.RowDataDao;
import com.tosigav.nwtw.domain.CalcTrend;
import com.tosigav.nwtw.service.CalcTrendService;
import com.tosigav.nwtw.utils.CommonConstant;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ronny susetyo  <ronny at susetyo.com>
 * @since 09 Nov 2016
 */
@Service
public class CalcTrendServiceImpl implements CalcTrendService {
    private static final Logger logger = LoggerFactory.getLogger(CalcTrendServiceImpl.class);

    @Autowired
    private CalcTrendDao calcTrendDao;

    @Autowired
    private RowDataDao rowDataDao;

    @Autowired
    private CalcTrendAlgorithm calcTrendAlgorithm;

    private List<CalcTrend> calcTrends;
    private CalcTrend calcTrend;

    @Override
    public void findByEnabledAndIsIdle() {
        calcTrends = calcTrendDao.findByEnableAndIsIdle(1, 1);
    }

    @Override
    public void calculate() {
        updateAllStatusToNotIdle();
        calculateTrends();
    }

    @Override
    public void calculateCobaTdd() {
        for (CalcTrend c : calcTrends) {
            initCalculate(c);
            calculateTrendsTd();
            calculateTrendsTv();
        }
    }

    public void updateAllStatusToNotIdle() {
        logger.trace("{}", String.format("updateAllStatusToNotIdle"));
        updateAllStatusIdle(0);
    }

    private void updateAllStatusIdle(int isIdle) {
        for (CalcTrend c : calcTrends) {
            c.setIsIdle(isIdle);
            calcTrendDao.save(c);
        }
    }

    private void calculateTrends() {
        logger.debug("{}", String.format("calcTrends count=%d", calcTrends.size()));
        for (CalcTrend c : calcTrends) {
            calculateTrend(c);
        }
    }

    private void calculateTrend(CalcTrend c) {
        logger.debug("{}", String.format("calcTrends idwell=%s, rec=%s, channel=%s", c.getTCalcTrendPK().getIdWell(), c.getTCalcTrendPK().getId(), c.getTCalcTrendPK().getChannel()));
        try {
            initCalculate(c);
            calculateTrendsTs();
            calculateTrendsTw();
            calculateTrendsTd();
            calculateTrendsTv();
            calculateTrendsTc();
            updateStatusToIdle();
        } catch (Exception e) {
            updateErrorMsgAndUpdateToIdle(e);
        }
    }

    private void updateErrorMsgAndUpdateToIdle(Exception e) {
        logger.error(e.getMessage());
        CalcTrend calcTrendUpdateError = calcTrendDao.findOne(this.calcTrend.getTCalcTrendPK());
        calcTrendUpdateError.setMsg(ExceptionUtils.getStackTrace(e));
        calcTrendUpdateError.setIsIdle(1);
        calcTrendDao.save(calcTrendUpdateError);
    }

    private void updateStatusToIdle() {
        calcTrend.setIsIdle(1);
        calcTrendDao.save(calcTrend);
    }

    private void initCalculate(CalcTrend c) {
        this.calcTrend = c;
        this.calcTrend.setIsIdle(0);
        rowDataDao.setRec(calcTrend.getTCalcTrendPK().getId());
        rowDataDao.setChannel(calcTrend.getTCalcTrendPK().getChannel());
        rowDataDao.setWellTablename(calcTrend.getWellTableName());
        rowDataDao.setChunkSize(calcTrend.getChunkSize());
    }

    private void calculateTrendsTs() {
        if (isEnableTs()) {
            List<String> listTs = rowDataDao.findDataByChunkSizeAndLastUpdateTs(calcTrend.getLastUpdateTs());
            logger.debug("{}", String.format("[ts] count=%d, lastTs=%s, chunk=%d", listTs.size(), calcTrend.getLastUpdateTs(), calcTrend.getChunkSize()));
            for (String lastTs : listTs) {
                Integer tren1 = calcByRangeTs(lastTs, calcTrend.getRangeSecond1());
                Integer tren2 = calcByRangeTs(lastTs, calcTrend.getRangeSecond2());
                Integer tren3 = calcByRangeTs(lastTs, calcTrend.getRangeSecond3());

                rowDataDao.updateTrendTs(lastTs, tren1, tren2, tren3);
                logger.trace("{}", String.format("%s, trend=[%d, %d, %d]", lastTs, tren1, tren2, tren3));

                calcTrend.setLastUpdateTs(lastTs);
                calcTrend.setMsg(null);
                calcTrend.setLastVisitTs(getDateAsStrDb());
                calcTrendDao.save(calcTrend);
            }
            calcTrend.setLastVisitTs(getDateAsStrDb());
            calcTrendDao.save(calcTrend);
        }
    }

    private String getDateAsStrDb() {
        DateTime dateTime = new DateTime();
        return dateTime.toString(CommonConstant.DATETIMEFORMAT);
    }

    private Integer calcByRangeTs(String lastTs, Integer rangeSecond) {
        Integer trend = null;
        if (rangeSecond > 0) {
            List<Double> data = rowDataDao.findByRangeTs(lastTs, rangeSecond);
            if (data.size() > 1) {
                trend = calcTrendAlgorithm.getTrend(data);
            }
        }
        return trend;
    }

    private boolean isEnableTs() {
        return calcTrend.getEnableCalcTs() == 1;
    }

    private void calculateTrendsTw() {
        if (isEnableTw()) {
            List<String> listTw = rowDataDao.findDataByChunkSizeAndLastUpdateTw(calcTrend.getLastUpdateTw());
            logger.debug("{}", String.format("[tw] count=%d, lastTw=%s, chunk=%d", listTw.size(), calcTrend.getLastUpdateTw(), calcTrend.getChunkSize()));
            for (String lastTw : listTw) {
                Integer tren1 = calcByRangeTw(lastTw, calcTrend.getRangeSecond1());
                Integer tren2 = calcByRangeTw(lastTw, calcTrend.getRangeSecond2());
                Integer tren3 = calcByRangeTw(lastTw, calcTrend.getRangeSecond3());

                rowDataDao.updateTrendTw(lastTw, tren1, tren2, tren3);
                logger.trace("{}", String.format("%s, trend=[%d, %d, %d]", lastTw, tren1, tren2, tren3));

                calcTrend.setLastUpdateTw(lastTw);
                calcTrend.setMsg(null);
                calcTrend.setLastVisitTw(getDateAsStrDb());
                calcTrendDao.save(calcTrend);
            }
            calcTrend.setLastVisitTw(getDateAsStrDb());
            calcTrendDao.save(calcTrend);
        }
    }

    private Integer calcByRangeTw(String lastTw, Integer rangeSecond) {
        Integer trend = null;
        if (rangeSecond > 0) {
            List<Double> data = rowDataDao.findByRangeTw(lastTw, rangeSecond);
            if (data.size() > 1) {
                trend = calcTrendAlgorithm.getTrend(data);
            }
        }
        return trend;
    }

    private boolean isEnableTw() {
        return calcTrend.getEnableCalcTw() == 1;
    }

    private void calculateTrendsTd() {
        if (isEnableTd()) {
            List<Double> listTd = rowDataDao.findDataByChunkSizeAndLastUpdateTd(calcTrend.getLastUpdateTd());
            logger.debug("{}", String.format("[td] count=%d, lastTd=%s, chunk=%d", listTd.size(), calcTrend.getLastUpdateTd(), calcTrend.getChunkSize()));
            for (Double lastTd : listTd) {
                Integer tren1 = calcByRangeTd(lastTd, calcTrend.getRangeDepth1());
                Integer tren2 = calcByRangeTd(lastTd, calcTrend.getRangeDepth2());
                Integer tren3 = calcByRangeTd(lastTd, calcTrend.getRangeDepth3());

                rowDataDao.updateTrendTd(lastTd, tren1, tren2, tren3);
                logger.trace("{}", String.format("%s, trend=[%d, %d, %d]", lastTd, tren1, tren2, tren3));

                calcTrend.setLastUpdateTd(lastTd);
                calcTrend.setMsg(null);
                calcTrend.setLastVisitTd(getDateAsStrDb());
                calcTrendDao.save(calcTrend);
            }
            calcTrend.setLastVisitTd(getDateAsStrDb());
            calcTrendDao.save(calcTrend);
        }
    }

    private Integer calcByRangeTd(Double lastTd, Integer rangeDepth) {
        Integer trend = null;
        if (rangeDepth > 0) {
            List<Double> data = rowDataDao.findByRangeTd(lastTd, rangeDepth);
            if (data.size() > 1) {
                trend = calcTrendAlgorithm.getTrend(data);
            }
        }
        return trend;
    }

    private boolean isEnableTd() {
        return calcTrend.getEnableCalcTd() == 1;
    }

    private void calculateTrendsTv() {
        if (calcTrend.getEnableCalcTv() == 1) {
            List<Double> listTv = rowDataDao.findDataByChunkSizeAndLastUpdateTv(calcTrend.getLastUpdateTv());
            logger.debug("{}", String.format("[tv] count=%d, lastTv=%s, chunk=%d", listTv.size(), calcTrend.getLastUpdateTv(), calcTrend.getChunkSize()));
            for (Double lastTv : listTv) {
                Integer tren1 = calcByRangeTv(lastTv, calcTrend.getRangeDepth1());
                Integer tren2 = calcByRangeTv(lastTv, calcTrend.getRangeDepth2());
                Integer tren3 = calcByRangeTv(lastTv, calcTrend.getRangeDepth3());

                rowDataDao.updateTrendTv(lastTv, tren1, tren2, tren3);
                logger.trace("{}", String.format("%s, trend=[%d, %d, %d]", lastTv, tren1, tren2, tren3));

                calcTrend.setLastUpdateTv(lastTv);
                calcTrend.setMsg(null);
                calcTrend.setLastVisitTv(getDateAsStrDb());
                calcTrendDao.save(calcTrend);
            }
            calcTrend.setLastVisitTv(getDateAsStrDb());
            calcTrendDao.save(calcTrend);
        }
    }

    private Integer calcByRangeTv(Double lastTv, Integer rangeDepth) {
        Integer trend = null;
        if (rangeDepth > 0) {
            List<Double> data = rowDataDao.findByRangeTv(lastTv, rangeDepth);
            if (data.size() > 1) {
                trend = calcTrendAlgorithm.getTrend(data);
            }
        }
        return trend;
    }

    private void calculateTrendsTc() {
        if (calcTrend.getEnableCalcTc() == 1) {
            List<Integer> listTc = rowDataDao.findDataByChunkSizeAndLastUpdateTc(calcTrend.getLastUpdateTc());
            logger.debug("{}", String.format("[tc] count=%d, lastTc=%s, chunk=%d", listTc.size(), calcTrend.getLastUpdateTc(), calcTrend.getChunkSize()));
            for (Integer lastTc : listTc) {
                Integer tren1 = calcByRangeTc(lastTc, calcTrend.getRangeCounter1());
                Integer tren2 = calcByRangeTc(lastTc, calcTrend.getRangeCounter2());
                Integer tren3 = calcByRangeTc(lastTc, calcTrend.getRangeCounter3());

                rowDataDao.updateTrendTc(lastTc, tren1, tren2, tren3);
                logger.trace("{}", String.format("%s, trend=[%d, %d, %d]", lastTc, tren1, tren2, tren3));

                calcTrend.setLastUpdateTc(lastTc);
                calcTrend.setMsg(null);
                calcTrend.setLastVisitTc(getDateAsStrDb());
                calcTrendDao.save(calcTrend);
            }
            calcTrend.setLastVisitTc(getDateAsStrDb());
            calcTrendDao.save(calcTrend);
        }
    }

    private Integer calcByRangeTc(Integer lastTc, Integer rangeCounter) {
        Integer trend = null;
        if (rangeCounter > 0) {
            List<Double> data = rowDataDao.findByRangeTc(lastTc, rangeCounter);
            if (data.size() > 1) {
                trend = calcTrendAlgorithm.getTrend(data);
            }
        }
        return trend;
    }

    public void setCalcTrendDao(CalcTrendDao calcTrendDao) {
        this.calcTrendDao = calcTrendDao;
    }

    public void setRowDataDao(RowDataDao rowDataDao) {
        this.rowDataDao = rowDataDao;
    }

    public void setCalcTrendAlgorithm(CalcTrendAlgorithm calcTrendAlgorithm) {
        this.calcTrendAlgorithm = calcTrendAlgorithm;
    }

    public List<CalcTrend> getCalcTrends() {
        return calcTrends;
    }

    public void setCalcTrends(List<CalcTrend> calcTrends) {
        this.calcTrends = calcTrends;
    }
}
