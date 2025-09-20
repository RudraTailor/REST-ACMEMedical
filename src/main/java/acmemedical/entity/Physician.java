/*******************************************************************************************************
 * File:  Physician.java
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
import java.util.Set;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

/**
 * Entity class mapping for the physician table.
 */
@Entity
@Table(name = "physician")
@NamedQuery(name = "Physician.findAll", query = "SELECT p FROM Physician p")
@AttributeOverride(name = "id", column = @Column(name = "id"))
@EntityListeners(PojoListener.class) // Remove/comment if no PojoListener
public class Physician extends PojoBase implements Serializable {
    private static final long serialVersionUID = 1L;

    @Basic(optional = false)
    @Column(name = "first_name", nullable = false, length = 50)
    private String firstName;

    @Basic(optional = false)
    @Column(name = "last_name", nullable = false, length = 50)
    private String lastName;

    @OneToMany(cascade = CascadeType.MERGE, fetch = FetchType.LAZY, mappedBy = "owner")
    private Set<MedicalCertificate> medicalCertificates = new HashSet<>();

    @OneToMany(cascade = CascadeType.MERGE, fetch = FetchType.LAZY, mappedBy = "physician")
    private Set<Prescription> prescriptions = new HashSet<>();

    // --- Constructors ---
    public Physician() {
        super();
    }

    // --- Getters and Setters ---
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Set<MedicalCertificate> getMedicalCertificates() {
        return medicalCertificates;
    }
    public void setMedicalCertificates(Set<MedicalCertificate> medicalCertificates) {
        this.medicalCertificates = medicalCertificates;
    }

    public Set<Prescription> getPrescriptions() {
        return prescriptions;
    }
    public void setPrescriptions(Set<Prescription> prescriptions) {
        this.prescriptions = prescriptions;
    }

    public void setFullName(String firstName, String lastName) {
        setFirstName(firstName);
        setLastName(lastName);
    }

    // hashCode and equals inherited from PojoBase

}

/*
 * Implemented by Armaan Singh (Backend Lead)
 */
