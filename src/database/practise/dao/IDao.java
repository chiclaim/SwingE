package database.practise.dao;

import java.util.List;

public interface IDao<T> {

    int add(T data) throws Exception;
    int remove(Object key) throws Exception;
    int update(T data) throws Exception;
    List<T> query(T data) throws Exception;
    T findById(Object key) throws Exception;

}
