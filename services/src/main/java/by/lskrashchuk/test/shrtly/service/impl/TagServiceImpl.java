package by.lskrashchuk.test.shrtly.service.impl;

import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import by.lskrashchuk.test.shrtly.dataaccess.TagDao;
import by.lskrashchuk.test.shrtly.dataaccess.UrlDao;
import by.lskrashchuk.test.shrtly.datamodel.Tag;
import by.lskrashchuk.test.shrtly.datamodel.Url;
import by.lskrashchuk.test.shrtly.service.TagService;

@Service
public class TagServiceImpl implements TagService {
	private static Logger LOGGER = LoggerFactory.getLogger(TagServiceImpl.class);

	@Inject
	private TagDao tagDao;

	@Inject
	private UrlDao urlDao;

	@Override
	public void insert(Tag tag) {
		tagDao.insert(tag);
		LOGGER.info("Tag inserted: {}", tag.getName());
	}

	@Override
	public Tag find(String name) {
		return tagDao.find(name);
	}

	@Override
	public void delete(Tag tag) {
		Tag fullTag = tagDao.getWithUrls(tag.getId());
		if (fullTag.getUrls().size() == 0) {
/*			for (Url url : fullTag.getUrls()) {
				for (Tag t : url.getTags()) {
					if (t.getName() == fullTag.getName()) {
						url.getTags().remove(t.getId());
						url.setTags(url.getTags());
					}
				}
			}*/
			tagDao.delete(tag.getId());
			LOGGER.info("Tag deleted: {}", tag.getName());
		}
	}

}
