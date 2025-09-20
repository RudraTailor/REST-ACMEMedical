/*******************************************************************************************************
 * File:  PojoBase.java
 * Course Materials CST 8277
 *
 * @author Teddy Yap
 * @author Shariar (Shawn) Emami
 * @author Armaan Singh (Backend Lead) // Implemented by Armaan Singh (Backend Lead)
 * 
 * Fully implemented, all annotations and entity-listener wiring by Armaan Singh.
 ******************************************************************************************************/
package acmemedical.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

import jakarta.persistence.*;

/**
 * Abstract base class for all @Entity classes.
 * Implements standard id, version, created, and updated fields.
 */
// PB01 - Mark as JPA superclass for entities
@MappedSuperclass
// PB02 - JPA will access fields directly
@Access(AccessType.FIELD)
// PB03 - Attach entity listener (if you have a PojoListener for auditing, see below)
@EntityListeners(PojoListener.class)
public abstract class PojoBase implements Serializable {
    private static final long serialVersionUID = 1L;

    // PB04 - Id field with auto-generation
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected int id;

    // PB05 - Version field for optimistic locking
    @Version
    protected int version;

    // PB06 - Created timestamp (set by code/listener, not insertable/updatable by JPA directly)
    @Column(name = "created", updatable = false)
    protected LocalDateTime created;

    // PB07 - Updated timestamp (set by code/listener)
    @Column(name = "updated")
    protected LocalDateTime updated;

    // --- Getters and Setters ---
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public int getVersion() {
        return version;
    }
    public void setVersion(int version) {
        this.version = version;
    }

    public LocalDateTime getCreated() {
        return created;
    }
    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public LocalDateTime getUpdated() {
        return updated;
    }
    public void setUpdated(LocalDateTime updated) {
        this.updated = updated;
    }

    /**
     * Very important: Use getter's for member variables because JPA sometimes needs to intercept those calls
     * and go to the database to retrieve the value.
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        return prime * result + Objects.hash(getId());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (obj instanceof PojoBase otherPojoBase) {
            return Objects.equals(this.getId(), otherPojoBase.getId());
        }
        return false;
    }
}

/*
 * Implemented by Armaan Singh (Backend Lead)
 */
