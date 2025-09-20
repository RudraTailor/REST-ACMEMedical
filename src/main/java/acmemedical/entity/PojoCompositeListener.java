/*******************************************************************************************************
 * File:  PojoCompositeListener.java
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
public class PojoCompositeListener {

    // PCL01 - Runs just before INSERT
    @PrePersist
    public void setCreatedOnDate(PojoBaseCompositeKey<?> pojoBaseComposite) {
        LocalDateTime now = LocalDateTime.now();
        pojoBaseComposite.setCreated(now);  // PCL02 - Set created field
        pojoBaseComposite.setUpdated(now);  // Also initialize updated to now
    }

    // PCL03 - Runs just before UPDATE
    @PreUpdate
    public void setUpdatedDate(PojoBaseCompositeKey<?> pojoBaseComposite) {
        pojoBaseComposite.setUpdated(LocalDateTime.now()); // PCL04 - Set updated field
    }
}

/*
 * Implemented by Armaan Singh (Backend Lead)
 */
