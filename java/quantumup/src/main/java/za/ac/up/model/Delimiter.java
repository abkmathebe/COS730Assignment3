package za.ac.up.model;

public enum Delimiter {
    COMMA(","),
    TAB("\t"),
    SEMICOLON(";"),
    SPACE(" ");

    private String del;

    Delimiter(String del)
    {
        this.del = del;
    }

    public String getDelimiter()
    {
        return this.del;
    }

    public static Delimiter fromString(String string)
    {
        for(Delimiter delimiter :values())
        {
            if(delimiter.name().equals(string))
            {
                return delimiter;
            }
        }

        return null;
    }

}
