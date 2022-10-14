package com.htn.mmt.service;

import com.htn.mmt.modal.Student;
import com.htn.mmt.repository.StudentRepository;
import com.htn.mmt.utils.DESAlgorithm;

import java.util.List;
import java.util.stream.Collectors;

public class StudentService {
    private final StudentRepository studentRepository;
    private final DESAlgorithm desAlgorithm;

    public StudentService() {
        this.studentRepository = new StudentRepository();
        desAlgorithm = DESAlgorithm.getInstance();
    }

    public int save(Student student) {

        String fullName = desAlgorithm.encrypt(student.getFullName());
        String id = desAlgorithm.encrypt(student.getId());
        String math = desAlgorithm.encrypt(student.getMathScores());
        String english = desAlgorithm.encrypt(student.getEnglishScores());
        String literature = desAlgorithm.encrypt(student.getLiteratureScores());

        student = Student.getBuilder()
                .withId(id)
                .withFullName(fullName)
                .withEnglishScores(english)
                .withLiteratureScores(literature)
                .withMathScores(math)
                .build();

        return studentRepository.save(student);
    }

    public String[][] gets() {
        List<Student> students = studentRepository.gets();
        students = students.stream().map(student -> Student.getBuilder()
                .withEnglishScores(desAlgorithm.decrypt(student.getEnglishScores()))
                .withFullName(desAlgorithm.decrypt(student.getFullName()))
                .withMathScores(desAlgorithm.decrypt(student.getMathScores()))
                .withId(desAlgorithm.decrypt(student.getId()))
                .withLiteratureScores(desAlgorithm.decrypt(student.getLiteratureScores()))
                .build()).collect(Collectors.toList());
        System.out.println(students);

        String[][] result = new String[students.size()][3];
        int i = 0;
        for (Student student : students) {
            result[i][0] = student.getFullName();
            result[i][1] = student.getId();
            float math = Float.parseFloat(student.getMathScores());
            float english = Float.parseFloat(student.getEnglishScores());
            float literature = Float.parseFloat(student.getLiteratureScores());
            result[i][2] = String.valueOf((math + english + literature) / 3);
            i++;
        }
        return result;
    }

}
