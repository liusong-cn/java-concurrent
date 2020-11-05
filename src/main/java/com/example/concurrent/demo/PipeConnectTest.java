package com.example.concurrent.demo;

import java.io.IOException;
import java.io.PipedReader;
import java.io.PipedWriter;

/**
 * @author:ls
 * @date: 2020/11/5 19:07
 * 线程之间使用pipedreader writer通信，同理可有pipedinputstream outputstream
 * 本例使用reader来接收writer的输出并打印
 * 适用于同一进程内多个线程之间
 **/
public class PipeConnectTest {

    static class WriterThread implements Runnable{

        private PipedWriter writer;

        public WriterThread(PipedWriter writer) {
            this.writer = writer;
        }

        @Override
        public void run() {
            System.out.println("这是write");
            try {
                writer.write("test");
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                try {
                    //记得关闭输出流，否则报错
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    static class ReaderThread implements Runnable{

        private PipedReader reader;

        public ReaderThread(PipedReader reader) {
            this.reader = reader;
        }

        @Override
        public void run() {
            System.out.println("reader");
            int rec = 0;

            while (rec > -1) {
                try {
                    if ((rec = reader.read()) != -1) {
                        System.out.print((char)rec);
                    };
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }



        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        PipedWriter writer = new PipedWriter();
        PipedReader reader = new PipedReader();
        //使用connect将输出连接到reader，必备
        writer.connect(reader);
        new Thread(new ReaderThread(reader)).start();
        Thread.sleep(1000);
        new Thread(new WriterThread(writer)).start();
    }
}
