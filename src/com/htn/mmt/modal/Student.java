package com.htn.mmt.modal;


import java.io.Serializable;

public class Student implements Serializable {
    private final String id;
    private final String fullName;
    private final String mathScores;
    private final String englishScores;
    private final String literatureScores;

    private Student(String id, String fullName, String mathScores, String englishScores, String literatureScores) {
        this.id = id;
        this.fullName = fullName;
        this.mathScores = mathScores;
        this.englishScores = englishScores;
        this.literatureScores = literatureScores;
    }

    public String getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public String getMathScores() {
        return mathScores;
    }

    public String getEnglishScores() {
        return englishScores;
    }

    public String getLiteratureScores() {
        return literatureScores;
    }

    public static Builder getBuilder() {
        return new Builder();
    }

    public static class Builder {
        private String id;
        private String fullName;
        private String mathScores;
        private String englishScores;
        private String literatureScores;

        public Builder withId(String id) {
            this.id = id;
            return this;
        }

        public Builder withFullName(String fullName) {
            this.fullName = fullName;
            return this;
        }

        public Builder withMathScores(String mathScores) {
            this.mathScores = mathScores;
            return this;
        }

        public Builder withEnglishScores(String englishScores) {
            this.englishScores = englishScores;
            return this;
        }

        public Builder withLiteratureScores(String literatureScores) {
            this.literatureScores = literatureScores;
            return this;
        }

        public Student build() {
            return new Student(id, fullName, mathScores, englishScores, literatureScores);
        }
    }

    @Override
    public String toString() {
        return
                "id='" + id + '\'' +
                ", fullName='" + fullName + '\'' +
                ", mathScores='" + mathScores + '\'' +
                ", englishScores='" + englishScores + '\'' +
                ", literatureScores='" + literatureScores + '\'';
    }
}

