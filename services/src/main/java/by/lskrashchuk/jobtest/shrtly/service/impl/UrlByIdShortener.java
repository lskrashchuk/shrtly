package by.lskrashchuk.jobtest.shrtly.service.impl;

import by.lskrashchuk.jobtest.shrtly.datamodel.Url;
import by.lskrashchuk.jobtest.shrtly.service.UrlShortener;

public class UrlByIdShortener implements UrlShortener{

	@Override
	public String getCode(Url url) {
		String result = Integer.toString(url.getId().intValue()*1000, 24);
		return result;
	}

}
