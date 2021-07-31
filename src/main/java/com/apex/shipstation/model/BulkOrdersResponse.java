package com.apex.shipstation.model;

import java.util.List;

public class BulkOrdersResponse {

    private boolean hasErrors;
    private List<OrderResponse> results;
    private String message;

    public boolean isHasErrors() {
        return hasErrors;
    }

    public void setHasErrors(boolean hasErrors) {
        this.hasErrors = hasErrors;
    }

    public List<OrderResponse> getResults() {
        return results;
    }

    public void setResults(List<OrderResponse> results) {
        this.results = results;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String m) {
        this.message = m;
    }
}
