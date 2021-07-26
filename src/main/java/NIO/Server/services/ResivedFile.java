package NIO.Server.services;

public class ResivedFile {
    private String name;
    private long size;
    private String path;
    private String atributeas;

    public ResivedFile(String name, long size, String path) {
        this.name = name;
        this.size = size;
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getAtributeas() {
        return atributeas;
    }

    public void setAtributeas(String atributeas) {
        this.atributeas = atributeas;
    }
}
