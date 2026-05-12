package com.dentalcare.app.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dentalcare.app.data.models.Appointment
import com.dentalcare.app.data.models.Doctor
import com.dentalcare.app.data.models.User
import com.dentalcare.app.domain.repository.AppointmentRepository
import com.dentalcare.app.domain.repository.DoctorRepository
import com.dentalcare.app.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import java.util.UUID

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val appointmentRepository: AppointmentRepository,
    private val doctorRepository: DoctorRepository
) : ViewModel() {
    private val _upcomingAppointments = MutableStateFlow<List<Appointment>>(emptyList())
    val upcomingAppointments: StateFlow<List<Appointment>> = _upcomingAppointments.asStateFlow()

    private val _popularDoctors = MutableStateFlow<List<Doctor>>(emptyList())
    val popularDoctors: StateFlow<List<Doctor>> = _popularDoctors.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                doctorRepository.getAllDoctors().collect {
                    _popularDoctors.value = it.take(5)
                }
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }
}

@HiltViewModel
class AppointmentsViewModel @Inject constructor(
    private val appointmentRepository: AppointmentRepository
) : ViewModel() {
    private val _appointments = MutableStateFlow<List<Appointment>>(emptyList())
    val appointments: StateFlow<List<Appointment>> = _appointments.asStateFlow()

    private val _selectedStatus = MutableStateFlow("all")
    val selectedStatus: StateFlow<String> = _selectedStatus.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val userId = "user_${UUID.randomUUID()}" // Replace with actual user ID from auth

    init {
        loadAppointments()
    }

    private fun loadAppointments() {
        viewModelScope.launch {
            appointmentRepository.getAllAppointments(userId).collect {
                _appointments.value = it
            }
        }
    }

    fun filterByStatus(status: String) {
        _selectedStatus.value = status
        viewModelScope.launch {
            if (status == "all") {
                appointmentRepository.getAllAppointments(userId).collect {
                    _appointments.value = it
                }
            } else {
                appointmentRepository.getAppointmentsByStatus(userId, status).collect {
                    _appointments.value = it
                }
            }
        }
    }

    fun cancelAppointment(id: String) {
        viewModelScope.launch {
            appointmentRepository.cancelAppointment(id)
            loadAppointments()
        }
    }
}

@HiltViewModel
class DoctorsViewModel @Inject constructor(
    private val doctorRepository: DoctorRepository
) : ViewModel() {
    private val _doctors = MutableStateFlow<List<Doctor>>(emptyList())
    val doctors: StateFlow<List<Doctor>> = _doctors.asStateFlow()

    private val _selectedSpecialty = MutableStateFlow("All")
    val selectedSpecialty: StateFlow<String> = _selectedSpecialty.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    init {
        loadDoctors()
    }

    private fun loadDoctors() {
        viewModelScope.launch {
            _isLoading.value = true
            doctorRepository.getAllDoctors().collect {
                _doctors.value = it
                _isLoading.value = false
            }
        }
    }

    fun filterBySpecialty(specialty: String) {
        _selectedSpecialty.value = specialty
        if (specialty == "All") {
            loadDoctors()
        } else {
            viewModelScope.launch {
                doctorRepository.getDoctorsBySpecialty(specialty).collect {
                    _doctors.value = it
                }
            }
        }
    }
}

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {
    private val _currentUser = MutableStateFlow<User?>(null)
    val currentUser: StateFlow<User?> = _currentUser.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    private val userId = "user_${UUID.randomUUID()}" // Replace with actual user ID from auth

    init {
        loadUser()
    }

    private fun loadUser() {
        viewModelScope.launch {
            _isLoading.value = true
            userRepository.getUser(userId).onSuccess {
                _currentUser.value = it
            }.onFailure {
                _error.value = it.message
            }
            _isLoading.value = false
        }
    }

    fun updateUserProfile(user: User) {
        viewModelScope.launch {
            _isLoading.value = true
            userRepository.updateUser(user).onSuccess {
                _currentUser.value = it
            }.onFailure {
                _error.value = it.message
            }
            _isLoading.value = false
        }
    }
}
