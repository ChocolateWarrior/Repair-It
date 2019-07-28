package com.helvetica.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//@Builder
public class User {

    private int id;
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private Set<Role> authorities;
    private Set<Specification> specifications;
    private Set<RepairRequest> userRequests;
    private Set<RepairRequest> masterRequests;

    public void addMasterRequest(RepairRequest request){
        masterRequests.add(request);
    }
    public void addUserRequest(RepairRequest request) {userRequests.add(request);}
    public void addAuthority(Role authority){
        authorities.add(authority);
    }
    public void addSpecification(Specification specification){
        specifications.add(specification);
    }

    public User() {
    }

    public User(String firstName,
                String lastName,
                String username,
                String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<Role> authorities) {
        this.authorities = authorities;
    }

    public Set<Specification> getSpecifications() {
        return specifications;
    }

    public void setSpecifications(Set<Specification> specifications) {
        this.specifications = specifications;
    }

    public Set<RepairRequest> getUserRequests() {
        return userRequests;
    }

    public void setUserRequests(Set<RepairRequest> userRequests) {
        this.userRequests = userRequests;
    }

    public Set<RepairRequest> getMasterRequests() {
        return masterRequests;
    }

    public void setMasterRequests(Set<RepairRequest> masterRequests) {
        this.masterRequests = masterRequests;
    }
}
