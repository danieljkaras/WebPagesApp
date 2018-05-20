package com.daniel.main;

import com.daniel.dao.WebPagesDao;
import com.daniel.model.WebPage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.Singleton;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Singleton
public class DownloadQueue {

    private final Logger logger = LoggerFactory.getLogger(getClass().getName());
    private ExecutorService executorService = Executors.newSingleThreadExecutor();
    private static final DownloadQueue INSTANCE = new DownloadQueue();

    private DownloadQueue() {
    }

    public void enqueue(WebPage page, WebPagesDao webPagesDao) {
        executorService.submit(() -> {
            try {
                page.downloadHtmlContent();
                logger.info("Website content was downloaded correctly.");
            } catch (IOException e) {
                logger.error("Website  pushed to query does not exist. ", e.getMessage());
                throw e;
            }
            webPagesDao.save(page);
            logger.info("WebPage is saved to database successfully.");
            logger.info("Executor service task is done");
            return page;
        });
    }

    public static DownloadQueue getInstance() {
        return INSTANCE;
    }
}
