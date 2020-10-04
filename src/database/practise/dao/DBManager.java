package database.practise.dao;


import java.sql.*;

/**
 * 统一封装数据管理
 *
 * @author yuzhiqiang
 */
public class DBManager {

    private static final String URL = "jdbc:mysql://localhost:3306/employee?characterEncoding=UTF-8";

    // 一般会放在配置文件中
    private static final String USERNAME = "root";
    private static final String PWD = "admin";

    private DBManager() {
    }

    public static DBManager get() {
        return new DBManager();
    }


    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }


    /**
     * 创建连接
     *
     * @return Connection
     */
    public Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USERNAME, PWD);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void setSqlParameters(PreparedStatement ps, Object[] parameters) throws SQLException {
        if (parameters != null) {
            int paramCount = ps.getParameterMetaData().getParameterCount();
            for (int i = 0; i < paramCount; i++) {
                ps.setObject(i + 1, parameters[i]);
            }
        }
    }

    public static PreparedStatement getPS(Connection connection, String sql, Object[] parameters) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(sql);
        setSqlParameters(ps, parameters);
        return ps;
    }

    private static PreparedStatement getPC(Connection connection, String sql, Object[] parameters) throws SQLException {
        CallableStatement ps = connection.prepareCall(sql);
        setSqlParameters(ps, parameters);
        return ps;
    }

    /**
     * 执行更新操作
     *
     * @param sql        SQL
     * @param parameters Parameters
     * @return effect row
     */
    public int executeUpdate(String sql, Object[] parameters) throws Exception {
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = getConnection();
            ps = getPS(conn, sql, parameters);
            return ps.executeUpdate();
        } finally {
            close(null, ps, conn);
        }
    }


    /**
     * 调用存储过程(更新)
     *
     * @param sql        SQL
     * @param parameters Parameters
     * @return effect row
     */
    public int callProUpdate(String sql, Object[] parameters) throws Exception{
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = getConnection();
            ps = getPC(conn, sql, parameters);
            return ps.executeUpdate();
        } finally {
            close(null, ps, conn);
        }
    }


    /**
     * 释放资源
     *
     * @param rs   ResultSet
     * @param ps   Statement
     * @param conn Connection
     */
    public void close(ResultSet rs, Statement ps, Connection conn) {
        if (rs != null) {
            try {
                rs.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (ps != null) {
            try {
                ps.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (conn != null) {
            try {
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}