package za.ac.up.model;

public enum Metric {
    CPU,
    MEMORY;

    public static Metric fromString(String string)
    {
        for(Metric delimiter :values())
        {
            if(delimiter.name().equals(string))
            {
                return delimiter;
            }
        }

        return null;
    }
}
