/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tosigav.nwtw.domain;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author ballacksave
 */
@Embeddable
public class CalcTrendPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "id")
    private String id;

    @Basic(optional = false)
    @Column(name = "id_well")
    private int idWell;

    @Basic(optional = false)
    @Column(name = "channel")
    private String channel;

    public CalcTrendPK() {
    }

    public CalcTrendPK(String id, int idWell, String channel) {
        this.id = id;
        this.idWell = idWell;
        this.channel = channel;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getIdWell() {
        return idWell;
    }

    public void setIdWell(int idWell) {
        this.idWell = idWell;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        hash += (int) idWell;
        hash += (channel != null ? channel.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CalcTrendPK)) {
            return false;
        }
        CalcTrendPK other = (CalcTrendPK) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        if (this.idWell != other.idWell) {
            return false;
        }
        if ((this.channel == null && other.channel != null) || (this.channel != null && !this.channel.equals(other.channel))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tosigav.nwtw.domain.CalcTrendPK[ id=" + id + ", idWell=" + idWell + ", channel=" + channel + " ]";
    }
    
}
