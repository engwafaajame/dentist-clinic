package com.dentalcare.app.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dentalcare.app.data.models.Appointment
import com.dentalcare.app.data.models.Doctor
import com.dentalcare.app.data.models.User

@Database(
    entities = [Appointment::class, Doctor::class, User::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun appointmentDao(): AppointmentDao
    abstract fun doctorDao(): DoctorDao
    abstract fun userDao(): UserDao
}
