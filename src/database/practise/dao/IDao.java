package database.practise.dao;

import java.util.List;

public interface IDao<T> {

    int add(T data);
    int remove(Object key);
    int update(T data);
    List<T> getAll();
    T findById(Object key);

}
