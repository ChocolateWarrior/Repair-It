package com.helvetica.model.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

public class RepairRequest {

    private int id;
    private User user;

    private Set<User> masters;

    private String specification;
    private String description;
    private String rejectionMessage;
    private String comment;
    private LocalDateTime requestTime;
    private LocalDateTime finishTime;
    private BigDecimal price;
    private RequestState state;
    public void addMaster(User master){
        masters.add(master);
    }

    public RepairRequest(int id,
                         User user,
                         Set<User> masters,
                         String specification,
                         String description,
                         String rejectionMessage,
                         String comment,
                         LocalDateTime requestTime,
                         LocalDateTime finishTime,
                         BigDecimal price,
                         RequestState state) {
        this.id = id;
        this.user = user;
        this.masters = masters;
        this.specification = specification;
        this.description = description;
        this.rejectionMessage = rejectionMessage;
        this.comment = comment;
        this.requestTime = requestTime;
        this.finishTime = finishTime;
        this.price = price;
        this.state = state;
    }

    public RepairRequest() {
    }

    public int getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public Set<User> getMasters() {
        return masters;
    }

    public String getSpecification() {
        return specification;
    }

    public String getDescription() {
        return description;
    }

    public String getRejectionMessage() {
        return rejectionMessage;
    }

    public String getComment() {
        return comment;
    }

    public LocalDateTime getRequestTime() {
        return requestTime;
    }

    public LocalDateTime getFinishTime() {
        return finishTime;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public RequestState getState() {
        return state;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setMasters(Set<User> masters) {
        this.masters = masters;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setRejectionMessage(String rejectionMessage) {
        this.rejectionMessage = rejectionMessage;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setRequestTime(LocalDateTime requestTime) {
        this.requestTime = requestTime;
    }

    public void setFinishTime(LocalDateTime finishTime) {
        this.finishTime = finishTime;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setState(RequestState state) {
        this.state = state;
    }
}
