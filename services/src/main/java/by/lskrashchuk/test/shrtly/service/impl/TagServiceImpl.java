package by.lskrashchuk.test.shrtly.service.impl;

import java.util.Date;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import by.lskrashchuk.test.shrtly.dataaccess.TagDao;
import by.lskrashchuk.test.shrtly.datamodel.Tag;
import by.lskrashchuk.test.shrtly.service.TagService;

@Service
public class TagServiceImpl implements TagService{
	private static Logger LOGGER = LoggerFactory.getLogger(TagServiceImpl.class);
	
	@Inject
	private TagDao tagDao;

	@Override
	public void saveOrUpdate(Tag tag) {
		if (tag.getId() == null) {
			tagDao.insert(tag);
			LOGGER.info("Tag inserted: {}", tag.getName());
		} else {
			tagDao.update(tag);
			LOGGER.info("Tag updated: {}", tag.getName());
		}
		
	}

	@Override
	public Tag find(String name) {
		return tagDao.find(name);
	}

}
