package com.edgenetflix.rest.db;


import com.edgenetflix.grpc.DownloadSeviceGrpc;
import com.edgenetflix.grpc.SeederList;
import com.edgenetflix.grpc.SeederRequest;
import com.edgenetflix.grpc.SeederResponse;
import com.edgenetflix.grpc.Void;
import com.google.protobuf.ProtocolStringList;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 *The Tracker keeps track of the information about the seeder and the peers (leechers) for each file
 */
public class Tracker {
    private static Tracker tracker = new Tracker();
    private HashMap<String,List<Leecher>> leechers;
    private HashMap<String, Seeder> seeders;

    public static Tracker getInstance() {
        return tracker;
    }


    private Tracker() {
        this.leechers = new HashMap<>();
        this.seeders = new HashMap<>();
    }

    /**
     * @param file  File name
     * @param new_leecher  The leecher to be indexed
     *If exists peers for a file, just add this new peer to the list
     * Otherwise, create a new list with the new peer and update the mapping for the file
     */
    public void indexLeecher(String file, Leecher new_leecher){
        if(leechers.containsKey(file)){
            List<Leecher> leechersList = leechers.get(file);
            leechersList.add(new_leecher);

            leechers.put(file,leechersList);
        }
        else{
            List<Leecher> peersList = new ArrayList<Leecher>();
            peersList.add(new_leecher);

            leechers.put(file,peersList);
        }

        System.out.println("$Tracker$ - New peer[" + new_leecher.getIp() +":" +new_leecher.getPort() + "] downloading the file: " + file);
    }

    public void updateLeecher(String file, String id, int chunk){
        List<Leecher> leechersList = leechers.get(file);

        for(Leecher leecher : leechersList) {
            if(leecher.getId().equals(id)) {
                leecher.getChunksSet().add(chunk);
                break;
            }
        }
    }

    /**
     * @param file  File name
     * @param id  Client id
     * @return
     * If there is a mapping for the file, remove the leecher form the list:
     *      If the lists is now empty, remove the mapping for the file and return true, meaning that the seeder should be closed
     *      Otherwise, just associate the updated list with the file and return false
     * Otherwise just return false, meaning that there's no procedure to execute.
     */
    public boolean removeLeecher(String file, String id){
        if(leechers.containsKey(file)){
            List<Leecher> leechersList = leechers.get(file);

            for(Leecher leecher : leechersList) {
                if(leecher.getId().equals(id)) {
                    leechersList.remove(leecher);
                    break;
                }
            }

            if(leechersList.size() == 0){
                leechers.remove(file);
                seederShutdown(file);
                return true;
            }
            else {
                leechers.put(file, leechersList);

                return false;
            }
        }
        else return false;
    }


    private void registerSeeder(String file, Seeder seeder){
        seeders.put(file, seeder);
        System.out.println("$Tracker$ - Seeder[" + seeder.getIp() + ":" + seeder.getPort() + "] registered to start seeding the file: " + file);
    }

    private void removeSeeder(String file){
        Seeder removed = seeders.get(file);
        seeders.remove(file);
        System.out.println("$Tracker$ - Seeder[" + removed.getIp() + ":" + removed.getPort() + "] removed. Therefore, the seeder is no longer seeding the file: " + file);
    }

    public String getChunkInfo(String file, String leecher_id, int chunk_index){
        List<Leecher> leechersList = leechers.get(file);
        List<Leecher> leechersWithChunk = new ArrayList<>();

        for (Leecher leecher : leechersList){
            if(!leecher.getId().equals(leecher_id)){
                if(leecher.getChunksSet().contains(chunk_index))
                    leechersWithChunk.add(leecher);
            }
        }
        String seederJson = seeders.get(file).toString();

        StringBuilder leechersJsonArray = new StringBuilder("[ ");

        for (Leecher l : leechersWithChunk) {
            leechersJsonArray.append(l.toString());
            leechersJsonArray.append(", ");
        }

        if(!leechersWithChunk.isEmpty())
            leechersJsonArray.deleteCharAt(leechersJsonArray.length() - 2);     //Remove the last comma

        leechersJsonArray.append("]");

        return "{ \"seeder\": " + seederJson + ", "
                + "\"leechers\": " + leechersJsonArray.toString() + " }";
    }

