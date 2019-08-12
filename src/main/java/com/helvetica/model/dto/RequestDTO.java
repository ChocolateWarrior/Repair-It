package com.helvetica.model.dto;

import com.helvetica.model.entity.Specification;
import com.helvetica.model.entity.User;

import java.time.LocalDateTime;

public class RequestDTO {

    private Specification specification;
    private String description;
    private LocalDateTime requestTime;
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public RequestDTO(Specification specification, String description, LocalDateTime requestTime, User user) {
        this.specification = specification;
        this.description = description;
        this.requestTime = requestTime;
        this.user = user;
    }

    public RequestDTO(Specification specification, String description) {
        this.specification = specification;
        this.description = description;
        this.requestTime = LocalDateTime.now();
    }

    public RequestDTO() {
    }

    public Specification getSpecification() {
        return specification;
    }

    public void setSpecification(Specification specification) {
        this.specification = specification;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(LocalDateTime requestTime) {
        this.requestTime = requestTime;
    }
}
