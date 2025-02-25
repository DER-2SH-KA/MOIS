package ru.der2shka.cursovedcote.Models;

import static ru.der2shka.cursovedcote.ForTesting.TestingVariables.studySubjectTestList;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class AddNewHomeworkHelper {
    private static AddNewHomeworkHelper instance = null;

    // Name.
    private Optional<String> name = Optional.ofNullable("");

    // Description.
    private Optional<String> description = Optional.ofNullable("");

    // Study Subject.
    private Optional<String> studySubject = Optional.ofNullable("");

    // Date of write.
    private Optional<LocalDate> dateOfWrite = Optional.ofNullable(LocalDate.now());

    // Date begin.
    private Optional<LocalDate> dateBegin = Optional.ofNullable(LocalDate.MIN);

    // Date end.
    private Optional<LocalDate> dateEnd = Optional.ofNullable(LocalDate.MAX);

    // Status code.
    private Optional<Integer> statusCode = Optional.ofNullable(0);

    // List Of  Study Subjects.
    private static List<String> studySubjectList = new ArrayList<String>();

    // Private constructor.
    private AddNewHomeworkHelper() {}

    /**
     * Return singleton object. If not exist then create them.
     * @return AddNewNoteHelper instance.
     * **/
    public static AddNewHomeworkHelper getInstance() {
        if (instance == null) {
            instance = new AddNewHomeworkHelper();
        }

        return instance;
    }

    /**
     *  Set new name value.
     * @param newName Optional new name string value.
     * @return Return true if name was updated, else false.
     */
    public Boolean setNameValue(Optional<String> newName) {
        if (newName.isPresent()) {
            name = newName;
            return true;
        }

        return false;
    }

    /**
     *  Get name string value.
     * @return if name is present, return name, else &quot;None value&quot;
     * **/
    public String getNameValue() {
        return name.orElse("None value");
    }

    /**
     *  Set new study subject value.
     * @param newStudySubject Optional new study subject string value.
     * @return Return true if study subject was updated, else false.
     */
    public Boolean setStudySubjectValue(Optional<String> newStudySubject) {
        if (newStudySubject.isPresent()) {
            studySubject = newStudySubject;
            return true;
        }

        return false;
    }

    /**
     *  Get study subject string value.
     * @return if study subject is present, return description, else &quot;None value&quot;
     * **/
    public String getStudySubjectValue() {
        return studySubject.orElse("None value");
    }

    /**
     *  Set new description value.
     * @param newDescription Optional new description string value.
     * @return Return true if description was updated, else false.
     */
    public Boolean setDescriptionValue(Optional<String> newDescription) {
        if (newDescription.isPresent()) {
            description = newDescription;
            return true;
        }

        return false;
    }

    /**
     *  Get description string value.
     * @return if description is present, return description, else &quot;None value&quot;
     * **/
    public String getDescriptionValue() {
        return description.orElse("None value");
    }

    /**
     *  Set new dateOfWrite localDate value.
     * @param newDateOfWrite Optional new date value.
     * @return Return true if date was updated, else false.
     */
    public Boolean setDateOfWrite(Optional<LocalDate> newDateOfWrite) {
        if (newDateOfWrite.isPresent()) {
            dateOfWrite = newDateOfWrite;
            return true;
        }

        return false;
    }

    /**
     *  Get  date of write local date value.
     * @return if date is present, return date of write, else LocalDate.MIN
     * **/
    public LocalDate getDateOfWrite() {
        return dateOfWrite.orElse( LocalDate.MIN );
    }

    /**
     *  Set new date begin localDate value.
     * @param newDateBegin Optional new date begin value.
     * @return Return true if date begin was updated, else false.
     */
    public Boolean setDateBegin(Optional<LocalDate> newDateBegin) {
        if (newDateBegin.isPresent()) {
            dateBegin = newDateBegin;
            return true;
        }

        return false;
    }

    /**
     *  Get  date begin local date value.
     * @return if date begin is present, return date begin, else LocalDate.MIN
     * **/
    public LocalDate getDateBegin() {
        return dateBegin.orElse( LocalDate.MIN );
    }

    /**
     *  Set new date end localDate value.
     * @param newDateEnd Optional new date end value.
     * @return Return true if date end was updated, else false.
     */
    public Boolean setDateEnd(Optional<LocalDate> newDateEnd) {
        if (newDateEnd.isPresent()) {
            dateEnd = newDateEnd;
            return true;
        }

        return false;
    }

    /**
     *  Get date end of write local date value.
     * @return if date end is present, return date end, else LocalDate.MIN
     * **/
    public LocalDate getDateEnd() {
        return dateEnd.orElse( LocalDate.MIN );
    }

    /**
     *  Set new status code value.
     * @param newStatusCode Optional new status int value.
     * @return Return true if status was updated, else false.
     */
    public Boolean setStatusCodeValue(Optional<Integer> newStatusCode) {
        if (newStatusCode.isPresent()) {
            statusCode = newStatusCode;
            return true;
        }

        return false;
    }

    /**
     * Get status int value.
     * @return if status is present, return status, else 0
     * **/
    public Integer getStatusCodeValue() {
        return statusCode.orElse(0);
    }

    /**
     * Set collection of study subject values
     * @param newStudySubjects Optional list of study subject string values.
     * **/
    public void setStudySubjectList(Optional<List<String>> newStudySubjects) {
        if (newStudySubjects.isPresent()) {
            studySubjectList = newStudySubjects.get();
        }
    }

    /**
     * Return list of study subject values.
     * @return List of String study subjects.
     * **/
    public List<String> getStudySubjectList() {
        if (studySubjectList.isEmpty()) {
            setStudySubjectList(
                    Optional.ofNullable(
                            // TODO: Change it to Service which get it collection from DB.
                            studySubjectTestList
                    )
            );
            setStudySubjectValue( Optional.ofNullable(getStudySubjectList().get(0)) );
        }

        return studySubjectList;
    }
}
