package by.lskrashchuk.jobtest.shrtly.service;

import by.lskrashchuk.jobtest.shrtly.datamodel.Url;

public interface UrlShortener {
	
	String getCode (Url url);
}
