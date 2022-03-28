package com.amazon.ata.kindlepublishingservice.activity;

import com.amazon.ata.kindlepublishingservice.dao.PublishingStatusDao;
import com.amazon.ata.kindlepublishingservice.dynamodb.models.PublishingStatusItem;
import com.amazon.ata.kindlepublishingservice.models.PublishingStatusRecord;
import com.amazon.ata.kindlepublishingservice.models.requests.GetPublishingStatusRequest;
import com.amazon.ata.kindlepublishingservice.models.response.GetPublishingStatusResponse;
import com.amazonaws.services.lambda.runtime.Context;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class GetPublishingStatusActivity {
    private PublishingStatusDao publishingStatusDao;
    @Inject
    public GetPublishingStatusActivity(PublishingStatusDao publishingStatusDao) {
        this.publishingStatusDao = publishingStatusDao;
    }

    public GetPublishingStatusResponse execute(GetPublishingStatusRequest publishingStatusRequest) {
        List<PublishingStatusItem> items = publishingStatusDao.getPublishingStatuses(publishingStatusRequest.getPublishingRecordId());
        List<PublishingStatusRecord> records = new ArrayList<>();

        for (PublishingStatusItem item : items) {
            PublishingStatusRecord record = PublishingStatusRecord.builder()
                    .withStatus(item.getStatus().toString())
                    .withStatusMessage(item.getStatusMessage())
                    .withBookId(item.getBookId())
                    .build();
            records.add(record);
        }

        return GetPublishingStatusResponse.builder().withPublishingStatusHistory(records).build();
    }
}
