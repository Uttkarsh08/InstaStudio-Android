package com.uttkarsh.InstaStudio.domain.usecase.member

data class MemberUseCases(
    val getAllMembers: GetAllMembersUseCase,
    val createMember: CreateMemberUseCase,
    val updateMemberById: UpdateMemberByIdUseCase,
    val getMemberById: GetMemberByIdUseCase,
    val getAvailableMembers: GetAvailableMembersUseCase
)
