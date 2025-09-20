/*******************************************************************************************************
 * File:  Prescription.java
 * Course Materials CST 8277
 *
 * @author Teddy Yap
 * @author Armaan Singh (Backend Lead) // Implemented by Armaan Singh (Backend Lead)
 *
 * Fully implemented and annotated by Armaan Singh.
 ******************************************************************************************************/
package acmemedical.entity;

import java.io.Serializable;
import java.util.Objects;

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
import jakarta.persistence.Table;

/**
 * Entity class mapping for the prescription table.
 */
@Entity
@Table(name = "prescription")
@NamedQueries({
    @NamedQuery(name = "Prescription.findAll", query = "SELECT p FROM Prescription p"),
    @NamedQuery(name = "Prescription.findById", query = "SELECT p FROM Prescription p WHERE p.id = :param1")
})
@AttributeOverride(name = "id", column = @Column(name = "prescription_id"))
@EntityListeners(PojoListener.class)
public class Prescription extends PojoBase implements Serializable {
    private static final long serialVersionUID = 1L;

    @Basic(optional = false)
    @Column(name = "instructions", nullable = false, length = 255)
    private String instructions;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medicine_id")
    private Medicine medicine;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "physician_id")
    private Physician physician;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id")
    private Patient patient;

    // --- Constructors ---
    public Prescription() {
        super();
    }

    public Prescription(String instructions, Medicine medicine, Physician physician, Patient patient) {
        this();
        this.instructions = instructions;
        this.medicine = medicine;
        this.physician = physician;
        this.patient = patient;
    }

    // --- Getters and Setters ---
    public String getInstructions() {
        return instructions;
    }
    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public Medicine getMedicine() {
        return medicine;
    }
    public void setMedicine(Medicine medicine) {
        this.medicine = medicine;
    }

    public Physician getPhysician() {
        return physician;
    }
    public void setPhysician(Physician physician) {
        this.physician = physician;
    }

    public Patient getPatient() {
        return patient;
    }
    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    // hashCode and equals inherited from PojoBase

}

/*
 * Implemented by Armaan Singh (Backend Lead)
 */
