package by.lskrashchuk.test.shrtly.dataaccess.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import by.lskrashchuk.test.shrtly.dataaccess.TagDao;
import by.lskrashchuk.test.shrtly.dataaccess.filters.TagFilter;
import by.lskrashchuk.test.shrtly.datamodel.Tag;

@Repository
public class TagDaoImpl extends AbstractDaoImpl<Tag, Long> implements TagDao{

	protected TagDaoImpl() {
		super(Tag.class);
	}

	@Override
	public Long count(TagFilter filter) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Tag> find(TagFilter filter) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Tag find(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Tag getWithUrls(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

}
