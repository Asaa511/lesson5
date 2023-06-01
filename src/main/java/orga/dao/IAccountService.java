package orga.dao;
import orga.pojp.Account;

import java.util.List;

public interface IAccountService {
    public int add(Account account);
    public int delete(int id);
    public int update(Account account);
    public Account findById(int id);
    List<Account> findByName(String name);
}
