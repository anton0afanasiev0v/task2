package t1.course.task2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import t1.course.task2.model.ExecutionTime;

import java.util.List;

public interface ExecutionTimeRepository extends JpaRepository<ExecutionTime, Long> {

    List<ExecutionTime> findByMethodName(String methodName);

    @Query("SELECT AVG(e.executionTime) FROM ExecutionTime e WHERE e.methodName = ?1")
    double findAverageExecutionTimeByMethodName(String methodName);
}