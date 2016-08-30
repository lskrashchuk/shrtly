package by.lskrashchuk.jobtest.shrtly.service;

import java.util.List;

import javax.transaction.Transactional;

import by.lskrashchuk.jobtest.shrtly.dataaccess.filters.UrlFilter;
import by.lskrashchuk.jobtest.shrtly.datamodel.Url;

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

	Url getUrlWithTags(Long id);
	
	@Transactional
	String redirect(Url url);

}
