package com.uttkarsh.InstaStudio.presentation.viewmodel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import com.uttkarsh.InstaStudio.domain.repository.MemberRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.uttkarsh.InstaStudio.domain.model.dto.member.MemberProfileRequestDTO
import com.uttkarsh.InstaStudio.domain.model.validators.validate
import com.uttkarsh.InstaStudio.domain.usecase.member.MemberUseCases
import com.uttkarsh.InstaStudio.utils.SharedPref.SessionStore
import com.uttkarsh.InstaStudio.utils.api.ApiErrorExtractor
import com.uttkarsh.InstaStudio.utils.session.SessionManager
import com.uttkarsh.InstaStudio.utils.states.MemberState
import com.uttkarsh.InstaStudio.utils.states.ResourceState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class MemberViewModel @Inject constructor(
    private val memberUseCases: MemberUseCases

): ViewModel() {

    private val _memberState = MutableStateFlow<MemberState>(MemberState.Idle)
    val memberState = _memberState.asStateFlow()

    private val _memberId = MutableStateFlow(0L)
    val memberId: StateFlow<Long> = _memberId

    private val _memberName = MutableStateFlow("")
    val memberName: StateFlow<String> = _memberName

    private val _memberEmail = MutableStateFlow("")
    val memberEmail: StateFlow<String> = _memberEmail

    private val _memberSalary = MutableStateFlow(0L)
    val memberSalary: StateFlow<Long> = _memberSalary

    private val _memberPhone= MutableStateFlow("")
    val memberPhone: StateFlow<String> = _memberPhone

    private val _memberAvgRating= MutableStateFlow(0L)
    val memberAvgRating: StateFlow<Long> = _memberAvgRating

    private val _memberSpecialization = MutableStateFlow("")
    val memberSpecialization: StateFlow<String> = _memberSpecialization

    @RequiresApi(Build.VERSION_CODES.O)
    private val _eventStartDate = MutableStateFlow(LocalDateTime.now())
    @RequiresApi(Build.VERSION_CODES.O)
    val eventStartDate: StateFlow<LocalDateTime> = _eventStartDate

    @RequiresApi(Build.VERSION_CODES.O)
    private val _eventEndDate = MutableStateFlow(LocalDateTime.now())
    @RequiresApi(Build.VERSION_CODES.O)
    val eventEndDate: StateFlow<LocalDateTime> = _eventEndDate

    fun updateMemberSalary(newSalary: Long) {
        _memberSalary.value = newSalary
    }

    fun updateMemberPhone(newPhone: String) {
        _memberPhone.value = newPhone
    }


    fun updateMemberName(newName: String) {
        _memberName.value = newName
    }

    fun updateMemberSpecialization(newSpec: String) {
        _memberSpecialization.value = newSpec
    }

    fun updateMemberEmail(newEmail: String) {
        _memberEmail.value = newEmail
    }

    fun updateMemberId(newId: Long) {
        _memberId.value = newId
    }

    fun clearMemberValues(){
        updateMemberId(0L)
        updateMemberEmail("")
        updateMemberSalary(0L)
        updateMemberSpecialization("")

    }

    init {
        getAllMembers()
    }

    fun getAllMembers(){
        viewModelScope.launch(Dispatchers.IO) {
            _memberState.value = MemberState.Loading

            val response = memberUseCases.getAllMembers()
                .cachedIn(viewModelScope)
                .stateIn(viewModelScope, SharingStarted.Lazily, PagingData.empty())

            _memberState.value = MemberState.PageSuccess(response)
        }
    }

    fun createNewUser(){
        viewModelScope.launch(Dispatchers.IO) {
            _memberState.value = MemberState.Loading
            try{

                val response = memberUseCases.createMember(
                    email = _memberEmail.value,
                    salary = _memberSalary.value,
                    specialization = _memberSpecialization.value
                )

                _memberState.value = MemberState.Success(response)


            }catch(e: Exception) {
                _memberState.value = MemberState.Error(e.localizedMessage ?: "An unexpected error occurred.")
            }
        }
    }

    fun updateMemberById() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _memberState.value = MemberState.Loading

                val response = memberUseCases.updateMemberById(
                    memberId = _memberId.value,
                    email = _memberEmail.value,
                    salary = _memberSalary.value,
                    specialization = _memberSpecialization.value
                )

                _memberState.value = MemberState.Success(response)

            } catch (e: Exception) {
                _memberState.value = MemberState.Error(e.localizedMessage ?: "Unexpected error")
            }
        }
    }

    fun getMemberById() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = memberUseCases.getMemberById(_memberId.value)
                _memberState.value = MemberState.Success(response)
            } catch (e: Exception) {
                _memberState.value = MemberState.Error(e.localizedMessage ?: "Unexpected error")
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getAvailableMembers() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = memberUseCases.getAvailableMembers(
                    startDate = _eventStartDate.value,
                    endDate = _eventEndDate.value
                )
                _memberState.value = MemberState.ListSuccess(response)
            } catch (e: Exception) {
                _memberState.value = MemberState.Error(e.localizedMessage ?: "Unexpected error")
            }
        }
    }

}