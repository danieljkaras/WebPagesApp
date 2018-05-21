package com.daniel.main;

import com.daniel.dao.WebPagesDao;
import com.daniel.model.WebPage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/pages")
public class WebPagesService {

    @EJB
    private WebPagesDao webPagesDao;

    private Logger logger = LoggerFactory.getLogger(getClass().getName());

    @GET
    @Path("/url/{url}")
    @Produces(MediaType.TEXT_HTML)
    public String getWebPagebyUrl(@PathParam("url") String url) {
        return webPagesDao.findByUrl(url).getContent();
    }

    @GET
    @Path("/id/{id}")
    @Produces(MediaType.TEXT_HTML)
    public String getWebPageById(@PathParam("id") Long id) {
        return webPagesDao.findById(id).getContent();
    }

    @GET
    @Path("/url-all")
    @Produces(MediaType.TEXT_HTML)
    public String getAllUrls() {
        String queryResult = webPagesDao.findAllUrls().toString();
        logger.info("All url's from database was loaded with success |||  " + logger.getName());
        return queryResult;
    }

    @GET
    @Path("/like/{like}")
    @Produces(MediaType.TEXT_HTML)
    public String getWebPageByLikeValue(@PathParam("like") String like) {
        String queryLikeResult = webPagesDao.findByValueContent(like).toString();
        logger.info("Content was found with success. |||  " + logger.getName());
        return queryLikeResult;
    }

    @PUT
    @Path("/{url}")
    public Response addToDataBase(@PathParam("url") String url) {
        WebPage webPage = new WebPage();
        webPage.setUrl(url);
        DownloadQueue.getInstance().enqueue(webPage, webPagesDao);
        logger.info("Web page URL enqueued for download and database persistance. |||  " + logger.getName());
        return Response.ok().build();
    }
}
