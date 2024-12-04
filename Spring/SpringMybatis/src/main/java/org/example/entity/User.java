package org.example.entity;

public class User extends AbstractEntity{
    private String email;
    private String password;
    private String name;

    public void setEmail(String email) {
        this.email = email;
    }
    public String getEmail() {
        return email;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getPassword() {
        return password;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return String.format("User [id=%s, email=%s, password=%s, createdAt=%s, createdDateTime=%s",
                getId(), getEmail(), getPassword(), getCreatedAt(), getCreatedAtAsZonedDateTime());
    }
}
