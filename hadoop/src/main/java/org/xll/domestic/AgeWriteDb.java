package org.xll.domestic;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.lib.db.DBConfiguration;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.db.DBOutputFormat;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;

import java.io.IOException;

public class AgeWriteDb {
  public static class AgePercentMapper extends Mapper<LongWritable, Text, AgePercent, NullWritable> {
    /**
     * 输出的 key
     */
    AgePercent outKey = new AgePercent();
    /**
     * 输出的 value
     */
    NullWritable outValue = NullWritable.get();

    @Override
    protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, AgePercent, NullWritable>.Context context) throws IOException, InterruptedException {
      String[] split = value.toString().split("\t");
      outKey.setAge(Integer.parseInt(split[0]));
      outKey.setValue(Integer.parseInt(split[1]));
      context.write(outKey, outValue);
    }
  }
  public static void main(String[] args) throws IOException, InterruptedException, ClassNotFoundException {
    // 设置环境变量 hadoop 用户名 为 root
    System.setProperty("HADOOP_USER_NAME", "root");

    // 参数配置对象
    Configuration conf = new Configuration();

    // 跨平台提交
    conf.set("mapreduce.app-submission.cross-platform", "true");

    // 本地运行
    // conf.set("mapreduce.framework.name", "local");

    // 设置默认文件系统为 本地文件系统
    // conf.set("fs.defaultFS", "file:///");

    // 配置JDBC 参数
    DBConfiguration.configureDB(conf,
        "com.mysql.cj.jdbc.Driver",
        "jdbc:mysql://spark01:3306/domestic?allowPublicKeyRetrieval=true&useUnicode=true&createDatabaseIfNotExist=true&characterEncoding=UTF8&useSSL=false&useServerPrepStmts=false&rewriteBatchedStatements=true&cachePrepStmts=true&allowMultiQueries=true&serverTimeZone=Asia/Shanghai&sslMode=DISABLED",
        "root", "Woshidale123@");

    // 声明Job对象 就是一个应用
    Job job = Job.getInstance(conf, "AgePercent write db");

    // 指定当前Job的驱动类
    // 本地提交 注释该行
    job.setJarByClass(AgeWriteDb.class);

    // 本地提交启用该行
    // job.setJar("F:\\新疆大学\\code\\bigdata2024\\bigdata\\hadoop\\target\\hadoop.jar");

    // 指定当前Job的 Mapper
    job.setMapperClass(AgePercentMapper.class);

    // 设置 reduce 数量为 零
    job.setNumReduceTasks(0);

    // 设置 map 输出 key 的数据类型
    job.setMapOutputValueClass(AgePercent.class);
    // 设置 map 输出 value 的数据类型
    job.setMapOutputValueClass(NullWritable.class);

    // 定义 map 输入的路径 注意：该路径默认为hdfs路径
    FileInputFormat.addInputPath(job, new Path("/domestic/age/part-r-00000"));

    // 设置输出类
    job.setOutputFormatClass(DBOutputFormat.class);

    // 将数据写入到数据表
    DBOutputFormat.setOutput(job, "violence_Age", "age", "value");

    // 提交 job
    System.exit(job.waitForCompletion(true) ? 0 : 1);
  }
}
