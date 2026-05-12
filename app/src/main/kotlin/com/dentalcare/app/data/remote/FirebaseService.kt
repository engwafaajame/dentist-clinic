package com.dentalcare.app.data.remote

import com.dentalcare.app.data.models.Appointment
import com.dentalcare.app.data.models.Doctor
import com.dentalcare.app.data.models.User
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirebaseService @Inject constructor(
    private val firestore: FirebaseFirestore
) {
    // Doctors Collection
    suspend fun getAllDoctors(): List<Doctor> {
        return try {
            firestore.collection("doctors")
                .get()
                .await()
                .toObjects(Doctor::class.java)
        } catch (e: Exception) {
            emptyList()
        }
    }

    suspend fun getDoctorById(id: String): Doctor? {
        return try {
            firestore.collection("doctors")
                .document(id)
                .get()
                .await()
                .toObject(Doctor::class.java)
        } catch (e: Exception) {
            null
        }
    }

    suspend fun getDoctorsBySpecialty(specialty: String): List<Doctor> {
        return try {
            firestore.collection("doctors")
                .whereEqualTo("specialty", specialty)
                .get()
                .await()
                .toObjects(Doctor::class.java)
        } catch (e: Exception) {
            emptyList()
        }
    }

    suspend fun addDoctor(doctor: Doctor): Boolean {
        return try {
            firestore.collection("doctors")
                .document(doctor.id)
                .set(doctor)
                .await()
            true
        } catch (e: Exception) {
            false
        }
    }

    // Appointments Collection
    suspend fun getAppointments(userId: String): List<Appointment> {
        return try {
            firestore.collection("appointments")
                .whereEqualTo("userId", userId)
                .get()
                .await()
                .toObjects(Appointment::class.java)
        } catch (e: Exception) {
            emptyList()
        }
    }

    suspend fun bookAppointment(appointment: Appointment): Boolean {
        return try {
            firestore.collection("appointments")
                .document(appointment.id)
                .set(appointment)
                .await()
            true
        } catch (e: Exception) {
            false
        }
    }

    suspend fun updateAppointment(appointment: Appointment): Boolean {
        return try {
            firestore.collection("appointments")
                .document(appointment.id)
                .set(appointment)
                .await()
            true
        } catch (e: Exception) {
            false
        }
    }

    suspend fun cancelAppointment(appointmentId: String): Boolean {
        return try {
            firestore.collection("appointments")
                .document(appointmentId)
                .delete()
                .await()
            true
        } catch (e: Exception) {
            false
        }
    }

    // Users Collection
    suspend fun getUser(userId: String): User? {
        return try {
            firestore.collection("users")
                .document(userId)
                .get()
                .await()
                .toObject(User::class.java)
        } catch (e: Exception) {
            null
        }
    }

    suspend fun updateUser(user: User): Boolean {
        return try {
            firestore.collection("users")
                .document(user.id)
                .set(user)
                .await()
            true
        } catch (e: Exception) {
            false
        }
    }

    suspend fun createUser(user: User): Boolean {
        return try {
            firestore.collection("users")
                .document(user.id)
                .set(user)
                .await()
            true
        } catch (e: Exception) {
            false
        }
    }
}
