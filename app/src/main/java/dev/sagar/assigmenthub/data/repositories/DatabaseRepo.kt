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

class DatabaseRepo @Inject constructor(
    private val api: ApiCategory
) {

    fun createTeacher(
        name: String,
        email: String,
        callback: (ResponseModel<GraphQLResponse<Teacher>>) -> Unit
    ) {
        val teacher = Teacher.builder()
            .name(name)
            .email(email)
            .build()

        api.mutate(
            ModelMutation.create(teacher),
            { result ->
                Timber.i("createTeacher result: $result")
                callback(ResponseModel.Success(result, "Teacher created in Database!"))
            },
            { error ->
                Timber.e("createTeacher error $error")
                callback(ResponseModel.Error(error, error.recoverySuggestion))
            }
        )
    }

    fun getTeacher(
        email: String,
        callback: (ResponseModel<Teacher>) -> Unit
    ) {
        api.mutate(
            ModelQuery.list(Teacher::class.java, Teacher.EMAIL.eq(email)),
            { result ->
                Timber.i("getTeacher result: $result")
                val teacher = (result.data.items).toList()[0]
                callback(ResponseModel.Success(teacher))
            },
            { error ->
                Timber.e("getTeacher error: $error")
                callback(ResponseModel.Error(error, error.recoverySuggestion))
            }
        )
    }

    fun getAssignments(
        email: String,
        callback: (ResponseModel<List<Assignment>>) -> Unit
    ) {
        api.mutate(
            ModelQuery.list(Teacher::class.java, Teacher.EMAIL.eq(email)),
            { result ->
                Timber.i("getAssignments result: $result")
                val assignments = (result.data.items).toList()[0].assignments.toList()
                callback(ResponseModel.Success(assignments))
            },
            { error ->
                Timber.e("getTeacher error: $error")
                callback(ResponseModel.Error(error, error.recoverySuggestion))
            }
        )
    }

    fun createStudent(
        name: String,
        rollNo: Int,
        branch: Branch,
        year: Year,
        email: String,
        callback: (ResponseModel<String>) -> Unit
    ) {
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
                callback(ResponseModel.Success("Student created in Database!"))
            },
            { error ->
                Timber.e("createStudent error $error")
                callback(ResponseModel.Error(error, error.recoverySuggestion))
            }
        )
    }

    fun createAssignment(
        name: String,
        subject: String,
        branch: Branch,
        year: Year,
        date: Date,
        description: String,
        teacherID: String,
        callback: (ResponseModel<String>) -> Unit
    ) {
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
                callback(ResponseModel.Success("Assignment created in Database!"))
            },
            { error ->
                Timber.e("createAssignment error $error")
                callback(ResponseModel.Error(error, error.recoverySuggestion))
            }
        )
    }
}
