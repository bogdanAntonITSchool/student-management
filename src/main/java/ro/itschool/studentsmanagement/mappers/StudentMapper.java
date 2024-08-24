package ro.itschool.studentsmanagement.mappers;

import lombok.experimental.UtilityClass;
import ro.itschool.studentsmanagement.controllers.dtos.StudentDto;
import ro.itschool.studentsmanagement.persistence.entity.Student;

@UtilityClass
public class StudentMapper {

    public Student toEntity(StudentDto studentDto) {
        return new Student(
                studentDto.id(),
                studentDto.name(),
                studentDto.age(),
                studentDto.email(),
                studentDto.phone(),
                studentDto.address()
        );
    }

    public StudentDto toDto(Student student) {
        return new StudentDto(
                student.getId(),
                student.getName(),
                student.getAge(),
                student.getEmail(),
                student.getPhone(),
                student.getAddress()
        );
    }
}
