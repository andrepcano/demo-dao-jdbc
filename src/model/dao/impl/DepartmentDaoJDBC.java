package model.dao.impl;

import db.DB;
import db.DbException;
import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.entities.Department;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DepartmentDaoJDBC implements DepartmentDao {

    private Connection conn;

    public DepartmentDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(Department obj) {
        try (PreparedStatement pst = conn.prepareStatement("INSERT INTO department (Id, Name) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS)) {
            pst.setInt(1, obj.getId());
            pst.setString(2, obj.getName());

            int rowsAffected = pst.executeUpdate();

            if (rowsAffected > 0) {
                ResultSet rs= pst.getGeneratedKeys();
                if (rs.next()) {
                    int id = rs.getInt(1);
                    obj.setId(id);
                }
                DB.closeResultSet(rs);
            }
            else {
                throw new DbException("Unexpected Error! no rows affected!");
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Department obj) {

    }

    @Override
    public void deleteById(Integer id) {

    }

    @Override
    public Department findById(Integer id) {
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            pst = conn.prepareStatement("SELECT * FROM department WHERE id = ?");

            pst.setInt(1, id);
            rs = pst.executeQuery();
            if (rs.next()) {
                Department dep = instantiateDepartment(rs);
                return dep;
            }
            return null;


        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
        finally {
            DB.closeStatement(pst);
            DB.closeResultSet(rs);
        }


    }

    @Override
    public List<Department> findAll() {
        try (PreparedStatement pst =
                        conn.prepareStatement("SELECT * FROM department");
             ResultSet rs = pst.executeQuery()) {

            List<Department> list = new ArrayList<>();

            while (rs.next()) {
                list.add(instantiateDepartment(rs));
            }

            return list;

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
    }

    private Department instantiateDepartment(ResultSet rs) throws SQLException {
        Department dep = new Department();
        dep.setId(rs.getInt("Id"));
        dep.setName(rs.getString("Name"));
        return dep;
    }
}
