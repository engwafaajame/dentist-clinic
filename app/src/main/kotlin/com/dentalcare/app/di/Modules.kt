package com.dentalcare.app.di

import android.content.Context
import androidx.room.Room
import com.dentalcare.app.data.local.AppDatabase
import com.dentalcare.app.data.local.AppointmentDao
import com.dentalcare.app.data.local.DoctorDao
import com.dentalcare.app.data.local.UserDao
import com.dentalcare.app.data.remote.FirebaseService
import com.dentalcare.app.data.repository.AppointmentRepositoryImpl
import com.dentalcare.app.data.repository.DoctorRepositoryImpl
import com.dentalcare.app.data.repository.UserRepositoryImpl
import com.dentalcare.app.domain.repository.AppointmentRepository
import com.dentalcare.app.domain.repository.DoctorRepository
import com.dentalcare.app.domain.repository.UserRepository
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "dental_care_db"
        ).build()
    }

    @Singleton
    @Provides
    fun provideAppointmentDao(database: AppDatabase): AppointmentDao {
        return database.appointmentDao()
    }

    @Singleton
    @Provides
    fun provideDoctorDao(database: AppDatabase): DoctorDao {
        return database.doctorDao()
    }

    @Singleton
    @Provides
    fun provideUserDao(database: AppDatabase): UserDao {
        return database.userDao()
    }
}

@Module
@InstallIn(SingletonComponent::class)
object FirebaseModule {
    @Singleton
    @Provides
    fun provideFirebaseFirestore(): FirebaseFirestore {
        return Firebase.firestore
    }

    @Singleton
    @Provides
    fun provideFirebaseService(firestore: FirebaseFirestore): FirebaseService {
        return FirebaseService(firestore)
    }
}

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Singleton
    @Provides
    fun provideAppointmentRepository(
        appointmentDao: AppointmentDao,
        firebaseService: FirebaseService
    ): AppointmentRepository {
        return AppointmentRepositoryImpl(appointmentDao, firebaseService)
    }

    @Singleton
    @Provides
    fun provideDoctorRepository(
        doctorDao: DoctorDao,
        firebaseService: FirebaseService
    ): DoctorRepository {
        return DoctorRepositoryImpl(doctorDao, firebaseService)
    }

    @Singleton
    @Provides
    fun provideUserRepository(
        userDao: UserDao,
        firebaseService: FirebaseService
    ): UserRepository {
        return UserRepositoryImpl(userDao, firebaseService)
    }
}
