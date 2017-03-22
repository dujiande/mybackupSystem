package com.djd.model;

import javax.persistence.*;
import java.util.Collection;

/**
 * Created by dujiande on 2017/1/11.
 */
@Entity
@Table(name = "cpu", schema = "springdemo", catalog = "")
public class CpuEntity {
    private int id;
    private double rate;
    private long timestamp;
    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "rate", nullable = false)
    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    @Basic
    @Column(name = "timestamp", nullable = false)
    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CpuEntity that = (CpuEntity) o;

        if (id != that.id) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        return result;
    }
}
