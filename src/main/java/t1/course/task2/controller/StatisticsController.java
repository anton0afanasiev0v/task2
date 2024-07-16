package t1.course.task2.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import t1.course.task2.model.ExecutionTime;
import t1.course.task2.repository.ExecutionTimeRepository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestController
@RequestMapping("/api/statistics")
@Tag(name = "Statistics", description = "API для получения статистики времени выполнения методов")

public class StatisticsController {

    private final ExecutionTimeRepository executionTimeRepository;

    @GetMapping
    @Operation(summary = "Получить время выполнения методов", description = "Возвращает время выполнения для всех методов")
    public List<ExecutionTime> getAllExecutionTimes() {
        return executionTimeRepository.findAll();
    }

    @GetMapping("/statistics")
    @Operation(summary = "Получить среднее время выполнения методов", description = "Возвращает среднее время выполнения для всех методов")
    public Map<String, Double> getExecutionTimeStatistics() {
        List<ExecutionTime> executionTimes = executionTimeRepository.findAll();
        return executionTimes.stream()
                .collect(Collectors.groupingBy(ExecutionTime::getMethodName,
                        Collectors.averagingLong(ExecutionTime::getExecutionTime)));
    }
}
