/*******************************************************************************************************
 * File:  ACMEMedicalService.java
 * Course Materials CST 8277
 *
 * @author Teddy Yap
 * @author Shariar (Shawn) Emami
 * @author Armaan Singh (Backend Lead) // Implemented by Armaan Singh (Backend Lead)
 *
 * Fully implemented and polished by Armaan Singh.
 ******************************************************************************************************/
package acmemedical.ejb;

import static acmemedical.utility.MyConstants.*;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jakarta.ejb.Singleton;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.security.enterprise.identitystore.Pbkdf2PasswordHash;
import jakarta.transaction.Transactional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import acmemedical.entity.*;

@Singleton
public class ACMEMedicalService implements Serializable {
    private static final long serialVersionUID = 1L;

    private static final Logger LOG = LogManager.getLogger();

    @PersistenceContext(name = PU_NAME)
    protected EntityManager em;

    @Inject
    protected Pbkdf2PasswordHash pbAndjPasswordHash;

    // ---- PHYSICIAN CRUD ----

    public List<Physician> getAllPhysicians() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Physician> cq = cb.createQuery(Physician.class);
        cq.select(cq.from(Physician.class));
        return em.createQuery(cq).getResultList();
    }

    public Physician getPhysicianById(int id) {
        return em.find(Physician.class, id);
    }

    @Transactional
    public Physician persistPhysician(Physician newPhysician) {
        em.persist(newPhysician);
        return newPhysician;
    }

    @Transactional
    public void buildUserForNewPhysician(Physician newPhysician) {
        SecurityUser userForNewPhysician = new SecurityUser();
        userForNewPhysician.setUsername(
            DEFAULT_USER_PREFIX + "_" + newPhysician.getFirstName() + "." + newPhysician.getLastName());
        Map<String, String> pbAndjProperties = new HashMap<>();
        pbAndjProperties.put(PROPERTY_ALGORITHM, DEFAULT_PROPERTY_ALGORITHM);
        pbAndjProperties.put(PROPERTY_ITERATIONS, DEFAULT_PROPERTY_ITERATIONS);
        pbAndjProperties.put(PROPERTY_SALT_SIZE, DEFAULT_SALT_SIZE);
        pbAndjProperties.put(PROPERTY_KEY_SIZE, DEFAULT_KEY_SIZE);
        pbAndjPasswordHash.initialize(pbAndjProperties);
        String pwHash = pbAndjPasswordHash.generate(DEFAULT_USER_PASSWORD.toCharArray());
        userForNewPhysician.setPwHash(pwHash);
        userForNewPhysician.setPhysician(newPhysician);
        TypedQuery<SecurityRole> findRole = em.createNamedQuery("SecurityRole.findByRoleName", SecurityRole.class);
        findRole.setParameter("roleName", USER_ROLE);
        SecurityRole userRole = findRole.getSingleResult();
        userForNewPhysician.getRoles().add(userRole);
        userRole.getUsers().add(userForNewPhysician);
        em.persist(userForNewPhysician);
    }

    @Transactional
    public Medicine setMedicineForPhysicianPatient(int physicianId, int patientId, Medicine newMedicine) {
        Physician physicianToBeUpdated = em.find(Physician.class, physicianId);
        if (physicianToBeUpdated != null) {
            Set<Prescription> prescriptions = physicianToBeUpdated.getPrescriptions();
            prescriptions.forEach(p -> {
                if (p.getPatient().getId() == patientId) {
                    if (p.getMedicine() != null) {
                        Medicine medicine = em.find(Medicine.class, p.getMedicine().getId());
                        medicine.setMedicine(
                            newMedicine.getDrugName(),
                            newMedicine.getManufacturerName(),
                            newMedicine.getDosageInformation()
                        );
                        em.merge(medicine);
                    } else {
                        p.setMedicine(newMedicine);
                        em.merge(physicianToBeUpdated);
                    }
                }
            });
            return newMedicine;
        }
        return null;
    }

    @Transactional
    public Physician updatePhysicianById(int id, Physician physicianWithUpdates) {
        Physician physicianToBeUpdated = getPhysicianById(id);
        if (physicianToBeUpdated != null) {
            em.refresh(physicianToBeUpdated);
            em.merge(physicianWithUpdates);
            em.flush();
        }
        return physicianToBeUpdated;
    }

    @Transactional
    public void deletePhysicianById(int id) {
        Physician physician = getPhysicianById(id);
        if (physician != null) {
            em.refresh(physician);
            TypedQuery<SecurityUser> findUser = em.createQuery(
                "SELECT su FROM SecurityUser su WHERE su.physician.id = :physicianId", SecurityUser.class
            );
            findUser.setParameter("physicianId", id);
            SecurityUser sUser = findUser.getSingleResult();
            em.remove(sUser);
            em.remove(physician);
        }
    }

    // ---- PATIENT CRUD ----

    @Transactional
    public Patient persistPatient(Patient newPatient) {
        em.persist(newPatient);
        return newPatient;
    }

    @Transactional
    public Patient updatePatientById(int id, Patient patientWithUpdates) {
        Patient patientToBeUpdated = em.find(Patient.class, id);
        if (patientToBeUpdated != null) {
            em.refresh(patientToBeUpdated);
            em.merge(patientWithUpdates);
            em.flush();
        }
        return patientToBeUpdated;
    }

    @Transactional
    public void deletePatientById(int id) {
        Patient patient = em.find(Patient.class, id);
        if (patient != null) {
            em.remove(patient);
        }
    }

    // ---- MEDICINE CRUD ----

    @Transactional
    public Medicine persistMedicine(Medicine newMedicine) {
        em.persist(newMedicine);
        return newMedicine;
    }

    @Transactional
    public Medicine updateMedicineById(int id, Medicine medicineWithUpdates) {
        Medicine medicineToBeUpdated = em.find(Medicine.class, id);
        if (medicineToBeUpdated != null) {
            em.refresh(medicineToBeUpdated);
            em.merge(medicineWithUpdates);
            em.flush();
        }
        return medicineToBeUpdated;
    }

    @Transactional
    public void deleteMedicineById(int id) {
        Medicine medicine = em.find(Medicine.class, id);
        if (medicine != null) {
            em.remove(medicine);
        }
    }

    // ---- PRESCRIPTION CRUD ----

    @Transactional
    public Prescription persistPrescription(Prescription newPrescription) {
        em.persist(newPrescription);
        return newPrescription;
    }

    @Transactional
    public Prescription updatePrescriptionById(int id, Prescription prescriptionWithUpdates) {
        Prescription prescriptionToBeUpdated = em.find(Prescription.class, id);
        if (prescriptionToBeUpdated != null) {
            em.refresh(prescriptionToBeUpdated);
            em.merge(prescriptionWithUpdates);
            em.flush();
        }
        return prescriptionToBeUpdated;
    }

    @Transactional
    public void deletePrescriptionById(int id) {
        Prescription prescription = em.find(Prescription.class, id);
        if (prescription != null) {
            em.remove(prescription);
        }
    }

    // ---- MEDICAL SCHOOL CRUD ----

    public List<MedicalSchool> getAllMedicalSchools() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<MedicalSchool> cq = cb.createQuery(MedicalSchool.class);
        cq.select(cq.from(MedicalSchool.class));
        return em.createQuery(cq).getResultList();
    }

    public MedicalSchool getMedicalSchoolById(int id) {
        TypedQuery<MedicalSchool> specificMedicalSchoolQuery =
            em.createNamedQuery("MedicalSchool.findById", MedicalSchool.class);
        specificMedicalSchoolQuery.setParameter(PARAM1, id);
        return specificMedicalSchoolQuery.getSingleResult();
    }

    public <T> List<T> getAll(Class<T> entity, String namedQuery) {
        TypedQuery<T> allQuery = em.createNamedQuery(namedQuery, entity);
        return allQuery.getResultList();
    }

    public <T> T getById(Class<T> entity, String namedQuery, int id) {
        TypedQuery<T> allQuery = em.createNamedQuery(namedQuery, entity);
        allQuery.setParameter(PARAM1, id);
        return allQuery.getSingleResult();
    }

    @Transactional
    public MedicalSchool deleteMedicalSchool(int id) {
        MedicalSchool ms = getById(MedicalSchool.class, "MedicalSchool.findById", id);
        if (ms != null) {
            Set<MedicalTraining> medicalTrainings = ms.getMedicalTrainings();
            List<MedicalTraining> list = new LinkedList<>();
            medicalTrainings.forEach(list::add);
            list.forEach(mt -> {
                if (mt.getCertificate() != null) {
                    MedicalCertificate mc = getById(MedicalCertificate.class, "MedicalCertificate.findById", mt.getCertificate().getId());
                    mc.setMedicalTraining(null);
                }
                mt.setCertificate(null);
                em.merge(mt);
            });
            em.remove(ms);
            return ms;
        }
        return null;
    }

    public boolean isDuplicated(MedicalSchool newMedicalSchool) {
        TypedQuery<Long> allMedicalSchoolsQuery =
            em.createNamedQuery("MedicalSchool.isDuplicate", Long.class);
        allMedicalSchoolsQuery.setParameter(PARAM1, newMedicalSchool.getName());
        return (allMedicalSchoolsQuery.getSingleResult() >= 1);
    }

    @Transactional
    public MedicalSchool persistMedicalSchool(MedicalSchool newMedicalSchool) {
        em.persist(newMedicalSchool);
        return newMedicalSchool;
    }

    @Transactional
    public MedicalSchool updateMedicalSchool(int id, MedicalSchool updatingMedicalSchool) {
        MedicalSchool medicalSchoolToBeUpdated = getMedicalSchoolById(id);
        if (medicalSchoolToBeUpdated != null) {
            em.refresh(medicalSchoolToBeUpdated);
            medicalSchoolToBeUpdated.setName(updatingMedicalSchool.getName());
            em.merge(medicalSchoolToBeUpdated);
            em.flush();
        }
        return medicalSchoolToBeUpdated;
    }

    // ---- MEDICAL TRAINING CRUD ----

    @Transactional
    public MedicalTraining persistMedicalTraining(MedicalTraining newMedicalTraining) {
        em.persist(newMedicalTraining);
        return newMedicalTraining;
    }

    public MedicalTraining getMedicalTrainingById(int mtId) {
        TypedQuery<MedicalTraining> allMedicalTrainingQuery =
            em.createNamedQuery("MedicalTraining.findById", MedicalTraining.class);
        allMedicalTrainingQuery.setParameter(PARAM1, mtId);
        return allMedicalTrainingQuery.getSingleResult();
    }

    @Transactional
    public MedicalTraining updateMedicalTraining(int id, MedicalTraining medicalTrainingWithUpdates) {
        MedicalTraining medicalTrainingToBeUpdated = getMedicalTrainingById(id);
        if (medicalTrainingToBeUpdated != null) {
            em.refresh(medicalTrainingToBeUpdated);
            em.merge(medicalTrainingWithUpdates);
            em.flush();
        }
        return medicalTrainingToBeUpdated;
    }

    @Transactional
    public void deleteMedicalTrainingById(int id) {
        MedicalTraining medicalTraining = em.find(MedicalTraining.class, id);
        if (medicalTraining != null) {
            em.remove(medicalTraining);
        }
    }

    // ---- MEDICAL CERTIFICATE CRUD ----

    @Transactional
    public MedicalCertificate persistMedicalCertificate(MedicalCertificate newMedicalCertificate) {
        em.persist(newMedicalCertificate);
        return newMedicalCertificate;
    }

    @Transactional
    public MedicalCertificate updateMedicalCertificateById(int id, MedicalCertificate certificateWithUpdates) {
        MedicalCertificate certificateToBeUpdated = em.find(MedicalCertificate.class, id);
        if (certificateToBeUpdated != null) {
            em.refresh(certificateToBeUpdated);
            em.merge(certificateWithUpdates);
            em.flush();
        }
        return certificateToBeUpdated;
    }

    @Transactional
    public void deleteMedicalCertificateById(int id) {
        MedicalCertificate certificate = em.find(MedicalCertificate.class, id);
        if (certificate != null) {
            em.remove(certificate);
        }
    }
}

/*
 * Implemented by Armaan Singh (Backend Lead)
 */
