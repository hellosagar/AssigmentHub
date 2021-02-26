package com.amplifyframework.datastore.generated.model;

import com.amplifyframework.core.model.annotations.BelongsTo;

import java.util.List;
import java.util.UUID;
import java.util.Objects;

import androidx.core.util.ObjectsCompat;

import com.amplifyframework.core.model.Model;
import com.amplifyframework.core.model.annotations.Index;
import com.amplifyframework.core.model.annotations.ModelConfig;
import com.amplifyframework.core.model.annotations.ModelField;
import com.amplifyframework.core.model.query.predicate.QueryField;

import static com.amplifyframework.core.model.query.predicate.QueryField.field;

/** This is an auto generated class representing the StudentAssignmentMapping type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "StudentAssignmentMappings")
@Index(name = "undefined", fields = {"studentID","assignmentID"})
@Index(name = "byAssignmentID", fields = {"assignmentID"})
@Index(name = "byStudentID", fields = {"studentID"})
public final class StudentAssignmentMapping implements Model {
  public static final QueryField ID = field("StudentAssignmentMapping", "id");
  public static final QueryField STATUS = field("StudentAssignmentMapping", "status");
  public static final QueryField STUDENT = field("StudentAssignmentMapping", "studentID");
  public static final QueryField ASSIGNMENT = field("StudentAssignmentMapping", "assignmentID");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="Boolean", isRequired = true) Boolean status;
  private final @ModelField(targetType="Student") @BelongsTo(targetName = "studentID", type = Student.class) Student student;
  private final @ModelField(targetType="Assignment") @BelongsTo(targetName = "assignmentID", type = Assignment.class) Assignment assignment;
  public String getId() {
      return id;
  }
  
  public Boolean getStatus() {
      return status;
  }
  
  public Student getStudent() {
      return student;
  }
  
  public Assignment getAssignment() {
      return assignment;
  }
  
  private StudentAssignmentMapping(String id, Boolean status, Student student, Assignment assignment) {
    this.id = id;
    this.status = status;
    this.student = student;
    this.assignment = assignment;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      StudentAssignmentMapping studentAssignmentMapping = (StudentAssignmentMapping) obj;
      return ObjectsCompat.equals(getId(), studentAssignmentMapping.getId()) &&
              ObjectsCompat.equals(getStatus(), studentAssignmentMapping.getStatus()) &&
              ObjectsCompat.equals(getStudent(), studentAssignmentMapping.getStudent()) &&
              ObjectsCompat.equals(getAssignment(), studentAssignmentMapping.getAssignment());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getStatus())
      .append(getStudent())
      .append(getAssignment())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("StudentAssignmentMapping {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("status=" + String.valueOf(getStatus()) + ", ")
      .append("student=" + String.valueOf(getStudent()) + ", ")
      .append("assignment=" + String.valueOf(getAssignment()))
      .append("}")
      .toString();
  }
  
  public static StatusStep builder() {
      return new Builder();
  }
  
  /** 
   * WARNING: This method should not be used to build an instance of this object for a CREATE mutation.
   * This is a convenience method to return an instance of the object with only its ID populated
   * to be used in the context of a parameter in a delete mutation or referencing a foreign key
   * in a relationship.
   * @param id the id of the existing item this instance will represent
   * @return an instance of this model with only ID populated
   * @throws IllegalArgumentException Checks that ID is in the proper format
   */
  public static StudentAssignmentMapping justId(String id) {
    try {
      UUID.fromString(id); // Check that ID is in the UUID format - if not an exception is thrown
    } catch (Exception exception) {
      throw new IllegalArgumentException(
              "Model IDs must be unique in the format of UUID. This method is for creating instances " +
              "of an existing object with only its ID field for sending as a mutation parameter. When " +
              "creating a new object, use the standard builder method and leave the ID field blank."
      );
    }
    return new StudentAssignmentMapping(
      id,
      null,
      null,
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      status,
      student,
      assignment);
  }
  public interface StatusStep {
    BuildStep status(Boolean status);
  }
  

  public interface BuildStep {
    StudentAssignmentMapping build();
    BuildStep id(String id) throws IllegalArgumentException;
    BuildStep student(Student student);
    BuildStep assignment(Assignment assignment);
  }
  

  public static class Builder implements StatusStep, BuildStep {
    private String id;
    private Boolean status;
    private Student student;
    private Assignment assignment;
    @Override
     public StudentAssignmentMapping build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new StudentAssignmentMapping(
          id,
          status,
          student,
          assignment);
    }
    
    @Override
     public BuildStep status(Boolean status) {
        Objects.requireNonNull(status);
        this.status = status;
        return this;
    }
    
    @Override
     public BuildStep student(Student student) {
        this.student = student;
        return this;
    }
    
    @Override
     public BuildStep assignment(Assignment assignment) {
        this.assignment = assignment;
        return this;
    }
    
    /** 
     * WARNING: Do not set ID when creating a new object. Leave this blank and one will be auto generated for you.
     * This should only be set when referring to an already existing object.
     * @param id id
     * @return Current Builder instance, for fluent method chaining
     * @throws IllegalArgumentException Checks that ID is in the proper format
     */
    public BuildStep id(String id) throws IllegalArgumentException {
        this.id = id;
        
        try {
            UUID.fromString(id); // Check that ID is in the UUID format - if not an exception is thrown
        } catch (Exception exception) {
          throw new IllegalArgumentException("Model IDs must be unique in the format of UUID.",
                    exception);
        }
        
        return this;
    }
  }
  

  public final class CopyOfBuilder extends Builder {
    private CopyOfBuilder(String id, Boolean status, Student student, Assignment assignment) {
      super.id(id);
      super.status(status)
        .student(student)
        .assignment(assignment);
    }
    
    @Override
     public CopyOfBuilder status(Boolean status) {
      return (CopyOfBuilder) super.status(status);
    }
    
    @Override
     public CopyOfBuilder student(Student student) {
      return (CopyOfBuilder) super.student(student);
    }
    
    @Override
     public CopyOfBuilder assignment(Assignment assignment) {
      return (CopyOfBuilder) super.assignment(assignment);
    }
  }
  
}
