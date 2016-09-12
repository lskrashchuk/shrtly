package by.lskrashchuk.jobtest.shrtly.dataaccess;

import java.io.Serializable;
import java.util.List;

import org.hibernate.criterion.Criterion;

public interface GenericDao<T, PK extends Serializable> {
	
	void save(T obj);
	
	void update(T obj);
	
	List<T> findAll();
	
	T findById(PK id);
	
	void delete(PK id);
	
	void delete(T persistentObject);
	
	public List<T> findByCriteria(Criterion criterion);

}
