package ro.itschool.studentsmanagement.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.itschool.studentsmanagement.controllers.dtos.StudentDto;
import ro.itschool.studentsmanagement.mappers.StudentMapper;
import ro.itschool.studentsmanagement.persistence.entity.Student;
import ro.itschool.studentsmanagement.services.StudentService;

import java.util.List;

@RestController
@RequestMapping("/students")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    @GetMapping
    public ResponseEntity<List<StudentDto>> getAllStudents() {
        List<Student> allStudents = studentService.getAllStudents();

        return ResponseEntity.ok(allStudents.stream()
                .map(StudentMapper::toDto)
                .toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentDto> getStudentById(@PathVariable Long id) {
        Student student = studentService.getStudentById(id);

        return ResponseEntity.ok(StudentMapper.toDto(student));
    }

    @PostMapping
    public ResponseEntity<StudentDto> addStudent(@RequestBody StudentDto studentDto) {
        Student student = studentService.addStudent(StudentMapper.toEntity(studentDto));

        return ResponseEntity.ok(StudentMapper.toDto(student));
    }

    @PutMapping("/{id}")
    public ResponseEntity<StudentDto> updateStudent(@PathVariable Long id, @RequestBody StudentDto studentDto) {
        Student student = studentService.updateStudent(id, StudentMapper.toEntity(studentDto));

        return ResponseEntity.ok(StudentMapper.toDto(student));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);

        return ResponseEntity.noContent().build();
    }
}
