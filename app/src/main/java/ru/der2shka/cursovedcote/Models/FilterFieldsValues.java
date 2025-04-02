package ru.der2shka.cursovedcote.Models;

import android.util.Log;

import java.util.Optional;

/**
 *  Class for remember filter fields values
 *  in searching bars.
 * **/
public class FilterFieldsValues {

    private static Optional<FilterFieldsValues> instance = Optional.empty();

    private Optional<String> homeWorkSearchString = Optional.empty(); // For Homework Search Text.
    private Optional<String> noteSearchString = Optional.empty(); // For Note Search Text.
    private Optional<String> gradeTypeSearchString = Optional.empty(); // For Grade Type Search Text.
    private Optional<String> studySubjectSearchString = Optional.empty(); // For Study Subject Search Text.

    private FilterFieldsValues() {}

    /**
     *  Return instance of FilterFieldsValues if it is not exist.
     * @return FilterFieldsValues object.
     * **/
    public static FilterFieldsValues getInstance() {
        if (instance.isEmpty()) {
            instance =  Optional.ofNullable( new FilterFieldsValues() );
        }

        return instance.orElse( new FilterFieldsValues() );
    }

    /**
     *  Set homework search string value.
     * @return was updated or not.
     * **/
    public boolean setHomeWorkSearchString( Optional<String> text) {
        if (text.isPresent()) {
            try {
                this.homeWorkSearchString = text;
                return true;
            }
            catch (Exception ex) {
                Log.e( "e", "Error to update homeWorkSearchString" );
            }
        }

        return false;
    }

    /**
     * @return homework search string.
     * **/
    public String getHomeWorkSearchString() {
        return this.homeWorkSearchString.orElse( "" );
    }

    /**
     *  Set note search string value.
     * @return was updated or not.
     * **/
    public boolean setNoteSearchString( Optional<String> text) {
        if (text.isPresent()) {
            try {
                this.noteSearchString = text;
                return true;
            }
            catch (Exception ex) {
                Log.e( "e", "Error to update noteSearchString" );
            }
        }

        return false;
    }

    /**
     * @return note search string.
     * **/
    public String getNoteSearchString() {
        return this.noteSearchString.orElse( "" );
    }

    /**
     *  Set grade type search string value.
     * @return was updated or not.
     * **/
    public boolean setGradeTypeSearchString( Optional<String> text) {
        if (text.isPresent()) {
            try {
                this.gradeTypeSearchString = text;
                return true;
            }
            catch (Exception ex) {
                Log.e( "e", "Error to update gradeTypeSearchString" );
            }
        }

        return false;
    }

    /**
     * @return grade type search string.
     * **/
    public String getGradeTypeString() {
        return this.gradeTypeSearchString.orElse( "" );
    }

    /**
     *  Set study subject search string value.
     * @return was updated or not.
     * **/
    public boolean setStudySubjectSearchString( Optional<String> text) {
        if (text.isPresent()) {
            try {
                this.studySubjectSearchString = text;
                return true;
            }
            catch (Exception ex) {
                Log.e( "e", "Error to update studySubjectSearchString" );
            }
        }

        return false;
    }

    /**
     * @return study subject search string.
     * **/
    public String getStudySubjectString() {
        return this.studySubjectSearchString.orElse( "" );
    }

}
