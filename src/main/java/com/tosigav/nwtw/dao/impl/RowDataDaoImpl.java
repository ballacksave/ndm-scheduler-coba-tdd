package com.tosigav.nwtw.dao.impl;

import com.tosigav.nwtw.dao.RowDataDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

/**
 * @author ronny susetyo  <ronny at susetyo.com>
 * @since 10 Nov 2016
 */
@Repository
public class RowDataDaoImpl implements RowDataDao {
    private static final Logger logger = LoggerFactory.getLogger(RowDataDaoImpl.class);

    private JdbcTemplate jdbcTemplate;

    public RowDataDaoImpl() {
    }

    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    private String rec;
    private String channel;
    private String wellTablename;
    private int chunkSize;

    @Override
    public List<Double> findByRangeTs(String lastTs, int range) {
        String sql = getSqlRangeTs(lastTs, range);
        List<Double> doubles = jdbcTemplate.queryForList(sql, Double.class);
        return doubles;
    }

    public String getSqlRangeTs(String lastTs, int range) {
        if (lastTs == null) {
            return "SELECT NULL;";
        } else {
            return "SELECT `" + channel + "` FROM ts_" + wellTablename + "_" + rec + " WHERE NOT ISNULL(`" + channel + "`) AND `" + channel + "` != '-999.25' AND ts BETWEEN DATE_SUB('" + lastTs + "', INTERVAL " + range + " SECOND) AND '" + lastTs + "';";
        }
    }

    @Override
    public List<Double> findByRangeTw(String lastTw, int range) {
        String sql = getSqlRangeTw(lastTw, range);
        List<Double> doubles = jdbcTemplate.queryForList(sql, Double.class);
        return doubles;
    }

    public String getSqlRangeTw(String lastTw, int range) {
        if (lastTw == null) {
            return "SELECT NULL;";
        } else {
            return "SELECT `" + channel + "` FROM tw_" + wellTablename + "_" + rec + " WHERE NOT ISNULL(`" + channel + "`) AND `" + channel + "` != '-999.25' AND tw BETWEEN DATE_SUB('" + lastTw + "', INTERVAL " + range + " SECOND) AND '" + lastTw + "';";
        }
    }

    @Override
    public List<Double> findByRangeTd(Double lastTd, int range) {
        String sql = getSqlRangeTd(lastTd, range);
        List<Double> doubles = jdbcTemplate.queryForList(sql, Double.class);
        return doubles;
    }

    public String getSqlRangeTd(Double lastTd, int range) {
        if (lastTd == null) {
            return "SELECT NULL;";
        } else {
            return "SELECT `" + channel + "` FROM td_" + wellTablename + "_" + rec + " WHERE NOT ISNULL(`" + channel + "`) AND `" + channel + "` != '-999.25' AND md BETWEEN " + lastTd + " - " + range + " AND " + lastTd + ";";
        }
    }

    @Override
    public List<Double> findByRangeTv(Double lastTv, int range) {
        String sql = getSqlRangeTv(lastTv, range);
        List<Double> doubles = jdbcTemplate.queryForList(sql, Double.class);
        return doubles;
    }

    public String getSqlRangeTv(Double lastTv, int range) {
        String last;
        if (lastTv == null) {
            return "SELECT NULL;";
        } else {
            return "SELECT `" + channel + "` FROM tv_" + wellTablename + "_" + rec + " WHERE NOT ISNULL(`" + channel + "`) AND `" + channel + "` != '-999.25' AND tv BETWEEN " + lastTv + " - " + range + " AND " + lastTv + ";";
        }
    }

    @Override
    public List<Double> findByRangeTc(Integer lastTc, int range) {
        String sql = getSqlRangeTc(lastTc, range);
        List<Double> doubles = jdbcTemplate.queryForList(sql, Double.class);
        return doubles;
    }

    public String getSqlRangeTc(Integer lastTc, int range) {
        String last;
        if (lastTc == null) {
            return "SELECT NULL;";
        } else {
            return "SELECT `" + channel + "` FROM tc_" + wellTablename + "_" + rec + " WHERE NOT ISNULL(`" + channel + "`) AND `" + channel + "` != '-999.25' AND tc BETWEEN IF(" + lastTc + "-" + range + " < 1, 0, " + lastTc + "-" + range + ") AND " + lastTc + ";";
        }
    }

    @Override
    public List<String> findDataByChunkSizeAndLastUpdateTs(String lastTs) {
        String sql = getSqlChunkSizeAndLastUpdateTs(lastTs);
        List<String> strings = jdbcTemplate.queryForList(sql, String.class);
        return strings;
    }

