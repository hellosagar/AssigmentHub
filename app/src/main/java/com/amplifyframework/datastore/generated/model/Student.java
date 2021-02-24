package com.amplifyframework.datastore.generated.model;


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

/** This is an auto generated class representing the Student type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "Students")
@Index(name = "byStudent", fields = {"branchYearID"})
public final class Student implements Model {
  public static final QueryField ID = field("Student", "id");
  public static final QueryField NAME = field("Student", "name");
  public static final QueryField ROLL_NO = field("Student", "rollNo");
  public static final QueryField BRANCH = field("Student", "branch");
  public static final QueryField YEAR = field("Student", "year");
  public static final QueryField BRANCH_YEAR_ID = field("Student", "branchYearID");
  public static final QueryField EMAIL = field("Student", "email");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="String", isRequired = true) String name;
  private final @ModelField(targetType="Int", isRequired = true) Integer rollNo;
  private final @ModelField(targetType="Branch", isRequired = true) Branch branch;
  private final @ModelField(targetType="Year", isRequired = true) Year year;
  private final @ModelField(targetType="ID", isRequired = true) String branchYearID;
  private final @ModelField(targetType="String", isRequired = true) String email;
  public String getId() {
      return id;
  }
  
  public String getName() {
      return name;
  }
  
  public Integer getRollNo() {
      return rollNo;
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
  
  public String getEmail() {
      return email;
  }
  
  private Student(String id, String name, Integer rollNo, Branch branch, Year year, String branchYearID, String email) {
    this.id = id;
    this.name = name;
    this.rollNo = rollNo;
    this.branch = branch;
    this.year = year;
    this.branchYearID = branchYearID;
    this.email = email;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      Student student = (Student) obj;
      return ObjectsCompat.equals(getId(), student.getId()) &&
              ObjectsCompat.equals(getName(), student.getName()) &&
              ObjectsCompat.equals(getRollNo(), student.getRollNo()) &&
              ObjectsCompat.equals(getBranch(), student.getBranch()) &&
              ObjectsCompat.equals(getYear(), student.getYear()) &&
              ObjectsCompat.equals(getBranchYearId(), student.getBranchYearId()) &&
              ObjectsCompat.equals(getEmail(), student.getEmail());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getName())
      .append(getRollNo())
      .append(getBranch())
      .append(getYear())
      .append(getBranchYearId())
      .append(getEmail())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("Student {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("name=" + String.valueOf(getName()) + ", ")
      .append("rollNo=" + String.valueOf(getRollNo()) + ", ")
      .append("branch=" + String.valueOf(getBranch()) + ", ")
      .append("year=" + String.valueOf(getYear()) + ", ")
      .append("branchYearID=" + String.valueOf(getBranchYearId()) + ", ")
      .append("email=" + String.valueOf(getEmail()))
      .append("}")
      .toString();
  }
  
  public static NameStep builder() {
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
  public static Student justId(String id) {
    try {
      UUID.fromString(id); // Check that ID is in the UUID format - if not an exception is thrown
    } catch (Exception exception) {
      throw new IllegalArgumentException(
              "Model IDs must be unique in the format of UUID. This method is for creating instances " +
              "of an existing object with only its ID field for sending as a mutation parameter. When " +
              "creating a new object, use the standard builder method and leave the ID field blank."
      );
    }
    return new Student(
      id,
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
      name,
      rollNo,
      branch,
      year,
      branchYearID,
      email);
  }
  public interface NameStep {
    RollNoStep name(String name);
  }
  

  public interface RollNoStep {
    BranchStep rollNo(Integer rollNo);
  }
  

  public interface BranchStep {
    YearStep branch(Branch branch);
  }
  

  public interface YearStep {
    BranchYearIdStep year(Year year);
  }
  

  public interface BranchYearIdStep {
    EmailStep branchYearId(String branchYearId);
  }
  

  public interface EmailStep {
    BuildStep email(String email);
  }
  

  public interface BuildStep {
    Student build();
    BuildStep id(String id) throws IllegalArgumentException;
  }
  

  public static class Builder implements NameStep, RollNoStep, BranchStep, YearStep, BranchYearIdStep, EmailStep, BuildStep {
    private String id;
    private String name;
    private Integer rollNo;
    private Branch branch;
    private Year year;
    private String branchYearID;
    private String email;
    @Override
     public Student build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new Student(
          id,
          name,
          rollNo,
          branch,
          year,
          branchYearID,
          email);
    }
    
    @Override
     public RollNoStep name(String name) {
        Objects.requireNonNull(name);
        this.name = name;
        return this;
    }
    
    @Override
     public BranchStep rollNo(Integer rollNo) {
        Objects.requireNonNull(rollNo);
        this.rollNo = rollNo;
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
     public EmailStep branchYearId(String branchYearId) {
        Objects.requireNonNull(branchYearId);
        this.branchYearID = branchYearId;
        return this;
    }
    
    @Override
     public BuildStep email(String email) {
        Objects.requireNonNull(email);
        this.email = email;
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
    private CopyOfBuilder(String id, String name, Integer rollNo, Branch branch, Year year, String branchYearId, String email) {
      super.id(id);
      super.name(name)
        .rollNo(rollNo)
        .branch(branch)
        .year(year)
        .branchYearId(branchYearId)
        .email(email);
    }
    
    @Override
     public CopyOfBuilder name(String name) {
      return (CopyOfBuilder) super.name(name);
    }
    
    @Override
     public CopyOfBuilder rollNo(Integer rollNo) {
      return (CopyOfBuilder) super.rollNo(rollNo);
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
     public CopyOfBuilder email(String email) {
      return (CopyOfBuilder) super.email(email);
    }
  }
  
}
