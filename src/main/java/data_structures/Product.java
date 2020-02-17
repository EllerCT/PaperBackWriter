package data_structures;

@Deprecated
public class Product {
    private static String currentID = "0";
    private String id;
    private String name;
    private String type;
    private String date;
    private String description;
    private String grade;
    private String totalCost;
    private String paperType;
    private String paperAmount;
    private String paperCost;
    private String threadType;
    private String threadAmount;
    private String threadCost;
    private String glueType;
    private String glueAmount;
    private String glueCost;
    private String boardType;
    private String boardAmount;
    private String boardCost;
    private String decoratedPaperType;
    private String decoratedPaperAmount;
    private String decoratedPaperCost;
    private String spineType;
    private String spineAmount;
    private String spineCost;
    private String endBandType;
    private String endBandAmount;
    private String endBandCost;
    private String other;
    private String otherAmount;
    private String otherCost;
    private String spiritType;
    private String spiritAmount;
    private String spiritCost;

    public static String getCurrentID() {
        return currentID;
    }

    public static void setCurrentID(String current) {
        currentID = current;
    }

    public String getSpiritCost() {
        return spiritCost;
    }

    public void setSpiritCost(String spiritCost) {
        this.spiritCost = spiritCost;
    }

    public String getSpiritAmount() {
        return spiritAmount;
    }

    public void setSpiritAmount(String spiritAmount) {
        this.spiritAmount = spiritAmount;
    }

    public static String generateKeyFor(Product product) {
        return product.getId();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(String totalCost) {
        this.totalCost = totalCost;
    }

    public String getPaperType() {
        return paperType;
    }

    public void setPaperType(String paperType) {
        this.paperType = paperType;
    }

    public String getPaperAmount() {
        return paperAmount;
    }

    public void setPaperAmount(String paperAmount) {
        this.paperAmount = paperAmount;
    }

    public String getPaperCost() {
        return paperCost;
    }

    public void setPaperCost(String paperCost) {
        this.paperCost = paperCost;
    }

    public String getThreadType() {
        return threadType;
    }

    public void setThreadType(String threadType) {
        this.threadType = threadType;
    }

    public String getThreadAmount() {
        return threadAmount;
    }

    public void setThreadAmount(String threadAmount) {
        this.threadAmount = threadAmount;
    }

    public String getThreadCost() {
        return threadCost;
    }

    public void setThreadCost(String threadCost) {
        this.threadCost = threadCost;
    }

    public String getGlueType() {
        return glueType;
    }

    public void setGlueType(String glueType) {
        this.glueType = glueType;
    }

    public String getGlueAmount() {
        return glueAmount;
    }

    public void setGlueAmount(String glueAmount) {
        this.glueAmount = glueAmount;
    }

    public String getGlueCost() {
        return glueCost;
    }

    public void setGlueCost(String glueCost) {
        this.glueCost = glueCost;
    }

    public String getBoardType() {
        return boardType;
    }

    public void setBoardType(String boardType) {
        this.boardType = boardType;
    }

    public String getBoardAmount() {
        return boardAmount;
    }

    public void setBoardAmount(String boardAmount) {
        this.boardAmount = boardAmount;
    }

    public String getBoardCost() {
        return boardCost;
    }

    public void setBoardCost(String boardCost) {
        this.boardCost = boardCost;
    }

    public String getDecoratedPaperType() {
        return decoratedPaperType;
    }

    public void setDecoratedPaperType(String decoratedPaperType) {
        this.decoratedPaperType = decoratedPaperType;
    }

    public String getDecoratedPaperAmount() {
        return decoratedPaperAmount;
    }

    public void setDecoratedPaperAmount(String decoratedPaperAmount) {
        this.decoratedPaperAmount = decoratedPaperAmount;
    }

    public String getDecoratedPaperCost() {
        return decoratedPaperCost;
    }

    public void setDecoratedPaperCost(String decoratedPaperCost) {
        this.decoratedPaperCost = decoratedPaperCost;
    }

    public String getSpineType() {
        return spineType;
    }

    public void setSpineType(String spineType) {
        this.spineType = spineType;
    }

    public String getSpineAmount() {
        return spineAmount;
    }

    public void setSpineAmount(String spineAmount) {
        this.spineAmount = spineAmount;
    }

    public String getSpineCost() {
        return spineCost;
    }

    public void setSpineCost(String spineCost) {
        this.spineCost = spineCost;
    }

    public String getEndBandType() {
        return endBandType;
    }

    public void setEndBandType(String endBandType) {
        this.endBandType = endBandType;
    }

    public String getEndBandAmount() {
        return endBandAmount;
    }

    public void setEndBandAmount(String endBandAmount) {
        this.endBandAmount = endBandAmount;
    }

    public String getEndBandCost() {
        return endBandCost;
    }

    public void setEndBandCost(String endBandCost) {
        this.endBandCost = endBandCost;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

    public String getOtherAmount() {
        return otherAmount;
    }

    public void setOtherAmount(String otherAmount) {
        this.otherAmount = otherAmount;
    }

    public String getOtherCost() {
        return otherCost;
    }

    public void setOtherCost(String otherCost) {
        this.otherCost = otherCost;
    }

    public String getSpiritType() {
        return this.spiritType;
    }

    public void setSpiritType(String spiritType) {
        this.spiritType = spiritType;
    }
}
