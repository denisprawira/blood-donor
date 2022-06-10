package com.example.denisprawira.ta.Model;

public class Component {

    String id, component, description;

    public Component() {
    }

    public Component(String id, String component, String description) {
        this.id = id;
        this.component = component;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getComponent() {
        return component;
    }

    public void setComponent(String component) {
        this.component = component;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
