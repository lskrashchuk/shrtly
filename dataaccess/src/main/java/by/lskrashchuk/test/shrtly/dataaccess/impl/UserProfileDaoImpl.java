package by.lskrashchuk.test.shrtly.dataaccess.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.jpa.criteria.OrderImpl;
import org.springframework.stereotype.Repository;

import by.lskrashchuk.test.shrtly.dataaccess.UserProfileDao;
import by.lskrashchuk.test.shrtly.dataaccess.filters.UserProfileFilter;
import by.lskrashchuk.test.shrtly.datamodel.UserProfile;
import by.lskrashchuk.test.shrtly.datamodel.UserProfile_;

@Repository
public class UserProfileDaoImpl extends AbstractDaoImpl<UserProfile, Long> implements UserProfileDao {

	protected UserProfileDaoImpl() {
		super(UserProfile.class);
	}

	@Override
	public Long count(UserProfileFilter filter) {
		EntityManager em = getEntityManager();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Long> cq = cb.createQuery(Long.class);
		Root<UserProfile> from = cq.from(UserProfile.class);
		cq.select(cb.count(from));
		TypedQuery<Long> q = em.createQuery(cq);
		return q.getSingleResult();
	}

	@Override
	public List<UserProfile> find(UserProfileFilter filter) {
		EntityManager em = getEntityManager();
		CriteriaBuilder cb = em.getCriteriaBuilder();

		CriteriaQuery<UserProfile> cq = cb.createQuery(UserProfile.class);

		Root<UserProfile> from = cq.from(UserProfile.class);

		// set selection
		cq.select(from);

		if (filter.getUserName() != null) {
			Predicate fNameEqualCondition = cb.equal(from.get(UserProfile_.firstName), filter.getUserName());
			Predicate lNameEqualCondition = cb.equal(from.get(UserProfile_.lastName), filter.getUserName());
			cq.where(cb.or(fNameEqualCondition, lNameEqualCondition));
		}

		if (filter.getEmail() != null) {
			Predicate emailEqualCondition = cb.equal(from.get(UserProfile_.email), filter.getEmail());
			cq.where(cb.and(emailEqualCondition));
		}

		// set sort params
		if (filter.getSortProperty() != null) {
			cq.orderBy(new OrderImpl(from.get(filter.getSortProperty()), filter.isSortOrder()));
		}

		TypedQuery<UserProfile> q = em.createQuery(cq);

		// set paging
		if (filter.getOffset() != null && filter.getLimit() != null) {
			q.setFirstResult(filter.getOffset());
			q.setMaxResults(filter.getLimit());
		}

		// set execute query
		List<UserProfile> allitems = q.getResultList();
		return allitems;
	}

	@Override
	public UserProfile find(String userName, String password) {
		EntityManager em = getEntityManager();

		CriteriaBuilder cb = em.getCriteriaBuilder();

		CriteriaQuery<UserProfile> cq = cb.createQuery(UserProfile.class);

		Root<UserProfile> from = cq.from(UserProfile.class);

		cq.select(from);
		Predicate usernameEqualCondition = cb.equal(from.get(UserProfile_.email), userName);
		Predicate passwEqualCondition = cb.equal(from.get(UserProfile_.password), password);
		cq.where(cb.and(usernameEqualCondition, passwEqualCondition));

		TypedQuery<UserProfile> q = em.createQuery(cq);

		List<UserProfile> allitems = q.getResultList();

		if (allitems.isEmpty()) {
			return null;
		} else if (allitems.size() == 1) {
			return allitems.get(0);
		} else {
			throw new IllegalArgumentException("more than 1 user found ");
		}
	}

	@Override
	public UserProfile getWithUrls(Long id) {
		EntityManager em = getEntityManager();

		if (em.find(getEntityClass(), id) == null) {
			return null;
		};

		CriteriaBuilder cb = em.getCriteriaBuilder();

		CriteriaQuery<UserProfile> cq = cb.createQuery(UserProfile.class);

		Root<UserProfile> from = cq.from(UserProfile.class);

		// set selection
		cq.select(from);

		from.fetch(UserProfile_.urls, JoinType.LEFT);

		cq.where(cb.equal(from.get(UserProfile_.id), id));
		cq.distinct(true);

		TypedQuery<UserProfile> q = em.createQuery(cq);

		// set execute query
		return q.getSingleResult();
	}

}
