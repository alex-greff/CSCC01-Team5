package com.team5.report.data;

public class XYDataPoint<X, Y> {
    private X xComponent;
    private Y yComponent;

    public XYDataPoint(X xComponent, Y yComponent) {
        this.xComponent = xComponent;
        this.yComponent = yComponent;
    }

    public void setXComponent(X newXComponent) {
        this.xComponent = newXComponent;
    }

    public void setYComponent(Y newYComponent) {
        this.yComponent = newYComponent;
    }

    public X getXComponent() {
        return this.xComponent;
    }

    public Y getYComponent() {
        return this.yComponent;
    }

    
    public boolean equals(XYDataPoint<X, Y> other) {
        return this.xComponent.equals(other.xComponent) && this.yComponent.equals(other.yComponent);
    }

    @Override
    public String toString() {
        return String.format("[%s, %s]", this.xComponent.toString(), this.yComponent.toString());
    }
}