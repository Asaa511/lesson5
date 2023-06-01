package orga;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import orga.config.SpringConfig;
import orga.dao.IAccountService;
import orga.pojp.Account;
import orga.dao.IAccountDao;


public class Main {
    public static void main(String[] args) {
        // 创建 Spring 应用程序上下文
        ApplicationContext ac = new AnnotationConfigApplicationContext(SpringConfig.class);

//        ApplicationContext ac1 = new ClassPathXmlApplicationContext("applicationContext.xml");

        // 获取 IAccountService 实例
        IAccountService iAccountService = ac.getBean(IAccountService.class);

        // 初始化 accountDao 中对象， Sping 获取对象实例
        IAccountDao accountDao = ac.getBean(IAccountDao.class);





        // 查找账户信息
        int id = 0;
        iAccountService.findById(id);

        // 获取 Account 实例
        Account account = ac.getBean(Account.class);



          // 添加新账户
//        account.setName("第");
//        account.setMoney(564955.625);
//        iAccountService.add(account);

          // 更新账户信息
//        account.setId(6);
//        account.setName("张给");
//        account.setMoney(1232.123);
//        iAccountService.update(account);

          // 删除账户信息
//        account.setId(3);
//        iAccountService.delete(account.getId());

          // 根据id查询对应信息
//        Account account2 = iAccountService.findById(5);
//        System.out.println(account2);

          // 模糊查找
//        List<Account> accountList = iAccountService.findByName("二");
////        System.out.println(accountList);


        // 转账操作
//        int outUserId = 5; // 转出账户ID
//        int inUserId = 4;  // 转入账户ID
//        double amount = 1000.0; // 转账金额
//        accountDao.transfer(outUserId, inUserId, amount);



    }
}
