package com.mutti.google.calendar.generated;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;

@Generated("com.googlecode.jsonschema2pojo")
public class EventList {

    @Expose
    private String kind;
    @Expose
    private String etag;
    @Expose
    private String updated;
    @Expose
    private String timeZone;
    @Expose
    private String accessRole;
    @Expose
    private List<DefaultReminder> defaultReminders = new ArrayList<DefaultReminder>();
    @Expose
    private List<Item> items = new ArrayList<Item>();

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getEtag() {
        return etag;
    }

    public void setEtag(String etag) {
        this.etag = etag;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public String getAccessRole() {
        return accessRole;
    }

    public void setAccessRole(String accessRole) {
        this.accessRole = accessRole;
    }

    public List<DefaultReminder> getDefaultReminders() {
        return defaultReminders;
    }

    public void setDefaultReminders(List<DefaultReminder> defaultReminders) {
        this.defaultReminders = defaultReminders;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

}
