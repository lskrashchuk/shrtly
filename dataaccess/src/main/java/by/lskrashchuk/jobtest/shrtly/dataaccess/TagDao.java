package by.lskrashchuk.jobtest.shrtly.dataaccess;

import java.util.List;

import by.lskrashchuk.jobtest.shrtly.dataaccess.filters.TagFilter;
import by.lskrashchuk.jobtest.shrtly.datamodel.Tag;

public interface TagDao extends AbstractDao<Tag, Long>{

	Long count(TagFilter filter);

	List<Tag> find(TagFilter filter);
	
	Tag find(String name);
	
	Tag getWithUrls(Long id);

}
