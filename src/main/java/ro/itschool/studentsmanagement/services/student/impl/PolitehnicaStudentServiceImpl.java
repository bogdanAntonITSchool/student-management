package ro.itschool.studentsmanagement.services.student.impl;

import org.springframework.stereotype.Service;
import ro.itschool.studentsmanagement.persistence.repositories.StudentRepository;
import ro.itschool.studentsmanagement.services.student.AbstractStudentService;

@Service("poli")
public class PolitehnicaStudentServiceImpl extends AbstractStudentService {
    public PolitehnicaStudentServiceImpl(StudentRepository studentRepository) {
        super(studentRepository);
    }
}
