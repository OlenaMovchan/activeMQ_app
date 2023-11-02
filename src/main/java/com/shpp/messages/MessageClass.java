package com.shpp.messages;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.shpp.eddr.EddrConstraint;
import jakarta.validation.constraints.*;

import java.time.LocalDateTime;

//@AllArgsConstructor  lombok
//@Getter
public class MessageClass {
    @NotNull
    @Size(min = 7,message ="name length should be >= 7")
    @Pattern(regexp = ".*[aAaA].*", message = "name must contain the character 'a'")
    private String name;
    @Size(min = 13, max = 14, message = "the number of eddr digits should be 13 or 14")
    @EddrConstraint
    private String eddr;
    @Min(value = 10,message ="count should be >= 10")
    private int count;
    @JsonDeserialize
    private LocalDateTime createdAt;

    public MessageClass(String name, String eddr, int count, LocalDateTime dateTime) {
        this.name = name;
        this.eddr = eddr;
        this.count = count;
        this.createdAt = dateTime;
    }

    public MessageClass() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEddr() {
        return eddr;
    }

    public void setEddr(String eddr) {
        this.eddr = eddr;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "MessageClass{" +
                "name='" + name + '\'' +
                ", eddr='" + eddr + '\'' +
                ", count=" + count +
                ", dateTime=" + createdAt +
                '}';
    }
}
