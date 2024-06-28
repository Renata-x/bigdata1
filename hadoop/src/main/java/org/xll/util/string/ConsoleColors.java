package org.xll.util.string;

/**
 * 控制台字体颜色
 *
 * @author 李昊哲
 * @version 1.0.0
 */
public interface ConsoleColors {
  // ANSI转义码开始
  String ANSI_RESET = "\u001B[0m";
  String ANSI_RED = "\u001B[31m";
  String ANSI_GREEN = "\u001B[32m";
  String ANSI_YELLOW = "\u001B[33m";
  String ANSI_BLUE = "\u001B[34m";
  String ANSI_PURPLE = "\u001B[35m";
  String ANSI_CYAN = "\u001B[36m";

  public static void main(String[] args) {
    // 打印彩色文本到控制台
    System.out.println(ANSI_RED + "This text will be red." + ANSI_RESET);
    System.out.println(ANSI_GREEN + "This text will be green." + ANSI_RESET);
    System.out.println(ANSI_YELLOW + "This text will be yellow." + ANSI_RESET);
    System.out.println(ANSI_BLUE + "This text will be blue." + ANSI_RESET);
    System.out.println(ANSI_PURPLE + "This text will be purple." + ANSI_RESET);
    System.out.println(ANSI_CYAN + "This text will be cyan." + ANSI_RESET);
  }
}
