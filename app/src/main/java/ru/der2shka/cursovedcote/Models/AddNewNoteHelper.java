package ru.der2shka.cursovedcote.Models;

import java.time.LocalDate;
import java.util.Optional;

public class AddNewNoteHelper {
    private static AddNewNoteHelper instance = null;

    // Name.
    private Optional<String> name = Optional.ofNullable("");

    // Description.
    private Optional<String> description = Optional.ofNullable("");

    // Date of write.
    private Optional<LocalDate> dateOfWrite = Optional.ofNullable(LocalDate.MIN);

    // Status code.
    private Optional<Integer> statusCode = Optional.ofNullable(0);

    // Private constructor.
    private AddNewNoteHelper() {}

    /**
     * Return singleton object. If not exist then create them.
     * @return AddNewNoteHelper instance.
     * **/
    public static AddNewNoteHelper getInstance() {
        if (instance == null) {
            instance = new AddNewNoteHelper();
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
}
