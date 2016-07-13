package com.otp.datastore.entity;

import com.googlecode.objectify.annotation.Cache;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

/**
 * Holds the tags statistics
 * 
 * @author Max
 *
 */
@Entity(name = "TAG_STATISTIC")
@Cache
public class TagStatistic {

    @Id
    @Index
    private String type;
    @Index
    private Integer usage;

    public TagStatistic() {
	super();
    }

    public TagStatistic(String str, Integer usage) {
	this.type = str;
	this.usage = usage;
    }

    public Integer getUsage() {
	return usage;
    }

    public void setUsage(Integer usage) {
	this.usage = usage;
    }

    public String getType() {
	return type;
    }

    public void setType(String type) {
	this.type = type;
    }

}
