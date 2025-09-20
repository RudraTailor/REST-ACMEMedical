/*******************************************************************************************************
 * File:  PojoBaseCompositeKey.java
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
 * Abstract base class for all @Entity classes using composite keys.
 * @param <ID> - type of composite key used
 */
// PC01 - Mark as JPA superclass for composite key entities
@MappedSuperclass
// PC02 - JPA will access fields directly
@Access(AccessType.FIELD)
// PC03 - Attach entity listener (same as for PojoBase)
@EntityListeners(PojoListener.class)
public abstract class PojoBaseCompositeKey<ID extends Serializable> implements Serializable {
    private static final long serialVersionUID = 1L;

    // PC04 - Version field for optimistic locking
    @Version
    protected int version;

    // PC05 - Created timestamp (not updatable by JPA directly)
    @Column(name = "created", updatable = false)
    protected LocalDateTime created;

    // PC06 - Updated timestamp
    @Column(name = "updated")
    protected LocalDateTime updated;

    public abstract ID getId();
    public abstract void setId(ID id);

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
        if (obj instanceof PojoBaseCompositeKey otherPojoBaseComposite) {
            return Objects.equals(this.getId(), otherPojoBaseComposite.getId());
        }
        return false;
    }
}

/*
 * Implemented by Armaan Singh (Backend Lead)
 */
