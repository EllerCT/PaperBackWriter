package data_structures;

public enum ResourceType {
    PAPER("Paper"),
    THREAD("Thread"),
    GLUE("Glue"),
    BOARD("Board"),
    DECORATED_PAPER("Decorated Paper"),
    SPINE("Spine"),
    END_BAND("End Band");

    private String name;

    ResourceType(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
