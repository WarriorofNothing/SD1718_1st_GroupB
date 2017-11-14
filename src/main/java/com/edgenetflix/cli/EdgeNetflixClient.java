package com.edgenetflix.cli;

import com.edgenetflix.cli.json.Peer;
import com.edgenetflix.cli.json.Response;
import com.edgenetflix.cli.rest.ConnectionService;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

import com.edgenetflix.cli.service.LeecherServiceImpl;
import com.edgenetflix.grpc.ChunkRequest;
import com.edgenetflix.grpc.ChunkResponse;
import com.edgenetflix.grpc.DownloadSeviceGrpc;
import com.google.protobuf.ByteString;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.apache.commons.cli.*;

public class EdgeNetflixClient {
    private final static String CLIENT_ID = createId();
    private final static String CLIENT_IP = "127.0.0.1";
    private final static int CLIENT_PORT = setPort();

    private HashMap<String, Thread> downloadingFiles = new HashMap<>();

    public static void main(String[] args) throws Exception{

        EdgeNetflixClient cli = new EdgeNetflixClient();
        cli.start();
    }

    private void start() throws Exception{
        Scanner in = new Scanner(System.in);
        Options options = new Options();
        options.addOption("h", "help", false, "help to use EdgeNetflix");
        options.addOption("e", "exit", false , "exit EdgeNetflix");
        Option downloadOption = new Option("d", "download", true, "download a file" );
        downloadOption.setArgName("'file name'");
        downloadOption.setOptionalArg(true);
        options.addOption(downloadOption);
        Option playOption = new Option("p", "play", true, "play a file" );
        playOption.setArgName("'file name'");
        playOption.setOptionalArg(true);
        options.addOption(playOption);
        Option listOption = new Option("l", "list", true, "list the files or seeders" );
        listOption.setArgName("(files | seeders)");
        listOption.setOptionalArg(true);
        options.addOption(listOption);
        Option infoOption = new Option("in", "info", true, "shows info of the file");
        infoOption.setArgName("'file name'");
        infoOption.setOptionalArg(true);
        options.addOption(infoOption);

        System.out.println("Port in use: " + CLIENT_PORT);
        System.out.println("Welcome to EdgeNetflix");
        System.out.println("If it's your first time here type -help for help.");

        String[] args = getCommandLine(in);
        CommandLine command = new DefaultParser().parse(options, args);
        while(!command.hasOption("exit")) {

            if(command.getArgs().length == 0) {
                if (command.hasOption("download")) {
                    if(command.getOptionValue("download") == null)
                        System.out.println("Missing argument: type -help for Help. ");
                    else {
                        download(command.getOptionValue("download"));
                    }

                } else if (command.hasOption("help")) {
                    for (Option o : options.getOptions()) {
                        if (o.hasArg()) {
                            System.out.println("    -" + o.getLongOpt() + " " + o.getArgName() + ": " + o.getDescription());
                        } else
                            System.out.println("    -" + o.getLongOpt() + ": " + o.getDescription());
                    }
                } else if (command.hasOption("list")) {
                    if(command.getOptionValue("list") == null)
                        System.out.println("Missing argument: type -help for Help. ");
                    else {
                        if (command.getOptionValue("list").equals("seeders"))
                            listSeeders();
                        else if (command.getOptionValue("list").equals("files"))
                            listFiles();
                        else {
                            System.out.println("Unknown argument: type -help for help.");
                        }
                    }
                } else if(command.hasOption("play")){
                    if(command.getOptionValue("play") == null)
                        System.out.println("Missing argument: type -help for Help. ");
                    else {
                        play(command.getOptionValue("play"));
                    }

                } else if(command.hasOption("info")) {
                    if (command.getOptionValue("info") == null)
                        System.out.println("Missing argument: type -help for Help. ");
                    else {
                        infoFile();
                    }
                }
                else {
                    System.out.println("Unknown command: type -help for Help.");
                }

            }
            else System.out.println("Unknown command: type -help for Help.");

            args = getCommandLine(in);
            command = new DefaultParser().parse(options, args);
        }
    }

    private static int setPort() {
        int port = 8085;

        while(!isPortAvailable(port)){
            //System.out.println(port);
            port++;
        }

        return port;
    }

