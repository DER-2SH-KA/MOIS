package ru.der2shka.cursovedcote.Models;

import java.util.Optional;

import ru.der2shka.cursovedcote.db.entity.GradeType;
import ru.der2shka.cursovedcote.db.entity.Note;
import ru.der2shka.cursovedcote.db.entity.StudySubject;

public class StudySubjectHelper {
    private static Optional<StudySubjectHelper> instance = Optional.ofNullable(null);

    private Optional<StudySubject> studySubject = Optional.ofNullable(null);

    private StudySubjectHelper() {
    }

    /**
     * Return instance of StudySubjectHelper and create them if it not exist.
     *
     * @return StudySubjectHelper instance.
     **/
    public static StudySubjectHelper getInstance() {
        if (!instance.isPresent()) {
            instance = Optional.ofNullable(new StudySubjectHelper());
        }

        return instance.get();
    }

    /**
     * Set study subject optional value.
     *
     * @param nstudySubject new study subject value.
     * @return true if value was updated else false.
     **/
    public Boolean setStudySubjectValue(Optional<StudySubject> nstudySubject) {
        if (nstudySubject.isPresent()) {
            studySubject = nstudySubject;

            return true;
        }

        return false;
    }

    /**
     * Return study subject object.
     *
     * @return study subject value if persist, else StudySubject(-1, "\\_( -_ -)-/", 0)
     **/
    public StudySubject getStudySubjectValue() {
        return studySubject.orElse(
                new StudySubject(-1, "\\_( -_ -)-/", 0)
        );
    }
}
