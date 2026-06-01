package model.dao;

import com.mysql.cj.xdevapi.Client;
import model.entities.Department;

import java.util.List;
import java.util.Objects;

public interface DepartmentDao {

    void insert(Department obj);
    void update(Department obj);
    void deleteById(Integer id);
    Department findById(Integer id);
    List<Department> findAll();
}
