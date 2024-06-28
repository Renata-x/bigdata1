package org.xll.domestic;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hdfs.DistributedFileSystem;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.xll.util.hadoop.HdfsUtil;

import java.io.IOException;

public class EmploymentPercentJob {
  public static class EmploymentPercentMapper extends Mapper<LongWritable, Text, Text, IntWritable> {
    // 输出的 key
    Text outKey = new Text();
    // 输出的 value
    IntWritable outValue = new IntWritable(1);

    @Override
    protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, Text, IntWritable>.Context context) throws IOException, InterruptedException {
      // 读取文档一行内容 使用逗号分隔将 内容转为字符串数组
      String[] split = value.toString().split(",");
      // 获取字符串数组下标为5的元素封装为输出的key
      outKey.set(split[3]);
      // 输出数据
      context.write(outKey, outValue);
    }
  }

  public static class EmploymentPercentReducer extends Reducer<Text, IntWritable, Text, IntWritable> {
    // 输出的 value
    IntWritable outValue = new IntWritable();

    @Override
    protected void reduce(Text key, Iterable<IntWritable> values, Reducer<Text, IntWritable, Text, IntWritable>.Context context) throws IOException, InterruptedException {
      int sum = 0;
      for (IntWritable val : values) {
        sum += val.get();
      }
      outValue.set(sum);
      context.write(key, outValue);
    }
  }

  public static void main(String[] args) throws IOException, InterruptedException, ClassNotFoundException {
    // 设置环境变量 hadoop 用户名 为 root
    System.setProperty("HADOOP_USER_NAME", "root");

    // 参数配置对象
    Configuration conf = new Configuration();

    // 跨平台提交
    conf.set("mapreduce.app-submission.cross-platform", "true");

    // 声明Job对象 就是一个应用
    Job job = Job.getInstance(conf, "Domestic data format");

    // 指定当前Job的驱动类
    job.setJarByClass(EmploymentPercentJob.class);

    // 指定当前Job的 Mapper
    job.setMapperClass(EmploymentPercentMapper.class);
    // 指定当前Job的 Combiner 注意：一定不能影响最终计算结果 否则 不使用
    job.setCombinerClass(EmploymentPercentReducer.class);
    // 指定当前Job的 Reducer
    job.setReducerClass(EmploymentPercentReducer.class);

    // 设置 reduce 数量为 零
    // job.setNumReduceTasks(0);
    // 设置 map 输出 key 的数据类型
    job.setMapOutputValueClass(Text.class);
    // 设置 map 输出 value 的数据类型
    job.setMapOutputValueClass(IntWritable.class);

    // 设置 map 输出 key 的数据类型
    job.setMapOutputValueClass(Text.class);
    // 设置 map 输出 value 的数据类型
    job.setMapOutputValueClass(IntWritable.class);

    // 设置 reduce 输出 key 的数据类型
    job.setOutputKeyClass(Text.class);
    // 设置 reduce 输出 value 的数据类型hdf
    job.setOutputValueClass(IntWritable.class);

    // 定义 map 输入的路径 注意：该路径默认为hdfs路径
    FileInputFormat.addInputPath(job, new Path("/domestic/data/violence.csv"));
    // 定义 reduce 输出数据持久化的路径 注意：该路径默认为hdfs路径
    Path dst = new Path("/domestic/employment");

    // 获取 HDFS 连接
    DistributedFileSystem dfs = HdfsUtil.getDfs();

    if (dfs.exists(dst)) {
      dfs.delete(dst, true);
    }

    FileOutputFormat.setOutputPath(job, dst);
    // 提交 job
    // job.submit();
    System.exit(job.waitForCompletion(true) ? 0 : 1);
  }
}
