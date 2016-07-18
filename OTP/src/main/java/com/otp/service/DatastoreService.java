package com.otp.service;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.reflections.Reflections;
import org.springframework.stereotype.Service;

import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.QueryResultList;
import com.otp.datastore.DatastoreResult;
import com.otp.datastore.Entity;
import com.otp.datastore.EntityMetadata;
import com.otp.datastore.Id;
import com.otp.datastore.Kind;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

/**
 * Data store service is in charge of CRUD low level API operations
 * 
 * @author Max
 *
 */
@Service
public class DatastoreService {

	private static Map<Kind, EntityMetadata> entities;

	/**
	 * Registers all the classes in the given package name as entities for those
	 * which are annotated.
	 * 
	 * @param packageName
	 * @throws IllegalArgumentException
	 *             If duplicate entity name is found.
	 */
	@SuppressFBWarnings("ST_WRITE_TO_STATIC_FROM_INSTANCE_METHOD")
	public DatastoreService(String packageName) throws IllegalArgumentException {
		// Finding all the classes in the given package name
		Reflections reflections = new Reflections(packageName);
		Set<Class<?>> entityClasses = reflections.getTypesAnnotatedWith(Entity.class);
		entities = new HashMap<>();
		// Putting the entities into the cache
		for (Class<?> clazz : entityClasses) {
			Kind kind = clazz.getAnnotation(Entity.class).kind();
			if (entities.containsKey(kind)) {
				throw new IllegalArgumentException("Duplicate entity name found [" + kind + "]");
			}
			entities.put(kind, new EntityMetadata(findIdField(clazz), clazz));
		}
	}

	/**
	 * Finds the field annotated with Id.
	 * 
	 * @param clazz
	 * @return
	 */
	public String findIdField(Class<?> clazz) {
		for (Field field : clazz.getDeclaredFields()) {
			if (field.isAnnotationPresent(Id.class)) {
				return field.getName();
			}
		}
		return null;
	}

	/**
	 * Gets the loaded entities.
	 * 
	 * @return
	 */
	public static Map<Kind, EntityMetadata> getEntities() {
		return entities;
	}

	/**
	 * Puts the given object into data store.
	 * 
	 * @param obj
	 * @return
	 * @throws IllegalArgumentException
	 *             If Entity object is not present on the object passed.
	 */
	public Key put(Object obj) throws IllegalArgumentException {

		Kind kind = getKind(obj);

		com.google.appengine.api.datastore.Entity entity;
		entity = instantiateEntity(obj, kind, entities.get(kind));

		populateEntityProperties(obj, entity);

		return getDatastoreService().put(entity);
	}

	/**
	 * Gets the object's kind
	 * 
	 * @param obj
	 * @return
	 * @throws IllegalArgumentException
	 */
	private Kind getKind(Object obj) throws IllegalArgumentException {

		if (!obj.getClass().isAnnotationPresent(Entity.class)) {
			throw new IllegalArgumentException("Entity annotation is not present on the object passed");
		}

		Entity annotation = obj.getClass().getAnnotation(Entity.class);
		return annotation.kind();
	}

	/**
	 * Populates the properties of the given entity based on the fields in the
	 * object passed. (excluding the field annotated with Id)
	 * 
	 * @param obj
	 * @param entity
	 */
	private void populateEntityProperties(Object obj, com.google.appengine.api.datastore.Entity entity) {
		for (Field field : obj.getClass().getDeclaredFields()) {
			try {
				if (!field.isAnnotationPresent(Id.class)) {
					field.setAccessible(true);
					Object value = field.get(obj);
					entity.setProperty(field.getName(), value);
				}
			} catch (IllegalAccessException e) {
				throw new IllegalArgumentException("Cannot get field value for property [" + field.getName() + "]", e);
			}
		}
	}

