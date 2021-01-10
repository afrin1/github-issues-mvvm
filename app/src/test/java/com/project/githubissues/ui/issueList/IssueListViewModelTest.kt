package com.project.githubissues.ui.issueList

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nhaarman.mockitokotlin2.KArgumentCaptor
import com.project.githubissues.model.database.IssueData
import com.project.githubissues.model.database.IssuesListDatabaseDAO
import com.project.githubissues.model.issuelist.Issue
import com.project.githubissues.model.issuelist.IssueService
import com.project.githubissues.model.issuelist.User
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.Request
import okhttp3.ResponseBody
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.CoreMatchers.nullValue
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner
import org.mockito4kotlin.annotation.KCaptor
import org.mockito4kotlin.annotation.KMockitoAnnotations
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


@RunWith(MockitoJUnitRunner::class)
class IssueListViewModelTest {

    private lateinit var systemUnderTest: IssueListViewModel

    private val ISSUES_LIST = generateIssues()
    private val ISSUES_DATA_LIST = listOf(
        IssueData(
            1,
            "title",
            "description",
            "updated at",
            "username",
            "avatar url"
        )
    )

    private fun generateIssues(): List<Issue> {
        val issue = Issue(1, "title", "description", "updated at", User("username", "avatar url"))
        return listOf(issue)
    }

    enum class ResponseCode(val rgb: Int) {
        SUCCESS(0),
        RESPONSE_ERROR(1),
        FAILURE(2)
    }

    @Mock
    private lateinit var mockIssueService: IssueService

    @Mock
    private lateinit var mockIssuesDBDatabase: IssuesListDatabaseDAO

    //    @Captor
    @KCaptor
    private lateinit var issueDataListCaptor: KArgumentCaptor<List<IssueData>>

    // To test ViewModel
    @Rule
    @JvmField
    var rule: TestRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        KMockitoAnnotations.initMocks(this)
        systemUnderTest = IssueListViewModel(mockIssueService, mockIssuesDBDatabase)
    }

    @Ignore
    @Test
    fun `should post issues as null when issues are not available in the cache`() {
        failure()
        emptyCache()

        assertThat(systemUnderTest.issues.value, `is`(nullValue()))
    }

    @Ignore
    @Test
    fun `should post issues list when issues are available in the cache`() {
        failure()
        createCache()

        systemUnderTest.getIssues()

        assertThat(systemUnderTest.issues.value, `is`(equalTo(ISSUES_DATA_LIST)))
    }

    @Test
    fun `should call getIssuesList to fetch using the Service when getIssues is called`() {
        emptyCache()
        success()

        systemUnderTest.getIssues()

        verify(mockIssueService, times(2)).getIssuesList()
    }

    @Test
    fun `should insert issues list to DB when fetch is successful`() {
        emptyCache()
        success()

        systemUnderTest.getIssues()

        verify(mockIssuesDBDatabase).insertIssues(issueDataListCaptor.capture())
        val captures: List<List<IssueData>> = issueDataListCaptor.allValues
        val captureData = captures[0]
        assertThat(captureData, `is`(equalTo(ISSUES_DATA_LIST)))
    }

    @Test
    fun `should call fetch error when db is empty and network fetch failed due to 404`() {
        emptyCache()
        failure()

        systemUnderTest.getIssues()

        assertThat(systemUnderTest.fetchError.value, `is`(equalTo(true)))
    }

    @Test
    fun `should throw fetch error when db is empty and network fetch failed due to response error`() {
        emptyCache()
        responseError()

        systemUnderTest.getIssues()

        assertThat(systemUnderTest.fetchError.value, `is`(equalTo(true)))
    }

    private fun emptyCache() {
//        val issues: LiveData<List<IssueData>> = MutableLiveData<List<IssueData>>(listOf())
//        `when`(mockIssuesDBDatabase.getIssuesList()).thenReturn(issues)
    }

    private fun createCache() {
        val issues: LiveData<List<IssueData>> = MutableLiveData<List<IssueData>>(ISSUES_DATA_LIST)
        `when`(mockIssuesDBDatabase.getIssuesList()).thenReturn(issues)
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