package utils.DTO;

import components.core.Request;
import components.status_enums.RequestStatus;

public final class RequestMapper {
    private RequestMapper(){}

    public static RequestResponse toResponse(Request r) {
        if (r == null) return null;
        RequestResponse resp = new RequestResponse();
        resp.id = r.getId();
        resp.bookTitle = r.getBookTitle();
        resp.status = r.getStatus() != null ? r.getStatus().name() : null;
        return resp;
    }

    public static Request toEntity(RequestRequest req) {
        if (req == null) return null;
        int id = req.id != null ? req.id : 0;
        Request r = new Request(id, req.bookTitle);
        if (req.status != null) {
            try { r.setStatus(RequestStatus.valueOf(req.status)); } catch (IllegalArgumentException ignored) {}
        }
        return r;
    }
}