package org.xll.util.hadoop;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hdfs.DistributedFileSystem;

import java.io.IOException;
import java.net.URI;

/**
 * HDFS 工具类
 *
 * @author 李昊哲
 * @version 1.0.0
 */
public class HdfsUtil {

  /**
   * 获取 HDFS 分布式文件系统
   *
   * @return 分布式文件系统
   * @throws IOException IOException
   */
  public static DistributedFileSystem getDfs() throws IOException {
    // 通过这种方式设置java客户端身份
    System.setProperty("HADOOP_USER_NAME", "root");
    // 配置参数
    Configuration conf = new Configuration();
    DistributedFileSystem dfs = new DistributedFileSystem();
    String nameService = conf.get("dfs.nameservices");
    String hdfsRPCUrl = "hdfs://" + nameService + ":" + 8020;
    // 分布式文件系初始化
    dfs.initialize(URI.create(hdfsRPCUrl), conf);
    return dfs;
  }

  /**
   * 断开 HDFS 连接
   *
   * @param dfs HDFS 分布式文件系统
   * @throws IOException IOException
   */
  public static void close(DistributedFileSystem dfs) throws IOException {
    dfs.close();
  }
}
