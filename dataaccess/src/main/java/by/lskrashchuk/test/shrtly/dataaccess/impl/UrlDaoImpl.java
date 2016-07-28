package by.lskrashchuk.test.shrtly.dataaccess.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import by.lskrashchuk.test.shrtly.dataaccess.UrlDao;
import by.lskrashchuk.test.shrtly.dataaccess.filters.UrlFilter;
import by.lskrashchuk.test.shrtly.datamodel.Url;

@Repository
public class UrlDaoImpl extends AbstractDaoImpl<Url, Long> implements UrlDao{

	protected UrlDaoImpl() {
		super(Url.class);
	}

	@Override
	public Long count(UrlFilter filter) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Url> find(UrlFilter filter) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Url find(String urlCode) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Url getWithTags(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

}
