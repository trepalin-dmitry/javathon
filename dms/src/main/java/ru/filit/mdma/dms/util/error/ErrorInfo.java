package ru.filit.mdma.dms.util.error;

public class ErrorInfo {

    private final String timestamp;
    private final int status;
    private final String message;
    private final String path;

    public ErrorInfo(String timestamp, int status, String message, String path) {
        this.timestamp = timestamp;
        this.status = status;
        this.message = message;
        this.path = path;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public String getPath() {
        return path;
    }
}
