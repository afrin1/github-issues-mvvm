package com.project.githubissues.ui.issueList

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.project.githubissues.model.issuelist.Issue
import com.project.githubissues.model.issuelist.IssueService
import com.project.githubissues.model.issuelist.User
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.Request
import okhttp3.ResponseBody
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@RunWith(MockitoJUnitRunner::class)
class IssueListViewModelTest {

    private lateinit var systemUnderTest: IssueListViewModel

    private val ISSUES_LIST = generateIssues()

    private fun generateIssues(): List<Issue> {
        val issue = Issue(1, "Title", "Description", "updated at", User("username", "avatar url"))
        return listOf(issue)
    }

    enum class ResponseCode(val rgb: Int) {
        SUCCESS(0),
        RESPONSE_ERROR(1),
        FAILURE(2)
    }

    @Mock
    private lateinit var mockIssueService: IssueService

    // To test ViewModel
    @Rule
    @JvmField
    var rule: TestRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        systemUnderTest = IssueListViewModel(mockIssueService)
    }

    @Test
    fun `should hide Progress Bar when issues are fetched successfully`() {
        success()

        systemUnderTest.getIssues()

        assertThat(systemUnderTest.issues.value, `is`(equalTo(ISSUES_LIST)))
    }

    @Test
    fun `should hide Progress Bar when issues are are not fetched succesfully due ro response error`() {
        responseError()

        systemUnderTest.getIssues()

        assertThat(systemUnderTest.fetchError.value, `is`(true))
    }

    @Test
    fun `should hide Progress Bar when issues are not fetched succesfully due to API Error`() {
        failure()

        systemUnderTest.getIssues()

        assertThat(systemUnderTest.fetchError.value, `is`(true))
    }

    private fun success() {
        val issuesListSuccess: Call<List<Issue>> = fetchResponse(ResponseCode.SUCCESS)
        `when`(mockIssueService.getIssuesList()).thenReturn(issuesListSuccess)
    }

    private fun responseError() {
        val issuesListFailure: Call<List<Issue>> = fetchResponse(ResponseCode.RESPONSE_ERROR)
        `when`(mockIssueService.getIssuesList()).thenReturn(issuesListFailure)
    }

    private fun failure() {
        val issuesListFailure: Call<List<Issue>> = fetchResponse(ResponseCode.FAILURE)
        `when`(mockIssueService.getIssuesList()).thenReturn(issuesListFailure)
    }


    private fun fetchResponse(response: ResponseCode): Call<List<Issue>> {
        return object : Call<List<Issue>> {
            override fun enqueue(callback: Callback<List<Issue>>?) {
                when (response.ordinal) {
                    0 -> {
                        callback?.onResponse(
                            mockIssueService.getIssuesList(),
                            Response.success(ISSUES_LIST)
                        )
                    }
                    1 -> {
                        callback?.onResponse(
                            mockIssueService.getIssuesList(), Response.error(
                                404, ResponseBody.create(
                                    "application/json".toMediaTypeOrNull(),
                                    "{\"key\":[\"somestuff\"]}"
                                )
                            )
                        )
                    }
                    else -> {
                        callback?.onFailure(
                            mockIssueService.getIssuesList(),
                            Exception("Fetch failed")
                        )
                    }
                }
            }

            override fun isExecuted(): Boolean {
                return false
            }

            override fun clone(): Call<List<Issue>> {
                return this
            }

            override fun isCanceled(): Boolean {
                return false
            }

            override fun cancel() {}

            override fun request(): Request {
                return Request.Builder().build()
            }

            override fun execute(): Response<List<Issue>> {
                return Response.success(listOf())
            }

        }
    }

}