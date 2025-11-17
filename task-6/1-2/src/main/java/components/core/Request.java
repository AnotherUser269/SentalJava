package components.core;

import components.status_enums.RequestStatus;
import utils.CsvHelper;

public class Request {
    private RequestStatus status = RequestStatus.Opened;
    private final int id;
    private final String bookTitle;

    public Request(int id, String bookTitle) {
        this.id = id;
        this.bookTitle = bookTitle;
    }

    public RequestStatus getStatus() {
        return status;
    }

    public void setStatus(RequestStatus newStatus) {
        this.status = newStatus;
    }

    public int getId() {
        return id;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public String toCsv(char delimiter) {
        return CsvHelper.escapeCsv(String.valueOf(id)) + delimiter +
                CsvHelper.escapeCsv(bookTitle) + delimiter +
                CsvHelper.escapeCsv(String.valueOf(status));
    }

    @Override
    public String toString() {
        return "Request{" +
                "id=" + id +
                ", bookTitle='" + bookTitle + '\'' +
                ", status=" + status +
                '}';
    }
}
