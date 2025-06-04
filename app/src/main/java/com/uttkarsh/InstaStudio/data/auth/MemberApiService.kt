package com.uttkarsh.InstaStudio.data.auth

import com.uttkarsh.InstaStudio.domain.model.ApiResponse
import com.uttkarsh.InstaStudio.domain.model.PagedResponse
import com.uttkarsh.InstaStudio.domain.model.dto.member.MemberProfileRequestDTO
import com.uttkarsh.InstaStudio.domain.model.dto.member.MemberProfileResponseDTO
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query
import java.time.LocalDateTime
import javax.inject.Singleton

@Singleton
interface MemberApiService {

    @POST("/api/v1/register/member")
    suspend fun createNewMember(@Body request: MemberProfileRequestDTO) : ApiResponse<MemberProfileResponseDTO>

    @GET("/api/v1/{studioId}/all-members")
    suspend fun getAllMembers(@Path("studioId") studioId: Long,
                              @Query("PageNumber") page: Int,
    ) : ApiResponse<PagedResponse<MemberProfileResponseDTO>>

    @GET("/api/v1/{studioId}/available-members")
    suspend fun getAvailableMembers(@Path("studioId") studioId: Long,
                            @Query("startDate") startDate: LocalDateTime,
                            @Query("endDate") endDate : LocalDateTime
    ) : ApiResponse<List<MemberProfileResponseDTO>>

    @GET("/api/v1/{studioId}/member/{memberId}")
    suspend fun getMemberBuId(
        @Path("studioId") studioId: Long,
        @Path("memberId") memberId: Long
    ): ApiResponse<MemberProfileResponseDTO>

    @PUT("/api/v1/{studioId}/edit-member/{memberId}")
    suspend fun updateMemberById(
        @Path("studioId") studioId: Long,
        @Path("memberId") memberId: Long,
        @Body request: MemberProfileRequestDTO

    ): ApiResponse<MemberProfileResponseDTO>

}