package com.otp.datastore;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Mark a class as an entity.
 * 
 * @author Max
 *
 */
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface Entity {

	/**
	 * Kind of the entity
	 * 
	 * @return
	 */
	Kind kind();
}
