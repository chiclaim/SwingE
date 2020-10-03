package database.practise.dao;

import database.practise.bean.Department;
import database.practise.bean.Employee;
import database.practise.bean.Staff;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDaoImpl implements IDao<Employee> {
    @Override
    public int add(Employee data) {
        Object[] parameters = new Object[]{data.getName(), data.getNickname(), data.getBirthday(),
                data.getSex(), data.getDepartment().getId(), data.getStaffLevel().getId()};
        return DBManager.get().executeUpdate("INSERT INTO employee (e_name, nickname,birthday,gender,department_id,staff_id) " +
                "VALUES (?,?,?,?,?,?)", parameters);
    }

    @Override
    public int remove(Object key) {
        Object[] parameters = new Object[]{key};
        return DBManager.get().executeUpdate("DELETE FROM employee WHERE id = ?", parameters);
    }

    @Override
    public int update(Employee data) {
        Object[] parameters = new Object[]{data.getName(), data.getNickname(), data.getBirthday(),
                data.getGender(), data.getDepartment().getId(), data.getStaffLevel().getId(), data.getId()};
        return DBManager.get().executeUpdate("UPDATE employee SET e_name = ?,nickname = ?,birthday = ?,gender = ?," +
                "department_id = ?,staff_id = ? WHERE id = ?", parameters);
    }

    @Override
    public List<Employee> getAll() {

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        final String sql = "SELECT e.id, e.e_name, e.nickname, e.birthday, e.gender, d.id did, d.d_name dname, s.id sid, s.s_name sname " +
                "FROM employee e, department d, staff_level s WHERE e.department_id = d.id AND e.staff_id = s.id";

        List<Employee> list = new ArrayList<>();
        try {
            conn = DBManager.get().getConnection();
            ps = DBManager.getPS(conn, sql, null);
            rs = ps.executeQuery();
            while (rs.next()) {
                Employee employee = new Employee();
                employee.setId(rs.getInt("id"));
                employee.setName(rs.getString("e_name"));
                employee.setNickname(rs.getString("nickname"));
                employee.setBirthday(rs.getDate("birthday"));
                employee.setGender(rs.getShort("gender") == 0 ? "男" : "女");
                employee.setDepartment(new Department(rs.getInt("did"), rs.getString("dname")));
                employee.setStaffLevel(new Staff(rs.getInt("sid"), rs.getString("sname")));
                list.add(employee);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBManager.get().close(rs, ps, conn);
        }
        return list;
    }

    @Override
    public Employee findById(Object key) {

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        final String sql = "SELECT e.id, e.e_name, e.nickname, e.birthday, e.gender, d.id did, d.d_name dname, s.id sid, s.s_name sname " +
                "FROM employee e, department d, staff_level s WHERE e.department_id = d.id AND e.staff_id = s.id AND e.id = ?";
        final Object[] parameters = new Object[]{key};
        try {
            conn = DBManager.get().getConnection();
            ps = DBManager.getPS(conn, sql, null);
            rs = ps.executeQuery();
            if (rs.next()) {
                Employee employee = new Employee();
                employee.setId(rs.getInt("id"));
                employee.setName(rs.getString("e_name"));
                employee.setNickname(rs.getString("nickname"));
                employee.setBirthday(rs.getDate("birthday"));
                employee.setSex(rs.getShort("gender"));
                employee.setDepartment(new Department(rs.getInt("did"), rs.getString("dname")));
                employee.setStaffLevel(new Staff(rs.getInt("sid"), rs.getString("sname")));
                return employee;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBManager.get().close(rs, ps, conn);
        }
        return null;
    }

    public static void main(String[] args) {
        List list = new EmployeeDaoImpl().getAll();
        System.out.println(list.size());
    }
}
