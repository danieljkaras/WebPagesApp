package com.daniel.dao;

import com.daniel.model.WebPage;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.ws.rs.QueryParam;
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

    public WebPage findByUrl(String url) {
        final TypedQuery<WebPage> query = entityManager.createQuery("SELECT s FROM WebPage s WHERE s.url = :url ORDER BY s.id DESC", WebPage.class);
        query.setParameter("url", url);
        query.setMaxResults(1);
        return query.getSingleResult();
    }

    public WebPage findById(Long id) {
        final TypedQuery<WebPage> query = entityManager.createQuery("SELECT w FROM WebPage w WHERE w.id = :id", WebPage.class);
        query.setParameter("id", id);
        return query.getSingleResult();
    }

    public List<String> findAllUrls() {
        final Query query = entityManager.createNativeQuery("SELECT url FROM web_page;");
        List<String> urls = query.getResultList();
        return urls;
    }

    public List<String> findByValueContent(String likeContent) {
        final Query query = entityManager.createQuery("SELECT w.url FROM WebPage w " + "WHERE w.content LIKE :content");
        query.setParameter("content", "%" + likeContent + "%");
        return query.getResultList();
    }
}
