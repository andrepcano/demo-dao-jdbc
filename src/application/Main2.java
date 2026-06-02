package application;

import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.entities.Department;

import java.util.List;

public class Main2 {
    public static void main(String[] args) {


        DepartmentDao departmentDao = DaoFactory.createDepartmentDao();

        System.out.println("=== TEST 1: department findById ===");
        Department department = departmentDao.findById(5);

        System.out.println(department);

        System.out.println("=== TEST 2: department findAll ===");
        List<Department> list = departmentDao.findAll();
        for (Department dep : list) {
            System.out.println(dep);
        }

        /*System.out.println("=== TEST 3: department Insert ===");
        Department department1 = new Department(7, "Furniture");
        departmentDao.insert(department1);
        System.out.println("Inserted! New id = " + department1.getId());*/

        System.out.println("=== TEST 4: department Update ===");
        department = departmentDao.findById(6);
        department.setName("Jewelry ");
        departmentDao.update(department);
        System.out.println("Update Complete!");
    }
}
