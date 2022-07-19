package com.example.demo;


import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.StreamTokenizer;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

class DemoApplicationTests {

    void contextLoads() {
    }

    void io() {
        String fileName = "C:/Users/hairui/Desktop/其他/demo.txt";
        FileReader fileReader = null;
        try {
            fileReader = new FileReader(fileName);
            //创建分析给定字符流的标记生成器
            StreamTokenizer st = new StreamTokenizer(new BufferedReader(
                    fileReader));

            //ordinaryChar方法指定字符参数在此标记生成器中是“普通”字符。
            //下面指定单引号、双引号和注释符号是普通字符
            st.ordinaryChar('\'');
            st.ordinaryChar('\"');
            st.ordinaryChar('/');

            String s;
            int numberSum = 0;
            int wordSum = 0;
            int symbolSum = 0;
            int total = 0;
            //nextToken方法读取下一个Token.
            //TT_EOF指示已读到流末尾的常量。
            while (st.nextToken() !=StreamTokenizer.TT_EOF) {
                //在调用 nextToken 方法之后，ttype字段将包含刚读取的标记的类型
                switch (st.ttype) {
                    //TT_EOL指示已读到行末尾的常量。
                    case StreamTokenizer.TT_EOL:
                        break;
                    //TT_NUMBER指示已读到一个数字标记的常量
                    case StreamTokenizer.TT_NUMBER:
                        //如果当前标记是一个数字，nval字段将包含该数字的值
                        s = String.valueOf((st.nval));
                        System.out.println("数字有："+s);
                        numberSum ++;
                        break;
                    //TT_WORD指示已读到一个文字标记的常量
                    case StreamTokenizer.TT_WORD:
                        //如果当前标记是一个文字标记，sval字段包含一个给出该文字标记的字符的字符串
                        s = st.sval;
                        System.out.println("单词有： "+s);
                        wordSum ++;
                        break;
                    default:
                        //如果以上3中类型都不是，则为英文的标点符号
                        s = String.valueOf((char) st.ttype);
                        System.out.println("标点有： "+s);
                        symbolSum ++;
                }
            }
            System.out.println("数字有 " + numberSum+"个");
            System.out.println("单词有 " + wordSum+"个");
            System.out.println("标点符号有： " + symbolSum+"个");
            total = symbolSum + numberSum +wordSum;
            System.out.println("Total = " + total);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fileReader != null) {
                try {
                    fileReader.close();
                } catch (IOException e1) {
                }
            }
        }
    }

    void ioTestRead() {
        BufferedReader bufferedReader =null;
        try {
            String oldpth = "C:/Users/hairui/Desktop/其他/demo.txt";
            bufferedReader = new BufferedReader(new InputStreamReader(new BufferedInputStream(new FileInputStream(new File(oldpth)))));
            String readLine = null;
            while ((readLine=bufferedReader.readLine())!=null){
                System.out.println(readLine);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e1) {
                }
            }
        }
    }
    void ioTestWrite() {
        BufferedWriter bufferedWriter =null;
        try {
            String oldpth = "C:/Users/hairui/Desktop/其他/demo.txt";
            bufferedWriter = new BufferedWriter(new FileWriter(oldpth));
            bufferedWriter.write("海瑞测试");
            bufferedWriter.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bufferedWriter != null) {
                try {
                    bufferedWriter.close();
                } catch (IOException e1) {
                }
            }
        }
    }

    public static class Fibonacci extends RecursiveTask<Integer> {

        private int n;
        public Fibonacci(int n) {
            this.n = n;
        }
        @Override
        protected Integer compute() {
            if (n <= 1){
                return n;
            }
            Fibonacci f1 = new Fibonacci(n - 1);
            f1.fork();
            Fibonacci f2 = new Fibonacci(n - 2);
            f2.fork();
            return f1.join() + f2.join();
        }

        public static void main(String[] args) throws ExecutionException, InterruptedException {
            ForkJoinPool pool = new ForkJoinPool();
            for (int i = 0; i< 10; i++) {
                ForkJoinTask task = pool.submit(new Fibonacci(i));
                System.out.println(task.get());
            }
        }
    }





}

