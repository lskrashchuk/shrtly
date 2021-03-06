package by.lskrashchuk.jobtest.shrtly.service.impl;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import by.lskrashchuk.jobtest.shrtly.dataaccess.UrlDao;
import by.lskrashchuk.jobtest.shrtly.dataaccess.filters.UrlFilter;
import by.lskrashchuk.jobtest.shrtly.datamodel.Url;
import by.lskrashchuk.jobtest.shrtly.service.TagService;
import by.lskrashchuk.jobtest.shrtly.service.UrlService;

@Transactional
@Service
public class UrlServiceImpl implements UrlService{
	private static Logger LOGGER = LoggerFactory.getLogger(UrlServiceImpl.class);
	
	@Inject
	private UrlDao urlDao;

	@Inject
	private TagService tagService;

	@Override
	public Url getUrl(Long id) {
		return urlDao.findById(id);

	}

	@Override
	public void saveOrUpdate(Url url) {
		if (url.getId() == null) {
			url.setCreated(new Date());
			urlDao.save(url);
			LOGGER.info("Url inserted: {}", url.getUrlCode());
		} else {
			urlDao.update(url);
			LOGGER.info("Url updated: {}", url.getUrlCode());
		}
	}

	@Override
	public void delete(Url url) {
//		Url fullUrl = urlDao.getWithTags(url.getId());
		urlDao.delete(url.getId());
//		for (Tag tag : fullUrl.getTags()) {
//			tagService.delete(tag);
//		}
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
		return urlDao.findAll();
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

	@Override
	public void checkIfClicksCountChangedBeforeUpdate(Url url) {
		if (url.getId() != null) {
			url.setClicks(find(url.getUrlCode()).getClicks());
		}
		saveOrUpdate(url);
	}

}
