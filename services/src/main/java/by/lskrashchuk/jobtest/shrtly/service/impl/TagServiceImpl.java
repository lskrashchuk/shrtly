package by.lskrashchuk.jobtest.shrtly.service.impl;

import java.util.List;

import javax.inject.Inject;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import by.lskrashchuk.jobtest.shrtly.dataaccess.TagDao;
import by.lskrashchuk.jobtest.shrtly.datamodel.Tag;
import by.lskrashchuk.jobtest.shrtly.service.TagService;

@Transactional
@Service
public class TagServiceImpl implements TagService {
	private static Logger LOGGER = LoggerFactory.getLogger(TagServiceImpl.class);

	@Inject
	private TagDao tagDao;

	@Override
	public void insert(Tag tag) {
		tagDao.save(tag);
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
			tagDao.delete(tag.getId());
			LOGGER.info("Tag deleted: {}", tag.getName());
		}
	}

	@Override
	public Tag getWithUrls(Tag tag) {
		return tagDao.getWithUrls(tag.getId());
	}

	@Override
	public List<Tag> getAll() {
		return tagDao.findAll();
	}

}
