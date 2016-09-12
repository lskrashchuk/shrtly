package by.lskrashchuk.jobtest.shrtly.dataaccess;

import by.lskrashchuk.jobtest.shrtly.dataaccess.filters.TagFilter;
import by.lskrashchuk.jobtest.shrtly.datamodel.Tag;

public interface TagDao extends GenericDao<Tag, Long>{

	Long count(TagFilter filter);

	Tag find(String name);
	
	Tag getWithUrls(Long id);

}
