package com.dentalcare.app.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable
import java.util.Date

@Serializable
@Entity(tableName = "appointments")
data class Appointment(
    @PrimaryKey
    val id: String = "",
    val doctorId: String = "",
    val doctorName: String = "",
    val specialty: String = "",
    val appointmentDate: Long = 0L,
    val appointmentTime: String = "",
    val serviceType: String = "",
    val status: String = "pending",
    val notes: String = "",
    val createdAt: Long = System.currentTimeMillis(),
    val userId: String = ""
)

@Serializable
@Entity(tableName = "doctors")
data class Doctor(
    @PrimaryKey
    val id: String = "",
    val name: String = "",
    val specialty: String = "",
    val experience: String = "",
    val rating: Float = 4.5f,
    val image: String = "",
    val bio: String = "",
    val phone: String = "",
    val availability: String = "Available",
    val createdAt: Long = System.currentTimeMillis()
)

@Serializable
@Entity(tableName = "users")
data class User(
    @PrimaryKey
    val id: String = "",
    val name: String = "",
    val email: String = "",
    val phone: String = "",
    val dateOfBirth: String = "",
    val gender: String = "",
    val address: String = "",
    val profileImage: String = "",
    val createdAt: Long = System.currentTimeMillis()
)
