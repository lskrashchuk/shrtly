package by.lskrashchuk.test.shrtly.service;

import java.util.List;

import javax.transaction.Transactional;

import by.lskrashchuk.test.shrtly.dataaccess.filters.UrlFilter;
import by.lskrashchuk.test.shrtly.datamodel.Url;

public interface UrlService {

	Url getUrl(Long id);
	
	@Transactional
	void saveOrUpdate(Url url);
	
	@Transactional
	void delete(Url url);
	
    List<Url> find(UrlFilter filter);

    Url find(String urlCode);

    List<Url> getAll();
    
    Long count(UrlFilter filter);
    
}
