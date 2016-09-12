package by.lskrashchuk.jobtest.shrtly.dataaccess.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import by.lskrashchuk.jobtest.shrtly.dataaccess.UserProfileDao;
import by.lskrashchuk.jobtest.shrtly.dataaccess.filters.UserProfileFilter;
import by.lskrashchuk.jobtest.shrtly.datamodel.UserProfile;
import by.lskrashchuk.jobtest.shrtly.datamodel.UserProfile_;

@Repository
public class UserProfileDaoImpl extends AbstractDaoImpl<UserProfile, Long> implements UserProfileDao {

	@Override
	public Long count(UserProfileFilter filter) {
		return (Long) getSession().createCriteria(UserProfile.class).setProjection(Projections.rowCount()).uniqueResult();
	}

	@Override
	public UserProfile find(String userName, String password) {

		Criteria criteria = getSession().createCriteria(UserProfile.class);
		criteria.add(Restrictions.eq(UserProfile_.email.getName(), userName));
		criteria.add(Restrictions.eq(UserProfile_.password.getName(), password));
		
		List<UserProfile> results = criteria.list();
		
		if (results.isEmpty()) {
			return null;
		} else if (results.size() == 1) {
			return results.get(0);
		} else {
			throw new IllegalArgumentException("more than 1 user found ");
		} 
	}

	@Override
	public List<UserProfile> find(UserProfileFilter userProfileFilter) {
		Criteria criteria = getSession().createCriteria(UserProfile.class);
		if (userProfileFilter.getUserName() != null) {
			criteria.add(Restrictions.eq(UserProfile_.firstName.getName(), userProfileFilter.getUserName()));
		}
		
		if (userProfileFilter.getEmail() != null) {
		criteria.add(Restrictions.eq(UserProfile_.email.getName(), userProfileFilter.getEmail()));
		}
		
		if (userProfileFilter.getOffset() != null && userProfileFilter.getLimit() != null) {
			criteria.setFirstResult(userProfileFilter.getOffset());
			criteria.setMaxResults(userProfileFilter.getLimit());
		}
		
		if (userProfileFilter.getSortProperty() != null) {
			if (userProfileFilter.getSortOrder()) {
				criteria.addOrder(Order.asc(userProfileFilter.getSortProperty().getName()));
			}
			else {
				criteria.addOrder(Order.desc(userProfileFilter.getSortProperty().getName()));
			}
		}
		
		List<UserProfile> results = criteria.list();
		return results;
	}

}
