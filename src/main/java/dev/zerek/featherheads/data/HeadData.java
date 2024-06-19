package dev.zerek.featherheads.data;

public class HeadData {
    private final String name;
    private final String sound;
    private final String headHash;

    public HeadData(String name, String sound, String headHash) {
        this.name = name;
        this.sound = sound;
        this.headHash = headHash;
    }


    public String getName() {
        return name;
    }
    public String getSound() {
        return sound;
    }
    public String getHeadHash() {
        return headHash;
    }
}
