package com.dentalcare.app.data.repository

import com.dentalcare.app.data.local.AppointmentDao
import com.dentalcare.app.data.local.DoctorDao
import com.dentalcare.app.data.local.UserDao
import com.dentalcare.app.data.models.Appointment
import com.dentalcare.app.data.models.Doctor
import com.dentalcare.app.data.models.User
import com.dentalcare.app.data.remote.FirebaseService
import com.dentalcare.app.domain.repository.AppointmentRepository
import com.dentalcare.app.domain.repository.DoctorRepository
import com.dentalcare.app.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppointmentRepositoryImpl @Inject constructor(
    private val appointmentDao: AppointmentDao,
    private val firebaseService: FirebaseService
) : AppointmentRepository {
    override fun getAllAppointments(userId: String): Flow<List<Appointment>> = flow {
        try {
            val appointments = firebaseService.getAppointments(userId)
            appointments.forEach { appointmentDao.insertAppointment(it) }
            emit(appointmentDao.getAllAppointments())
        } catch (e: Exception) {
            emit(emptyList())
        }
    }

    override suspend fun getAppointmentById(id: String): Appointment? =
        appointmentDao.getAppointmentById(id)

    override fun getAppointmentsByStatus(userId: String, status: String): Flow<List<Appointment>> =
        appointmentDao.getAppointmentsByStatus(status)

    override suspend fun bookAppointment(appointment: Appointment): Result<Appointment> =
        try {
            val success = firebaseService.bookAppointment(appointment)
            if (success) {
                appointmentDao.insertAppointment(appointment)
                Result.success(appointment)
            } else {
                Result.failure(Exception("Failed to book appointment"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }

    override suspend fun updateAppointment(appointment: Appointment): Result<Appointment> =
        try {
            val success = firebaseService.updateAppointment(appointment)
            if (success) {
                appointmentDao.updateAppointment(appointment)
                Result.success(appointment)
            } else {
                Result.failure(Exception("Failed to update appointment"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }

    override suspend fun cancelAppointment(id: String): Result<Unit> =
        try {
            val success = firebaseService.cancelAppointment(id)
            if (success) {
                appointmentDao.deleteAppointmentById(id)
                Result.success(Unit)
            } else {
                Result.failure(Exception("Failed to cancel appointment"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
}

@Singleton
class DoctorRepositoryImpl @Inject constructor(
    private val doctorDao: DoctorDao,
    private val firebaseService: FirebaseService
) : DoctorRepository {
    override fun getAllDoctors(): Flow<List<Doctor>> = flow {
        try {
            val doctors = firebaseService.getAllDoctors()
            doctorDao.insertDoctors(doctors)
            emit(doctors)
        } catch (e: Exception) {
            emit(emptyList())
        }
    }

    override suspend fun getDoctorById(id: String): Doctor? =
        firebaseService.getDoctorById(id)

    override fun getDoctorsBySpecialty(specialty: String): Flow<List<Doctor>> = flow {
        try {
            val doctors = firebaseService.getDoctorsBySpecialty(specialty)
            emit(doctors)
        } catch (e: Exception) {
            emit(emptyList())
        }
    }

    override suspend fun fetchAndSaveDoctors(): Result<Unit> =
        try {
            val doctors = firebaseService.getAllDoctors()
            doctorDao.insertDoctors(doctors)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
}

@Singleton
class UserRepositoryImpl @Inject constructor(
    private val userDao: UserDao,
    private val firebaseService: FirebaseService
) : UserRepository {
    override suspend fun getUser(userId: String): Result<User> =
        try {
            val user = firebaseService.getUser(userId)
            if (user != null) {
                userDao.insertUser(user)
                Result.success(user)
            } else {
                Result.failure(Exception("User not found"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }

    override suspend fun updateUser(user: User): Result<User> =
        try {
            val success = firebaseService.updateUser(user)
            if (success) {
                userDao.updateUser(user)
                Result.success(user)
            } else {
                Result.failure(Exception("Failed to update user"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }

    override suspend fun createUser(user: User): Result<User> =
        try {
            val success = firebaseService.createUser(user)
            if (success) {
                userDao.insertUser(user)
                Result.success(user)
            } else {
                Result.failure(Exception("Failed to create user"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
}
