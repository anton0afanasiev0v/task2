package t1.course.task2.servece;


import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import t1.course.task2.model.ExecutionTime;
import t1.course.task2.repository.ExecutionTimeRepository;

import java.util.concurrent.CompletableFuture;

@Service
@AllArgsConstructor
public class ExecutionTimeService {

    private final ExecutionTimeRepository executionTimeRepository;

    public void saveExecutionTime(String methodName, long executionTime) {
        ExecutionTime executionTimeEntity = new ExecutionTime(methodName, executionTime);
        executionTimeRepository.save(executionTimeEntity);
    }

    @Async
    public CompletableFuture<Void> saveExecutionTimeAsync(String methodName, long executionTime) {
        ExecutionTime executionTimeEntity = new ExecutionTime(methodName, executionTime);
        executionTimeRepository.save(executionTimeEntity);
        return CompletableFuture.completedFuture(null);
    }
}