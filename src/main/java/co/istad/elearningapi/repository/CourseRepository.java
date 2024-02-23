package co.istad.elearningapi.repository;

import co.istad.elearningapi.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface CourseRepository extends
        JpaRepository<Course, Long> {
 @Modifying
    @Query(
            """
                    UPDATE Course AS c SET c.isDeleted = true 
                    WHERE c.id= ?1
                    """
    )
    void updateIsDeletedById(Long id);
 @Modifying
 @Query("""
        DELETE
        FROM Course AS c
        WHERE c.id = :id
    """)
     void deleteById( Long id);
}
