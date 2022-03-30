package com.amazon.ata.kindlepublishingservice.publishing;

import com.amazon.ata.kindlepublishingservice.dao.CatalogDao;
import com.amazon.ata.kindlepublishingservice.dao.PublishingStatusDao;
import com.amazon.ata.kindlepublishingservice.dynamodb.models.CatalogItemVersion;
import com.amazon.ata.kindlepublishingservice.enums.PublishingRecordStatus;
import com.amazon.ata.kindlepublishingservice.exceptions.BookNotFoundException;

import javax.inject.Inject;

public class BookPublishTask implements Runnable{
    private BookPublishRequestManager requestManager;
    private PublishingStatusDao publishingStatusDao;
    private CatalogDao catalogDao;

    @Inject
    public BookPublishTask(BookPublishRequestManager requestManager, PublishingStatusDao publishingStatusDao, CatalogDao catalogDao) {
        this.requestManager = requestManager;
        this.publishingStatusDao = publishingStatusDao;
        this.catalogDao = catalogDao;
    }

    @Override
    public void run() {
        BookPublishRequest request = requestManager.getBookPublishRequestToProcess();
        int i = 0;
        while (request == null && i < 500) {
            try {
                Thread.sleep(1000);
                request = requestManager.getBookPublishRequestToProcess();
                i++;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        publishingStatusDao.setPublishingStatus(request.getPublishingRecordId(), PublishingRecordStatus.IN_PROGRESS, request.getBookId());
        KindleFormattedBook formattedBook = KindleFormatConverter.format(request);
        try {
            CatalogItemVersion book = catalogDao.createOrUpdateBook(formattedBook);
            publishingStatusDao.setPublishingStatus(request.getPublishingRecordId(), PublishingRecordStatus.SUCCESSFUL, book.getBookId());
        } catch (BookNotFoundException e) {
            publishingStatusDao.setPublishingStatus(request.getPublishingRecordId(), PublishingRecordStatus.FAILED, request.getBookId(), e.getMessage());
        } catch (Exception e) {
            publishingStatusDao.setPublishingStatus(request.getPublishingRecordId(), PublishingRecordStatus.FAILED, request.getBookId(), e.getMessage());
        }
    }
}
