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

import by.lskrashchuk.test.shrtly.dataaccess.TagDao;
import by.lskrashchuk.test.shrtly.dataaccess.filters.TagFilter;
import by.lskrashchuk.test.shrtly.datamodel.Tag;
import by.lskrashchuk.test.shrtly.datamodel.Tag_;

@Repository
public class TagDaoImpl extends AbstractDaoImpl<Tag, Long> implements TagDao{

	protected TagDaoImpl() {
		super(Tag.class);
	}

	@Override
	public Long count(TagFilter filter) {
		EntityManager em = getEntityManager();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Long> cq = cb.createQuery(Long.class);
		Root<Tag> from = cq.from(Tag.class);
		cq.select(cb.count(from));
		TypedQuery<Long> q = em.createQuery(cq);
		return q.getSingleResult();
	}

	@Override
	public List<Tag> find(TagFilter filter) {
		EntityManager em = getEntityManager();
		CriteriaBuilder cb = em.getCriteriaBuilder();

		CriteriaQuery<Tag> cq = cb.createQuery(Tag.class);

		Root<Tag> from = cq.from(Tag.class);

		// set selection
		cq.select(from);

		if (filter.getName() != null) {
			Predicate fNameEqualCondition = cb.equal(from.get(Tag_.name), filter.getName());
			cq.where(fNameEqualCondition);
		}

		// set sort params
		if (filter.getSortProperty() != null) {
			cq.orderBy(new OrderImpl(from.get(filter.getSortProperty()), filter.isSortOrder()));
		}

		TypedQuery<Tag> q = em.createQuery(cq);

		// set paging
		if (filter.getOffset() != null && filter.getLimit() != null) {
			q.setFirstResult(filter.getOffset());
			q.setMaxResults(filter.getLimit());
		}

		// set execute query
		List<Tag> allitems = q.getResultList();
		return allitems;
	}

	@Override
	public Tag find(String name) {
		EntityManager em = getEntityManager();

		CriteriaBuilder cb = em.getCriteriaBuilder();

		CriteriaQuery<Tag> cq = cb.createQuery(Tag.class);

		Root<Tag> from = cq.from(Tag.class);

		cq.select(from);
		Predicate nameEqualCondition = cb.equal(from.get(Tag_.name), name);
		cq.where(nameEqualCondition);

		TypedQuery<Tag> q = em.createQuery(cq);

		List<Tag> allitems = q.getResultList();

		if (allitems.isEmpty()) {
			return null;
		} else if (allitems.size() == 1) {
			return allitems.get(0);
		} else {
			throw new IllegalArgumentException("more than 1 user found ");
		}
	}

	@Override
	public Tag getWithUrls(Long id) {
		EntityManager em = getEntityManager();

		if (em.find(getEntityClass(), id) == null) {
			return null;
		};

		CriteriaBuilder cb = em.getCriteriaBuilder();

		CriteriaQuery<Tag> cq = cb.createQuery(Tag.class);

		Root<Tag> from = cq.from(Tag.class);

		// set selection
		cq.select(from);

		from.fetch(Tag_.urls, JoinType.LEFT);

		cq.where(cb.equal(from.get(Tag_.id), id));
		cq.distinct(true);

		TypedQuery<Tag> q = em.createQuery(cq);

		// set execute query
		return q.getSingleResult();
	}

}
