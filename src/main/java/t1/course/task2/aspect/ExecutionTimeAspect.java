package t1.course.task2.aspect;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import t1.course.task2.annotation.TrackAsyncTime;
import t1.course.task2.annotation.TrackTime;
import t1.course.task2.servece.ExecutionTimeService;

import java.util.concurrent.CompletableFuture;

@Aspect
@Component
@Slf4j
@AllArgsConstructor
public class ExecutionTimeAspect {

    private final ExecutionTimeService executionTimeService;

    @Around("@annotation(trackTime)")
    public Object trackTime(ProceedingJoinPoint joinPoint, TrackTime trackTime) throws Throwable {
        long start = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long executionTime = System.currentTimeMillis() - start;

        executionTimeService.saveExecutionTime(joinPoint.getSignature().getName(), executionTime);
        return result;
    }

    @Around("@annotation(trackAsyncTime)")
    @Async
    public Object trackAsyncTime(ProceedingJoinPoint joinPoint, TrackAsyncTime trackAsyncTime) throws Throwable {
        return CompletableFuture.runAsync(() -> {
            try {

                long start = System.currentTimeMillis();
                Object result = joinPoint.proceed();
                long executionTime = System.currentTimeMillis() - start;

                log.info("Асинхронный запуск в AsyncRunnerAspect");
                executionTimeService.saveExecutionTimeAsync(joinPoint.getSignature().getName(), executionTime);

                joinPoint.proceed();
            } catch (Throwable e) {
                log.error("Ошибка AsyncRunnerAspect", e);
            }
        });
    }
}