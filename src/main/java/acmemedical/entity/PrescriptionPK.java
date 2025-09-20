/*******************************************************************************************************
 * File:  PrescriptionPK.java
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

import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

/**
 * Composite primary key class for the prescription database table.
 */
@Embeddable // PRPK01 - Embeddable composite key
@Access(AccessType.FIELD)
public class PrescriptionPK implements Serializable {
    private static final long serialVersionUID = 1L;

    @Basic(optional = false)
    @Column(name = "physician_id", nullable = false)
    private int physicianId;

    @Basic(optional = false)
    @Column(name = "patient_id", nullable = false)
    private int patientId;

    public PrescriptionPK() {}

    public PrescriptionPK(int physicianId, int patientId) {
        setPhysicianId(physicianId);
        setPatientId(patientId);
    }

    public int getPhysicianId() {
        return physicianId;
    }
    public void setPhysicianId(int physicianId) {
        this.physicianId = physicianId;
    }

    public int getPatientId() {
        return patientId;
    }
    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    // -- hashCode/equals/toString --

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        return prime * result + Objects.hash(getPhysicianId(), getPatientId());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (obj instanceof PrescriptionPK otherPrescriptionPK) {
            return Objects.equals(this.getPhysicianId(), otherPrescriptionPK.getPhysicianId())
                && Objects.equals(this.getPatientId(), otherPrescriptionPK.getPatientId());
        }
        return false;
    }

    @Override
    public String toString() {
        return "PrescriptionPK [physicianId = " + physicianId + ", patientId = " + patientId + "]";
    }
}

/*
 * Implemented by Armaan Singh (Backend Lead)
 */
