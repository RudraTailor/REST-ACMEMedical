/*******************************************************************************************************
 * File:  MedicalCertificate.java
 * Course Materials CST 8277
 *
 * @author Teddy Yap
 * @author Armaan Singh (Backend Lead) // Implemented by Armaan Singh (Backend Lead)
 *
 * Fully implemented and annotated by Armaan Singh.
 ******************************************************************************************************/
package acmemedical.entity;

import java.io.Serializable;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;

/**
 * Entity class mapping for the medical_certificate table.
 */
@Entity
@Table(name = "medical_certificate")
@NamedQueries({
    @NamedQuery(name = "MedicalCertificate.findAll", query = "SELECT mc FROM MedicalCertificate mc"),
    @NamedQuery(name = "MedicalCertificate.findById", query = "SELECT mc FROM MedicalCertificate mc WHERE mc.id = :param1")
})
@AttributeOverride(name = "id", column = @Column(name = "certificate_id"))
@EntityListeners(PojoListener.class)
public class MedicalCertificate extends PojoBase implements Serializable {
    private static final long serialVersionUID = 1L;

    @Basic(optional = false)
    @Column(name = "certificate_number", nullable = false, length = 100)
    private String certificateNumber;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "certificate")
    private MedicalTraining medicalTraining;

    // --- Constructors ---
    public MedicalCertificate() {
        super();
    }

    public MedicalCertificate(String certificateNumber, MedicalTraining medicalTraining) {
        this();
        this.certificateNumber = certificateNumber;
        this.medicalTraining = medicalTraining;
    }

    // --- Getters and Setters ---
    public String getCertificateNumber() {
        return certificateNumber;
    }
    public void setCertificateNumber(String certificateNumber) {
        this.certificateNumber = certificateNumber;
    }

    public MedicalTraining getMedicalTraining() {
        return medicalTraining;
    }
    public void setMedicalTraining(MedicalTraining medicalTraining) {
        this.medicalTraining = medicalTraining;
    }

    // hashCode and equals inherited from PojoBase

}

/*
 * Implemented by Armaan Singh (Backend Lead)
 */
