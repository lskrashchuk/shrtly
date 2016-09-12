package by.lskrashchuk.jobtest.shrtly.dataaccess.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import by.lskrashchuk.jobtest.shrtly.dataaccess.TagDao;
import by.lskrashchuk.jobtest.shrtly.dataaccess.filters.TagFilter;
import by.lskrashchuk.jobtest.shrtly.datamodel.Tag;
import by.lskrashchuk.jobtest.shrtly.datamodel.Tag_;

@Repository
public class TagDaoImpl extends AbstractDaoImpl<Tag, Long> implements TagDao {

	@Override
	public Long count(TagFilter filter) {
		return (Long) getSession().createCriteria(Tag.class).setProjection(Projections.rowCount()).uniqueResult();
	}

	@Override
	public Tag find(String name) {
		Criteria criteria = getSession().createCriteria(Tag.class);
		criteria.add(Restrictions.eq(Tag_.name.getName(), name));

		List<Tag> results = criteria.list();

		if (results.isEmpty()) {
			return null;
		} else if (results.size() == 1) {
			return results.get(0);
		} else {
			throw new IllegalArgumentException("more than 1 tag found ");
		}

	}

	@Override
	public Tag getWithUrls(Long id) {
		List<Tag> results = getSession().createCriteria(Tag.class).add(Restrictions.eq(Tag_.id.getName(), id))
				.setFetchMode(Tag_.urls.getName(), FetchMode.JOIN).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
				.list();

		if (results.isEmpty()) {
			return null;
		} else if (results.size() == 1) {
			return results.get(0);
		} else {
			throw new IllegalArgumentException("more than 1 tag found ");
		}

	}

}
