package database.practise.dao;

import database.practise.bean.Department;
import database.practise.bean.Employee;
import database.practise.bean.StaffLevel;
import database.practise.exception.ClientException;
import database.practise.utils.StringUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDaoImpl implements IDao<Employee> {

    IDao<Department> departmentDao;
    IDao<StaffLevel> staffLevelDao;

    public EmployeeDaoImpl() {
        departmentDao = new DepartmentDaoImpl();
        staffLevelDao = new StaffLevelDaoImpl();
    }

    @Override
    public int add(Employee data) throws Exception {

        // 查询部门
        setDepartmentByName(data);

        // 查询职级
        setStaffLevelByName(data);

        Object did = null;
        if (data.getDepartment() != null && data.getDepartment().getId() > 0) {
            did = data.getDepartment().getId();
        }
        Object sid = null;
        if (data.getStaffLevel() != null && data.getStaffLevel().getId() > 0) {
            sid = data.getStaffLevel().getId();
        }
        Object[] parameters = new Object[]{data.getName(), data.getNickname(), data.getBirthday(),
                data.getSex(), did, sid};
        return DBManager.get().executeUpdate("INSERT INTO employee (e_name, nickname,birthday,gender,department_id,staff_id) " +
                "VALUES (?,?,?,?,?,?)", parameters);
    }

    private void setDepartmentByName(Employee employee) throws Exception {
        if (employee.getDepartment() != null && !StringUtils.isEmpty(employee.getDepartment().getName())) {
            List<Department> ds = departmentDao.query(new Department(employee.getDepartment().getName()));
            if (ds != null && !ds.isEmpty()) {
                employee.setDepartment(ds.get(0));
            } else {
                throw new ClientException("输入的部门不存在");
            }
        }
    }

    private void setStaffLevelByName(Employee employee) throws Exception {
        if (employee.getStaffLevel() != null && !StringUtils.isEmpty(employee.getStaffLevel().getName())) {
            List<StaffLevel> ss = staffLevelDao.query(new StaffLevel(employee.getStaffLevel().getName()));
            if (ss != null && !ss.isEmpty()) {
                employee.setStaffLevel(ss.get(0));
            } else {
                throw new ClientException("输入的职级不存在");
            }
        }
    }


    @Override
    public int remove(Object key) throws Exception {
        Object[] parameters = new Object[]{key};
        return DBManager.get().executeUpdate("DELETE FROM employee WHERE id = ?", parameters);
    }

    @Override
    public int update(Employee data) throws Exception {

        setDepartmentByName(data);
        setStaffLevelByName(data);

        Object[] parameters = new Object[]{data.getName(), data.getNickname(), data.getBirthday(),
                data.getSex(), data.getDepartment().getId() <= 0 ? null : data.getDepartment().getId(),
                data.getStaffLevel().getId() <= 0 ? null : data.getStaffLevel().getId(),
                data.getId()};
        return DBManager.get().executeUpdate("UPDATE employee SET e_name = ?,nickname = ?,birthday = ?,gender = ?," +
                "department_id = ?,staff_id = ? WHERE id = ?", parameters);
    }

    @Override
    public List<Employee> query(Employee param) throws Exception {

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        // Natural Join
        //final StringBuilder sql = new StringBuilder("SELECT e.id, e.e_name, e.nickname, e.birthday, e.gender, d.id did, d.d_name dname, s.id sid, s.s_name sname " +
        //        "FROM employee e, department d, staff_level s WHERE e.department_id = d.id AND e.staff_id = s.id ");

        // Left Join
        final StringBuilder sql = new StringBuilder("SELECT e.id, e.e_name, e.nickname, e.birthday, e.gender, d.id did, d.d_name dname, s.id sid, s.s_name sname FROM employee e " +
                "LEFT JOIN department d ON e.department_id = d.id LEFT JOIN staff_level s ON e.staff_id = s.id ");
        List<Employee> list = new ArrayList<>();
        Object[] parameters = whereParams(sql, param);

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
                employee.setStaffLevel(new StaffLevel(rs.getInt("sid"), rs.getString("sname")));
                list.add(employee);
            }
        } finally {
            DBManager.get().close(rs, ps, conn);
        }
        return list;
    }

    public Object[] whereParams(StringBuilder sb, Employee employee) {
        if (employee == null) return null;
        List<Object> params = new ArrayList<>();
        StringBuilder builder = new StringBuilder();
        if (!StringUtils.isEmpty(employee.getName())) {
            params.add(employee.getName());
            builder.append(" e_name = ?").append(AND);
        }
        if (!StringUtils.isEmpty(employee.getNickname())) {
            params.add(employee.getNickname());
            builder.append(" nickname = ?").append(AND);
        }
        if (employee.getBirthday() != null) {
            params.add(employee.getBirthday());
            builder.append(" birthday = ?").append(AND);
        }
        if (employee.getSex() != -1) {
            params.add(employee.getSex());
            builder.append(" gender = ?").append(AND);
        }
        if (employee.getDepartment() != null) {
            params.add(employee.getDepartment().getName());
            builder.append(" d.d_name = ?").append(AND);
        }
        if (employee.getStaffLevel() != null) {
            params.add(employee.getStaffLevel().getName());
            builder.append(" s.s_name = ?").append(AND);
        }

        if (builder.length() != 0) {
            builder.delete(builder.length() - AND.length(), builder.length());
            sb.append(" WHERE ").append(builder);
        }
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
                employee.setStaffLevel(new StaffLevel(rs.getInt("sid"), rs.getString("sname")));
                return employee;
            }
        } finally {
            DBManager.get().close(rs, ps, conn);
        }
        return null;
    }

}
