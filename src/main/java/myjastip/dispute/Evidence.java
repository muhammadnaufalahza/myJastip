package myjastip.dispute;

import java.time.LocalDateTime;

public class Evidence {
    private String evidenceId;
    private String userId;
    private String desctiption;
    private LocalDateTime evidenceTime;

    public Evidence(String evidenceId, String userId, String desctiption) {
        this.evidenceId = evidenceId;
        this.userId = userId;
        this.desctiption = desctiption;
        this.evidenceTime = LocalDateTime.now();
    }

    public String getEvidenceId() {
        return evidenceId;
    }

    public void setEvidenceId(String evidenceId) {
        this.evidenceId = evidenceId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDesctiption() {
        return desctiption;
    }

    public void setDesctiption(String desctiption) {
        this.desctiption = desctiption;
    }

    public LocalDateTime getEvidenceTime() {
        return evidenceTime;
    }

    public void setEvidenceTime(LocalDateTime evidenceTime) {
        this.evidenceTime = evidenceTime;
    }
}
