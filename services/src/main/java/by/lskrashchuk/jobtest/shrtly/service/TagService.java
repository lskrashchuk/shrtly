package by.lskrashchuk.jobtest.shrtly.service;

import java.util.List;

import javax.transaction.Transactional;

import by.lskrashchuk.jobtest.shrtly.datamodel.Tag;

public interface TagService {

	@Transactional
	void delete(Tag tag);

	@Transactional
	void insert(Tag tag);

    Tag find(String name);
    
    Tag getWithUrls(Tag tag);
    
    List<Tag> getAll();

}
