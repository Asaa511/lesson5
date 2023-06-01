package orga.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;

@Component
@Aspect
public class Advice {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public Advice(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Pointcut("execution(* orga.dao.impl.*.*(..))")
    public void pt() {
    }

    @Around("pt()")
    @Transactional
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        // 获取当前时间
        long start = System.currentTimeMillis();
        // 定义 SQL 语句
        String startSql = "INSERT INTO log(method, start_inf) VALUES (?, ?)";
        String successSql = "INSERT INTO log(method, sccessu_inf) VALUES (?, ?)";
        String exceptionSql = "INSERT INTO log(method, exception_inf) VALUES (?, ?)";
        // 获取方法签名
        String method = pjp.getSignature().toString();
        // 获取方法名
        String methodName = pjp.getSignature().getName();
        // 获取方法参数
        Object[] args = pjp.getArgs();
        // 构造方法使用信息
        String methodInfo = methodName + "(" + StringUtils.arrayToCommaDelimitedString(args) + ")";
        // 获取开始时间信息
        String startInf = "start time: " +
                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(start);
        // 将方法名和开始时间信息插入数据库
        jdbcTemplate.update(startSql, method, startInf);

        Object re = null;
        try {

            re = pjp.proceed();

            if (isTransferMethod(pjp)) {
                String successInf = "transfer success time: " +
                        new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(System.currentTimeMillis());
                jdbcTemplate.update(successSql, method, successInf);
            }
        } catch (Throwable ex) {

            String exInf = "exception time: " +
                    new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(System.currentTimeMillis());
            jdbcTemplate.update(exceptionSql, method, exInf);
            throw ex;
        } finally {

            long end = System.currentTimeMillis();
            String endInf = "end time: " +
                    "run time: " + (end - start) + "ms!";

            jdbcTemplate.update(startSql, method, endInf);

            System.out.println("Method used: " + methodInfo);
        }
        return re;
    }


    private boolean isTransferMethod(ProceedingJoinPoint pjp) {
        // 判断方法名是否包含 "transfer"
        String methodName = pjp.getSignature().getName();
        return methodName.contains("transfer");
    }
}

