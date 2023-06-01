package orga.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import orga.dao.IAccountDao;
import orga.dao.IAccountService;
import orga.pojp.Account;

import java.util.List;


@Transactional
@Service
public class AccountService implements IAccountService {
    @Autowired
    private IAccountDao iAccountDao;

    @Transactional
    @Override
    public int add(Account account) {
        return iAccountDao.add(account);
    }


    @Transactional
    @Override
    public int delete(int id) {
        return iAccountDao.delete(id);
    }

    @Transactional
    @Override
    public int update(Account account) {
        return iAccountDao.update(account);
    }

    @Transactional
    @Override
    public Account findById(int id) {
        return iAccountDao.findById(id);
    }

    @Transactional
    @Override
    public List<Account> findByName(String name) {
        return iAccountDao.findByName(name);
    }

    public Account selectById(int id) {
        return iAccountDao.findById(id);
    }


}