    private static boolean isPortAvailable(int port){

        boolean result = false;

        try {
            ServerSocket reserved_port = new ServerSocket(port);
            result = true;
        }
        catch(IOException e) {
            // Could not connect.
            //System.out.println(e.getMessage());
        }

        return result;
    }

    private String[] getCommandLine(Scanner in){
        System.out.print("$ ");
        String line = in.nextLine();
        return line.split(" ");
    }

    private static String createId() {
        return UUID.randomUUID().toString();
    }

    /**
     *
     * @param chunk_index   Sequence number of the chunk
     * @param peerList      List of peers
     * @return              If a peer has the chunk available it returns that peer, otherwise it returns null, meaning that there is no peers that are
     * leeching the file. Therefore the client should request another 'chunkInfo' to that chunk.
     */
    private Peer consultPeerList(int chunk_index, List<Peer> peerList){
        for(Peer p : peerList){
            if(p.getAvailableChunks().contains(chunk_index))
                return p;
        }

        return null;
    }

    /**
     *
     * @param receivedChunks    Hash map where each chunk is associated with its sequence number (key)
     * @param number_of_chunks  Number of chunks that the file was divided into. Announced in the metadata
     * @return  If there is a missing chunk, it generates a random index from a list of missing chunks nad returns that index. Otherwise if the client has all the
     * chunks, it returns a sequence number (index) out of the possible sequences number that a chunk can be associated to.
     */
    private int randomIndexChoice(HashMap<Integer, byte[]> receivedChunks, int number_of_chunks){
        List<Integer> missingChunks = new ArrayList<>();
        for(int i=1;i<=number_of_chunks;i++)
            if(!receivedChunks.containsKey(i))
                missingChunks.add(i);
        if(missingChunks.size() > 0) {
            int random_index = new Random().nextInt(missingChunks.size());
            return missingChunks.get(random_index);
        }
        else{
            return number_of_chunks + 1;
        }
    }

    /**
     * @param announced_hash    Hash announced by the Tracker
     * @param chunk             Chunk received
     * @return                  True if the announced hash is the same as the hash computed to the chunk. Otherwise return false
     */
    private boolean checkChunkHash(String announced_hash, byte[] chunk) throws NoSuchAlgorithmException{
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(chunk, 0, chunk.length);

        byte[] mdbytes = md.digest();

        StringBuilder hex_hash = new StringBuilder();
        for (int i = 0; i<mdbytes.length;i++) {
            hex_hash.append(Integer.toHexString(0xFF & mdbytes[i]));
        }

        return announced_hash.equals(hex_hash.toString());
    }

