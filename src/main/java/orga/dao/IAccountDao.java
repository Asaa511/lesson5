package orga.dao;

import orga.pojp.Account;

import java.util.List;

public interface IAccountDao {
     int add(Account account);
     int delete(int id);
     int update(Account account);
     Account findById(int id);
     Account selectId(int id);
     List<Account> findByName(String name);
     int outMoney(int out_id, double money);
     int inMoney(int out_id, double money);
     void transfer(int outUserId, int inUserId, double amount);
}
