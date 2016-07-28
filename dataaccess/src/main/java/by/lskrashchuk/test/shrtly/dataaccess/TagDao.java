package by.lskrashchuk.test.shrtly.dataaccess;

import java.util.List;

import by.lskrashchuk.test.shrtly.dataaccess.filters.TagFilter;
import by.lskrashchuk.test.shrtly.datamodel.Tag;

public interface TagDao extends AbstractDao<Tag, Long>{

	Long count(TagFilter filter);

	List<Tag> find(TagFilter filter);
	
	Tag find(String name);
	
	Tag getWithUrls(Long id);

}
