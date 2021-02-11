package model.json;


/**
 * Represents a session
 */
public class Session {
    private String sessionId;

    public Session(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getSessionId() {
        return sessionId;
    }
}
