package com.otp.datastore;

import java.util.ArrayList;
import java.util.List;

/**
 * Stores the result of a query together with the cursor.
 * 
 * @author Max
 *
 * @param <T>
 */
public class DatastoreResult<T> {

    private List<T> results;
    private String cursor;

    public String getCursor() {
	return cursor;
    }

    public void setCursor(String cursor) {
	this.cursor = cursor;
    }

    /**
     * Adds an element to result
     * 
     * @param result
     */
    public void addResult(T result) {
	if (this.results == null) {
	    this.results = new ArrayList<>();
	}
	this.results.add(result);
    }

    public List<T> getResults() {
	return results;
    }

    public void setResults(List<T> results) {
	this.results = results;
    }

}
