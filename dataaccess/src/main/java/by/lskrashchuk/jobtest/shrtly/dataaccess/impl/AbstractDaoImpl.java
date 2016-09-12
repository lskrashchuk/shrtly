package by.lskrashchuk.jobtest.shrtly.dataaccess.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.NonUniqueObjectException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import by.lskrashchuk.jobtest.shrtly.dataaccess.AbstractDao;

/**
	 * Класс реализовывает методы некоторые интерфейса HibernateDao.
	 * 
	 * @author Alexey Suslov
	 *
	 * @param <T> объект над которым происходят CRUD операции.
	 * @param <PK> ключ объекта над которыми происходят операции (CRUD).
	 */
	@Repository
	public abstract class AbstractDaoImpl<T, PK extends Serializable> implements AbstractDao<T, PK> {
		
		/**
		 * Данное поле инстанцируется Spring'ом.
		 */
		@Autowired
		private SessionFactory sessionFactory;
		
		protected Session getSession() {
			return sessionFactory.getCurrentSession();
		}
		
		/**
		 * Определяет класс дженерика суперкласса.
		 * 
		 * @return класс объекта.
		 */
		@SuppressWarnings("unchecked")
		protected Class<T> getGenericEntityClass() {
			Class<T> clazz = (Class<T>) getClass();
			ParameterizedType parameterizedType = (ParameterizedType) clazz.getGenericSuperclass();
			return (Class<T>) parameterizedType.getActualTypeArguments()[0];
		}
		
		@SuppressWarnings("unchecked")
		@Override
		public void save(T obj) {
			getSession().persist(obj);
		}
		
		@Override
		public void update(T obl) {
//			getSession().update(obl);		
			getSession().merge(obl);		
			getSession().flush();		
		}
		
		@SuppressWarnings("unchecked")
		@Override
		public List<T> findAll() {
			Criteria cr = getSession().createCriteria(this.getGenericEntityClass());
			return (List<T>) cr.list();
		}
		
		@SuppressWarnings("unchecked")
		@Override
		public List<T> findByCriteria(Criterion criterion) {
			Criteria crit = getSession().createCriteria(this.getGenericEntityClass());
			crit.add(criterion);
			return (List<T>) crit.list();
		}
		
		@SuppressWarnings("unchecked")
		@Override
		public T findById(PK id) {
			return (T) getSession().get(this.getGenericEntityClass(), id);
		}
		
		@SuppressWarnings("unchecked")
		@Override
		public void delete(PK id) {
			T persistentObject = (T) getSession().load(this.getGenericEntityClass(), id);
			try {
				getSession().delete(persistentObject);
			} catch (NonUniqueObjectException e) {
				// если объект не уникальный
				T instance = (T) getSession().merge(persistentObject);
				getSession().delete(instance);
			}
		}
		
		@SuppressWarnings("unchecked")
		@Override
		public void delete(T persistentObject) {
			try {
				getSession().delete(persistentObject);
			} catch (NonUniqueObjectException e) {
				// если объект не уникальный
				T instance = (T) getSession().merge(persistentObject);
				getSession().delete(instance);
			}
		}

	}
	