    /**
     * @param file File name
     * First of all the client requests the metadata of that file. With the metadata the client knows the number of chunks that he has to request, as well
     * as the hash of each chunk.
     * Then the client starts to request chunks randomly.
     */
    private void download(String file){
        Thread download_thread = new Thread( () -> {

            try {
                ConnectionService service = new ConnectionService("http://104.198.245.139:8080/home");

                Response metadata = service.getMetadata(file, CLIENT_ID,CLIENT_IP, CLIENT_PORT);
                //metadata.printMetainfo();

                HashMap<Integer, byte[]> receivedChunks = new HashMap<>();
                Server server = ServerBuilder.forPort(CLIENT_PORT)
                        .addService(new LeecherServiceImpl(receivedChunks))
                        .build();

                server.start();

                int bytes_received = 0;
                int next_chunk = randomIndexChoice(receivedChunks, metadata.getNumberOfhunks());
                Response chunkInfo = service.getChunkInfo(file, CLIENT_ID, next_chunk);
                while (receivedChunks.size() < metadata.getNumberOfhunks()) {
                    Peer peerToGetChunk = consultPeerList(next_chunk, chunkInfo.getPeers());
                    if (peerToGetChunk == null)
                        peerToGetChunk = chunkInfo.getSeeder();

                    ManagedChannel channel = ManagedChannelBuilder.forAddress(peerToGetChunk.getIp(), peerToGetChunk.getPort())
                            .usePlaintext(true)
                            .build();
                    DownloadSeviceGrpc.DownloadSeviceBlockingStub stub = DownloadSeviceGrpc.newBlockingStub(channel);
                    Iterator<ChunkResponse> chunkData = stub.getChunk(ChunkRequest.newBuilder()
                            .setChunkIndex(next_chunk)
                            .build());

                    while (chunkData.hasNext()) {

                        ByteString chunk = chunkData.next().getChunk();
                        byte[] received_chunk = chunk.toByteArray();
                        bytes_received = bytes_received + received_chunk.length;

                        //System.out.format("Downloading file... %.2f%s %n", (double) bytes_received / metadata.getFileLength() * 100, "%");
                        System.out.println("Chunk (" + next_chunk + ") downloaded from: " + peerToGetChunk.getIp() + ":" + peerToGetChunk.getPort());

                        int sequence_number = chunkData.next().getSeqNumber();
                        long payload = chunkData.next().getPayload();

                        if (checkChunkHash(metadata.getHashList().get(next_chunk - 1), received_chunk)) {
                            receivedChunks.put(next_chunk, received_chunk);
                            service.sendAck(file, CLIENT_ID, next_chunk, false);
                        }
                    }

                    channel.shutdown();

                    next_chunk = randomIndexChoice(receivedChunks, metadata.getNumberOfhunks());
                    chunkInfo = service.getChunkInfo(file, CLIENT_ID, next_chunk);
                }

                service.sendAck(file, CLIENT_ID, next_chunk, true);
                server.shutdown();

                File downloaded_file = new File("/home/alberto/Desktop/Stuff/EdgeNetflix/src/main/java/com/edgenetflix/cli/downloads/" + file + ".mp4");
                FileOutputStream fos = new FileOutputStream(downloaded_file);
                BufferedOutputStream bos = new BufferedOutputStream(fos);

                for (int seq = 1; seq <= receivedChunks.size(); seq++) {
                    byte[] chunk = receivedChunks.get(seq);
                    bos.write(chunk, 0, chunk.length);
                }

                fos.close();
                bos.close();

                System.out.println("File " + file + " downloaded!");
                System.out.print("$ ");
                downloadingFiles.remove(file);
            } catch(Exception e){
                System.out.println(e.getMessage());
            }
        });

        downloadingFiles.put(file, download_thread);
        download_thread.start();
    }

    private void play(String file_name){
        String path = "/home/alberto/Desktop/Stuff/EdgeNetflix/src/main/java/com/edgenetflix/cli/downloads";

        File[] files = new File(path).listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.isFile() && file.getName().equals(file_name)) {
                    new Thread(() -> {
                        ProcessBuilder b = new ProcessBuilder("/usr/bin/xplayer","/home/alberto/Desktop/Stuff/EdgeNetflix/src/main/java/com/edgenetflix/cli/downloads/" + file.getName());
                        try {
                            b.start();

                        } catch (IOException e) {
                            System.out.println(e.getMessage());
                        }
                    }).start();
                }
            }
        } else System.out.println("File not found! Try to download it first.");
    }

    private void listFiles(){
        String path = "/home/alberto/Desktop/Stuff/EdgeNetflix/src/main/java/com/edgenetflix/cli/downloads/";

        File[] files = new File(path).listFiles();

        if (files != null) {
            for(File file : files){
                if(file.isFile())
                    System.out.println("    " + file.getName());
            }
        }

        for(String file_name : downloadingFiles.keySet()){
            System.out.println("    " + file_name + " ( Downloading... )");
        }
    }

    private void listSeeders() throws Exception{
        ConnectionService service = new ConnectionService("http://104.198.245.139:8080/home");

        service.getList();
    }



    private void infoFile(){

        String path = "/home/alberto/Desktop/Stuff/EdgeNetflix/src/main/java/com/edgenetflix/cli/downloads/";

        File[] files = new File(path).listFiles();
        if (files != null) {
            for(File file : files){
                if(file.isFile()){
                    System.out.println("File name: " + file.getName());
                    System.out.println("Full path: " + file.getPath());
                    System.out.println("File size: " + bytesToMeg(file.length()) + "Mb");
                }
            }
        }

    }

    private static final long  MEGABYTE = 1024L * 1024L;

    private static long bytesToMeg(long bytes) {
        return bytes / MEGABYTE ;
    }
}
