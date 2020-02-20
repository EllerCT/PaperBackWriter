package data_structures;

public enum ResourceType {
    PAPER("Paper"),
    THREAD("Thread"),
    GLUE("Glue"),
    BOARD("Board"),
    DECORATED_PAPER("Decorated Paper"),
    SPINE("Spine"),
    END_BAND("End Band"),
    OTHER("Other"),
    MINERAL_SPIRIT("Mineral Spirit");

    private String name;

    ResourceType(String name) {
        this.name = name;
    }

    public static ResourceType parseString(String text) {
        for (ResourceType type : ResourceType.values()) {
            if (type.toString().equalsIgnoreCase(text)) {
                return type;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return name;
    }
}
