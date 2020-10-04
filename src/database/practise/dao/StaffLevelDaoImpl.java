package database.practise.dao;

import database.practise.bean.StaffLevel;
import database.practise.utils.StringUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class StaffLevelDaoImpl implements IDao<StaffLevel> {
    @Override
    public int add(StaffLevel data) throws Exception {
        Object[] parameters = new Object[]{data.getName(), data.getType(), data.getLevel()};
        return DBManager.get().executeUpdate("INSERT INTO staff_level (s_name, s_type, s_level) " +
                "VALUES (?,?,?)", parameters);
    }

    @Override
    public int remove(Object key) throws Exception {
        Object[] parameters = new Object[]{key};
        return DBManager.get().executeUpdate("DELETE FROM staff_level WHERE id = ?", parameters);
    }

    @Override
    public int update(StaffLevel data) throws Exception {
        Object[] parameters = new Object[]{data.getName(), data.getType(), data.getLevel(), data.getId()};
        return DBManager.get().executeUpdate("UPDATE staff_level SET s_name = ?, s_type = ? , s_level = ? WHERE id = ?", parameters);
    }

    @Override
    public List<StaffLevel> query(StaffLevel data) throws Exception {

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        final StringBuilder sql = new StringBuilder("SELECT id, s_name, s_type, s_level FROM staff_level ");

        List<StaffLevel> list = new ArrayList<>();
        Object[] parameters = whereParams(sql, data);

        try {
            conn = DBManager.get().getConnection();
            ps = DBManager.getPS(conn, sql.toString(), parameters);
            rs = ps.executeQuery();
            while (rs.next()) {
                StaffLevel staffLevel = new StaffLevel(rs.getInt("id"), rs.getString("s_name"));
                staffLevel.setType(rs.getString("s_type"));
                staffLevel.setLevel(rs.getShort("s_level"));
                list.add(staffLevel);
            }
        } finally {
            DBManager.get().close(rs, ps, conn);
        }
        return list;
    }

    @Override
    public Object[] whereParams(StringBuilder sb, StaffLevel staffLevel) {
        if (staffLevel == null) return null;
        List<Object> params = new ArrayList<>();
        StringBuilder builder = new StringBuilder();
        if (!StringUtils.isEmpty(staffLevel.getName())) {
            params.add(staffLevel.getName());
            builder.append(" s_name = ?").append(AND);
        }
        if (!StringUtils.isEmpty(staffLevel.getType())) {
            params.add(staffLevel.getType());
            builder.append(" s_type = ?").append(AND);
        }

        if (staffLevel.getLevel() > 0) {
            params.add(staffLevel.getLevel());
            builder.append(" s_level = ?").append(AND);
        }

        if (builder.length() != 0) {
            builder.delete(builder.length() - AND.length(), builder.length());
            sb.append(" WHERE ").append(builder);
        }
        return params.toArray();
    }

}
