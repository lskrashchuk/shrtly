package by.lskrashchuk.test.shrtly.service.impl;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import by.lskrashchuk.test.shrtly.dataaccess.UrlDao;
import by.lskrashchuk.test.shrtly.service.UrlShortener;

@Service
public class SimpleUrlShortener implements UrlShortener {

	@Inject
	private UrlDao urlDao;

	@Override
	public String getCode(String fullUrl) {
		// random int 10000..99999
		String result;
		do {
			int i = 10000 + (int) (Math.random() * ((99999 - 10000) + 1));
			result = Integer.toString(i);
		} while (urlDao.find(result) != null);

		return result;
	}

}
