package ro.itschool.studentsmanagement.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ro.itschool.studentsmanagement.exceptions.StudentNotFoundException;
import ro.itschool.studentsmanagement.persistence.entity.Student;
import ro.itschool.studentsmanagement.persistence.repositories.StudentRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;

    public List<Student> getAllStudents() {
        return (List<Student>) studentRepository.findAll();
    }

    public Student getStudentById(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException("Student with id " + id + " not found"));
    }

    public Student addStudent(Student student) {
        return studentRepository.save(student);
    }

    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }

    public Student updateStudent(Long id, Student student) {
        Student existingStudent = getStudentById(id);
        existingStudent.setName(student.getName());
        existingStudent.setAge(student.getAge());
        existingStudent.setEmail(student.getEmail());
        existingStudent.setPhone(student.getPhone());
        existingStudent.setAddress(student.getAddress());
        return studentRepository.save(existingStudent);
    }
}
