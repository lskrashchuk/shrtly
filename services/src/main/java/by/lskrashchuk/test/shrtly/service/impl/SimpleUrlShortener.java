package by.lskrashchuk.test.shrtly.service.impl;

import javax.inject.Inject;

import by.lskrashchuk.test.shrtly.dataaccess.UrlDao;
import by.lskrashchuk.test.shrtly.dataaccess.filters.UrlFilter;
import by.lskrashchuk.test.shrtly.datamodel.Url;
import by.lskrashchuk.test.shrtly.service.UrlService;
import by.lskrashchuk.test.shrtly.service.UrlShortener;

public class SimpleUrlShortener implements UrlShortener{

    @Inject
	private UrlService urlService;
	
	public String getCode(String longUrl) {
		// random int 10000..99999
		String result;
//		UrlFilter urlFilter = new UrlFilter();
//		urlFilter.setLongUrl(longUrl);
//		do {
			int i = 10000 + (int) (Math.random() * ((99999 - 10000) + 1));
			result = Integer.toString(i);
//		} while (urlService.find(result) != null);

		return result;
	}

}
