package za.ac.up.model;

public enum ChartTypes {
    BAR,
    LINE,
    PIE,
    SCATTER;

    public static ChartTypes fromString(String string)
    {
        for(ChartTypes delimiter :values())
        {
            if(delimiter.name().equals(string))
            {
                return delimiter;
            }
        }

        return null;
    }
}