	/**
	 * Instantiates Entity object based on its metadata and name given.
	 * 
	 * @param obj
	 * @param kind
	 * @param entityMetadata
	 * @return
	 */
	private com.google.appengine.api.datastore.Entity instantiateEntity(Object obj, Kind kind, EntityMetadata entityMetadata) {

		com.google.appengine.api.datastore.Entity entity = null;

		String name = kind.name();
		if (isIdSet(obj, entityMetadata)) {
			try {
				Object value = getIdValue(entityMetadata, obj);
				if (value instanceof Long) {
					entity = new com.google.appengine.api.datastore.Entity(name, (Long) value);
				} else if (value instanceof String) {
					String idValue = value == null ? null : value.toString();
					entity = new com.google.appengine.api.datastore.Entity(name, idValue);
				}
			} catch (IllegalAccessException | NoSuchFieldException | SecurityException e) {
				throw new IllegalArgumentException("Cannot get the field which is annotated with Id value", e);
			}
		} else {
			entity = new com.google.appengine.api.datastore.Entity(name);
		}

		return entity;
	}

	/**
	 * Checks to see if the Id field is set on the entity
	 * 
	 * @param obj
	 * @param entityMetadata
	 * @return
	 * @throws IllegalArgumentException
	 *             If it cannot get the Id value
	 */
	private boolean isIdSet(Object obj, EntityMetadata entityMetadata) throws IllegalArgumentException {
		try {
			return getIdValue(entityMetadata, obj) != null;
		} catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
			throw new IllegalArgumentException("Cannot get the Id value", e);
		}
	}

	/**
	 * Gets the value of the field annotated with Id.
	 * 
	 * @param entityMetadata
	 * @param obj
	 * @return
	 * @throws SecurityException
	 * @throws NoSuchFieldException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 */
	public Object getIdValue(EntityMetadata entityMetadata, Object obj) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
		return getValue(obj, entityMetadata.getIdField());
	}

	/**
	 * Gets the value of the given field name in the object passed.
	 * 
	 * @param obj
	 * @param fieldName
	 * @return
	 * @throws NoSuchFieldException
	 * @throws SecurityException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public Object getValue(Object obj, String fieldName) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		Field field = obj.getClass().getDeclaredField(fieldName);
		field.setAccessible(true);
		return field.get(obj);
	}

	/**
	 * Gets the record in datastore using the id and the kind.
	 * 
	 * @param kind
	 * @param id
	 * @return
	 * @throws EntityNotFoundException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws SecurityException
	 * @throws NoSuchFieldException
	 * @throws IllegalArgumentException
	 */
	public Object get(Kind kind, String id) throws EntityNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, NoSuchFieldException, SecurityException {

		Key key = KeyFactory.createKey(kind.name(), id);
		return fetchEntity(kind, id, key);
	}

	/**
	 * Fetches the entity for the given kind and key.
	 * 
	 * @param kind
	 * @param id
	 * @param key
	 * @return
	 * @throws EntityNotFoundException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws NoSuchFieldException
	 */
	private Object fetchEntity(Kind kind, Object id, Key key) throws EntityNotFoundException, InstantiationException, IllegalAccessException, NoSuchFieldException {
		com.google.appengine.api.datastore.Entity entity = getDatastoreService().get(key);

		EntityMetadata entityMetadata = entities.get(kind);

		// Creating the entity
		Object result = createEntity(entity.getProperties(), entityMetadata);
		// Putting the id into the entity
		setFieldValue(result, id, entityMetadata.getIdField());
		return result;
	}

	/**
	 * Gets the record stored in datastore based on the id and the kind.
	 * 
	 * @param kind
	 * @param id
	 * @throws EntityNotFoundException
	 * @throws NoSuchFieldException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */
	public Object get(Kind kind, Long id) throws InstantiationException, IllegalAccessException, NoSuchFieldException, EntityNotFoundException {

		Key key = KeyFactory.createKey(kind.name(), id);
		return fetchEntity(kind, id, key);
	}

	/**
	 * Creates a query for the given object.
	 * 
	 * @param kind
	 * @return
	 */
	public Query getQuery(Kind kind) {
		return new Query(kind.name());
	}

	/**
	 * Sets the value passed into the field name in object.
	 * 
	 * @param object
	 * @param value
	 * @param fieldName
	 * @throws SecurityException
	 * @throws NoSuchFieldException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 */
	private void setFieldValue(Object object, Object value, String fieldName) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {

		Field field = object.getClass().getDeclaredField(fieldName);
		field.setAccessible(true);
		field.set(object, value);
	}

	/**
	 * Instantiates an entity and populates its values using the properties
	 * given.
	 * 
	 * @param properties
	 * @param entityMetadata
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public Object createEntity(Map<String, Object> properties, EntityMetadata entityMetadata) throws InstantiationException, IllegalAccessException {

		Object newInstance = entityMetadata.getClazz().newInstance();
		for (Field field : newInstance.getClass().getDeclaredFields()) {
			field.setAccessible(true);
			field.set(newInstance, properties.get(field.getName()));
		}
		return newInstance;
	}

	/**
	 * Calls data store factory to get an instance of the service.
	 * 
	 * @return
	 */
	public com.google.appengine.api.datastore.DatastoreService getDatastoreService() {
		return com.google.appengine.api.datastore.DatastoreServiceFactory.getDatastoreService();
	}

	/**
	 * Fetches the data based on the query passed.
	 * 
	 * @param query
	 * @param clazz
	 * @param options
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws NoSuchFieldException
	 * @throws SecurityException
	 */
	public <T> DatastoreResult<T> asQueryResultList(Query query, Class<T> clazz, FetchOptions options) throws InstantiationException, IllegalAccessException, IllegalArgumentException, NoSuchFieldException, SecurityException {
		DatastoreResult<T> result = new DatastoreResult<>();
		Iterator<com.google.appengine.api.datastore.Entity> iterator = null;
		if (options != null) {
			QueryResultList<com.google.appengine.api.datastore.Entity> queryResult = getDatastoreService().prepare(query).asQueryResultList(options);
			iterator = queryResult.iterator();
			result.setCursor(queryResult.getCursor().toWebSafeString());
		} else {
			iterator = getDatastoreService().prepare(query).asIterator();
		}
		while (iterator.hasNext()) {
			com.google.appengine.api.datastore.Entity entity = iterator.next();
			T newInstance = clazz.newInstance();
			populateKindFields(newInstance, entity);
			setFieldValue(newInstance, entity.getKey().getId(), entities.get(Kind.valueOf(query.getKind())).getIdField());
			result.addResult(newInstance);
		}
		return result;
	}

	/**
	 * Populates the object fields based on the entity data given.
	 * 
	 * @param obj
	 * @param entity
	 */
	private void populateKindFields(Object obj, com.google.appengine.api.datastore.Entity entity) {
		for (Field field : obj.getClass().getDeclaredFields()) {
			try {
				field.setAccessible(true);
				field.set(obj, entity.getProperty(field.getName()));
			} catch (IllegalAccessException e) {
				throw new IllegalArgumentException("Cannot get field value for field [" + field.getName() + "]", e);
			}
		}
	}

	/**
	 * Sets ID field of the object passed.
	 * 
	 * @param object
	 * @param value
	 * @param fieldName
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws NoSuchFieldException
	 * @throws SecurityException
	 */
	private void setFieldValue(Object object, Long value, String fieldName) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {

		Field field = object.getClass().getDeclaredField(fieldName);
		field.setAccessible(true);
		field.set(object, value);
	}

	/**
	 * Deletes teh given object from database using the Id stored on it.
	 * 
	 * @param obj
	 * @throws IllegalArgumentException
	 */
	public void delete(Object obj) throws IllegalArgumentException {
		try {
			Kind kind = getKind(obj);
			Object idValue = getIdValue(entities.get(kind), obj);
			Key key = null;
			if (idValue instanceof String) {
				key = KeyFactory.createKey(kind.name(), (String) getIdValue(entities.get(kind), obj));
			}
			if (idValue instanceof Long) {
				key = KeyFactory.createKey(kind.name(), (Long) getIdValue(entities.get(kind), obj));
			}
			getDatastoreService().delete(key);
		} catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
			throw new IllegalArgumentException("Cannot delete the object given", e);
		}
	}

}
