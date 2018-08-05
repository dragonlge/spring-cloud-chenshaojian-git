package com.demo.merchant.domain.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;

/**
 * @author yangyueming
 */
@Entity
@Data
public class PersistentLogin implements Serializable {
    @Id
    @Column(length = 64, nullable = false)
    private String series;
    @Column(length = 64, nullable = false)
    private String username;
    @Column(length = 64, nullable = false)
    private String token;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date lastUsed;

}
