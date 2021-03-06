package by.lskrashchuk.jobtest.shrtly.webapp.page.links.panel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

import javax.inject.Inject;
import javax.persistence.PersistenceException;
import javax.persistence.metamodel.SingularAttribute;

import org.apache.wicket.datetime.markup.html.basic.DateLabel;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.OrderByBorder;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.SortOrder;
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.ExternalLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigator;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import by.lskrashchuk.jobtest.shrtly.dataaccess.filters.UrlFilter;
import by.lskrashchuk.jobtest.shrtly.datamodel.Tag;
import by.lskrashchuk.jobtest.shrtly.datamodel.Url;
import by.lskrashchuk.jobtest.shrtly.datamodel.Url_;
import by.lskrashchuk.jobtest.shrtly.service.UrlService;
import by.lskrashchuk.jobtest.shrtly.webapp.app.WicketApplication;
import by.lskrashchuk.jobtest.shrtly.webapp.page.links.LinkEditPage;
import by.lskrashchuk.jobtest.shrtly.webapp.page.links.LinksPage;
import by.lskrashchuk.jobtest.shrtly.webapp.page.redirect.RealUrlRedirectorPage;


public class LinkListPanel extends Panel{
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
    private UrlService urlService;
	
	private UrlFilter urlFilter;

	public LinkListPanel(String id, UrlFilter urlFilter) {
		super(id);
		
		this.urlFilter = urlFilter;
		
        LinksDataProvider linksDataProvider = new LinksDataProvider();
        DataView<Url> dataView = new DataView<Url>("rows", linksDataProvider, 5) {
            /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
            protected void populateItem(Item<Url> item) {
                Url url = item.getModelObject();

                item.add(new ExternalLink("fullUrl", url.getFullUrl(),url.getFullUrl()));

                Link<Void> el = new Link<Void>("urlCode") {
					/**
					 * 
					 */
					private static final long serialVersionUID = 1L;

					@Override
					public void onClick() {
						PageParameters params = new PageParameters();
						params.set("urlCode", url.getUrlCode());
			       		setResponsePage(new RealUrlRedirectorPage(params));
					}
                };
                el.add(new Label("linktext", Model.of(WicketApplication.getDomainName()+WicketApplication.URL_ADDITIONAL_NAME+"/"+url.getUrlCode())));
                item.add(el);
                item.add(DateLabel.forDatePattern("created", Model.of(url.getCreated()), "dd-MM-yyyy"));
                item.add(new Label("clicks", url.getClicks()));

                item.add(new Link<Void>("edit-link") {
                    /**
					 * 
					 */
					private static final long serialVersionUID = 1L;

					@Override
                    public void onClick() {
		        		setResponsePage(new LinkEditPage(urlService.getUrlWithTags(url.getId()), new ArrayList<Tag>()));
                    }
                });

                item.add(new Link<Void>("delete-link") {
                    /**
					 * 
					 */
					private static final long serialVersionUID = 1L;

					@Override
                    public void onClick() {
                        try {
                            urlService.delete(url);
                        } catch (PersistenceException e) {
                            System.out.println("caughth persistance exception");
                        }

                        setResponsePage(new LinksPage());
                    }
                });

            }
        };
        add(dataView);
        add(new PagingNavigator("paging", dataView));

        add(new OrderByBorder("sort-fullUrl", Url_.fullUrl, linksDataProvider));
        add(new OrderByBorder("sort-urlCode", Url_.urlCode, linksDataProvider));
        add(new OrderByBorder("sort-created", Url_.created, linksDataProvider));
        add(new OrderByBorder("sort-clicks", Url_.clicks, linksDataProvider));
	}


    private class LinksDataProvider extends SortableDataProvider<Url, Serializable> {

        /**
		 * 
		 */
		private static final long serialVersionUID = 1L;

        public LinksDataProvider() {
            super();
            setSort((Serializable) Url_.created, SortOrder.DESCENDING);
        }

        @Override
        public Iterator<Url> iterator(long first, long count) {
            Serializable property = getSort().getProperty();
            SortOrder propertySortOrder = getSortState().getPropertySortOrder(property);

            urlFilter.setSortProperty((SingularAttribute) property);
            urlFilter.setSortOrder(propertySortOrder.equals(SortOrder.ASCENDING) ? true : false);

            urlFilter.setLimit((int) count);
            urlFilter.setOffset((int) first);
            return urlService.find(urlFilter).iterator();
        }

        @Override
        public long size() {
            return urlService.count(urlFilter);
        }

        @Override
        public IModel<Url> model(Url object) {
            return new Model<Url>(object);
        }

    }


	public UrlFilter getUrlFilter() {
		return urlFilter;
	}


	public void setUrlFilter(UrlFilter urlFilter) {
		this.urlFilter = urlFilter;
	}



}
