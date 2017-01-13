package com.tosigav.nwtw.dao;

import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ronny susetyo  <ronny at susetyo.com>
 * @since 10 Nov 2016
 */
public interface RowDataDao {
    void setRec(String rec);

    void setChannel(String channel);

    void setWellTablename(String wellTablename);

    void setChunkSize(int chunkSize);

    List<Double> findByRangeTs(String lastTs, int range);

    List<Double> findByRangeTw(String lastTw, int range);

    List<Double> findByRangeTd(Double lastTd, int range);

    List<Double> findByRangeTv(Double lastTv, int range);

    List<Double> findByRangeTc(Integer lastTc, int range);

    List<String> findDataByChunkSizeAndLastUpdateTs(String lastTs);

    List<String> findDataByChunkSizeAndLastUpdateTw(String lastTw);

    List<Double> findDataByChunkSizeAndLastUpdateTd(Double lastTd);

    List<Double> findDataByChunkSizeAndLastUpdateTv(Double lastTv);

    List<Integer> findDataByChunkSizeAndLastUpdateTc(Integer lastTc);

    void updateTrendTs(String lastTs, Integer trend1, Integer trend2, Integer trend3);

    void updateTrendTw(String lastTw, Integer trend1, Integer trend2, Integer trend3);

    void updateTrendTd(Double lastTd, Integer trend1, Integer trend2, Integer trend3);

    void updateTrendTv(Double lastTv, Integer trend1, Integer trend2, Integer trend3);

    void updateTrendTc(Integer lastTc, Integer trend1, Integer trend2, Integer trend3);
}
