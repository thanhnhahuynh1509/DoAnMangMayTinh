package com.htn.mmt.repository;

import com.htn.mmt.database.Database;
import com.htn.mmt.modal.Student;
import com.htn.mmt.utils.DESAlgorithm;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentRepository {

    private final String SAVE = "INSERT INTO student "
            + "VALUES(?, ?, ?, ?, ?)";

    private final String GETS = "SELECT * FROM student";
    private final Connection connection;

    public StudentRepository() {
        connection = Database.getConnection();
    }

    public int save(Student student) {
        int result = 0;
        try (PreparedStatement ps = connection.prepareStatement(SAVE)) {
            ps.setString(1, student.getId());
            ps.setString(2, student.getFullName());
            ps.setString(3, student.getMathScores());
            ps.setString(4, student.getEnglishScores());
            ps.setString(5, student.getLiteratureScores());
            result = ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return result;
    }

    public List<Student> gets() {
        List<Student> students = new ArrayList<>();

        try(PreparedStatement ps = connection.prepareStatement(GETS)) {
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                String fullName = rs.getString("full_name");
                String id = rs.getString("id");
                String math = rs.getString("math");
                String english = rs.getString("english");
                String literature = rs.getString("literature");

                Student student = Student.getBuilder()
                        .withFullName(fullName)
                        .withEnglishScores(english)
                        .withLiteratureScores(literature)
                        .withMathScores(math)
                        .withId(id)
                        .build();

                students.add(student);
            }
        } catch(SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return students;
    }

}
