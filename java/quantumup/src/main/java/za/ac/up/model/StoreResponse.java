package za.ac.up.model;

public class StoreResponse {
    public StoreResponse() {
    }

    public StoreResponse(Boolean successful, String reason) {
        this.successful = successful;
        this.reason = reason;
    }

    public Boolean getSuccessful() {
        return successful;
    }

    public void setSuccessful(Boolean successful) {
        this.successful = successful;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    private Boolean successful;
    private String reason;
}
