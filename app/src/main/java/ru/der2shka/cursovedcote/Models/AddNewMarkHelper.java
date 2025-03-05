package ru.der2shka.cursovedcote.Models;

import static ru.der2shka.cursovedcote.ForTesting.TestingVariables.markTypeTestList;
import static ru.der2shka.cursovedcote.ForTesting.TestingVariables.markValueTestList;
import static ru.der2shka.cursovedcote.ForTesting.TestingVariables.studySubjectTestList;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import ru.der2shka.cursovedcote.db.entity.GradeType;

public class AddNewMarkHelper {

    // Singleton object.
    private static Optional<AddNewMarkHelper> addNewMarkHelper = Optional.ofNullable(null);

    // Current mark value.
    private static Optional<String> currentMarkValue = Optional.ofNullable(null);

    // Current mark type.
    private static Optional<GradeType> currentMarkType = Optional.ofNullable(null);

    // Current study subject.
    private static Optional<String> currentStudySubject = Optional.ofNullable(null);

    // Current local date.
    private static Optional<LocalDate> currentLocalDate = Optional.ofNullable(LocalDate.now());

    // List Of Mark Values.
    private static List<String> markValueList = new ArrayList<String>();

    // List Of Mark Types.
    private static List<GradeType> markTypeList = new ArrayList<GradeType>();

    // List Of  Study Subjects.
    private static List<String> studySubjectList = new ArrayList<String>();

    // Private constructor.
    private AddNewMarkHelper() {}

    /**
     * If  instance is not exist, create it and return instance.
     * @return AddNewMarkHelper instance
     * **/
    public static AddNewMarkHelper getInstance() {
        if ( !addNewMarkHelper.isPresent() ) {
            addNewMarkHelper = Optional.ofNullable( new AddNewMarkHelper());
        }

        return addNewMarkHelper.get();
    }

    /**
     *  Set new current mark value.
     * @param newMark Optional new mark string value.
     * @return Return true if mark was updated, else false.
     */
    public Boolean setCurrentMarkValue(Optional<String> newMark) {
        if (newMark.isPresent()) {
            currentMarkValue = newMark;
            return true;
        }

        return false;
    }

    /**
     *  Get current mark string value.
     * @return if mark is present, return current mark, else &quot;None value&quot;
     * **/
    public String getCurrentMarkValue() {
        return currentMarkValue.orElse("None value");
    }

    /**
     *  Set new current mark type.
     * @param newMarkType Optional new mark type string value.
     * @return Return true if mark was type updated, else false.
     */
    public Boolean setCurrentMarkType(Optional<GradeType> newMarkType) {
        if (newMarkType.isPresent()) {
            currentMarkType = newMarkType;
            return true;
        }

        return false;
    }

    /**
     *  Get current mark type string value.
     * @return if mark type is present, return current mark type, else &quot;None value&quot;
     * **/
    public GradeType getCurrentMarkType() {
        return currentMarkType.orElse( null );
    }

    /**
     *  Set new current study subject.
     * @param newStudySubject Optional new study subject string value.
     * @return Return true if study subject was updated, else false.
     */
    public Boolean setCurrentStudySubject(Optional<String> newStudySubject) {
        if (newStudySubject.isPresent()) {
            currentStudySubject = newStudySubject;
            return true;
        }

        return false;
    }

    /**
     *  Get current study subject string value.
     * @return if study subject is present, return current subject, else &quot;None value&quot;
     * **/
    public String getCurrentStudySubject() {
        return currentStudySubject.orElse("None value");
    }

    /**
     *  Set new current localDate value.
     * @param newLocalDate Optional new date LocalDate value.
     * @return Return true if date was updated, else false.
     */
    public Boolean setCurrentLocalDate(Optional<LocalDate> newLocalDate) {
        if (newLocalDate.isPresent()) {
            currentLocalDate = newLocalDate;
            return true;
        }

        return false;
    }

    /**
     *  Get current local date value.
     * @return if date is present, return current date, else LocalDate.MIN
     * **/
    public LocalDate getCurrentLocalDate() {
        return currentLocalDate.orElse( LocalDate.MIN );
    }

    // Collections.
    /**
     * Set collection of mark values
     * @param newMarkValues Optional list of mark string values.
     * **/
    public void setMarkValueList( Optional<List<String>> newMarkValues) {
        if (newMarkValues.isPresent()) {
            markValueList = newMarkValues.get();
        }
    }

    /**
     * Return list of mark string values.
     * @return List of String mark values
     * **/
    public List<String> getMarkValueList() {
        if (markValueList.isEmpty()) {
            setMarkValueList(
                    Optional.ofNullable(
                            // TODO: Change it to Service which get it collection from DB.
                            markValueTestList
                    )
            );
            setCurrentMarkValue( Optional.ofNullable(getMarkValueList().get(0)) );
        }

        return markValueList;
    }

    /**
     * Set collection of mark values
     * @param newMarkTypes Optional list of mark string values.
     * **/
    public void setMarkTypeList( Optional<List<GradeType>> newMarkTypes) {
        if (newMarkTypes.isPresent()) {
            markTypeList = newMarkTypes.get();
        }
    }

    /**
     * Return list of mark type string values.
     * @return List of String mark type values
     * **/
    public List<GradeType> getMarkTypeList() {
        if (markTypeList.isEmpty()) {
            /*setMarkTypeList(
                    Optional.ofNullable(
                            // TODO: Change it to Service which get it collection from DB.

                    )
            );
            setCurrentMarkType( Optional.ofNullable(getMarkTypeList().get(0)) );*/
        }

        return markTypeList;
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
            setCurrentStudySubject( Optional.ofNullable(getStudySubjectList().get(0)) );
        }

        return studySubjectList;
    }
}
