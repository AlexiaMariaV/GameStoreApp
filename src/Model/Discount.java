package Model;

public class Discount implements HasId{
    private Integer discountId;
    private float discountPercentage;

    public Discount(Integer discountId, float discountPercentage) {
        this.discountId = discountId;
        this.discountPercentage = discountPercentage;
    }

    public Integer getDiscountId() {
        return discountId;
    }

    public void setDiscountId(Integer discountId) {
        this.discountId = discountId;
    }

    public float getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(float discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    @Override
    public String toString() {
        return "Discount{" +
                "discountId=" + discountId +
                ", discountPercentage=" + discountPercentage +
                '}';
    }

    @Override
    public Integer getId() {
        return discountId;
    }
}
