package by.lskrashchuk.test.shrtly.service.impl;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import by.lskrashchuk.test.shrtly.dataaccess.TagDao;
import by.lskrashchuk.test.shrtly.dataaccess.UrlDao;
import by.lskrashchuk.test.shrtly.dataaccess.filters.UrlFilter;
import by.lskrashchuk.test.shrtly.datamodel.Tag;
import by.lskrashchuk.test.shrtly.datamodel.Url;
import by.lskrashchuk.test.shrtly.service.TagService;
import by.lskrashchuk.test.shrtly.service.UrlService;

@Service
public class UrlServiceImpl implements UrlService{
	private static Logger LOGGER = LoggerFactory.getLogger(UrlServiceImpl.class);
	
	@Inject
	private UrlDao urlDao;

	@Inject
	private TagService tagService;

	@Override
	public Url getUrl(Long id) {
		return urlDao.get(id);
	}

	@Override
	public void saveOrUpdate(Url url) {
		if (url.getId() == null) {
			url.setCreated(new Date());
			urlDao.insert(url);
			LOGGER.info("Url inserted: {}", url.getUrlCode());
		} else {
			urlDao.update(url);
			LOGGER.info("Url updated: {}", url.getUrlCode());
		}
	}

	@Override
	public void delete(Url url) {
		Url fullUrl = urlDao.getWithTags(url.getId());
		urlDao.delete(url.getId());
		for (Tag tag : fullUrl.getTags()) {
			tagService.delete(tag);
		}
		LOGGER.info("Url deleted: {}", url.getUrlCode());
	}

	@Override
	public List<Url> find(UrlFilter filter) {
		return urlDao.find(filter);
	}

	@Override
	public Url find(String urlCode) {
		return urlDao.find(urlCode);
	}

	@Override
	public List<Url> getAll() {
		return urlDao.getAll();
	}

	@Override
	public Long count(UrlFilter filter) {
		return urlDao.count(filter);
	}

	@Override
	public Url getUrlWithTags(Long id) {
		return urlDao.getWithTags(id);
	}

	@Override
	public String redirect(Url url) {
		if (url.getClicks()==null) {
			url.setClicks(0);
		}
		url.setClicks(url.getClicks()+1);
		saveOrUpdate(url);
		return url.getFullUrl();
	}

}
