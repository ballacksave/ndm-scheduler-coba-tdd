/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tosigav.nwtw.domain;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author ballacksave
 */
@Entity
@Table(name = "t_calc_trend")
public class CalcTrend implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected CalcTrendPK calcTrendPK;

    @Column(name = "enable", updatable = false)
    private Integer enable;

    @Column(name = "range_second_1", updatable = false)
    private Integer rangeSecond1;

    @Column(name = "range_second_2", updatable = false)
    private Integer rangeSecond2;

    @Column(name = "range_second_3", updatable = false)
    private Integer rangeSecond3;

    @Column(name = "range_depth_1", updatable = false)
    private Integer rangeDepth1;

    @Column(name = "range_depth_2", updatable = false)
    private Integer rangeDepth2;

    @Column(name = "range_depth_3", updatable = false)
    private Integer rangeDepth3;

    @Column(name = "range_counter_1", updatable = false)
    private Integer rangeCounter1;

    @Column(name = "range_counter_2", updatable = false)
    private Integer rangeCounter2;

    @Column(name = "range_counter_3", updatable = false)
    private Integer rangeCounter3;

    @Column(name = "enable_calc_ts", updatable = false)
    private Integer enableCalcTs;

    @Column(name = "enable_calc_tw", updatable = false)
    private Integer enableCalcTw;

    @Column(name = "enable_calc_td", updatable = false)
    private Integer enableCalcTd;

    @Column(name = "enable_calc_tv", updatable = false)
    private Integer enableCalcTv;

    @Column(name = "enable_calc_tc", updatable = false)
    private Integer enableCalcTc;

    @Lob
    @Column(name = "msg")
    private String msg;

    @Column(name = "last_update_ts")
//    @Temporal(TemporalType.TIMESTAMP)
    private String lastUpdateTs;

    @Column(name = "last_update_tw")
    private String lastUpdateTw;

    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "last_update_td")
    private Double lastUpdateTd;

    @Column(name = "last_update_tv")
    private Double lastUpdateTv;

    @Column(name = "last_update_tc")
    private Integer lastUpdateTc;

    @Column(name = "last_visit_ts")
//    @Temporal(TemporalType.TIMESTAMP)
    private String lastVisitTs;

    @Column(name = "last_visit_tw")
//    @Temporal(TemporalType.TIMESTAMP)
    private String lastVisitTw;

    @Column(name = "last_visit_td")
//    @Temporal(TemporalType.TIMESTAMP)
    private String lastVisitTd;

    @Column(name = "last_visit_tv")
//    @Temporal(TemporalType.TIMESTAMP)
    private String lastVisitTv;

    @Column(name = "last_visit_tc")
