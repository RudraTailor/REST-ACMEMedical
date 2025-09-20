/*******************************************************************************************************
 * File:  MedicalTraining.java
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
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

/**
 * Entity class mapping for the medical_training table.
 */
@Entity
@Table(name = "medical_training")
@NamedQueries({
    @NamedQuery(name = "MedicalTraining.findAll", query = "SELECT mt FROM MedicalTraining mt"),
    @NamedQuery(name = "MedicalTraining.findById", query = "SELECT mt FROM MedicalTraining mt WHERE mt.id = :param1")
})
@AttributeOverride(name = "id", column = @Column(name = "training_id"))
@EntityListeners(PojoListener.class)
public class MedicalTraining extends PojoBase implements Serializable {
    private static final long serialVersionUID = 1L;

    @Basic(optional = false)
    @Column(name = "training_name", nullable = false, length = 100)
    private String trainingName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medical_school_id")
    private MedicalSchool medicalSchool;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "certificate_id")
    private MedicalCertificate certificate;

    // --- Constructors ---
    public MedicalTraining() {
        super();
    }

    public MedicalTraining(String trainingName, MedicalSchool medicalSchool, MedicalCertificate certificate) {
        this();
        this.trainingName = trainingName;
        this.medicalSchool = medicalSchool;
        this.certificate = certificate;
    }

    // --- Getters and Setters ---
    public String getTrainingName() {
        return trainingName;
    }
    public void setTrainingName(String trainingName) {
        this.trainingName = trainingName;
    }

    public MedicalSchool getMedicalSchool() {
        return medicalSchool;
    }
    public void setMedicalSchool(MedicalSchool medicalSchool) {
        this.medicalSchool = medicalSchool;
    }

    public MedicalCertificate getCertificate() {
        return certificate;
    }
    public void setCertificate(MedicalCertificate certificate) {
        this.certificate = certificate;
    }

    // hashCode and equals inherited from PojoBase

}

/*
 * Implemented by Armaan Singh (Backend Lead)
 */