    public String getSeedMetadata(String file) throws InterruptedException{
        if(!seeders.containsKey(file)) {
            seederRequest(file);
        }

        Seeder reg_seeder = seeders.get(file);
        long chunkLength = reg_seeder.getChunkLength();
        List<String> hashsList = reg_seeder.getChunkHashList();

        StringBuilder hashJsonArray = new StringBuilder("[ ");

        int index = 1;
        for (String h : hashsList) {
            String element = "{ \"" + index + "\": " + h + " }, ";
            hashJsonArray.append(element);

            index++;
        }

        if(!hashsList.isEmpty())
            hashJsonArray.deleteCharAt(hashJsonArray.length() - 2);     //Remove the last comma

        hashJsonArray.append("]");

        return "{ \"fileLength\": " + reg_seeder.getSeedLength() + ", "
                + "\"fileHash\": " + reg_seeder.getSeedHash() + ", "
                + "\"chunkLength\": " + chunkLength + ", "
                + "\"numberOfChunks\": " + hashsList.size() + ", "
                + "\"hashList\": " + hashJsonArray.toString() + " }";
    }

    /**
     * @param file File name
     * Communicate with the Seeder Server that gives the information about the seeder that is seeding the chunks of the file.
     * Thence it register that seeder
     */
    private void seederRequest(String file) throws InterruptedException{
        ManagedChannel channel = ManagedChannelBuilder.forAddress("35.184.72.242", 8081)
                .usePlaintext()
                .build();

        DownloadSeviceGrpc.DownloadSeviceBlockingStub stub = DownloadSeviceGrpc.newBlockingStub(channel);

        Iterator<SeederResponse> response = stub.requestForSeeder(SeederRequest.newBuilder()
                .setFileName(file)
                .build());

        while (response.hasNext()){
            String ip = response.next().getIp();
            long port = response.next().getPort();
            long seed_length = response.next().getFileLength();
            String seed_hash = response.next().getFileHash();
            long chunkLength = response.next().getChunkLegth();
            ProtocolStringList list = response.next().getChunkHashList();
            List<String> hash_list = list.subList(0,list.size());

            registerSeeder(file, new Seeder(ip, port, seed_length, seed_hash, chunkLength, hash_list));
        }

        channel.shutdown();
    }

    /**
     * Sends a message to shutdown the seeder of that file. The Tracker also removes the seeder from the list of registered seeders.
     * @param file File name
     */

    private void seederShutdown(String file){
        ManagedChannel channel = ManagedChannelBuilder.forAddress("35.184.72.242", 8081)
                .usePlaintext()
                .build();

        DownloadSeviceGrpc.DownloadSeviceBlockingStub stub = DownloadSeviceGrpc.newBlockingStub(channel);

        Void without_response = stub.shutdownSeeder(SeederRequest.newBuilder()
                .setFileName(file)
                .build());

        removeSeeder(file);

        channel.shutdown();
    }

    public String seederList(){
        ManagedChannel channel = ManagedChannelBuilder.forAddress("35.184.72.242", 8081)
                .usePlaintext()
                .build();

        DownloadSeviceGrpc.DownloadSeviceBlockingStub stub = DownloadSeviceGrpc.newBlockingStub(channel);

        Iterator<SeederList> response = stub.getSeederList(Void.newBuilder()
                .build());

        StringBuilder seederJsonArray = new StringBuilder("[ ");

        while (response.hasNext()) {
            ProtocolStringList list = response.next().getSeederList();
            List<String> seederList = list.subList(0,list.size());


            for (String s : seederList) {
                seederJsonArray.append(s);
                seederJsonArray.append(", ");
            }

            if(!seederList.isEmpty())
                seederJsonArray.deleteCharAt(seederJsonArray.length() - 2);     //Remove the last comma

            seederJsonArray.append("]");
        }

        return "{ \"seederList\": " + seederJsonArray.toString() + " }";
    }
}
