package by.lskrashchuk.test.shrtly.service;

import java.util.List;

import javax.transaction.Transactional;

import by.lskrashchuk.test.shrtly.datamodel.Tag;

public interface TagService {

	@Transactional
	void delete(Tag tag);

	@Transactional
	void insert(Tag tag);

    Tag find(String name);
    
    Tag getWithUrls(Tag tag);
    
    List<Tag> getAll();

}
