/*******************************************************************************************************
 * File:  DurationAndStatus.java
 * Course Materials CST 8277
 *
 * @author Teddy Yap
 * @author Armaan Singh (Backend Lead) // Implemented by Armaan Singh (Backend Lead)
 *
 * Fully implemented and annotated by Armaan Singh.
 ******************************************************************************************************/
package acmemedical.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

/**
 * Embeddable value object for duration and status (not an entity).
 */
@Embeddable // DS01 - This class can be embedded in other entities
public class DurationAndStatus implements Serializable {
    private static final long serialVersionUID = 1L;

    @Column(name = "start_date")
    private LocalDateTime startDate; // DS02

    @Column(name = "end_date")
    private LocalDateTime endDate;   // DS03

    @Column(name = "active", nullable = false)
    private byte active;             // DS04

    public DurationAndStatus() {}

    public LocalDateTime getStartDate() {
        return startDate;
    }
    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }
    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public byte getActive() {
        return active;
    }
    public void setActive(byte active) {
        this.active = active;
    }

    public void setDurationAndStatus(LocalDateTime startDate, LocalDateTime endDate, String active) {
        setStartDate(startDate);
        setEndDate(endDate);
        this.active = ("+".equals(active)) ? (byte)1 : (byte)0;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        return prime * result + Objects.hash(getStartDate(), getEndDate(), getActive());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (obj instanceof DurationAndStatus otherDurationAndStatus) {
            return Objects.equals(this.getStartDate(), otherDurationAndStatus.getStartDate()) &&
                    Objects.equals(this.getEndDate(), otherDurationAndStatus.getEndDate()) &&
                    Objects.equals(this.getActive(), otherDurationAndStatus.getActive());
        }
        return false;
    }
}

/*
 * Implemented by Armaan Singh (Backend Lead)
 */
