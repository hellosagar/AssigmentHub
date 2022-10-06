package dev.sagar.assigmenthub.data.repositories

import com.amplifyframework.api.ApiCategory
import com.amplifyframework.api.graphql.GraphQLResponse
import com.amplifyframework.api.graphql.model.ModelMutation
import com.amplifyframework.api.graphql.model.ModelQuery
import com.amplifyframework.core.model.temporal.Temporal
import com.amplifyframework.datastore.generated.model.* // ktlint-disable no-wildcard-imports
import dev.sagar.assigmenthub.utils.ResponseModel
import dev.sagar.assigmenthub.utils.getUUIDString
import timber.log.Timber
import java.util.* // ktlint-disable no-wildcard-imports
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class DatabaseRepo @Inject constructor(
    private val api: ApiCategory
) {

    suspend fun createTeacher(
        name: String,
        email: String
    ) = suspendCoroutine<ResponseModel<GraphQLResponse<Teacher>>> {
        val teacher = Teacher.builder()
            .name(name)
            .email(email)
            .build()

        api.mutate(
            ModelMutation.create(teacher),
            { result ->
                Timber.i("createTeacher result: $result")
                it.resume(ResponseModel.Success(result, "Teacher created in Database!"))
            },
            { error ->
                Timber.e("createTeacher error $error")
                it.resume(ResponseModel.Error(error, error.recoverySuggestion))
            }
        )
    }

    suspend fun getTeacher(
        email: String
    ) = suspendCoroutine<ResponseModel<Teacher>> {
        api.mutate(
            ModelQuery.list(Teacher::class.java, Teacher.EMAIL.eq(email)),
            { result ->
                Timber.i("getTeacher result: $result")
                val teacher = (result.data.items).toList()[0]
                it.resume(ResponseModel.Success(teacher))
            },
            { error ->
                Timber.e("getTeacher error: $error")
                it.resume(ResponseModel.Error(error, error.recoverySuggestion))
            }
        )
    }

    suspend fun getAssignments(
        teacherID: String
    ) = suspendCoroutine<ResponseModel<List<Assignment>>> {
        api.mutate(
            ModelQuery.list(Assignment::class.java, Assignment.TEACHER_ID.eq(teacherID)),
            { result ->
                Timber.i("getAssignments result: $result")
                val assignments: List<Assignment> = (result.data.items).toList()
                it.resume(ResponseModel.Success(assignments))
            },
            { error ->
                Timber.e("getTeacher error: $error")
                it.resume(ResponseModel.Error(error, error.recoverySuggestion))
            }
        )
    }

    suspend fun getAllAssignmentsSameBranchYearID(
        branchYearID: String
    ) = suspendCoroutine<ResponseModel<List<Assignment>>> {
        api.mutate(
            ModelQuery.list(Assignment::class.java, Assignment.BRANCH_YEAR_ID.eq(branchYearID)),
            { result ->
                Timber.i("getAllAssignmentsSameBranchYearID result: $result")
                val assignments: List<Assignment> = (result.data.items).toList()
                it.resume(ResponseModel.Success(assignments))
            },
            { error ->
                Timber.e("getAllAssignmentsSameBranchYearID error: $error")
                it.resume(ResponseModel.Error(error, error.recoverySuggestion))
            }
        )
    }

    suspend fun endAssignment(
        assignment: Assignment
    ) = suspendCoroutine<ResponseModel<String>> {
        api.mutate(
            ModelMutation.update(assignment),
            { result ->
                Timber.i("endAssignment result: $result")
                it.resume(ResponseModel.Success(result.toString()))
            },
            { error ->
                Timber.e("endAssignment error: $error")
                it.resume(ResponseModel.Error(error, error.recoverySuggestion))
            }
        )
    }

    suspend fun createStudent(
        name: String,
        rollNo: Int,
        branch: Branch,
        year: Year,
        email: String
    ) = suspendCoroutine<ResponseModel<Student>> {
        val student = Student.builder()
            .name(name)
            .rollNo(rollNo)
            .branch(branch)
            .year(year)
            .branchYearId("$branch$year".getUUIDString())
            .email(email)
            .build()

        api.mutate(
            ModelMutation.create(student),
            { result ->
                Timber.i("createStudent result: $result")
                it.resume(ResponseModel.Success(result.data))
            },
            { error ->
                Timber.e("createStudent error $error")
                it.resume(ResponseModel.Error(error, error.recoverySuggestion))
            }
        )
    }

    suspend fun getStudentsFromBranchYearID(
        branchYearID: String
    ) = suspendCoroutine<ResponseModel<List<Student>>> {
        api.query(
            ModelQuery.list(Student::class.java, Student.BRANCH_YEAR_ID.eq(branchYearID)),
            { result ->
                val studentsList: List<Student> = (result.data.items).toList()
                it.resume(ResponseModel.Success(studentsList))
            },
            { error ->
                it.resume(ResponseModel.Error(error, "getStudentsFromBranchYearID error $error"))
            }
        )
    }

    suspend fun createStudentAssignmentMapping(
        studentID: String,
        assignmentID: String,
        status: Boolean = false
    ) = suspendCoroutine<ResponseModel<String>> {
        val studentAssignmentMapping = StudentAssignmentMapping.builder()
            .status(status)
            .assignment(Assignment.justId(assignmentID))
            .student(Student.justId(studentID))
            .build()

        api.mutate(
            ModelMutation.create(studentAssignmentMapping),
            { result ->
                Timber.i("createStudentAssignmentMapping result: $result")
                it.resume(ResponseModel.Success(result.data.toString()))
            },
            { error ->
                Timber.e("createAssignment error $error")
                it.resume(ResponseModel.Error(error, error.recoverySuggestion))
            }
        )
    }

    suspend fun updateStudentAssignmentMapping(
        studentAssignmentMapping: StudentAssignmentMapping
    ) = suspendCoroutine<ResponseModel<String>> {
        api.mutate(
            ModelMutation.update(studentAssignmentMapping),
            { result ->
                Timber.i("updateStudentAssignmentMapping result: $result")
                it.resume(ResponseModel.Success(result.data.toString()))
            },
            { error ->
                Timber.e("updateStudentAssignmentMapping error $error")
                it.resume(ResponseModel.Error(error, error.recoverySuggestion))
            }
        )
    }

    suspend fun createAssignment(
        name: String,
        subject: String,
        branch: Branch,
        year: Year,
        date: Date,
        description: String,
        teacherID: String
    ) = suspendCoroutine<ResponseModel<Assignment>> {
        val assignment = Assignment.builder()
            .teacherId(teacherID)
            .name(name)
            .subject(subject)
            .status(Status.ONGOING)
            .branch(branch)
            .year(year)
            .branchYearId("$branch$year".getUUIDString())
            .description(description)
            .lastDateSubmission(Temporal.Date(date))
            .build()

        api.mutate(
            ModelMutation.create(assignment),
            { result ->
                Timber.i("createAssignment result: $result")
                it.resume(ResponseModel.Success(result.data))
            },
            { error ->
                Timber.e("createAssignment error $error")
                it.resume(ResponseModel.Error(error, error.recoverySuggestion))
            }
        )
    }
}
