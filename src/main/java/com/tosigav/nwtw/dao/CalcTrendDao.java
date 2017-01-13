package com.tosigav.nwtw.dao;

import com.tosigav.nwtw.domain.CalcTrend;
import com.tosigav.nwtw.domain.CalcTrendPK;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author ronny susetyo  <ronny at susetyo.com>
 * @since 09 Nov 2016
 */
@Repository
public interface CalcTrendDao extends CrudRepository<CalcTrend, CalcTrendPK> {

    List<CalcTrend> findByEnableAndIsIdle(int enable, int isIdle);
}
