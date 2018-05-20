package za.ac.up.model;

public class ReportFile {

    private String encodedFile;

    public ReportFile() {
    }

    public ReportFile(String encodedFile) {

        this.encodedFile = encodedFile;
    }

    public String getEncodedFile() {
        return encodedFile;
    }

    public void setEncodedFile(String encodedFile) {
        this.encodedFile = encodedFile;
    }
}
