package Model;

public class PaymentMethod implements HasId{
    private Integer paymentId;
    private String paymentType;


    public PaymentMethod(Integer paymentId, String paymentType) {
        this.paymentId = paymentId;
        this.paymentType = paymentType;
    }

    public Integer getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Integer paymentId) {
        this.paymentId = paymentId;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    @Override
    public String toString() {
        return "PaymentMethod{" +
                "paymentId=" + paymentId +
                ", paymentType='" + paymentType + '\'' +
                '}';
    }

    @Override
    public Integer getId() {
        return paymentId;
    }


}
