package orga.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import orga.dao.IAccountDao;
import orga.pojp.Account;

import java.util.ArrayList;
import java.util.List;


@Transactional
@Component
public class AccountDao implements IAccountDao {
    private final JdbcTemplate jdbcTemplate;
    @Autowired
    public AccountDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int add(Account account) {
        String sql = "insert into user(name,money) values (?,?)";
        return jdbcTemplate.update(sql, account.getName(), account.getMoney());
    }

    @Override
    public int delete(int id) {
        String sql = "delete from user where id=?";
        return jdbcTemplate.update(sql, id);
    }

    @Override
    public int update(Account account) {

//        String sql = "update user set name=?,money=? where id=?";
//        return jdbcTemplate.update(sql, account.getName(), account.getMoney(), account.getId());
//        String sql = "update user set id=id";
//        List<Object> args = new ArrayList<>();
//        if (account.getName() != null && !("".equals(account.getName()))) {
//            sql = sql + ", name=?";
//            args.add(account.getId());
//        }
//        if (account.getMoney() > 0) {
//            sql = sql + ", money=?";
//            args.add(account.getMoney());
//        }
//        sql = sql + " where id=?";
//        args.add(account.getId());
//        return jdbcTemplate.update(sql, args.toArray());



        Account oldAccount = findById(account.getId());
        if (oldAccount == null) {
            System.out.println("Account with id " + account.getId() + " not found.");
            return 0;
        }


        StringBuilder sqlBuilder = new StringBuilder("update user set ");
        List<Object> args = new ArrayList<>();
        if (account.getName() != null && !account.getName().isEmpty()) {
            sqlBuilder.append("name=?,");
            args.add(account.getName());
        }
        if (account.getMoney() != null) {
            sqlBuilder.append("money=?,");
            args.add(account.getMoney());
        }
        sqlBuilder.setLength(sqlBuilder.length() - 1);
        sqlBuilder.append(" where id=?");
        args.add(account.getId());


        int rowsAffected = jdbcTemplate.update(sqlBuilder.toString(), args.toArray());
        if (rowsAffected == 0) {
            System.out.println("Failed to update account with id " + account.getId());
        }
        return rowsAffected;
    }

    @Override
    public Account findById(int id) {
        String sql = "select * from user where id = ?";
        List<Account> accountList = jdbcTemplate.query(sql, new Object[]{id}, new BeanPropertyRowMapper<>(Account.class));
        if (accountList.size() > 0) {
            return accountList.get(0);
        } else {
            return null;
        }
    }

    @Override
    public Account selectId(int id) {
        return null;
    }

    @Override
    public List<Account> findByName(String name) {
        String sql = "SELECT * FROM user WHERE name LIKE ?";
        return jdbcTemplate.query(sql, new Object[]{"%" + name + "%"}, new BeanPropertyRowMapper<>(Account.class));
    }

    @Override
    public int outMoney(int out_id, double money) {
        return 0;
    }

    @Override
    public int inMoney(int out_id, double money) {
        return 0;
    }

    @Override
    public void transfer(int outUserId, int inUserId, double amount) {
        try {
            // 查询转出账户余额
            String queryBalanceSql = "SELECT money FROM user WHERE id = ?";
            double balance = jdbcTemplate.queryForObject(queryBalanceSql, Double.class, outUserId);

            if (balance > amount) {
                String outMoneySql = "UPDATE user SET money = money - ? WHERE id = ?";
                String inMoneySql = "UPDATE user SET money = money + ? WHERE id = ?";

                jdbcTemplate.update(outMoneySql, amount, outUserId);
                jdbcTemplate.update(inMoneySql, amount, inUserId);
            } else {
                throw new RuntimeException("金额不够");
            }

            // 提交事务
        } catch (Exception e) {
            // 回滚事务
            throw new RuntimeException("转账失败", e);
        }
    }

}
