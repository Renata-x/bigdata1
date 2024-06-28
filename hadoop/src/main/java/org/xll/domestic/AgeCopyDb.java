package org.xll.domestic;

import com.mysql.cj.jdbc.Driver;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AgeCopyDb {
  public static void main(String[] args) throws SQLException {
    List<AgePercent> list = select();
    insert(list);
  }

  /**
   * 大数据业务员部门将 视频类别占比数据查询出来
   *
   * @return 视频类别占比数据
   */
  public static List<AgePercent> select() throws SQLException {
    List<AgePercent> list = new ArrayList<AgePercent>();
    String url = "jdbc:mysql://spark01:3306/domestic?allowPublicKeyRetrieval=true&useUnicode=true&createDatabaseIfNotExist=true&characterEncoding=UTF8&useSSL=false&useServerPrepStmts=false&rewriteBatchedStatements=true&cachePrepStmts=true&allowMultiQueries=true&serverTimeZone=Asia/Shanghai&sslMode=DISABLED";
    String user = "root";
    String password = "Woshidale123@";
    String sql = "select age,count from violence_Age";
    DriverManager.registerDriver(new Driver());
    Connection conn = DriverManager.getConnection(url, user, password);
    PreparedStatement pst = conn.prepareStatement(sql);
    ResultSet rs = pst.executeQuery();
    AgePercent percent = null;
    while (rs.next()) {
      percent = new AgePercent();
      percent.setAge(rs.getInt("age"));
      percent.setValue(rs.getInt("value"));
      list.add(percent);
    }
    rs.close();
    pst.close();
    conn.close();
    return list;
  }

  /**
   * 将视频类别占比数据 写入到数据可视化部门的数据表中
   *
   * @param list 视频类别占比数据
   */
  public static void insert(List<AgePercent> list) throws SQLException {
    String url = "jdbc:mysql://spark01:3306/domestic_view?allowPublicKeyRetrieval=true&useUnicode=true&createDatabaseIfNotExist=true&characterEncoding=UTF8&useSSL=false&useServerPrepStmts=false&rewriteBatchedStatements=true&cachePrepStmts=true&allowMultiQueries=true&serverTimeZone=Asia/Shanghai&sslMode=DISABLED";
    String user = "root";
    String password = "Woshidale123@";
    String sql = "insert into violence_Age values(?,?)";
    DriverManager.registerDriver(new Driver());
    Connection conn = DriverManager.getConnection(url, user, password);
    conn.setAutoCommit(false);
    PreparedStatement pst = conn.prepareStatement(sql);
    int i = 0;
    for (AgePercent percent : list) {
      pst.setInt(1, percent.getAge());
      pst.setInt(2, percent.getValue());
      i = pst.executeUpdate();
    }
    if (i > 0) {
      conn.commit();
    } else {
      conn.rollback();
    }
    pst.close();
    conn.close();
  }
}
