package org.example;

public class Game {

    // barは0.0 ~ 1.0 まで動く
    private double bar = 0.0;
    private boolean moveLeft = false;
    private double barSpeed = 0.01;
    private final double barMaxLeft = 0.0;
    private final double barMaxRight = 1.0;
    private final double barCenter = (barMaxRight + barMaxLeft) / 2;
    private State state = State.None;
    private long clickLastTime = 0;
    public final double criticalWidth = 0.2;
    public final double hitWidth = 0.5;

    private double leftEdge(double center, double width){
        return center - width/2.0;
    }
    private double rightEdge(double center, double width){
        return center + width/2.0;
    }

    public double getCriticalLeft(){
        return leftEdge(barCenter, criticalWidth);
    }
    public double getCriticalRight(){
        return rightEdge(barCenter, criticalWidth);
    }

    public double getHitLeft(){
        return leftEdge(barCenter, hitWidth);
    }

    public double getHitRight(){
        return rightEdge(barCenter, hitWidth);
    }


    public enum State {
        None,
        Critical,
        Hit,
        Miss
    }

    public Game(){
        this.clickLastTime = System.currentTimeMillis();
    }

    public void click(){
        this.clickLastTime = System.currentTimeMillis();
        if(this.state != State.None){
            return;
        }

        if(isCritical()){
            this.state = State.Critical;
        }else if(isHit()){
            this.state = State.Hit;
        }else{
            this.state = State.Miss;
        }
    }

    public State getState(){
        return this.state;
    }

    private void moveBar(double speed){
        if(moveLeft) {
            bar -= speed;
        }else{
            bar += speed;
        }

        if(bar < barMaxLeft){
            bar = barMaxLeft;
            moveLeft = false;
        }else if(bar > barMaxRight ){
            bar = barMaxRight;
            moveLeft = true;
        }
    }

    public boolean setBarSpeed(double newBarSpeed){
        double maxBarSpeed = 0.3;
        double minBarSpeed = 0.001;
        if( minBarSpeed < newBarSpeed && maxBarSpeed > newBarSpeed){
            barSpeed = newBarSpeed;
            return true;
        }
        return false;
    }

    public void update(){
        moveBar(barSpeed);
        double clickInterval = 100;
        if( System.currentTimeMillis() - clickLastTime > clickInterval ){
            this.state = State.None;
        }
    }

    public double getBarPos(){
        return bar;
    }

    private boolean isCritical(){
        var gap = Math.abs(bar - barCenter);
        return gap < criticalWidth / 2;
    }

    private boolean isHit(){
        var gap = Math.abs(bar - barCenter);
        return gap < hitWidth / 2;
    }
}