    public String getSqlChunkSizeAndLastUpdateTs(String lastTs) {
        if (lastTs == null) {
            return "SELECT `ts` FROM ts_" + wellTablename + "_" + rec + " WHERE `ts` <> '0000-00-00 00:00:00' ORDER BY ts LIMIT 1;";
        } else {
            return "SELECT `ts` FROM ts_" + wellTablename + "_" + rec + " WHERE ts > '" + lastTs + "' LIMIT " + chunkSize + ";";
        }
    }

    @Override
    public List<String> findDataByChunkSizeAndLastUpdateTw(String lastTw) {
        String sql = getSqlChunkSizeAndLastUpdateTw(lastTw);
        List<String> strings = jdbcTemplate.queryForList(sql, String.class);
        return strings;
    }

    public String getSqlChunkSizeAndLastUpdateTw(String lastTw) {
        if (lastTw == null) {
            return "SELECT `tw` FROM tw_" + wellTablename + "_" + rec + " WHERE `tw` <> '0000-00-00 00:00:00' ORDER BY tw LIMIT 1;";
        } else {
            return "SELECT `tw` FROM tw_" + wellTablename + "_" + rec + " WHERE tw > '" + lastTw + "' LIMIT " + chunkSize + ";";
        }
    }

    @Override
    public List<Double> findDataByChunkSizeAndLastUpdateTd(Double lastTd) {
        String sql = getSqlChunkSizeAndLastUpdateTd(lastTd);
        List<Double> doubles = jdbcTemplate.queryForList(sql, Double.class);
        return doubles;
    }

    public String getSqlChunkSizeAndLastUpdateTd(Double lastTd) {
        if (lastTd == null) {
            return "SELECT `md` FROM td_" + wellTablename + "_" + rec + " ORDER BY md LIMIT 1;";
        } else {
            return "SELECT `md` FROM td_" + wellTablename + "_" + rec + " WHERE md > " + lastTd + " LIMIT " + chunkSize + ";";
        }
    }

    @Override
    public List<Double> findDataByChunkSizeAndLastUpdateTv(Double lastTv) {
        String sql = getSqlChunkSizeAndLastUpdateTv(lastTv);
        List<Double> doubles = jdbcTemplate.queryForList(sql, Double.class);
        return doubles;
    }

    public String getSqlChunkSizeAndLastUpdateTv(Double lastTv) {
        if (lastTv == null) {
            return "SELECT `tv` FROM tv_" + wellTablename + "_" + rec + " ORDER BY tv LIMIT 1;";
        } else {
            return "SELECT `tv` FROM tv_" + wellTablename + "_" + rec + " WHERE tv > " + lastTv + " LIMIT " + chunkSize + ";";
        }
    }

    @Override
    public List<Integer> findDataByChunkSizeAndLastUpdateTc(Integer lastTc) {
        String sql = getSqlChunkSizeAndLastUpdateTc(lastTc);
        List<Integer> integers = jdbcTemplate.queryForList(sql, Integer.class);
        return integers;
    }

    public String getSqlChunkSizeAndLastUpdateTc(Integer lastTc) {
        if (lastTc == null) {
            return "SELECT `tc` FROM tc_" + wellTablename + "_" + rec + " ORDER BY tc LIMIT 1;";
        } else {
            return "SELECT `tc` FROM tc_" + wellTablename + "_" + rec + " WHERE tc > " + lastTc + " LIMIT " + chunkSize + ";";
        }
    }

    @Override
    public void updateTrendTs(String lastTs, Integer trend1, Integer trend2, Integer trend3) {
        String sql = getSqlUpdateTrendTs(lastTs, trend1, trend2, trend3);
        jdbcTemplate.update(sql);
    }

    public String getSqlUpdateTrendTs(String lastTs, Integer trend1, Integer trend2, Integer trend3) {
        String valTrend1 = trend1 == null ? "NULL" : "'" + trend1 + "'";
        String valTrend2 = trend2 == null ? "NULL" : "'" + trend2 + "'";
        String valTrend3 = trend3 == null ? "NULL" : "'" + trend3 + "'";
        String valLastTs = lastTs == null ? "NULL" : "'" + lastTs + "'";
        return "UPDATE ts_" + wellTablename + "_" + rec + " SET `" + channel + "_1`=" + valTrend1 + ", `" + channel + "_2`=" + valTrend2 + ", `" + channel + "_3`=" + valTrend3 + " WHERE ts = " + valLastTs + ";";
    }

