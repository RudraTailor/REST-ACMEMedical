/*******************************************************************************************************
 * File:  MedicalSchool.java
 * Course Materials CST 8277
 *
 * @author Teddy Yap
 * @author Armaan Singh (Backend Lead) // Implemented by Armaan Singh (Backend Lead)
 *
 * Fully implemented and annotated by Armaan Singh.
 ******************************************************************************************************/
package acmemedical.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

// For JSON annotations, let Member 2 do advanced Jackson (@JsonTypeInfo etc).

/**
 * Abstract entity for the medical_school table (base for Public/PrivateSchool).
 */
@Entity
@Table(name = "medical_school")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@AttributeOverride(name = "id", column = @Column(name = "school_id"))
@NamedQuery(name = "MedicalSchool.findAll", query = "SELECT m FROM MedicalSchool m")
@EntityListeners(PojoListener.class) // Remove/comment if no PojoListener
public abstract class MedicalSchool extends PojoBase implements Serializable {
    private static final long serialVersionUID = 1L;

    @Basic(optional = false)
    @Column(name = "name", nullable = false, unique = true, length = 100)
    private String name;

    @OneToMany(cascade = CascadeType.MERGE, fetch = FetchType.LAZY, mappedBy = "medicalSchool")
    private Set<MedicalTraining> medicalTrainings = new HashSet<>();

    @Basic(optional = false)
    @Column(name = "is_public", nullable = false)
    private boolean isPublic;

    // --- Constructors ---
    public MedicalSchool() {
        super();
    }
    public MedicalSchool(boolean isPublic) {
        this();
        this.isPublic = isPublic;
    }

    // --- Getters and Setters ---
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public Set<MedicalTraining> getMedicalTrainings() {
        return medicalTrainings;
    }
    public void setMedicalTrainings(Set<MedicalTraining> medicalTrainings) {
        this.medicalTrainings = medicalTrainings;
    }

    public boolean isPublic() {
        return isPublic;
    }
    public void setPublic(boolean isPublic) {
        this.isPublic = isPublic;
    }

    // hashCode/equals must use id and name due to DB uniqueness constraint
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        return prime * result + Objects.hash(getId(), getName());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (obj instanceof MedicalSchool otherMedicalSchool) {
            return Objects.equals(this.getId(), otherMedicalSchool.getId())
                && Objects.equals(this.getName(), otherMedicalSchool.getName());
        }
        return false;
    }
}

/*
 * Implemented by Armaan Singh (Backend Lead)
 */