//    @Temporal(TemporalType.TIMESTAMP)
    private String lastVisitTc;

    @Column(name = "well_table_name", updatable = false)
    private String wellTableName;

    @Column(name = "is_idle")
    private Integer isIdle;

    @Column(name = "chunk_size", updatable = false)
    private Integer chunkSize;

    public CalcTrend() {
    }

    public CalcTrend(CalcTrendPK calcTrendPK) {
        this.calcTrendPK = calcTrendPK;
    }

    public CalcTrend(String id, int idWell, String channel) {
        this.calcTrendPK = new CalcTrendPK(id, idWell, channel);
    }

    public CalcTrendPK getTCalcTrendPK() {
        return calcTrendPK;
    }

    public void setTCalcTrendPK(CalcTrendPK calcTrendPK) {
        this.calcTrendPK = calcTrendPK;
    }

    public Integer getEnable() {
        return enable;
    }

    public void setEnable(Integer enable) {
        this.enable = enable;
    }

    public Integer getRangeSecond1() {
        return rangeSecond1;
    }

    public void setRangeSecond1(Integer rangeSecond1) {
        this.rangeSecond1 = rangeSecond1;
    }

    public Integer getRangeSecond2() {
        return rangeSecond2;
    }

    public void setRangeSecond2(Integer rangeSecond2) {
        this.rangeSecond2 = rangeSecond2;
    }

    public Integer getRangeSecond3() {
        return rangeSecond3;
    }

    public void setRangeSecond3(Integer rangeSecond3) {
        this.rangeSecond3 = rangeSecond3;
    }

    public Integer getRangeDepth1() {
        return rangeDepth1;
    }

    public void setRangeDepth1(Integer rangeDepth1) {
        this.rangeDepth1 = rangeDepth1;
    }

    public Integer getRangeDepth2() {
        return rangeDepth2;
    }

    public void setRangeDepth2(Integer rangeDepth2) {
        this.rangeDepth2 = rangeDepth2;
    }

    public Integer getRangeDepth3() {
        return rangeDepth3;
    }

    public void setRangeDepth3(Integer rangeDepth3) {
        this.rangeDepth3 = rangeDepth3;
    }

    public Integer getRangeCounter1() {
        return rangeCounter1;
    }

    public void setRangeCounter1(Integer rangeCounter1) {
        this.rangeCounter1 = rangeCounter1;
    }

    public Integer getRangeCounter2() {
        return rangeCounter2;
    }

    public void setRangeCounter2(Integer rangeCounter2) {
        this.rangeCounter2 = rangeCounter2;
    }

    public Integer getRangeCounter3() {
        return rangeCounter3;
    }

    public void setRangeCounter3(Integer rangeCounter3) {
        this.rangeCounter3 = rangeCounter3;
    }

    public Integer getEnableCalcTs() {
        return enableCalcTs;
    }

    public void setEnableCalcTs(Integer enableCalcTs) {
        this.enableCalcTs = enableCalcTs;
    }

    public Integer getEnableCalcTw() {
        return enableCalcTw;
    }

    public void setEnableCalcTw(Integer enableCalcTw) {
        this.enableCalcTw = enableCalcTw;
    }

    public Integer getEnableCalcTd() {
        return enableCalcTd;
    }

    public void setEnableCalcTd(Integer enableCalcTd) {
        this.enableCalcTd = enableCalcTd;
    }

    public Integer getEnableCalcTv() {
        return enableCalcTv;
    }

    public void setEnableCalcTv(Integer enableCalcTv) {
        this.enableCalcTv = enableCalcTv;
    }

    public Integer getEnableCalcTc() {
        return enableCalcTc;
    }

    public void setEnableCalcTc(Integer enableCalcTc) {
        this.enableCalcTc = enableCalcTc;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getLastUpdateTs() {
        return lastUpdateTs;
    }

    public void setLastUpdateTs(String lastUpdateTs) {
        this.lastUpdateTs = lastUpdateTs;
    }

    public String getLastUpdateTw() {
        return lastUpdateTw;
    }

    public void setLastUpdateTw(String lastUpdateTw) {
        this.lastUpdateTw = lastUpdateTw;
    }

    public Double getLastUpdateTd() {
        return lastUpdateTd;
    }

    public void setLastUpdateTd(Double lastUpdateTd) {
        this.lastUpdateTd = lastUpdateTd;
    }

    public Double getLastUpdateTv() {
        return lastUpdateTv;
    }

    public void setLastUpdateTv(Double lastUpdateTv) {
        this.lastUpdateTv = lastUpdateTv;
    }

    public Integer getLastUpdateTc() {
        return lastUpdateTc;
    }

    public void setLastUpdateTc(Integer lastUpdateTc) {
        this.lastUpdateTc = lastUpdateTc;
    }

    public String getLastVisitTs() {
        return lastVisitTs;
    }

    public void setLastVisitTs(String lastVisitTs) {
        this.lastVisitTs = lastVisitTs;
    }

    public String getLastVisitTw() {
        return lastVisitTw;
    }

    public void setLastVisitTw(String lastVisitTw) {
        this.lastVisitTw = lastVisitTw;
    }

    public String getLastVisitTd() {
        return lastVisitTd;
    }

    public void setLastVisitTd(String lastVisitTd) {
        this.lastVisitTd = lastVisitTd;
    }

    public String getLastVisitTv() {
        return lastVisitTv;
    }

    public void setLastVisitTv(String lastVisitTv) {
        this.lastVisitTv = lastVisitTv;
    }

    public String getLastVisitTc() {
        return lastVisitTc;
    }

    public void setLastVisitTc(String lastVisitTc) {
        this.lastVisitTc = lastVisitTc;
    }

    public String getWellTableName() {
        return wellTableName;
    }

    public void setWellTableName(String wellTableName) {
        this.wellTableName = wellTableName;
    }

    public Integer getIsIdle() {
        return isIdle;
    }

    public void setIsIdle(Integer isIdle) {
        this.isIdle = isIdle;
    }

    public Integer getChunkSize() {
        return chunkSize;
    }

    public void setChunkSize(Integer chunkSize) {
        this.chunkSize = chunkSize;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (calcTrendPK != null ? calcTrendPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CalcTrend)) {
            return false;
        }
        CalcTrend other = (CalcTrend) object;
        if ((this.calcTrendPK == null && other.calcTrendPK != null) || (this.calcTrendPK != null && !this.calcTrendPK.equals(other.calcTrendPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tosigav.nwtw.domain.CalcTrend[ calcTrendPK=" + calcTrendPK + " ]";
    }

}
