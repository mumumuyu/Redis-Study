package com.lgd;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

public class ErgodicFiles {
    /*
     *下面说的都是IO
     *这里稍微说一下，字节流字符流，
     *字节有8个二进制位组成，计算机里面储存的任何东西，都是以二进制位保存的，所以，任何文件，都可以用字节流来保存操作。
     *字节流，InputStream ,OutputStream
     *但是，如果明确知道这个文件，属于文本文件，并且对这个文本可能进行操作，读取行，写入行等，进行行级操作的时候，用字符流。
     *字符流Reader ,Writer
     *
     *关于NIO的正在研究，
     *IO：流
     *NIO：缓冲区
     */
    static List<File> javalsf = new ArrayList<File>();
    static List<File> lsf = new ArrayList<File>();
    static int javaFileLinenum = 0;

    TreeSet<String> nums = new TreeSet<String>();

    public static void main(String[] args) throws Exception {

        File fir = new File("F:\\IDE\\Easyweb-boke\\src");
        FileOutputStream fio = null;
        try {
            //1、
            //字节流输入文件
            getJavaFiles(fir,lsf);
            fio = new FileOutputStream(new File("/testNumbers.txt"));
            String javastr = "java文件个数是："+javalsf.size()+"个";
            String str = "其他文件个数是："+lsf.size()+"个";
            //输入到文件中。
            fio.write(javastr.getBytes());

            //FileOutputStream 换行
            fio.write("\r\n".getBytes());

            //继续输出文件
            fio.write(str.getBytes());

            fio.write("\r\n".getBytes());
            fio.write("\r\n".getBytes());
            if(javalsf.size()>0){
                for(File javafir : javalsf){
                    getJavaFileNum(javafir);
                }
            }
            String numstr = "html代码的行数是："+javaFileLinenum;
            fio.write(numstr.getBytes());

            //2、
            //字符流输入文件
            //FileWriter fw = new FileWriter("w:/zfAllFile.txt");


        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            if(fio!=null){
                fio.close();
            }
        }
    }


    //循环路径下所有的文件。
    public static List<File> getJavaFiles (File fir, List<File> listfile){

        //判断文件是否存在
        if (!fir.exists()){
            System.out.println("文件名称不存在!");
        }else{
            //判断是否是文件
            if (fir.isFile()){
                //如果当前fir是文件，则判断是什么文件
                String firName = fir.getName();//获取文件名

                //System.err.println(firName.hashCode());  获取文件后缀名
                if(firName.substring(firName.lastIndexOf(".")+1) .equals("java")){
                    javalsf.add(fir);
                }else{
                    lsf.add(fir);
                }
            } else{
                //如果当前fir是文件夹，则遍历整个文件夹为File数组，继续循环遍历
                File[] files = fir.listFiles();
                for (int i = 0; i < files.length; i++  ){
                    getJavaFiles(files[i], listfile);
                }
            }
        }

        return lsf;
    }

    //查看java文件中的代码数量
    public static void getJavaFileNum(File fir){
        int num = 0 ;
        try {
            //不能指定编码格式，会出现乱码问题
            //FileReader reader = new FileReader(new File("w:/test.java"));

            System.out.println("查看的文件是："+fir.getName());
            System.out.println("当前文件的路径是"+fir.getPath());
            //可以指定编码格式。
            InputStreamReader isr=new InputStreamReader(new FileInputStream(fir),"GBK");
            BufferedReader br = new BufferedReader(isr);
            String str = null;
            while ((str = br.readLine()) != null) {
                if(!str.startsWith("//")&&!str.equals("")){
                    num ++;
                }
            }
            System.out.println("num的数量是："+num);
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        javaFileLinenum += num;
        System.out.println("java代码的总量是："+javaFileLinenum);
    }
}
