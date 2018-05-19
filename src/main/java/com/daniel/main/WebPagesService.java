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
    @Path("/{url}")
    @Produces(MediaType.TEXT_HTML)
    public String getWebPagebyUrl(@PathParam("url") String url) {
        return webPagesDao.findByUrl(url).toString();
    }

    @GET
    @Path("/id/{id}")
    @Produces(MediaType.TEXT_HTML)
    public String getWebPageById(@PathParam("id") Long id) {
        return webPagesDao.findById(id).getContent();
    }

    @GET
    @Path("/getAllUrls")
    @Produces(MediaType.TEXT_HTML)
    public String getAllUrls() {
        logger.info("All url's from database was loaded with success", logger.getName());
        return webPagesDao.findAllUrls().toString();
    }

    @GET
    @Path("/like/{like}")
    @Produces(MediaType.TEXT_HTML)
    public String getWebPageByLikeValue(@PathParam("like") String like) {
        logger.info("Content was found with success.   |||" + logger.getName());
        return webPagesDao.findByValueContent(like).toString();
    }

    @PUT
    @Path("/{url}")
    public Response addToDataBase(@PathParam("url") String url) {
        WebPage webPage = new WebPage();
        webPage.setUrl(url);
        DownloadQueue.getInstance().enqueue(webPage, webPagesDao);
        logger.info("Web page is insert correct to database via PUT METHOD. :)", logger.getName());
        return Response.ok().build();
    }

    @POST
    @Path("/addPage/{url}")
    public Response addPostToDataBase(@PathParam("url") String url) {
        WebPage webPage = new WebPage();
        webPage.setUrl(url);
        DownloadQueue.getInstance().enqueue(webPage, webPagesDao);
        logger.info("Web page is insert correct to database via POST METHOD. :)", logger.getName());
        return Response.ok().build();
    }
}
