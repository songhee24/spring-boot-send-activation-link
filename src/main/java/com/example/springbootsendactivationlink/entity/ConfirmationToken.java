package com.example.springbootsendactivationlink.entity;

import javax.persistence.*;
import java.lang.annotation.Target;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "confirmation_token")
public class ConfirmationToken {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "token_id")
    private Long tokenId;

    @Column(name = "confirmation_token")
    private String confirmationToken;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @OneToOne(targetEntity = UserEntity.class,cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_id")
    private UserEntity userEntity;

    public ConfirmationToken() {
    }

    public ConfirmationToken(UserEntity userEntity) {
        this.userEntity = userEntity;
        createdDate= new Date();
        confirmationToken = UUID.randomUUID().toString();
    }

    public Long getTokenId() {
        return tokenId;
    }

    public void setTokenId(Long tokenId) {
        this.tokenId = tokenId;
    }

    public String getConfirmationToken() {
        return confirmationToken;
    }

    public void setConfirmationToken(String confirmationToken) {
        this.confirmationToken = confirmationToken;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }
}
