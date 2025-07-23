package com.uttkarsh.InstaStudio.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.uttkarsh.InstaStudio.domain.model.dto.member.MemberProfileResponseDTO
import com.uttkarsh.InstaStudio.domain.usecase.member.MemberUseCases
import com.uttkarsh.InstaStudio.utils.states.MemberState
import com.uttkarsh.InstaStudio.utils.time.TimeProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MemberViewModel @Inject constructor(
    private val memberUseCases: MemberUseCases,
    private val timeProvider: TimeProvider

): ViewModel() {

    private val _memberState = MutableStateFlow<MemberState>(MemberState.Idle)
    val memberState = _memberState.asStateFlow()

    private val _expandedMemberId = MutableStateFlow<Long?>(null)
    val expandedMemberId = _expandedMemberId.asStateFlow()

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


    fun updateMemberSalary(newSalary: Long) {
        _memberSalary.value = newSalary
    }

    fun setExpandedMemberId(id: Long?) {
        _expandedMemberId.value = id
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
    fun prepareEditMember(member: MemberProfileResponseDTO) {
        updateMemberId(member.memberId)
        updateMemberName(member.memberName)
        updateMemberSalary(member.salary)
        updateMemberSpecialization(member.specialization)
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

    fun getAvailableMembers(eventStartDate: String, eventStartTime: String, eventEndDate: String, eventEndTime: String) {
        viewModelScope.launch(Dispatchers.IO) {

            val eventStart = timeProvider.parseToLocalDateTime(eventStartDate + "T" + eventStartTime)!!
            val eventEnd = timeProvider.parseToLocalDateTime(eventEndDate + "T" + eventEndTime)!!

            try {
                val response = memberUseCases.getAvailableMembers(
                    startDate = eventStart,
                    endDate = eventEnd
                )
                _memberState.value = MemberState.ListSuccess(response)
            } catch (e: Exception) {
                _memberState.value = MemberState.Error(e.localizedMessage ?: "Unexpected error")
            }
        }
    }


}