package com.helvetica.model.entity;


import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class User {

    private int id;
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private BigDecimal balance;
    private Set<Role> authorities = new HashSet<>();
    private Set<Specification> specifications = new HashSet<>();
    private Set<RepairRequest> userRequests = new HashSet<>();
    private Set<RepairRequest> masterRequests = new HashSet<>();

    public void addMasterRequest(RepairRequest request){
        masterRequests.add(request);
    }
    public void addUserRequest(RepairRequest request) {userRequests.add(request);}
    public void addSpecification(Specification specification){
        specifications.add(specification);
    }
    public void addAuthority(Role authority){
        authorities.add(authority);
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

    public User(String firstName, String lastName, String username, String password, List<Specification> specifications) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.specifications = new HashSet<>(specifications);
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

    public void setAuthorities(Set<Role> authorities) {
        this.authorities = authorities;
    }

    public Set<Role> getAuthorities() {
        return authorities;
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

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public boolean hasAuthority(Role authority){
        return authorities.contains(authority);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id &&
                firstName.equals(user.firstName) &&
                lastName.equals(user.lastName) &&
                username.equals(user.username) &&
                password.equals(user.password) &&
                Objects.equals(balance, user.balance) &&
                Objects.equals(authorities, user.authorities) &&
                Objects.equals(specifications, user.specifications) &&
                Objects.equals(userRequests, user.userRequests) &&
                Objects.equals(masterRequests, user.masterRequests);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, firstName, lastName, password, authorities);
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", password='" + password + '\'' +
                ", balance='" + balance + '\'' +
                ", user requests='" + userRequests + '\'' +
                ", master requests='" + masterRequests + '\'' +
                ", authorities=" + authorities +
                '}';
    }


}
