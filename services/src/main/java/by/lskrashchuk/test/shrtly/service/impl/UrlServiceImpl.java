package by.lskrashchuk.test.shrtly.service.impl;

import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import by.lskrashchuk.test.shrtly.dataaccess.UrlDao;
import by.lskrashchuk.test.shrtly.dataaccess.filters.UrlFilter;
import by.lskrashchuk.test.shrtly.datamodel.Url;
import by.lskrashchuk.test.shrtly.service.UrlService;

@Service
public class UrlServiceImpl implements UrlService{
	private static Logger LOGGER = LoggerFactory.getLogger(UrlServiceImpl.class);
	
	@Inject
	private UrlDao urlDao;

	@Override
	public Url getUrl(Long id) {
		return urlDao.get(id);
	}

	@Override
	public void saveOrUpdate(Url url) {
		if (url.getId() == null) {
			urlDao.insert(url);
			LOGGER.info("Url inserted: {}", url);
		} else {
			urlDao.update(url);
			LOGGER.info("Url updated: {}", url);
		}
	}

	@Override
	public void delete(Url url) {
		urlDao.delete(url.getId());
		LOGGER.info("User deleted: {}", url);
	}

	@Override
	public List<Url> find(UrlFilter filter) {
		return urlDao.find(filter);
	}

	@Override
	public List<Url> getAll() {
		return urlDao.getAll();
	}

	@Override
	public Long count(UrlFilter filter) {
		return urlDao.count(filter);
	}

}
