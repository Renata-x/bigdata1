package org.xll.domestic;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.hadoop.mapred.lib.db.DBWritable;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class EmploymentPercent implements DBWritable {
  private String employment;
  private int value;

  @Override
  public void write(PreparedStatement preparedStatement) throws SQLException {
    preparedStatement.setString(1,this.employment);
    preparedStatement.setInt(2,this.value);
  }

  @Override
  public void readFields(ResultSet resultSet) throws SQLException {
    this.employment = resultSet.getString(1);
    this.value = resultSet.getInt(2);
  }
}
