package orga.config;

import org.springframework.context.annotation.*;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration // 该类标记为配置类
@ComponentScan("orga") // 扫描指定包下的组件，并将其注入到 Spring 容器中
@PropertySource("classpath:jdbc.properties") // 加载指定的属性文件
@Import(JdbcConfig.class) // 导入 JdbcConfig 配置类
@EnableAspectJAutoProxy // 开启 AOP 支持
@EnableTransactionManagement // 开启事务管理支持
public class SpringConfig {
}
