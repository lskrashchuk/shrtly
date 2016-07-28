package by.lskrashchuk.test.shrtly.dataaccess;

import java.util.List;

import by.lskrashchuk.test.shrtly.dataaccess.filters.UrlFilter;
import by.lskrashchuk.test.shrtly.datamodel.Url;

public interface UrlDao extends AbstractDao<Url, Long> {

	Long count(UrlFilter filter);

	List<Url> find(UrlFilter filter);
	
	Url find(String urlCode);
	
	Url getWithTags(Long id);


}
