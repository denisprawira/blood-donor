package com.example.denisprawira.ta.User.Model.Event;

public class UnapprovedEvent {

    String id,idEvent,description;

    public UnapprovedEvent() {
    }

    public UnapprovedEvent(String id, String idEvent, String description) {
        this.id = id;
        this.idEvent = idEvent;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdEvent() {
        return idEvent;
    }

    public void setIdEvent(String idEvent) {
        this.idEvent = idEvent;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
