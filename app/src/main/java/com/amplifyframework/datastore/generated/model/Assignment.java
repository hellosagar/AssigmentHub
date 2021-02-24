package com.amplifyframework.datastore.generated.model;

import com.amplifyframework.core.model.annotations.HasMany;
import com.amplifyframework.core.model.temporal.Temporal;

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

/** This is an auto generated class representing the Assignment type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "Assignments")
@Index(name = "teacherIndex", fields = {"teacherID"})
@Index(name = "byAssignment", fields = {"teacherID"})
public final class Assignment implements Model {
  public static final QueryField ID = field("Assignment", "id");
  public static final QueryField TEACHER_ID = field("Assignment", "teacherID");
  public static final QueryField NAME = field("Assignment", "name");
  public static final QueryField SUBJECT = field("Assignment", "subject");
  public static final QueryField STATUS = field("Assignment", "status");
  public static final QueryField BRANCH = field("Assignment", "branch");
  public static final QueryField YEAR = field("Assignment", "year");
  public static final QueryField BRANCH_YEAR_ID = field("Assignment", "branchYearID");
  public static final QueryField DESCRIPTION = field("Assignment", "description");
  public static final QueryField LAST_DATE_SUBMISSION = field("Assignment", "lastDateSubmission");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="ID", isRequired = true) String teacherID;
  private final @ModelField(targetType="String", isRequired = true) String name;
  private final @ModelField(targetType="String", isRequired = true) String subject;
  private final @ModelField(targetType="Status", isRequired = true) Status status;
  private final @ModelField(targetType="Branch", isRequired = true) Branch branch;
  private final @ModelField(targetType="Year", isRequired = true) Year year;
  private final @ModelField(targetType="ID", isRequired = true) String branchYearID;
  private final @ModelField(targetType="String", isRequired = true) String description;
  private final @ModelField(targetType="Student") @HasMany(associatedWith = "branchYearID", type = Student.class) List<Student> students = null;
  private final @ModelField(targetType="AWSDate", isRequired = true) Temporal.Date lastDateSubmission;
  public String getId() {
      return id;
  }
  
  public String getTeacherId() {
      return teacherID;
  }
  
  public String getName() {
      return name;
  }
  
  public String getSubject() {
      return subject;
  }
  
  public Status getStatus() {
      return status;
  }
  
  public Branch getBranch() {
      return branch;
  }
  
  public Year getYear() {
      return year;
  }
  
  public String getBranchYearId() {
      return branchYearID;
  }
  
  public String getDescription() {
      return description;
  }
  
  public List<Student> getStudents() {
      return students;
  }
  
  public Temporal.Date getLastDateSubmission() {
      return lastDateSubmission;
  }
  
  private Assignment(String id, String teacherID, String name, String subject, Status status, Branch branch, Year year, String branchYearID, String description, Temporal.Date lastDateSubmission) {
    this.id = id;
    this.teacherID = teacherID;
    this.name = name;
    this.subject = subject;
    this.status = status;
    this.branch = branch;
    this.year = year;
    this.branchYearID = branchYearID;
    this.description = description;
    this.lastDateSubmission = lastDateSubmission;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      Assignment assignment = (Assignment) obj;
      return ObjectsCompat.equals(getId(), assignment.getId()) &&
              ObjectsCompat.equals(getTeacherId(), assignment.getTeacherId()) &&
              ObjectsCompat.equals(getName(), assignment.getName()) &&
              ObjectsCompat.equals(getSubject(), assignment.getSubject()) &&
              ObjectsCompat.equals(getStatus(), assignment.getStatus()) &&
              ObjectsCompat.equals(getBranch(), assignment.getBranch()) &&
              ObjectsCompat.equals(getYear(), assignment.getYear()) &&
              ObjectsCompat.equals(getBranchYearId(), assignment.getBranchYearId()) &&
              ObjectsCompat.equals(getDescription(), assignment.getDescription()) &&
              ObjectsCompat.equals(getLastDateSubmission(), assignment.getLastDateSubmission());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getTeacherId())
      .append(getName())
      .append(getSubject())
      .append(getStatus())
      .append(getBranch())
      .append(getYear())
      .append(getBranchYearId())
      .append(getDescription())
      .append(getLastDateSubmission())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("Assignment {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("teacherID=" + String.valueOf(getTeacherId()) + ", ")
      .append("name=" + String.valueOf(getName()) + ", ")
      .append("subject=" + String.valueOf(getSubject()) + ", ")
      .append("status=" + String.valueOf(getStatus()) + ", ")
      .append("branch=" + String.valueOf(getBranch()) + ", ")
      .append("year=" + String.valueOf(getYear()) + ", ")
      .append("branchYearID=" + String.valueOf(getBranchYearId()) + ", ")
      .append("description=" + String.valueOf(getDescription()) + ", ")
      .append("lastDateSubmission=" + String.valueOf(getLastDateSubmission()))
      .append("}")
      .toString();
  }
  
  public static TeacherIdStep builder() {
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
  public static Assignment justId(String id) {
    try {
      UUID.fromString(id); // Check that ID is in the UUID format - if not an exception is thrown
    } catch (Exception exception) {
      throw new IllegalArgumentException(
              "Model IDs must be unique in the format of UUID. This method is for creating instances " +
              "of an existing object with only its ID field for sending as a mutation parameter. When " +
              "creating a new object, use the standard builder method and leave the ID field blank."
      );
    }
    return new Assignment(
      id,
      null,
      null,
      null,
      null,
      null,
      null,
      null,
      null,
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      teacherID,
      name,
      subject,
      status,
      branch,
      year,
      branchYearID,
      description,
      lastDateSubmission);
  }
  public interface TeacherIdStep {
    NameStep teacherId(String teacherId);
  }
  

  public interface NameStep {
    SubjectStep name(String name);
  }
  

  public interface SubjectStep {
    StatusStep subject(String subject);
  }
  

  public interface StatusStep {
    BranchStep status(Status status);
  }
  

  public interface BranchStep {
    YearStep branch(Branch branch);
  }
  

  public interface YearStep {
    BranchYearIdStep year(Year year);
  }
  

  public interface BranchYearIdStep {
    DescriptionStep branchYearId(String branchYearId);
  }
  

  public interface DescriptionStep {
    LastDateSubmissionStep description(String description);
  }
  

  public interface LastDateSubmissionStep {
    BuildStep lastDateSubmission(Temporal.Date lastDateSubmission);
  }
  

  public interface BuildStep {
    Assignment build();
    BuildStep id(String id) throws IllegalArgumentException;
  }
  

  public static class Builder implements TeacherIdStep, NameStep, SubjectStep, StatusStep, BranchStep, YearStep, BranchYearIdStep, DescriptionStep, LastDateSubmissionStep, BuildStep {
    private String id;
    private String teacherID;
    private String name;
    private String subject;
    private Status status;
    private Branch branch;
    private Year year;
    private String branchYearID;
    private String description;
    private Temporal.Date lastDateSubmission;
    @Override
     public Assignment build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new Assignment(
          id,
          teacherID,
          name,
          subject,
          status,
          branch,
          year,
          branchYearID,
          description,
          lastDateSubmission);
    }
    
    @Override
     public NameStep teacherId(String teacherId) {
        Objects.requireNonNull(teacherId);
        this.teacherID = teacherId;
        return this;
    }
    
    @Override
     public SubjectStep name(String name) {
        Objects.requireNonNull(name);
        this.name = name;
        return this;
    }
    
    @Override
     public StatusStep subject(String subject) {
        Objects.requireNonNull(subject);
        this.subject = subject;
        return this;
    }
    
    @Override
     public BranchStep status(Status status) {
        Objects.requireNonNull(status);
        this.status = status;
        return this;
    }
    
    @Override
     public YearStep branch(Branch branch) {
        Objects.requireNonNull(branch);
        this.branch = branch;
        return this;
    }
    
    @Override
     public BranchYearIdStep year(Year year) {
        Objects.requireNonNull(year);
        this.year = year;
        return this;
    }
    
    @Override
     public DescriptionStep branchYearId(String branchYearId) {
        Objects.requireNonNull(branchYearId);
        this.branchYearID = branchYearId;
        return this;
    }
    
    @Override
     public LastDateSubmissionStep description(String description) {
        Objects.requireNonNull(description);
        this.description = description;
        return this;
    }
    
    @Override
     public BuildStep lastDateSubmission(Temporal.Date lastDateSubmission) {
        Objects.requireNonNull(lastDateSubmission);
        this.lastDateSubmission = lastDateSubmission;
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
    private CopyOfBuilder(String id, String teacherId, String name, String subject, Status status, Branch branch, Year year, String branchYearId, String description, Temporal.Date lastDateSubmission) {
      super.id(id);
      super.teacherId(teacherId)
        .name(name)
        .subject(subject)
        .status(status)
        .branch(branch)
        .year(year)
        .branchYearId(branchYearId)
        .description(description)
        .lastDateSubmission(lastDateSubmission);
    }
    
    @Override
     public CopyOfBuilder teacherId(String teacherId) {
      return (CopyOfBuilder) super.teacherId(teacherId);
    }
    
    @Override
     public CopyOfBuilder name(String name) {
      return (CopyOfBuilder) super.name(name);
    }
    
    @Override
     public CopyOfBuilder subject(String subject) {
      return (CopyOfBuilder) super.subject(subject);
    }
    
    @Override
     public CopyOfBuilder status(Status status) {
      return (CopyOfBuilder) super.status(status);
    }
    
    @Override
     public CopyOfBuilder branch(Branch branch) {
      return (CopyOfBuilder) super.branch(branch);
    }
    
    @Override
     public CopyOfBuilder year(Year year) {
      return (CopyOfBuilder) super.year(year);
    }
    
    @Override
     public CopyOfBuilder branchYearId(String branchYearId) {
      return (CopyOfBuilder) super.branchYearId(branchYearId);
    }
    
    @Override
     public CopyOfBuilder description(String description) {
      return (CopyOfBuilder) super.description(description);
    }
    
    @Override
     public CopyOfBuilder lastDateSubmission(Temporal.Date lastDateSubmission) {
      return (CopyOfBuilder) super.lastDateSubmission(lastDateSubmission);
    }
  }
  
}
