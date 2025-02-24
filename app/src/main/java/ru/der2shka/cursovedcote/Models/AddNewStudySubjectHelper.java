package ru.der2shka.cursovedcote.Models;

import java.util.Optional;

public class AddNewStudySubjectHelper {
    private static AddNewStudySubjectHelper instance = null;

    // Name.
    private Optional<String> name = Optional.ofNullable("");

    /**
     * Return singleton object. If not exist then create them.
     * @return AddNewStudySubjectHelper instance.
     * **/
    public static AddNewStudySubjectHelper getInstance() {
        if (instance == null) {
            instance = new AddNewStudySubjectHelper();
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
}
