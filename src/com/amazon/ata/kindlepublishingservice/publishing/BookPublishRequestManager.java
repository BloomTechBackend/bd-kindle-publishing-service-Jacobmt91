package com.amazon.ata.kindlepublishingservice.publishing;

import javax.inject.Inject;
import java.util.Objects;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class BookPublishRequestManager {
    private Queue<BookPublishRequest> bookPublishRequestQueue;

    @Inject
    public BookPublishRequestManager() {
        this.bookPublishRequestQueue = new ConcurrentLinkedQueue<>();
    }

    public Queue<BookPublishRequest> getBookPublishRequestQueue() {
        Queue<BookPublishRequest> copyQueue = new ConcurrentLinkedQueue<>(this.bookPublishRequestQueue);
        return copyQueue;
    }

    public void setBookPublishRequestQueue(Queue<BookPublishRequest> bookPublishRequestQueue) {
        this.bookPublishRequestQueue = bookPublishRequestQueue;
    }

    public void addBookPublishRequest(BookPublishRequest bookPublishRequest) {
        bookPublishRequestQueue.add(bookPublishRequest);
    }

    public BookPublishRequest getBookPublishRequestToProcess() {
        return bookPublishRequestQueue.poll();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookPublishRequestManager that = (BookPublishRequestManager) o;
        return getBookPublishRequestQueue().equals(that.getBookPublishRequestQueue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getBookPublishRequestQueue());
    }
}
