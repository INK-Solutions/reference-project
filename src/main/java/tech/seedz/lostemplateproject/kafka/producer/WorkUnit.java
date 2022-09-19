package tech.seedz.lostemplateproject.kafka.producer;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class WorkUnit {

    private String id;
    private String definition;

    public WorkUnit() {
        //for Spring-Web binding
    }
    @JsonCreator
    public WorkUnit(@JsonProperty("id") String id,
                    @JsonProperty("definition") String definition) {
        this.id = id;
        this.definition = definition;
    }

    public String getId() {
        return id;
    }


    public String getDefinition() {
        return definition;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

}
