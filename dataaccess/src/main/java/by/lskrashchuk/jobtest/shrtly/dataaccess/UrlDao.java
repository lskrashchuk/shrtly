package by.lskrashchuk.jobtest.shrtly.dataaccess;

import java.util.List;

import by.lskrashchuk.jobtest.shrtly.dataaccess.filters.UrlFilter;
import by.lskrashchuk.jobtest.shrtly.datamodel.Url;

public interface UrlDao extends GenericDao<Url, Long> {

	Long count(UrlFilter filter);

	List<Url> find(UrlFilter filter);
	
	Url find(String urlCode);
	
	Url getWithTags(Long id);


}
