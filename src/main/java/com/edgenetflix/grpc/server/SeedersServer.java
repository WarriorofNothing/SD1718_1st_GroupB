package com.edgenetflix.grpc.server;

import com.edgenetflix.grpc.server.seeder.InitialSeeder;
import com.edgenetflix.grpc.server.services.ServerServiceImpl;
import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

public class SeedersServer {
    private HashMap<String, InitialSeeder> seeders;

    private SeedersServer(){
        seeders = new HashMap<String, InitialSeeder>();
    }

    static public void main(String[] args) throws IOException, InterruptedException, NoSuchAlgorithmException{
        SeedersServer ss = new SeedersServer();

        ss.bootstrapInitialSeeder("the.letter","/media/sd-files/The Letter.mp4", 8082, 1);
        ss.bootstrapInitialSeeder("popeye.ali.baba","/media/sd-files/Popeye AliBaba.mp4",8083, 1);
        ss.bootstrapInitialSeeder("night.of.the.lining.dead","/media/sd-files/Night of the Living Dead.mp4",8084, 2);
        ss.bootstrapInitialSeeder("the.vagabond","/media/sd-files/The Vagabond (10-07-1916).mp4",8085, 2);
        ss.runServer();
    }

    private void runServer()throws IOException, InterruptedException{
        Server server = ServerBuilder.forPort(8081)
                .addService(new ServerServiceImpl(seeders))
                .build();

        server.start();
        System.out.println("$Server[127.0.0.1:8081]$ - Running... ");

        server.awaitTermination();
    }

    /**
     *
     * @param file_path File Path of the file that is going to be seeded
     * @param port Port where the seeder is going to listen to chunk requests
     * @param max_chunks_size Size in Megabytes
     */
    private void bootstrapInitialSeeder(String file_name, String file_path, int port, int max_chunks_size) throws IOException, NoSuchAlgorithmException{
        File file = new File(file_path);

        MessageDigest md = MessageDigest.getInstance("SHA-256");

        byte[] chunk = new byte[max_chunks_size * 1024 * 1024];

        FileInputStream fis = new FileInputStream(file);
        BufferedInputStream bis = new BufferedInputStream(fis);

        int read_bytes;
        while((read_bytes = bis.read(chunk)) != -1){
            md.update(chunk, 0, read_bytes);
        }

        fis.close();
        bis.close();

        byte[] mdbytes = md.digest();

        StringBuilder hex_hash = new StringBuilder();
        for (int i=0;i<mdbytes.length;i++) {
            hex_hash.append(Integer.toHexString(0xFF & mdbytes[i]));
        }

        seeders.put(file_name, new InitialSeeder(port, file, hex_hash.toString(), max_chunks_size));
    }
}
