package za.ac.up.model;

public enum FileTypes {

    PDF("PDF", ".pdf"),
    PNG("PNG", ".png"),
    HTML("HTML", ".html");

    private final String fileType;
    private final String extension;

    private FileTypes(String fileType, String extension) {
        this.fileType = fileType;
        this.extension = extension;
    }

    public static FileTypes byFileType(String fileType) {
        for (FileTypes fileTypes : values()) {
            if (fileTypes.fileType.equalsIgnoreCase(fileType)) {
                return fileTypes;
            }
        }
        return null;
    }
}
