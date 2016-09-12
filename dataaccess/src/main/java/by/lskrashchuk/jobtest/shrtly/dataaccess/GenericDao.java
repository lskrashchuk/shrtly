package by.lskrashchuk.jobtest.shrtly.dataaccess;

import java.io.Serializable;
import java.util.List;

import org.hibernate.criterion.Criterion;

public interface GenericDao<T, PK extends Serializable> {
	
	/**
	 * Сохраняет объект в хранилище
	 * 
	 * @param obj сохраняемый объект.
	 * @return индентификатор объекта (ключ).
	 */
	void save(T obj);
	
	/**
	 * Сохраняет изменённый объект в хранилище
	 * 
	 * @param obj изменённый объект.
	 */
	void update(T obj);
	
	/**
	 * Возвращает все объекты указанного типа.
	 * 
	 * @return список объектов заданного типа.
	 */
	List<T> findAll();
	
	/**
	 * Возвращает объект указанного типа по заданному ключу.
	 * 
	 * @param id
	 * @return
	 */
	T findById(PK id);
	
	/**
	 * Удаляет указанный обект по заданному ключу.
	 * 
	 * @param id ключ удаляемого объекта.
	 */
	void delete(PK id);
	
	/**
	 * Удаляет заданный объект.
	 * 
	 * @param persistentObject заданный объект.
	 */
	void delete(T persistentObject);
	
	/**
	 * Возвращает список объект указанного типа по заданному критерию.
	 * 
	 * @param criterion
	 * @return список объектов
	 */
	public List<T> findByCriteria(Criterion criterion);

}
