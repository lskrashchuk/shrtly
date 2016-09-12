package by.lskrashchuk.jobtest.shrtly.dataaccess.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import by.lskrashchuk.jobtest.shrtly.dataaccess.UrlDao;
import by.lskrashchuk.jobtest.shrtly.dataaccess.filters.UrlFilter;
import by.lskrashchuk.jobtest.shrtly.datamodel.Url;
import by.lskrashchuk.jobtest.shrtly.datamodel.Url_;

@Repository
public class UrlDaoImpl extends AbstractDaoImpl<Url, Long> implements UrlDao{

	@Override
	public Long count(UrlFilter filter) {
		Criteria criteria = getSession().createCriteria(Url.class);
		if (filter.getUserProfile() != null) {
			criteria.add(Restrictions.eq(Url_.userProfile.getName(), filter.getUserProfile()));
		}

		return (Long) criteria.setProjection(Projections.rowCount()).uniqueResult();

	}

	@Override
	public List<Url> find(UrlFilter filter) {

		Criteria criteria = getSession().createCriteria(Url.class);

		if (filter.getUserProfile() != null) {
			criteria.add(Restrictions.eq(Url_.userProfile.getName(), filter.getUserProfile()));
		}
		
		if (filter.getOffset() != null && filter.getLimit() != null) {
			criteria.setFirstResult(filter.getOffset());
			criteria.setMaxResults(filter.getLimit());
		}
		if (filter.getSortProperty() != null) {
			if (filter.getSortOrder()) {
				criteria.addOrder(Order.asc(filter.getSortProperty().getName()));
			}
			else {
				criteria.addOrder(Order.desc(filter.getSortProperty().getName()));
			}
		}
		
		List<Url> results = criteria.list();
		return results;

	}

	@Override
	public Url find(String urlCode) {
		Criteria criteria = getSession().createCriteria(Url.class);
		criteria.add(Restrictions.eq(Url_.urlCode.getName(), urlCode));
		
		List<Url> results = criteria.list();
		
		if (results.isEmpty()) {
			return null;
		} else if (results.size() == 1) {
			return results.get(0);
		} else {
			throw new IllegalArgumentException("more than 1 url found ");
		} 

	}

	@Override
	public Url getWithTags(Long id) {
		 
		Criteria criteria = getSession().createCriteria(Url.class);
		criteria.add(Restrictions.eq(Url_.id.getName(), id));
		criteria.setFetchMode(Url_.tags.getName(), FetchMode.JOIN);
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		
		List<Url> results = criteria.list();

		if (results.isEmpty()) {
			return null;
		} else if (results.size() == 1) {
			return results.get(0);
		} else {
			throw new IllegalArgumentException("more than 1 url found ");
		} 

	}

}
