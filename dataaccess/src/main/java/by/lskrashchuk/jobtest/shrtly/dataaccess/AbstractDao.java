package by.lskrashchuk.jobtest.shrtly.dataaccess;

import java.io.Serializable;
import java.util.List;

import org.hibernate.criterion.Criterion;

public interface AbstractDao<T, PK extends Serializable> extends GenericDao<T, PK> {
	
	/**
	 * Возвращает объект соответствующие ритерию.
	 * 
	 * @param criterion критерий.
	 * @return список объекто, удовлетворяющих критерию.
	 */
	List<T> findByCriteria(Criterion criterion);
	
}
