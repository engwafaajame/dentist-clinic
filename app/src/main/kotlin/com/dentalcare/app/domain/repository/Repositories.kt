package com.dentalcare.app.domain.repository

import com.dentalcare.app.data.models.Appointment
import com.dentalcare.app.data.models.Doctor
import com.dentalcare.app.data.models.User
import kotlinx.coroutines.flow.Flow

interface AppointmentRepository {
    fun getAllAppointments(userId: String): Flow<List<Appointment>>
    suspend fun getAppointmentById(id: String): Appointment?
    fun getAppointmentsByStatus(userId: String, status: String): Flow<List<Appointment>>
    suspend fun bookAppointment(appointment: Appointment): Result<Appointment>
    suspend fun updateAppointment(appointment: Appointment): Result<Appointment>
    suspend fun cancelAppointment(id: String): Result<Unit>
}

interface DoctorRepository {
    fun getAllDoctors(): Flow<List<Doctor>>
    suspend fun getDoctorById(id: String): Doctor?
    fun getDoctorsBySpecialty(specialty: String): Flow<List<Doctor>>
    suspend fun fetchAndSaveDoctors(): Result<Unit>
}

interface UserRepository {
    suspend fun getUser(userId: String): Result<User>
    suspend fun updateUser(user: User): Result<User>
    suspend fun createUser(user: User): Result<User>
}
