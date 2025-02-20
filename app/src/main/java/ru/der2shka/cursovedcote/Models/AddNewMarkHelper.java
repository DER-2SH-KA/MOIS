package ru.der2shka.cursovedcote.Models;

import java.time.LocalDate;
import java.util.Optional;

public class AddNewMarkHelper {

    // Singleton object.
    private static Optional<AddNewMarkHelper> addNewMarkHelper = Optional.ofNullable(null);

    // Current mark value.
    private static Optional<String> currentMarkValue = Optional.ofNullable(null);

    // Current mark type.
    private static Optional<String> currentMarkType = Optional.ofNullable(null);

    // Current study subject.
    private static Optional<String> currentStudySubject = Optional.ofNullable(null);

    // Current local date.
    private static Optional<LocalDate> currentLocalDate = Optional.ofNullable(LocalDate.now());

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
    public Boolean setCurrentMarkType(Optional<String> newMarkType) {
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
    public String getCurrentMarkType() {
        return currentMarkType.orElse("None value");
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
}
