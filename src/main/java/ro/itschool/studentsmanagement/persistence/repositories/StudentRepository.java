package ro.itschool.studentsmanagement.persistence.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ro.itschool.studentsmanagement.persistence.entity.Student;

@Repository
public interface StudentRepository extends CrudRepository<Student, Long> {
}
