package com.otp.datastore.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Cache;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Ignore;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Load;
import com.googlecode.objectify.annotation.OnLoad;

/**
 * Quote kind to store quotations
 * 
 * @author Max
 *
 */
@Entity(name = "QUOTE")
@Cache
public class Quote {

    @Id
    @Index
    private Long id;
    private String str;
    @Index
    private String username;
    @Index
    private Date createdOn;
    private List<String> viewers;
    @Index
    private Long viewerCount;
    @Load
    @Index
    private List<Ref<Tag>> tagsRefs;
    @Ignore
    private List<Tag> tags;

    @OnLoad
    public void deRef() {
	if (tagsRefs != null) {
	    tags = new ArrayList<>();
	    for (Ref<Tag> tagLoaded : tagsRefs) {
		tags.add(tagLoaded.get());
	    }
	}
    }

    public Quote() {
    }

    public Date getCreatedOn() {
	return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
	this.createdOn = createdOn;
    }

    public String getUsername() {
	return username;
    }

    public void setUsername(String username) {
	this.username = username;
    }

    public String getStr() {
	return str;
    }

    public void setStr(String str) {
	this.str = str;
    }

    public Long getId() {
	return id;
    }

    public void setId(Long id) {
	this.id = id;
    }

    public List<String> getViewers() {
	return viewers;
    }

    public void setViewers(List<String> viewers) {
	this.viewers = viewers;
    }

    public Long getViewerCount() {
	return viewerCount;
    }

    public void setViewerCount(Long viewerCount) {
	this.viewerCount = viewerCount;
    }

    /**
     * Use setTags
     * 
     * @param tags
     */
    @Deprecated
    public void setTagsRefs(List<Ref<Tag>> tags) {
	this.tagsRefs = tags;
    }

    public void setTags(List<Tag> tags) {
	this.tags = tags;
    }

    public List<Tag> getTags() {
	return this.tags;
    }

}