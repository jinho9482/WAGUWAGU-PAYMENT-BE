package com.example.waguwagu_payment.domain.type;

public enum RiderTransportation {
    WALK(500), BICYCLE(800), MOTORBIKE(1300), CAR(1500);

    public final int costPerKm;

    private RiderTransportation(int costPerKm) {
        this.costPerKm = costPerKm;
    }

    public static int calculateDeliveryFeeByTransportation(RiderTransportation riderTransportation, double distanceFromStoreToCustomer) {
        int costByDistance;
        if (riderTransportation.equals(RiderTransportation.CAR)) costByDistance
                = (int) (distanceFromStoreToCustomer * RiderTransportation.CAR.costPerKm);
        else if (riderTransportation.equals(RiderTransportation.MOTORBIKE)) costByDistance
                = (int) (distanceFromStoreToCustomer * RiderTransportation.MOTORBIKE.costPerKm);
        else if (riderTransportation.equals(RiderTransportation.BICYCLE)) costByDistance
                = (int) (distanceFromStoreToCustomer * RiderTransportation.BICYCLE.costPerKm);
        else costByDistance = (int) (distanceFromStoreToCustomer * RiderTransportation.WALK.costPerKm);
        return costByDistance;
    }
}
