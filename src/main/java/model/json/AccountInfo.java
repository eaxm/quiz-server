package model.json;

import java.sql.Timestamp;

public class AccountInfo {
    private String username;
    private Timestamp registerDate;

    public AccountInfo(String username, Timestamp registerDate) {
        this.username = username;
        this.registerDate = registerDate;
    }
}
