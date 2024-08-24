package ro.itschool.studentsmanagement.controllers.dtos;

public record StudentDto(Long id,
                         String name,
                         Integer age,
                         String email,
                         String phone,
                         String address) {
}
