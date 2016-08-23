package by.lskrashchuk.test.shrtly.service;

import javax.transaction.Transactional;

import by.lskrashchuk.test.shrtly.datamodel.Tag;

public interface TagService {

	@Transactional
	void saveOrUpdate(Tag tag);

    Tag find(String name);

}
