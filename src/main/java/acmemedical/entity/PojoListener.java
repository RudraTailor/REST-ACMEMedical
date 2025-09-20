/*******************************************************************************************************
 * File:  PojoListener.java
 * Course Materials CST 8277
 *
 * @author Teddy Yap
 * @author Shariar (Shawn) Emami
 * @author Armaan Singh (Backend Lead) // Implemented by Armaan Singh (Backend Lead)
 *
 * Fully implemented by Armaan Singh.
 ******************************************************************************************************/
package acmemedical.entity;

import java.time.LocalDateTime;

import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

@SuppressWarnings("unused")
public class PojoListener {

    // PL01 - Runs just before INSERT
    @PrePersist
    public void setCreatedOnDate(PojoBase pojoBase) {
        LocalDateTime now = LocalDateTime.now();
        pojoBase.setCreated(now);  // PL02 - Set created field
        pojoBase.setUpdated(now);  // Also initialize updated to now
    }

    // PL03 - Runs just before UPDATE
    @PreUpdate
    public void setUpdatedDate(PojoBase pojoBase) {
        pojoBase.setUpdated(LocalDateTime.now()); // PL04 - Set updated field
    }
}

/*
 * Implemented by Armaan Singh (Backend Lead)
 */
