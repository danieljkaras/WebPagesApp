package com.daniel.main;

import com.daniel.dao.WebPagesDao;
import com.daniel.model.WebPage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DownloadQueue {

    private final Logger logger = LoggerFactory.getLogger(getClass().getName());
    private ExecutorService executorService = Executors.newSingleThreadExecutor();
    private static final DownloadQueue INSTANCE = new DownloadQueue();

    private DownloadQueue() {
    }

    public void enqueue(WebPage page, WebPagesDao webPagesDao) {
        executorService.submit(() -> {
            try {
                page.downloadContent();
                logger.info("Website content was downloaded correct.");
            } catch (IOException e) {
                logger.error("Website  pushed to query is not exist. ", e.getMessage());
            }
            webPagesDao.save(page);
            logger.info("WebPage is save to database succesfully.");
            return page;
        });
    }
    public static DownloadQueue getInstance() {
        return INSTANCE;
    }
}
