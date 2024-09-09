package ro.itschool.studentsmanagement.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestTemplate;
import ro.itschool.studentsmanagement.controllers.dtos.StudentDto;
import ro.itschool.studentsmanagement.mappers.StudentMapper;
import ro.itschool.studentsmanagement.persistence.entity.Student;
import ro.itschool.studentsmanagement.services.student.AbstractStudentService;

import java.time.Duration;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/students")
public class StudentController {

    private final AbstractStudentService studentService;
    private final RestTemplate firstExternalService;
    private final RestTemplate secondExternalService;

    public StudentController(@Qualifier("poli") AbstractStudentService studentService,
                             RestTemplate firstExternalService,
                             RestTemplate secondExternalService) {
        this.studentService = studentService;
        this.firstExternalService = firstExternalService;
        this.secondExternalService = secondExternalService;
    }

    @GetMapping
    public ResponseEntity<List<StudentDto>> getAllStudents() {
        List<Student> allStudents = studentService.getAllStudents();

        return ResponseEntity.ok(allStudents.stream()
                .map(StudentMapper::toDto)
                .toList());
    }

    @GetMapping("/external")
    public ResponseEntity<StudentDto> getExternal() {
        RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder();

        restTemplateBuilder.setConnectTimeout(Duration.ofSeconds(10));
        restTemplateBuilder.setReadTimeout(Duration.ofSeconds(10));
        restTemplateBuilder.basicAuthentication("user", "password");
        restTemplateBuilder.defaultHeader("header", "value");

        RestTemplate restTemplate = restTemplateBuilder.build();

        StudentDto forObject = restTemplate.getForObject("http://localhost:8080/api/v1/students/1", StudentDto.class);
        return new ResponseEntity<>(forObject, HttpStatusCode.valueOf(200));
    }

    @GetMapping("/external/first")
    public ResponseEntity<StudentDto> getExternalFirst() {
        StudentDto forObject = firstExternalService.getForObject("/students/1", StudentDto.class);
        return new ResponseEntity<>(forObject, HttpStatusCode.valueOf(200));
    }

    @GetMapping("/external/second")
    public ResponseEntity<StudentDto> getExternalSecond() {
        StudentDto forObject = secondExternalService.getForObject("http://localhost:8080/api/v1/students/1", StudentDto.class);
        return new ResponseEntity<>(forObject, HttpStatusCode.valueOf(200));
    }

    @PostMapping("/external")
    public ResponseEntity<StudentDto> postExternal() {
        RestTemplate restTemplate = new RestTemplate();

        StudentDto studentDto =
                new StudentDto(null, "John Doe", 20, "email", "phone", "address");

        StudentDto forObject = restTemplate.postForObject("http://localhost:8080/api/v1/students", studentDto, StudentDto.class);
        return new ResponseEntity<>(forObject, HttpStatusCode.valueOf(200));
    }

    @PostMapping("/exchange")
    public ResponseEntity<StudentDto> exchangeExternal() {
        RestTemplate restTemplate = new RestTemplate();

        StudentDto studentDto =
                new StudentDto(null, "John Doe", 20, "email", "phone", "address");
        HttpEntity<StudentDto> studentDtoHttpEntity = new HttpEntity<>(studentDto);

        ResponseEntity<StudentDto> exchange = restTemplate.exchange("http://localhost:8080/api/v1/students", HttpMethod.POST, studentDtoHttpEntity, StudentDto.class);
        return new ResponseEntity<>(exchange.getBody(), HttpStatusCode.valueOf(200));
    }

    @GetMapping("/execute")
    public ResponseEntity<String> executeExternal() {
        RequestCallback requestCallback = request -> {
            StudentDto studentDto = new StudentDto(null, "John Doe", 20, "email", "phone", "address");

            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writeValue(request.getBody(), studentDto);

            request.getHeaders().add("Content-Type", "application/json");
        };

        ResponseExtractor<Void> responseExtractor = response -> {
            log.info("Response status: {}", response.getStatusCode());
            return null;
        };
        firstExternalService.execute("/students", HttpMethod.POST, requestCallback, responseExtractor);
        return ResponseEntity.ok("Executed");
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
