package by.lskrashchuk.test.shrtly.service;

import javax.transaction.Transactional;

import by.lskrashchuk.test.shrtly.datamodel.Tag;
import by.lskrashchuk.test.shrtly.datamodel.Url;

public interface TagService {

	@Transactional
	void delete(Tag tag);

	@Transactional
	void insert(Tag tag);

    Tag find(String name);

}
