package com.otp.datastore;

/**
 * Entity meta data to keep the information about each entities.
 * 
 * @author Max
 *
 */
public class EntityMetadata {

	private String idField;
	private Class<?> clazz;

	/**
	 * Default constructor
	 * 
	 * @param idField
	 */
	public EntityMetadata(String idField, Class<?> clazz) {
		this.idField = idField;
		this.clazz = clazz;
	}

	public String getIdField() {
		return idField;
	}

	public void setIdField(String idField) {
		this.idField = idField;
	}

	public Class<?> getClazz() {
		return clazz;
	}

	public void setClazz(Class<?> clazz) {
		this.clazz = clazz;
	}
}
