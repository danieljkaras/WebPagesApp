package com.daniel.dao;

import com.daniel.model.WebPage;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Stateless
public class WebPagesDao {

    @PersistenceContext
    private EntityManager entityManager;

    public Long save(WebPage page) {
        entityManager.persist(page);
        return page.getId();
    }


    public WebPage update(WebPage page) {
        return entityManager.merge(page);
    }

    public void delete(Long id) {
        final WebPage webPage = entityManager.find(WebPage.class, id);

        if (webPage != null) {
            entityManager.remove(webPage);
        }
    }

    public WebPage findById(Long id) {
        return entityManager.find(WebPage.class, id);
    }

    public List<WebPage> findAll() {
        final Query query = entityManager.createQuery("SELECT s FROM WebPage s");

        return query.getResultList();
    }

    public List<String> findAllUrls() {
        final Query query = entityManager.createNativeQuery("SELECT url_info FROM web_page;");

        final List<String> urls = query.getResultList();
        return urls;
    }
}
