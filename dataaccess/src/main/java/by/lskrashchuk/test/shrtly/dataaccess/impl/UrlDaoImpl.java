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

import by.lskrashchuk.test.shrtly.dataaccess.UrlDao;
import by.lskrashchuk.test.shrtly.dataaccess.filters.UrlFilter;
import by.lskrashchuk.test.shrtly.datamodel.Url;
import by.lskrashchuk.test.shrtly.datamodel.Url_;
import by.lskrashchuk.test.shrtly.datamodel.UserProfile;
import by.lskrashchuk.test.shrtly.datamodel.UserProfile_;

@Repository
public class UrlDaoImpl extends AbstractDaoImpl<Url, Long> implements UrlDao{

	protected UrlDaoImpl() {
		super(Url.class);
	}

	@Override
	public Long count(UrlFilter filter) {
		EntityManager em = getEntityManager();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Long> cq = cb.createQuery(Long.class);
		Root<Url> from = cq.from(Url.class);
		cq.select(cb.count(from));
		TypedQuery<Long> q = em.createQuery(cq);
		return q.getSingleResult();
	}

	@Override
	public List<Url> find(UrlFilter filter) {
		EntityManager em = getEntityManager();
		CriteriaBuilder cb = em.getCriteriaBuilder();

		CriteriaQuery<Url> cq = cb.createQuery(Url.class);

		Root<Url> from = cq.from(Url.class);

		// set selection
		cq.select(from);

		if (filter.getUrlCode() != null) {
			Predicate urlEqualCondition = cb.equal(from.get(Url_.urlCode), filter.getUrlCode());
			cq.where(urlEqualCondition);
		}

		// set sort params
		if (filter.getSortProperty() != null) {
			cq.orderBy(new OrderImpl(from.get(filter.getSortProperty()), filter.isSortOrder()));
		}

		TypedQuery<Url> q = em.createQuery(cq);

		// set paging
		if (filter.getOffset() != null && filter.getLimit() != null) {
			q.setFirstResult(filter.getOffset());
			q.setMaxResults(filter.getLimit());
		}

		// set execute query
		List<Url> allitems = q.getResultList();
		return allitems;
	}

	@Override
	public Url find(String urlCode) {
		EntityManager em = getEntityManager();

		CriteriaBuilder cb = em.getCriteriaBuilder();

		CriteriaQuery<Url> cq = cb.createQuery(Url.class);

		Root<Url> from = cq.from(Url.class);

		cq.select(from);
		Predicate urlEqualCondition = cb.equal(from.get(Url_.urlCode), urlCode);
		cq.where(urlEqualCondition);

		TypedQuery<Url> q = em.createQuery(cq);

		List<Url> allitems = q.getResultList();

		if (allitems.isEmpty()) {
			return null;
		} else if (allitems.size() == 1) {
			return allitems.get(0);
		} else {
			throw new IllegalArgumentException("more than 1 user found ");
		}
	}

	@Override
	public Url getWithTags(Long id) {
		EntityManager em = getEntityManager();

		if (em.find(getEntityClass(), id) == null) {
			return null;
		};

		CriteriaBuilder cb = em.getCriteriaBuilder();

		CriteriaQuery<Url> cq = cb.createQuery(Url.class);

		Root<Url> from = cq.from(Url.class);

		// set selection
		cq.select(from);

		from.fetch(Url_.tags, JoinType.LEFT);

		cq.where(cb.equal(from.get(Url_.id), id));
		cq.distinct(true);

		TypedQuery<Url> q = em.createQuery(cq);

		// set execute query
		return q.getSingleResult();
	}

}
