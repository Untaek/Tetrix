package untaek.server;

import com.mysql.cj.jdbc.MysqlConnectionPoolDataSource;

import javax.sql.PooledConnection;
import java.sql.*;

public class DB {
  private static final String HOST = "127.0.0.1";
  private static final int PORT = 3306;
  private static final String DB = "tetrix";
  private static final String URL = String.format("jdbc:mysql://%s:%d/%s?serverTimezone=UTC", HOST, PORT, DB);
  private static final String USER = "root";
  private static final String PASSWORD = "a123456";

  private PooledConnection pooledConnection;

  public DB(){
    try {
      Class.forName("com.mysql.cj.jdbc.Driver");
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }

    try {
      MysqlConnectionPoolDataSource dataSource = new MysqlConnectionPoolDataSource();
      dataSource.setUrl(URL);
      dataSource.setPort(PORT);
      dataSource.setDatabaseName(DB);
      dataSource.setUser(USER);
      dataSource.setPassword(PASSWORD);
      dataSource.setUseSSL(false);
      pooledConnection = dataSource.getPooledConnection();

      System.out.println("Connection pool established");
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  Connection getConnection() {
    try {
      return this.pooledConnection.getConnection();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return null;
  }
}
