package data_structures;

public class PinNumber {
    private String pin;
    private static int maxSize = 4;

    public PinNumber(String pin){
        if (pin.length() == maxSize){
            this.pin=pin;
        } else if (pin.length() > maxSize) {
            this.pin = pin.substring(0,maxSize-1);
        } else{
            this.pin = pin.concat("0".repeat(maxSize - pin.length()));
        }
    }

    @Override
    public String toString() {
        return this.pin;
    }

    @Override
    public boolean equals(Object obj) {
        return obj.getClass() == this.getClass() && obj.toString().equalsIgnoreCase(this.toString());
    }

    @Override
    public int hashCode() {
        return this.pin.hashCode() * maxSize;
    }
}
