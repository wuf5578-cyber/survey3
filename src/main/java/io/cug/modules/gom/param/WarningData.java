package io.cug.modules.gom.param;

import lombok.Data;

@Data
public class WarningData {
    private int index;
    private String objectName;
    private String param1;
    private String param2;

    // Constructors, getters, and setters

    // Example constructor
    public WarningData(int index, String objectName, String param1, String param2) {
        this.index = index;
        this.objectName = objectName;
        this.param1 = param1;
        this.param2 = param2;
    }

    public WarningData(){
        this.index = 0;
        this.objectName = "";
        this.param1 = "";
        this.param2 = "";
    }
}
