package database.practise.dao;

import database.practise.bean.Department;
import database.practise.bean.Employee;
import database.practise.bean.Staff;
import database.practise.utils.StringUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDaoImpl implements IDao<Employee> {
    @Override
    public int add(Employee data) throws Exception {
        Object[] parameters = new Object[]{data.getName(), data.getNickname(), data.getBirthday(),
                data.getSex(), data.getDepartment().getId(), data.getStaffLevel().getId()};
        return DBManager.get().executeUpdate("INSERT INTO employee (e_name, nickname,birthday,gender,department_id,staff_id) " +
                "VALUES (?,?,?,?,?,?)", parameters);
    }

    @Override
    public int remove(Object key) throws Exception {
        Object[] parameters = new Object[]{key};
        return DBManager.get().executeUpdate("DELETE FROM employee WHERE id = ?", parameters);
    }

    @Override
    public int update(Employee data) throws Exception {
        Object[] parameters = new Object[]{data.getName(), data.getNickname(), data.getBirthday(),
                data.getSex(), data.getDepartment().getId(), data.getStaffLevel().getId(), data.getId()};
        return DBManager.get().executeUpdate("UPDATE employee SET e_name = ?,nickname = ?,birthday = ?,gender = ?," +
                "department_id = ?,staff_id = ? WHERE id = ?", parameters);
    }

    @Override
    public List<Employee> query(Employee param) throws Exception {

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        final StringBuilder sql = new StringBuilder("SELECT e.id, e.e_name, e.nickname, e.birthday, e.gender, d.id did, d.d_name dname, s.id sid, s.s_name sname " +
                "FROM employee e, department d, staff_level s WHERE e.department_id = d.id AND e.staff_id = s.id ");

        List<Employee> list = new ArrayList<>();
        Object[] parameters = convertToParams(sql, param);

        try {
            conn = DBManager.get().getConnection();
            ps = DBManager.getPS(conn, sql.toString(), parameters);
            rs = ps.executeQuery();
            while (rs.next()) {
                Employee employee = new Employee();
                employee.setId(rs.getInt("id"));
                employee.setName(rs.getString("e_name"));
                employee.setNickname(rs.getString("nickname"));
                employee.setBirthday(rs.getDate("birthday"));
                employee.setSex(rs.getShort("gender"));
                employee.setDepartment(new Department(rs.getInt("did"), rs.getString("dname")));
                employee.setStaffLevel(new Staff(rs.getInt("sid"), rs.getString("sname")));
                list.add(employee);
            }
        } finally {
            DBManager.get().close(rs, ps, conn);
        }
        return list;
    }

    private Object[] convertToParams(StringBuilder builder, Employee employee) {
        if (employee == null) return null;
        List<Object> params = new ArrayList<>();
        if (!StringUtils.isEmpty(employee.getName())) {
            params.add(employee.getName());
            builder.append(" AND e_name = ? ");
        }
        if (!StringUtils.isEmpty(employee.getNickname())) {
            params.add(employee.getNickname());
            builder.append(" AND nickname = ? ");
        }
        if (employee.getBirthday() != null) {
            params.add(employee.getBirthday());
            builder.append(" AND birthday = ? ");
        }
        if (employee.getSex() != -1) {
            params.add(employee.getSex());
            builder.append(" AND gender = ? ");
        }
        if (employee.getDepartment() != null && !StringUtils.isEmpty(employee.getDepartment().getName())) {
            params.add(employee.getDepartment().getName());
            builder.append(" AND d.id = ? ");
        }
        if (employee.getStaffLevel() != null && !StringUtils.isEmpty(employee.getStaffLevel().getName())) {
            params.add(employee.getStaffLevel().getName());
            builder.append(" AND s.id = ? ");
        }
        System.out.println(builder.toString());
        System.out.println(params.size());

        return params.toArray();
    }


    @Override
    public Employee findById(Object key) throws Exception {

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        final String sql = "SELECT e.id, e.e_name, e.nickname, e.birthday, e.gender, d.id did, d.d_name dname, s.id sid, s.s_name sname " +
                "FROM employee e, department d, staff_level s WHERE e.department_id = d.id AND e.staff_id = s.id AND e.id = ?";
        final Object[] parameters = new Object[]{key};
        try {
            conn = DBManager.get().getConnection();
            ps = DBManager.getPS(conn, sql, parameters);
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
        } finally {
            DBManager.get().close(rs, ps, conn);
        }
        return null;
    }

}
