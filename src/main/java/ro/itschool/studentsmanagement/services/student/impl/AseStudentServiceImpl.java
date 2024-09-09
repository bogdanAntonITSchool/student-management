package ro.itschool.studentsmanagement.services.student.impl;

import org.springframework.stereotype.Service;
import ro.itschool.studentsmanagement.persistence.entity.Student;
import ro.itschool.studentsmanagement.persistence.repositories.StudentRepository;
import ro.itschool.studentsmanagement.services.student.AbstractStudentService;

@Service("ase")
public class AseStudentServiceImpl extends AbstractStudentService {
    public AseStudentServiceImpl(StudentRepository studentRepository) {
        super(studentRepository);
    }

    @Override
    public Student getStudentById(Long id) {
        if (id > 10) {
            throw new RuntimeException("Student with id " + id + " not found");
        }
        return super.getStudentById(id);
    }
}