    @Override
    public void updateTrendTw(String lastTw, Integer trend1, Integer trend2, Integer trend3) {
        String sql = getSqlUpdateTrendTw(lastTw, trend1, trend2, trend3);
        jdbcTemplate.update(sql);
    }

    public String getSqlUpdateTrendTw(String lastTw, Integer trend1, Integer trend2, Integer trend3) {
        String valTrend1 = trend1 == null ? "NULL" : "'" + trend1 + "'";
        String valTrend2 = trend2 == null ? "NULL" : "'" + trend2 + "'";
        String valTrend3 = trend3 == null ? "NULL" : "'" + trend3 + "'";
        String valLastTw = lastTw == null ? "NULL" : "'" + lastTw + "'";
        return "UPDATE tw_" + wellTablename + "_" + rec + " SET `" + channel + "_1`=" + valTrend1 + ", `" + channel + "_2`=" + valTrend2 + ", `" + channel + "_3`=" + valTrend3 + " WHERE tw = " + valLastTw + ";";
    }

    @Override
    public void updateTrendTd(Double lastTd, Integer trend1, Integer trend2, Integer trend3) {
        String sql = getSqlUpdateTrendTd(lastTd, trend1, trend2, trend3);
        jdbcTemplate.update(sql);
    }

    public String getSqlUpdateTrendTd(Double lastTd, Integer trend1, Integer trend2, Integer trend3) {
        String valTrend1 = trend1 == null ? "NULL" : "'" + trend1 + "'";
        String valTrend2 = trend2 == null ? "NULL" : "'" + trend2 + "'";
        String valTrend3 = trend3 == null ? "NULL" : "'" + trend3 + "'";
        String valLastTd = lastTd == null ? "NULL" : "'" + lastTd + "'";
        return "UPDATE td_" + wellTablename + "_" + rec + " SET `" + channel + "_1`=" + valTrend1 + ", `" + channel + "_2`=" + valTrend2 + ", `" + channel + "_3`=" + valTrend3 + " WHERE md = " + valLastTd + ";";
    }

    @Override
    public void updateTrendTv(Double lastTv, Integer trend1, Integer trend2, Integer trend3) {
        String sql = getSqlUpdateTrendTv(lastTv, trend1, trend2, trend3);
        jdbcTemplate.update(sql);
    }

    public String getSqlUpdateTrendTv(Double lastTv, Integer trend1, Integer trend2, Integer trend3) {
        String valTrend1 = trend1 == null ? "NULL" : "'" + trend1 + "'";
        String valTrend2 = trend2 == null ? "NULL" : "'" + trend2 + "'";
        String valTrend3 = trend3 == null ? "NULL" : "'" + trend3 + "'";
        String valLastTv = lastTv == null ? "NULL" : "'" + lastTv + "'";
        return "UPDATE tv_" + wellTablename + "_" + rec + " SET `" + channel + "_1`=" + valTrend1 + ", `" + channel + "_2`=" + valTrend2 + ", `" + channel + "_3`=" + valTrend3 + " WHERE tv = " + valLastTv + ";";
    }

    @Override
    public void updateTrendTc(Integer lastTc, Integer trend1, Integer trend2, Integer trend3) {
        String sql = getSqlUpdateTrendTc(lastTc, trend1, trend2, trend3);
        jdbcTemplate.update(sql);
    }

    public String getSqlUpdateTrendTc(Integer lastTc, Integer trend1, Integer trend2, Integer trend3) {
        String valTrend1 = trend1 == null ? "NULL" : "'" + trend1 + "'";
        String valTrend2 = trend2 == null ? "NULL" : "'" + trend2 + "'";
        String valTrend3 = trend3 == null ? "NULL" : "'" + trend3 + "'";
        String valLastTc = lastTc == null ? "NULL" : "'" + lastTc + "'";
        return "UPDATE tc_" + wellTablename + "_" + rec + " SET `" + channel + "_1`=" + valTrend1 + ", `" + channel + "_2`=" + valTrend2 + ", `" + channel + "_3`=" + valTrend3 + " WHERE tc = " + valLastTc + ";";
    }

    @Override
    public void setRec(String rec) {
        this.rec = rec;
    }

    @Override
    public void setChannel(String channel) {
        this.channel = channel;
    }

    @Override
    public void setWellTablename(String wellTablename) {
        this.wellTablename = wellTablename;
    }

    @Override
    public void setChunkSize(int chunkSize) {
        this.chunkSize = chunkSize;
    }
}
