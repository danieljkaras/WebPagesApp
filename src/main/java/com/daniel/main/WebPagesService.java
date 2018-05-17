package com.daniel.main;


import com.daniel.dao.WebPagesDao;
import com.daniel.model.WebPage;
import org.hibernate.annotations.MetaValue;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.LocalDate;
import java.util.List;


@Path("/pages")

public class WebPagesService {

    @EJB
    private WebPagesDao webPagesDao;

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public WebPage getWebPage(@PathParam("id") Long id) {
        return webPagesDao.findById(id);
    }

    @GET
    @Path("/all")
    public List<WebPage> getAllRecords() {
        return webPagesDao.findAll();
    }

    @GET
    @Path("/test")
    public Response webPagesList() {

        return Response.ok("Lista wszystkich dodanych url " + "+ coś tam jeszcze + w końcu udało się wszystko skonfigurować. ").build();
    }

    @GET
    @Path("/getUserAgent")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAgent(@HeaderParam("user-agent") String userAgent) {

        return Response.ok("You are using: [ " + userAgent + "]").build();
    }

    @GET
    @Path("/getAllUrls")
    public List<String> getAllUrls() {
        return webPagesDao.findAllUrls();
    }

}
