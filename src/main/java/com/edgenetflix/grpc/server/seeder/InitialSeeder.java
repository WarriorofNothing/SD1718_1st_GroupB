package com.edgenetflix.grpc.server.seeder;

import com.edgenetflix.grpc.server.services.SeederServiceImpl;
import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class InitialSeeder {
    private int port;
    private File file;
    private long max_chunks_size; //Size in bytes
    private String file_hash;
    private Server server;      //Listening to requests of chunks

    private List<Chunk> chunks;

    public InitialSeeder(int port, File file, String file_hash, int max_chunks_size) {
        this.port = port;
        this.file = file;
        this.max_chunks_size = max_chunks_size * 1024 * 1204;
        this.file_hash = file_hash;

        try {
            this.chunks = splitInChunks();

        } catch (IOException| NoSuchAlgorithmException e){
            System.out.println(e.getMessage());
        }
    }

    public void startSeeding() throws IOException, InterruptedException{
        server = ServerBuilder.forPort(port)
                .addService(new SeederServiceImpl(this))
                .build();

        server.start();
        System.out.println("$InitialSeeder[127.0.0.1:"+ port +"]$ - Seeding file " + file.getName());

        server.awaitTermination();
    }

    public void stopSeeding(){
        server.shutdown();
        System.out.println("$InitialSeeder[127.0.0.1:"+ port +"]$ - Stopped seeding file " + file.getName());
    }

    public boolean isSeeding(){
        return server != null && !server.isShutdown();
    }

    private List<Chunk> splitInChunks() throws NoSuchAlgorithmException, IOException{
        List<Chunk> chunksList = new ArrayList<>();
        MessageDigest md = MessageDigest.getInstance("SHA-256");

        byte[] chunk_buffer = new byte[(int) max_chunks_size];

        FileInputStream fis = new FileInputStream(file);
        BufferedInputStream bis = new BufferedInputStream(fis);

        int read_bytes;
        int index = 1;
        while((read_bytes = bis.read(chunk_buffer)) != -1){
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            baos.write(chunk_buffer,0, read_bytes);
            md.update(chunk_buffer, 0, read_bytes);

            byte[] mdbytes = md.digest();

            //convert the byte to hex format
            StringBuilder hex_hash = new StringBuilder();
            for (int i = 0; i<mdbytes.length;i++) {
                hex_hash.append(Integer.toHexString(0xFF & mdbytes[i]));
            }

            chunksList.add(new Chunk(baos.toByteArray(), hex_hash.toString(),index));

            baos.close();
            index++;
        }

        fis.close();
        bis.close();

        return chunksList;
    }

    public List<Chunk> getChunks() {
        return chunks;
    }

    public int getPort() {
        return port;
    }

    public String getFileHash() {
        return file_hash;
    }

    public File getFile() {
        return file;
    }

    public long getChunkSize() {
        return max_chunks_size;
    }

    public void printForTest(){
        System.out.println("File name: " + file.getName());
        System.out.println("File length: " + file.length());
        System.out.println("File hash: " + file_hash);
        System.out.println("Chunks size: " + max_chunks_size);
        System.out.println("Chunks list: ");
        for (Chunk c : chunks){
            System.out.println("Chunk with length: " + c.getBytes().length + " and the hash: " + c.getHash());
        }
    }
}
