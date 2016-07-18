package com.otp.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.googlecode.objectify.Ref;

/**
 * Common logics of DAO layer will be sitting here.
 * 
 * @author Max
 *
 */
public class DAOImpl {

	/**
	 * Converts the list to ref list.
	 * 
	 * @param entity
	 * @return
	 */
	protected <T> List<Ref<T>> convert(List<T> entity) {
		List<Ref<T>> result = null;
		if (CollectionUtils.isNotEmpty(entity)) {
			result = new ArrayList<>();
			for (T tag : entity) {
				result.add(Ref.create(tag));
			}
		}
		return result;
	}

}
