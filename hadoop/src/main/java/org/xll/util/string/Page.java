package org.xll.util.string;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * @author 李昊哲
 * @version 1.0.0
 */
@Setter
@Getter
@ToString
public class Page<T> {
  /**
   * 查询页码
   */
  private int pageNo;
  /**
   * 每页记录数
   */
  private int pageSize;
  /**
   * 总记录数
   */
  private long totalCount;
  /**
   * 总页码数
   */
  private int totalPage;

  /**
   * 当前页码在数据库中的起始索引
   */
  private int pageIndex;
  /**
   * 是否有上一页
   */
  private boolean hasPrevious;
  /**
   * 是否有下一页
   */
  private boolean hasNext;
  /**
   * 是否是首页
   */
  private boolean first;
  /**
   * 是否是末页
   */
  private boolean last;

  /**
   * 分页后的数据列表
   */
  private List<T> pageList;

  public Page() {
    this(1, 5);
  }

  public Page(int pageNo, int pageSize) {
    this.pageNo = pageNo;
    this.pageSize = pageSize;
    this.pageIndex = (pageNo - 1) * pageSize;
  }

  public Page(int pageNo, int pageSize, int totalCount) {
    this.pageNo = pageNo;
    this.pageSize = pageSize;
    this.pageIndex = (pageNo - 1) * pageSize;
    this.totalCount = totalCount;
    this.totalPage = totalCount % pageSize == 0 ? totalCount / pageSize : totalCount / pageSize + 1;
    this.first = pageNo == 1;
    this.last = pageNo == totalPage;
    this.hasPrevious = pageNo > 1;
    this.hasNext = pageNo < totalPage;
  }

  public void setTotalCount(long totalCount) {
    this.totalCount = totalCount;
    this.totalPage = Math.toIntExact(totalCount % pageSize == 0 ? totalCount / pageSize : totalCount / pageSize + 1);
    this.first = pageNo == 1;
    this.last = pageNo == totalPage;
    this.hasPrevious = pageNo > 1;
    this.hasNext = pageNo < totalPage;
  }
}
