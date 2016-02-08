package com.pink.triangle.hussain.elastic.model;

import io.searchbox.annotations.JestId;

import java.util.Date;

/**
 * Created by Hussain on 08/02/2016.
 */
public class SynchStatus {

    @JestId
    private String synchId;
    private long lastSynchDate;

    public SynchStatus(){
        this.synchId = "synch-id";
    }

    public String getSynchId() {
        return synchId;
    }

    public Date getLastSynchDate() {
        return new Date(this.lastSynchDate);
    }

    public void setSynchId(String synchId) {
        this.synchId = synchId;
    }

    public void setLastSynchDate(long lastSynchDate) {
        this.lastSynchDate = lastSynchDate;
    }
}
